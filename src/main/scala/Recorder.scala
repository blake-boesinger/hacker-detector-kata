package com.detector

import collection.mutable

class Recorder {

  private var failedLogins: mutable.HashMap[String, List[Long]] = new mutable.HashMap[String, List[Long]]()

  def recordLogin( ip : String, date: Long) = {
    if (ipHasFailedPreviously(ip)) {
      addTheNewFailedLoginToTheExistingOnes(ip, date)
    } else {
      insertFailedLogin(ip, date)
    }
  }


  private def ipHasFailedPreviously(ip: String): Boolean = {
    failedLogins.contains(ip)
  }

  private def insertFailedLogin(ip: String, date: Long)= {
    failedLogins += (ip -> List(date))
  }

  private def addTheNewFailedLoginToTheExistingOnes(ip: String, date: Long) = {
    failedLogins += (ip -> (failedLogins.get(ip).get ::: List(date)))
  }

  def ipOfHackerOrNull(ip : String, date : Long, hackerPolicy : HackerPolicy) : String = {

    val isHacker  = hackerPolicy.isHacker(numberOfFailedLogins(ip), timeOfFirstFailedLogin(ip), date )

    if (isHacker)  {
       // assume that once we have reported that ip, we do not need to report it again, unless there is a future occurence that violates the policy
      removeRecordsOf(ip)
      return ip
    }
    else
      return null
  }


  private def removeRecordsOf(ip: String) {
    failedLogins.remove(ip)
  }

  private def timeOfFirstFailedLogin(ip: String): Long = {
    failedLogins.get(ip).get.head
  }

  private def numberOfFailedLogins(ip: String): Int = {
    failedLogins.get(ip).get.size
  }
}
