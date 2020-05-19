package com.tom

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object SapientAssignment1 {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .master("local")
      .appName("SapientAssignment")
      .getOrCreate()

    val path = getClass.getResource("/dataset.csv").toString

    val schema = StructType(List(
      StructField("Transaction_date" , StringType),
      StructField("Product",StringType),
      StructField("Price",StringType),
      StructField("Payment_Type",StringType),
      StructField("Name",StringType),
      StructField("City",StringType),
      StructField("State",StringType),
      StructField("Country",StringType),
      StructField("Account_Created",StringType),
      StructField("Last_Login",StringType),
      StructField("Latitude",StringType),
      StructField("Longitude",StringType)))

    //    val data = sparkSession.read.schema(schema).csv("/user/cloudera/data/sales/SalesJan2009.csv")
    //    data.write.saveAsTable("sales_data_2009")
    sparkSession.read.format("csv").load(path).show()
  }

}
