package com.detector


class HackerPolicy {

  //     Strategy / Policy pattern
  def isHacker(numberOfFailedLogins: Int, timeOfFirstFailedLogin: Long, timeOfCurrentLogin: Long): Boolean = {

    def timeBetweenFirstAndCurrentLogin: Long = {
      timeOfCurrentLogin - timeOfFirstFailedLogin
    }

    if (numberOfFailedLogins >= 5) {
      val fiveMinutes: Int = 300000
      if (timeBetweenFirstAndCurrentLogin <= fiveMinutes) {
        return true
      }
    }
    return false

  }


}
