import scala.io.Source

object Day02 extends App {
  val filename = "../input02"
  val s = Source.fromFile(filename)
  val passwords = s.getLines.toSeq

  println(passwords
    .count{ x=>
      val Array(rule,password)= x.split(":")
      val Array(ruleCount,ruleLetter) = rule.split(" ")
      val Array(min,max) = ruleCount.split("-").map(_.toInt)

      min <= password.toSeq.count(_ == ruleLetter.head) && password.toSeq.count(_ == ruleLetter.head) <= max
    })

  println(passwords
    .count{ x=>
      val Array(rule,password)= x.split(":")
      val Array(ruleCount,ruleLetter) = rule.split(" ")
      val Array(pos1,pos2) = ruleCount.split("-").map(_.toInt)
      val check = Set(password(pos1),password(pos2))
      check.size == 2 && check.contains(ruleLetter.head)
    })

  s.close()

}
