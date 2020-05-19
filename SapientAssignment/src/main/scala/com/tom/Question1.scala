package com.tom

import scala.io.Source

object Question1 {

  def main(args: Array[String]): Unit = {

    val path = getClass.getResourceAsStream("/dataset.csv")
    val lines = Source.fromInputStream(path).getLines()

    val data = lines.map{x =>
      val splitData = x.split(",")
      (splitData(0),splitData(1))
    }.toList

    data.distinct.map(x => println(x._1 , x._2))

    val data1 = data.foldLeft(List[(String,String)]()){
      case (acc, item) if acc.contains(item) => acc
      case (acc, item) => item::acc
    }

    data1.foreach(println(_))

  }

}
