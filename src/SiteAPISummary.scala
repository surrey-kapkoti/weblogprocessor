package com.oreilly.learningsparkexamples

import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SchemaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark._
import org.apache.spark.SparkContext._
object SiteAPISummary {

  def main(args: Array[String]){
    val conf = new SparkConf().setAppName("FileProcessor")
    val sc = new SparkContext(conf)

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

    val path = "/home/hduser/Downloads/1.json"
    val webLog = sqlContext.jsonFile(path)

    webLog.printSchema()

    webLog.registerTempTable("webLogTable")

    val teenagers = sqlContext.sql("SELECT backend, http_request, avg(session_duration)/1000000, count(http_request), sum(bytes_read ), sum(bytes_uploaded ) FROM webLogTable where backend is not null and http_request like 'POST%'  group by backend, http_request order by backend, count(http_request) desc")
    val webDataRdd = teenagers.rdd
    val outputPath = "output"
    //println( "count = ........" + b.count )
    teenagers.collect().foreach( println ) 
    //b.saveAsTextFile(outputPath)
  }
}
