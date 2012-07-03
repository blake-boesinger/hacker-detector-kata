package com.detector

import org.junit._
import org.junit.Assert._

class HackerPolicyTest {


  val policy: HackerPolicy = new HackerPolicy

  val sixMinutes = 360000


  @Test
  def oneFailedLoginWithinLessThanFiveMinutesIsNotHacking {
    assertFalse(policy.isHacker(1, Date(0), Date(1)))
  }


  @Test
  def fiveFailedLoginsWithinLessThanFiveMinutesIsHacking {
    assertTrue(policy.isHacker(5, Date(0), Date(1)))
  }

  @Test
  def oneFailedLoginInGreaterThanFiveMinutesIsNotHacking {
    assertFalse(policy.isHacker(1, Date(0), Date(sixMinutes)))
  }

  @Test
  def fiveFailedLoginsInGreaterThanFiveMinutesIsNotHacking {
    assertFalse(policy.isHacker(5, Date(0), Date(sixMinutes)))
  }
}
