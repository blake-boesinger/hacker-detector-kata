package com.sky.detector

import com.sky.detector.{LineParser, InMemoryHackerDetector, HackerDetector}
import org.junit._



class HackerDetectorTest {




  @Test
  def test {


    //TODO make this a field

    //TODO refactor away the duplication and  make the times explicit
    //TODO test with different IPs ( not black box testing )
    //TODO test that successful login returns null
    val detector : HackerDetector = new InMemoryHackerDetector(new LineParser)

    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612949,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612950,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612951,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")

    org.junit.Assert.assertEquals( "80.238.9.179", detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))

  }


  @Test
  def notHackingIfThereAreOnlyFourAttemptsWithinFiveMinutes {

    val detector : HackerDetector = new InMemoryHackerDetector(new LineParser)

    detector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
    detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")

    org.junit.Assert.assertEquals( null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))

  }

  @Test
   def test3 {

     val detector : HackerDetector = new InMemoryHackerDetector(new LineParser)

     detector.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")

     org.junit.Assert.assertEquals(  "80.238.9.179", detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))

   }


  @Test
   def notHackingIfTheFiveAttemptsWereNotWithinFiveMinutes {

     val detector : HackerDetector = new InMemoryHackerDetector(new LineParser)

     detector.parseLine("80.238.9.179,133312952,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")

     org.junit.Assert.assertEquals(  null, detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))

   }

  @Test
   def hackingAgain {

     val detector : HackerDetector = new InMemoryHackerDetector(new LineParser)

     detector.parseLine("80.238.9.179,133312954,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Dave.Branning")
     detector.parseLine("80.238.9.179,133612952,SIGNIN_FAILURE,Dave.Branning")

     org.junit.Assert.assertEquals(  "80.238.9.179", detector.parseLine("80.238.9.179,133612953,SIGNIN_FAILURE,Dave.Branning"))

   }

}
