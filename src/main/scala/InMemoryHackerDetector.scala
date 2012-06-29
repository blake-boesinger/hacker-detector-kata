package com.detector


class InMemoryHackerDetector(lineParser: LineParser, recorder: Recorder, hackerPolicy : HackerPolicy) extends HackerDetector {

  override def parseLine(line: String): String = {

    val parsedLine: Line = lineParser.parseLine(line)

    parsedLine match {
      case Line(ip, date, "SIGNIN_FAILURE", username) => {

        recorder.recordLogin(ip, date)

        val hackerIpOrNull: String = recorder.ipOfHackerOrNull(ip, date, hackerPolicy)
        return hackerIpOrNull


      }


      case _ => null
    }


  }

}
