/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @description Snowflake ID有64bits长，由以下三部分组成：
 *              time—42bits,精确到ms，那就意味着其可以表示长达(2^42-1)/(1000360024*365)=139.5年，
 *              另外使用者可以自己定义一个开始纪元（epoch)，然后用(当前时间-
 *              开始纪元）算出time，这表示在time这个部分在140年的时间里是不会重复的，官方文档在这里写成了41bits，应该是写错了。另外，
 *              这里用time还有一个很重要的原因， 就是可以直接更具time进行排序，对于twitter这种更新频繁的应用，时间排序就显得尤为重要了。 
 *              machine id—10bits,该部分其实由datacenterId和workerId两部分组成，这两部分是在配置文件中指明的。
 *              datacenterId :
 *              1.方便搭建多个生成uid的service，并保证uid不重复，比如在datacenter0将机器0，1，2组成了一个生成uid的service，
 *              而datacenter1此时也需要一个生成uid的service，从本中心获取uid显然是最快最方便的，那么它可以在自己中心搭建，
 *              只要保证datacenterId唯一。
 *              如果没有datacenterId，即用10bits，那么在搭建一个新的service前必须知道目前已经在用的id，否则不能保证生成的id唯一， 比如搭建的两个uid
 *              service中都有machine id为100的机器，如果其server时间相同，那么产生相同id的情况不可避免。 
 *              2.加快server启动速度。启动一台uid server时，会去检查zk同workerId目录中其他机器的情况，如其在zk上注册的id和向它请求返回的work_id是否相同， 
 *              是否处同一个datacenter下，另外还会检查该server的时间与目前已有机器的平均时间误差是否在10s范围内等，这些检查是会耗费一定时间的。
 *              将一个datacenter下的机器数限制在32台(5bits)以内， 在一定程度上也保证了server的启动速度。
 *              workerId是实际server机器的代号，最大到32，同一个datacenter下的workerId是不能重复的。它会被注册到zookeeper上
 *              ，确保workerId未被其他机器占用， 并将host:port值存入，注册成功后就可以对外提供服务了。 sequence id
 *              —12bits,该id可以表示4096个数字，它是在time相同的情况下，递增该值直到为0，即一个循环结束，此时便只能等到下一个ms到来，
 *              一般情况下4096/ms的请求是不太可能出现的，
 * <a>https://github.com/twitter/snowflake</a>
 * @author xuyq - chainisit@126.com
 * @since 1.0 - 2016年10月22日
 */
public class IdWorker {
  protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);

  private long workerId;
  private long datacenterId;
  private long sequence = 0L;

  private long workerIdBits = 5L;
  private long datacenterIdBits = 5L;
  private long maxWorkerId = -1L ^ (-1L << workerIdBits);
  private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
  private long sequenceBits = 12L;

  private long workerIdShift = sequenceBits;
  private long datacenterIdShift = sequenceBits + workerIdBits;
  private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
  private long sequenceMask = -1L ^ (-1L << sequenceBits);

  private long lastTimestamp = -1L;

  public IdWorker(long workerId, long datacenterId) {
    // sanity check for workerId
    if (workerId > maxWorkerId || workerId < 0) {
      throw new IllegalArgumentException(
          String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
    }
    if (datacenterId > maxDatacenterId || datacenterId < 0) {
      throw new IllegalArgumentException(
          String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
    }
    this.workerId = workerId;
    this.datacenterId = datacenterId;
    LOG.info(String.format(
        "worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
        timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
  }

  public synchronized long nextId() {
    long timestamp = timeGen();

    if (timestamp < lastTimestamp) {
      LOG.error(
          String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
      throw new RuntimeException(
          String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
              lastTimestamp - timestamp));
    }

    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      if (sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0L;
    }

    lastTimestamp = timestamp;

    return (timestamp << timestampLeftShift) | (datacenterId << datacenterIdShift)
        | (workerId << workerIdShift) | sequence;
  }

  private long tilNextMillis(final long lastTimestamp) {
    long timestamp = this.timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = this.timeGen();
    }
    return timestamp;
  }

  private long timeGen() {
    return System.currentTimeMillis();
  }

}
