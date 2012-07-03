

package com.detector

class LineParser {




  def parseLine( line : String) : Line  = {
    val Array(ip, time, action, username) = line.split(",")

    val tuple = (ip, time, action, username)

    tuple match {
     case Tuple4(ip:String, date:String, action:String,username:String) => Line(new IpAddress(ip), new Date(date.toLong),new Action(action),new Username(username))
    }



  }

}
