import scala.collection.Searching.{Found, InsertionPoint}
import scala.io.Source
import scala.util.control.Breaks.{break, breakable}

object Day01 extends App {

  val filename = "../input01"
  val numbers = Source.fromFile(filename).getLines.map(_.toInt).toArray.sorted

  def part1() = {

    val y = numbers.reverse
      .map(x =>
        numbers.toIndexedSeq
          .search(2020 - x)
      )
      .collectFirst {
        case Found(y) => numbers(y)
      }
      .get

    println(y, 2020 - y, y * (2020 - y))

  }

  def part2() = {
    numbers.reverse
      .map(x =>
        numbers.toIndexedSeq
          .search(2020 - x)
      )
      .collectFirst {
        case Found(y) => numbers(y)
      }
      .get
  }

  part2()
}
