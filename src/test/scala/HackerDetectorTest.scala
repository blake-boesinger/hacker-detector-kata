package com.detector

import org.junit._
import org.junit.Assert._


class HackerDetectorTest {

  var detector: HackerDetector = _

  @Before
  def setup = {
    detector = new InMemoryHackerDetector(new LineParser, new LoginRecorder, new HackerPolicy)
  }

  @Test
  def successfulLoginReturnsNull {
    assertEquals(null, detector.parseLine("80.238.9.179,133612952,SIGNIN_SUCCESS,Dave.Branning"))

  }


  @Test
  def manySuccessfulLoginsWithinLessThanFiveMinutesReturnsNull {
    assertEquals(null, detector.parseLine("80.238.9.179,133612952,SIGNIN_SUCCESS,Dave.Branning"))
    assertEquals(null, detector.parseLine("80.238.9.179,133612953,SIGNIN_SUCCESS,Dave.Branning"))
    assertEquals(null, detector.parseLine("80.238.9.179,133612954,SIGNIN_SUCCESS,Dave.Branning"))
    assertEquals(null, detector.parseLine("80.238.9.179,133612955,SIGNIN_SUCCESS,Dave.Branning"))
    assertEquals(null, detector.parseLine("80.238.9.179,133612956,SIGNIN_SUCCESS,Dave.Branning"))
    assertEquals(null, detector.parseLine("80.238.9.179,133612957,SIGNIN_SUCCESS,Dave.Branning"))
  }


  @Test
  def fiveFailedLoginsWithinFiveMinutesReturnsTheIPButTheNextFailedLoginReturnsNullBecauseTheFailureHasAlreadyBeenReported {
    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612949,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612950,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612951,SIGNIN_FAILURE,Dave.Branning")
    assertEquals("80.238.9.179", detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning"))
    assertEquals(null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))


  }


  @Test
  def notHackingIfThereAreOnlyFourAttemptsWithinFiveMinutes {
    detector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")
    assertEquals(null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))
  }


  @Test
  def notHackingIfTheFiveAttemptsWereNotWithinFiveMinutes {
    detector.parseLine("80.238.9.179,133312952,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")
    assertEquals(null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))

  }


}
