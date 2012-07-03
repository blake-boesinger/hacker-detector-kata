package com.detector

import collection.mutable

class LoginRecorder {

  private var failedLogins: mutable.HashMap[IpAddress, List[Date]] = new mutable.HashMap[IpAddress, List[Date]]()

  def recordLogin( ip : IpAddress, date: Date) = {
    if (ipHasFailedPreviously(ip)) {
      addTheNewFailedLoginToTheExistingOnes(ip, date)
    } else {
      insertFailedLogin(ip, date)
    }
  }


  def ipOfHackerOrNull(ip : IpAddress, date : Date, hackerPolicy : HackerPolicy) : IpAddress = {

    val isHacker  = hackerPolicy.isHacker(numberOfFailedLogins(ip), timeOfFirstFailedLogin(ip), date )

    if (isHacker)  {
       // assume that once we have reported that ip, we do not need to report it again, unless there is a future occurence that violates the policy
      removeRecordsOf(ip)
      return ip
    }
    else
      return null
  }


  private def ipHasFailedPreviously(ip: IpAddress): Boolean = {
    failedLogins.contains(ip)
  }

  private def insertFailedLogin(ip: IpAddress, date: Date)= {
    failedLogins += (ip -> List(date))
  }

  private def addTheNewFailedLoginToTheExistingOnes(ip: IpAddress, date: Date) = {
    failedLogins += (ip -> (failedLogins.get(ip).get ::: List(date)))
  }

  private def removeRecordsOf(ip: IpAddress) {
    failedLogins.remove(ip)
  }

  private def timeOfFirstFailedLogin(ip: IpAddress): Date = {
    failedLogins.get(ip).get.head
  }

  private def numberOfFailedLogins(ip: IpAddress): Int = {
    failedLogins.get(ip).get.size
  }
}
