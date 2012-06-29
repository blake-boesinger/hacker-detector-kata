package com.detector

import com.detector.Line
import org.junit._


class LineParserTest {


  @Test
  def splitsLineByCommasToGiveTuple {

    val parser : LineParser = new LineParser

    val parsed = parser.parseLine ("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning")

    org.junit.Assert.assertEquals( Line("80.238.9.179", 133612947, "SIGNIN_SUCCESS" , "Dave.Branning"  ) , parsed)





  }


}
