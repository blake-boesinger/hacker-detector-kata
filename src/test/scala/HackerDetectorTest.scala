package com.detector

import org.junit._



class HackerDetectorTest {

  var detector : HackerDetector= null

  @Before
  def setup = {
    detector =    new InMemoryHackerDetector(new LineParser, new Recorder, new HackerPolicy)
  }

  @Test
  def successfulLoginReturnsNull {
    org.junit.Assert.assertEquals(null, detector.parseLine("80.238.9.179,133612952,SIGNIN_SUCCESS,Dave.Branning"))

  }


  @Test
  def manySuccessfulLoginsWithinLessThanFiveMinutesReturnsNull {
    org.junit.Assert.assertEquals(null, detector.parseLine("80.238.9.179,133612952,SIGNIN_SUCCESS,Dave.Branning"))
    org.junit.Assert.assertEquals(null, detector.parseLine("80.238.9.179,133612953,SIGNIN_SUCCESS,Dave.Branning"))
    org.junit.Assert.assertEquals(null, detector.parseLine("80.238.9.179,133612954,SIGNIN_SUCCESS,Dave.Branning"))
    org.junit.Assert.assertEquals(null, detector.parseLine("80.238.9.179,133612955,SIGNIN_SUCCESS,Dave.Branning"))
    org.junit.Assert.assertEquals(null, detector.parseLine("80.238.9.179,133612956,SIGNIN_SUCCESS,Dave.Branning"))
    org.junit.Assert.assertEquals(null, detector.parseLine("80.238.9.179,133612957,SIGNIN_SUCCESS,Dave.Branning"))
  }


  @Test
  def fiveFailedLoginsWithinFiveMinutesReturnsTheIPButTheNextFailedLoginReturnsNullBecauseTheFailureHasAlreadyBeenReported {
    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612949,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612950,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612951,SIGNIN_FAILURE,Dave.Branning")
    org.junit.Assert.assertEquals( "80.238.9.179", detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning"))
    org.junit.Assert.assertEquals( null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))


  }


  @Test
  def notHackingIfThereAreOnlyFourAttemptsWithinFiveMinutes {
    detector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")
    org.junit.Assert.assertEquals( null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))
  }



  @Test
   def notHackingIfTheFiveAttemptsWereNotWithinFiveMinutes {
     detector.parseLine("80.238.9.179,133312952,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")
     org.junit.Assert.assertEquals(  null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))

   }



}
