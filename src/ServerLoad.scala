package com.oreilly.learningsparkexamples

import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.sql.SQLContext;
//import org.apache.spark.sql.SchemaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark._
import org.apache.spark.SparkContext._
object ServerLoad {

  def main(args: Array[String]){
    val conf = new SparkConf().setAppName("FileProcessor")
    val sc = new SparkContext(conf)

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

    val path = "/home/hduser/Downloads/logs/"
    val webLog = sqlContext.jsonFile(path)

    webLog.printSchema()

    webLog.registerTempTable("webLogTable")

    val teenagers = sqlContext.sql("SELECT upstream_addr, backend_name,count(http_request) FROM webLogTable where backend_name is not null group by upstream_addr, backend_name order by upstream_addr, count(http_request)desc")
    val webDataRdd = teenagers.rdd
    val outputPath = "output"
    //println( "count = ........" + b.count )
    teenagers.collect().foreach( println ) 
    //b.saveAsTextFile(outputPath)
  }
}
