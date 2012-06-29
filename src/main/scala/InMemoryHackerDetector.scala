package com.detector


class InMemoryHackerDetector(lineParser: LineParser, recorder: Recorder) extends HackerDetector {


  def isHackerPolicy(numberOfFailedLogins: Int, timeOfFirstFailedLogin: Long, timeOfCurrentLogin : Long): Boolean = {
          if (numberOfFailedLogins >= 5) {
            if (timeOfCurrentLogin - timeOfFirstFailedLogin <= 300000) {
              return true
            }
          }
          return false

        }


  override def parseLine(line: String): String = {

    val parsedLine: Line = lineParser.parseLine(line)

    parsedLine match {
      case Line(ip, date, "SIGNIN_FAILURE", username) => {


        recorder.recordLogin(ip, date)







        val hackerIpOrNull: String = recorder.ipOfHackerOrNull(ip, date, isHackerPolicy)
        return hackerIpOrNull


      }


      case _ => null
    }


  }

}
