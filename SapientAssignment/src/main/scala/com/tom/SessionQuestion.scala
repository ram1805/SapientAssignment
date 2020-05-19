package com.tom

import com.tom.CommonUtils._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.functions._

object SessionQuestion {

  def main(args: Array[String]): Unit = {

    Logger.getLogger(getClass).setLevel(Level.ERROR)
    val spark = createSparkSession()
    val path = getClass.getResource("/sessiondata.csv").toString
    val data = spark.read.csv(path)
    val header = data.first()
    import spark.implicits._
    val dataWithoutHeader = data.filter(x => x != header).rdd
    val header1 = header.toString().replace("[" , "").replace("]" , "").split(",").toList
    val header2 = StructType(header1.map(x => StructField(x,StringType,false)))
    val data1 = spark.createDataFrame(dataWithoutHeader,header2)
    data1.show()

    val window = Window.partitionBy("userid").orderBy("timestamp")

    data1
      .withColumn("lead" , lead(col("timestamp"),1).over(window))
      .withColumn("timediffInSec" , to_timestamp(col("lead")).cast(LongType) - to_timestamp(col("timestamp")).cast(LongType) )
      .withColumn("timediffInMinutes" , round(col("timediffInSec")/60) )
      .withColumn("indicator" ,
        when(col("timediffInMinutes") > 30 , lit("Inactive"))
          .otherwise(lit("Active"))).show()
  }

}
