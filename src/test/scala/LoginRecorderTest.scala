package com.detector

import org.junit.Test
import org.junit.Assert._

class LoginRecorderTest {


  var recorder: LoginRecorder = new LoginRecorder()

  var policy: HackerPolicy = new HackerPolicy


  @Test
  def fiveFailedLoginsWithinFiveMinutesReturnsTheIPButTheNextFailedLoginReturnsNullBecauseTheFailureHasAlreadyBeenReported {
    recorder.recordLogin(IpAddress("80.238.9.179"), Date(0))
    recorder.recordLogin(IpAddress("80.238.9.179"), Date(1))
    recorder.recordLogin(IpAddress("80.238.9.179"), Date(2))
    recorder.recordLogin(IpAddress("80.238.9.179"), Date(3))
    recorder.recordLogin(IpAddress("80.238.9.179"), Date(4))
    assertEquals(IpAddress("80.238.9.179"), recorder.ipOfHackerOrNull(IpAddress("80.238.9.179"), Date(4), policy))
    recorder.recordLogin(IpAddress("80.238.9.179"), Date(5))
    assertEquals(null, recorder.ipOfHackerOrNull(IpAddress("80.238.9.179"), Date(5), policy))

  }


}
