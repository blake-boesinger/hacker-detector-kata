package com.detector


class HackerPolicy {

  //     Strategy / Policy pattern
  def isHacker(numberOfFailedLogins: Int, dateOfFirstFailedLogin: Date, dateOfCurrentLogin: Date): Boolean = {

     def timeBetweenFirstAndCurrentLogin: Long = {
      dateOfCurrentLogin.date - dateOfFirstFailedLogin.date
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
