package com.detector


class HackerPolicy {

  //     Strategy / Policy pattern
  def isHacker(numberOfFailedLogins: Int, timeOfFirstFailedLogin: Long, timeOfCurrentLogin: Long): Boolean = {

    def timeBetweenFirstAndCurrentLogin: Long = {
      timeOfCurrentLogin - timeOfFirstFailedLogin
    }

    if (numberOfFailedLogins >= 5) {
      val timeInMillis: Int = 5 * 60 *1000
      if (timeBetweenFirstAndCurrentLogin <= timeInMillis) {
        return true
      }
    }
    return false

  }


}
