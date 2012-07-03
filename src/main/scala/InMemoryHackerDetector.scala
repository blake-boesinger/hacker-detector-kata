package com.detector


class InMemoryHackerDetector(lineParser: LineParser, recorder: LoginRecorder, hackerPolicy : HackerPolicy) extends HackerDetector {


  override def parseLine(line: String): String = {

    val parsedLine: Line = lineParser.parseLine(line)

    parsedLine match {
      case Line(ip, date, Action("SIGNIN_FAILURE"), username) => {

        recorder.recordLogin(ip, date)

        val ipOfHackerOrNull: IpAddress = recorder.ipOfHackerOrNull(ip, date, hackerPolicy)

        if ( ipOfHackerOrNull == null) {
          return null
        }

        return ipOfHackerOrNull.address


      }


      case _ => null
    }


  }

}
