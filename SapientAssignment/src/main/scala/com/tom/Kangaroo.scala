package com.tom

object Kangaroo {

  def main(args: Array[String]): Unit = {

    val x1 = 21
    val v1 = 6
    val x2 = 47
    val v2 = 3
    var flag = true
    var output = "NO"
    var tmp1 = x1
    var tmp2 = x2
    if(x1 > x2 || v1 > v2){
      while(flag) {
        tmp1 = tmp1 + v1
        tmp2 = tmp2 + v2
        //println(tmp1 + " " + tmp2)
        if(tmp1 == tmp2){
          println(tmp1 + " " + tmp2)
          output = "YES"
          flag = false
        }else if(tmp1 > tmp2) {
          flag = false
        }
      }
    }

    println(output)
  }

}
