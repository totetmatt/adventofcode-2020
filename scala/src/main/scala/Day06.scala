import scala.annotation.tailrec
import scala.io.Source

object Day06 extends App {
  val filename = "../input06"

  val part1 = (agg: Set[Char], el: Set[Char]) => agg ++ el
  val part2 = (agg: Set[Char], el: Set[Char]) => agg.intersect(el)
  val s = Source.fromFile(filename)
  val ids =
    s.getLines()
      .mkString("\n")
      .split("\n\n")
      .toSeq
      .map(
        _.split("\n")
          .map(_.toSet)
          .reduce(part2)
          .size
      )
      .sum
  println(ids)
  s.close()

}
