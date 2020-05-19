package com.tom

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, concat, lag, lead, lit, round, to_timestamp, when}
import org.apache.spark.sql.types.LongType

import scala.util.Random

object Question2 {

  def main(args: Array[String]): Unit = {

    val path = args(0)
    val str = Random

    val sparkSession = SparkSession.builder().master("yarn").appName("Question2").getOrCreate()

    val df = sparkSession.read.format("csv").option("header",true).option("inferschema",true).load(path)

    df.write.mode("overwrite").saveAsTable("clickstream")

    val tableData = sparkSession.read.table("clickstream")

    val orderData = Window.partitionBy("userid")orderBy("timestamp")

    val df1 = tableData.withColumn("leadTime" , lead(col("timestamp"),1).over(orderData))
      .withColumn("lagTime" , lag(col("timestamp"),1).over(orderData))
      .withColumn("timediff" ,
        when(col("leadTime").isNull and col("lagTime").isNull , lit(null))
          .when(col("leadTime").isNotNull and col("lagTime").isNull , to_timestamp(col("leadTime")).cast(LongType) - to_timestamp(col("timestamp")).cast(LongType))
          .when(col("leadTime").isNotNull and col("lagTime").isNotNull , to_timestamp(col("timestamp")).cast(LongType) - to_timestamp(col("lagTime")).cast(LongType))
          .when(col("leadTime").isNull and col("lagTime").isNotNull , to_timestamp(col("timestamp")).cast(LongType) - to_timestamp(col("lagTime")).cast(LongType)).otherwise(lit(null)))
      .withColumn("diffsec" , round(col("timediff")/60))

    val df2 = df1.withColumn("indicator" ,
      when(col("diffsec").isNull or  col("diffsec") > 30 , lit(str.nextInt().toString))
        .otherwise(concat(col("userid"),lit("-Active"))))

    df2.write.mode("Overwrite").saveAsTable("clickstream_enrich_data")


  }

}
