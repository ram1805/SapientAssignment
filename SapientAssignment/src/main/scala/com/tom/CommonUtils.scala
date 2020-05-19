package com.tom

import org.apache.spark.sql.SparkSession

object CommonUtils {

  def createSparkSession(): SparkSession = {
    val spark = SparkSession.builder().appName("LocalRun").master("local").getOrCreate()
    spark
  }

}
