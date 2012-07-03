package com.detector


class InMemoryHackerDetector(lineParser: LineParser, recorder: LoginRecorder, hackerPolicy : HackerPolicy) extends HackerDetector {


  override def parseLine(line: String): String = {

    val parsedLine: Line = lineParser.parseLine(line)

    parsedLine match {
      case Line(ip, date, Action("SIGNIN_FAILURE"), username) => {

        recorder.recordLogin(ip, date)

        val hackerIpOrNull: IpAddress = recorder.ipOfHackerOrNull(ip, date, hackerPolicy)

        if ( hackerIpOrNull == null) {
          return null
        }

        return hackerIpOrNull.address


      }


      case _ => null
    }


  }

}
