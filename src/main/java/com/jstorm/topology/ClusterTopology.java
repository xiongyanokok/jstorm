package com.jstorm.topology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jstorm.bolt.DoubleBolt;
import com.jstorm.bolt.OddBolt;
import com.jstorm.bolt.PrintBolt;
import com.jstorm.spout.DbSpout;
import com.jstorm.spout.KafkaSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class ClusterTopology {
	
	private static final Logger logger = LoggerFactory.getLogger(ClusterTopology.class);

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("kafka-spout", new KafkaSpout());
		builder.setSpout("db-spout", new DbSpout());
		builder.setBolt("double-bolt", new DoubleBolt()).shuffleGrouping("kafka-spout").shuffleGrouping("db-spout");
		builder.setBolt("odd-bolt", new OddBolt()).shuffleGrouping("kafka-spout").shuffleGrouping("db-spout");
		builder.setBolt("print-bolt", new PrintBolt()).shuffleGrouping("double-bolt").shuffleGrouping("odd-bolt");
		
		Config config = new Config();
		// 集群模式
		//config.setNumWorkers(3); // 启动3个线程执行
       // StormSubmitter.submitTopology("Toplogy-Start", config, builder.createTopology());
		LocalCluster cluster = new LocalCluster();
	    cluster.submitTopology("Toplogy-Start", config, builder.createTopology());
        logger.info("Jstorm Toplogy Start");
	}
}
