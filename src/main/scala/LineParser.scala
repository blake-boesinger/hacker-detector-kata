

package com.sky.detector

class LineParser {




  def parseLine( line : String) : Line  = {
    val Array(one, two, three, four) = line.split(",")

    val tuple = (one, two, three, four)

    tuple match {
     case Tuple4(a:String, b:String, c:String,d:String) => Line(a,b.toLong,c,d)
    }



  }

}
