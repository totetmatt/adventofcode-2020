import scala.io.Source

object Day02 extends App {
  val filename = "../input02"
  val s = Source.fromFile(filename)
  val passwords = s.getLines.toSeq

  val parsePattern = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".r
  val test = "13-16 f: rfjmkrqkqrxmfnqj"

  val parsePattern(min,max, checkLetter, password) = test


  println(passwords
    .count{ x=>
      val parsePattern(min,max, ruleLetter, password) = x
      min.toInt <= password.toSeq.count(_ == ruleLetter.head) && password.toSeq.count(_ == ruleLetter.head) <= max.toInt
    })

  println(passwords
    .count{ x=>
      val parsePattern(pos1,pos2, ruleLetter, password) = x
      val check = Set(password(pos1.toInt-1),password(pos2.toInt-1))
      check.size == 2 && check.contains(ruleLetter.head)
    })

  s.close()

}
