package com.detector

import collection.mutable

class Recorder {

  private var failedLogins: mutable.HashMap[String, List[Long]] = new mutable.HashMap[String, List[Long]]()

  def recordLogin( ip : String, date: Long) = {
    if (ipHasAlreadyFailed(ip)) {
      addTheNewFailedLoginToTheExistingOnes(ip, date)
    } else {
      insertFailedLogin(ip, date)
    }
  }


  private def ipHasAlreadyFailed(ip: String): Boolean = {
    failedLogins.contains(ip)
  }

  private def insertFailedLogin(ip: String, date: Long)= {
    failedLogins += (ip -> List(date))
  }

  private def addTheNewFailedLoginToTheExistingOnes(ip: String, date: Long) = {
    failedLogins += (ip -> (failedLogins.get(ip).get ::: List(date)))
  }

  def ipOfHackerOrNull(ip : String, date : Long, hackerPolicy : HackerPolicy) : String = {

    val numberOfFailedLogins: Int = failedLogins.get(ip).get.size
    val timeOfFirstFailedLogin: Long = failedLogins.get(ip).get.head

    val isHacker  = hackerPolicy.isHacker(numberOfFailedLogins  , timeOfFirstFailedLogin, date )

    if (isHacker)  {
      failedLogins.remove(ip) // assume that once we have reported that ip, we do not need to report it again, unless there is a future occurence that violates the policy
      return ip
    }
    else
      return null
  }
}
