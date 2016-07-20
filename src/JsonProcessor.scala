package com.oreilly.learningsparkexamples

import org.apache.spark._
import play.api.libs.json._
import play.api.libs.functional.syntax._


object JsonProcessor {
   //case class Person(name: String, lovesPandas: Boolean)
  case class Weblogs(Type:String, timestamp: Long, http_status: Int, http_request: String, remote_addr: String, bytes_read: Long, upstream_addr: String, backend_name: String, retries: Int, bytes_uploaded: Long, upstream_connect_time: Int, session_duration: Int)

  implicit val personReads = Json.format[Weblogs]

  def main(args: Array[String]) {
    if (args.length < 3) {
      println("Usage: [sparkmaster] [inputfile] [outputfile]")
      exit(1)
      }

    val master = args(0)
    val inputFile = args(1)
    val outputFile = args(2)
    val sc = new SparkContext(master, "JsonProcessor", System.getenv("SPARK_HOME"))
    val input = sc.textFile(inputFile)
    val parsed = input.map(Json.parse(_))
    println("parsed data......................" + input.first)
    // We use asOpt combined with flatMap so that if it fails to parse we
    // get back a None and the flatMap essentially skips the result.
    //val result = parsed.flatMap(record => personReads.reads(record).asOpt)
    //println("......................" + result.first )
    //result.filter(_.backend_name == "API_JuicerApi").map(Json.toJson(_)).saveAsTextFile(outputFile)
    //result.map(Json.toJson(_)).saveAsTextFile(outputFile)
    } 

}
