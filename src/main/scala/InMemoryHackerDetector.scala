package com.sky.detector

import collection.mutable



class InMemoryHackerDetector(lineParser : LineParser) extends HackerDetector {


  var failedLogins : mutable.HashMap[String, List[Long]] = new mutable.HashMap[String,List[Long]]()

  override def parseLine(line : String) : String = {

            val  parsedLine : Line =     lineParser.parseLine(line)

    parsedLine match {
      case Line(ip, date , "SIGNIN_FAILURE", username) => {
        if ( failedLogins.contains(ip))    {
          val existing : List[Long] =    failedLogins.get(ip).get
          val newOne = List(date)
            failedLogins += (ip -> (existing ::: newOne)   )

        } else {
          failedLogins += (ip -> List(date))

        }



        if ( failedLogins.get(ip).get.size >= 5) {
         val firstLoginAttempt  = failedLogins.get(ip).get.head
          if ( date - firstLoginAttempt <= 300000) {
                return ip
          }
        }
        // I usually avoid returning nulls, favouring "Tell, Don't Ask", "Null Object Pattern" or an Option or Maybe
        return null


      }
      case _ => null
    }







  }

}
