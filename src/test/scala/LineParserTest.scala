package com.detector

import org.junit._
import org.junit.Assert._


class LineParserTest {


  @Test
  def splitsLineByCommasToGiveTuple {

    val parser: LineParser = new LineParser

    val parsed = parser.parseLine("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning")

    assertEquals(Line(IpAddress("80.238.9.179"), Date(133612947), Action("SIGNIN_SUCCESS"), Username("Dave.Branning")), parsed)


  }


}
