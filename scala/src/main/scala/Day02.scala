import scala.io.Source

object Day02 extends App {
  val filename = "../input02"
  val s = Source.fromFile(filename)
  val passwords = s.getLines.toSeq

  val parsePattern = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".r

  val Seq(check1:Int,check2:Int) = passwords.foldLeft(Seq(0, 0))((agg, el) => {
    val parsePattern(n1, n2, ruleLetter, password) = el
    Seq(
      agg.head + check1(n1.toInt, n2.toInt, ruleLetter.head, password),
      agg.last + check2(n1.toInt - 1, n2.toInt - 1, ruleLetter.head, password)
    )
  })

  def check1(min: Int, max: Int, ruleLetter: Char, password: String): Int = if (min <= password.toSeq.count(_ == ruleLetter) && password.toSeq.count(_ == ruleLetter) <= max) 1 else 0

  def check2(pos1: Int, pos2: Int, ruleLetter: Char, password: String): Int = {
    val check = Set(password(pos1), password(pos2))
    if (check.size == 2 && check.contains(ruleLetter)) 1 else 0
  }
  println(s"Check 1 count:${check1} \nCheck 2 count:${check2}")

}
