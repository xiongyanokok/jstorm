package com.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * kafka队列回调
 * 
 * @author xiongyan
 * @date 2016年10月13日 下午6:07:44
 */
public interface KafkaCallback {

	/**
	 * 接收消息
	 * 
	 * @param record
	 */
	public void receive(ConsumerRecord<String, String> record);
}
