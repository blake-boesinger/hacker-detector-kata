package com.detector


class InMemoryHackerDetector(lineParser: LineParser, recorder: LoginRecorder, hackerPolicy : HackerPolicy) extends HackerDetector {

  //TODO use domain objects rather than strings




  override def parseLine(line: String): String = {

    val parsedLine: Line = lineParser.parseLine(line)

    parsedLine match {
      case Line(ip, date, "SIGNIN_FAILURE", username) => {

        recorder.recordLogin(ip, date)

        val hackerIpOrNull: String = recorder.ipOfHackerOrNull(ip, date, hackerPolicy)
        return hackerIpOrNull


      }


      case _ => null

        // instead of returning null, I usually prefer to use "Tell, don't ask", Null Object Pattern, or using an Option / Maybe
    }


  }

}
