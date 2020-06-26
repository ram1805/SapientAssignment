package com.tom
import com.tom.CommonUtils._
import org.apache.log4j.{Level, Logger}

import scala.io.Source

object AccumulatorExample {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = createSparkSession()
    val path = Source.getClass.getResource("/sessiondata.csv").toString
    val data = spark.read.format("csv").option("header" , "true").option("delimiter" , ",").load(path)
    data.show()

    val rdd1 = data.rdd
    var output = 0
    rdd1.foreach { line =>
      if (line.toString().contains("u1")) {
        output + 1
        println(output)
      }
    }
    println(output)

  }

}
