package com.detector

import collection.mutable

class Recorder {


  var failedLogins: mutable.HashMap[String, List[Long]] = new mutable.HashMap[String, List[Long]]()

  def recordLogin( ip : String, date: Long) = {
    if (failedLogins.contains(ip)) {
      addTheNewFailedLogin(ip, date)

    } else {
      failedLogins += (ip -> List(date))

    }


  }


  def addTheNewFailedLogin(ip: String, date: Long) = {
    failedLogins += (ip -> (failedLogins.get(ip).get ::: List(date)))
  }

  def ipOfHackerOrNull(ip : String, date : Long, hackerPolicy : HackerPolicy) : String = {

    val numberOfFailedLogins: Int = failedLogins.get(ip).get.size
    val timeOfFirstFailedLogin: Long = failedLogins.get(ip).get.head

    val isHacker  = hackerPolicy.isHacker(numberOfFailedLogins  , timeOfFirstFailedLogin, date )

    if (isHacker)
      return ip
    else
      return null



  }

}
