package com.tom

case class person(name:String,age:Int){
  def apply(): Unit={
    println(name + " " + age)
  }
}
object CaseClassExample {
  def main(args: Array[String]): Unit = {

    person("ram" , 23)

  }

}
