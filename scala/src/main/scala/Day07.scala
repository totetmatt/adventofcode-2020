import scala.io.Source

case class Color(qt: Int, name: String, contain: Option[Seq[Color]])

object Day07 extends App {
  val filename = "../input07"
  val s = Source.fromFile(filename)
  var data = "light red bags contain 1 bright white bag, 2 muted yellow bags.\ndark orange bags contain 3 bright white bags, 4 muted yellow bags.\nbright white bags contain 1 shiny gold bag.\nmuted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\nshiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\ndark olive bags contain 3 faded blue bags, 4 dotted black bags.\nvibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\nfaded blue bags contain no other bags.\ndotted black bags contain no other bags."

  def parseData(s: String) = {
    s.split("\n").map { x =>
      val Array(k, v) = x.replace("bags","")
        .replace("bag","")
        .split("contain")

      val vp = if (v.contains("no other")) {
        None
      } else {
        Some(v.split(",").map(y => {

          val el = y.strip()
            .replace("."," ")

            .split(" ")
          Color(el.head.toInt, el.tail.mkString(" "), None)
        }).toSeq)
      }
      Color(1, k.strip(), vp)
    }

  }

 val graph = parseData(s.getLines().mkString("\n"))


  {
    println("part1")
    val start = scala.collection.mutable.Stack.from(Array("shiny gold"))
    val result = scala.collection.mutable.Set.empty[String]
    while (start.nonEmpty) {
      val t = start.pop()

      val r = graph.filter(_.contain.exists(_.exists(_.name == t)))
      result.addAll(r.map(_.name))
      start.addAll(r.map(_.name))
    }
    println(result.size)
  }

    {
      def dive(c:Color): Color = {
        val r = graph.find(_.name == c.name).get

        c.copy(contain = r.contain.map(_.map(dive)))
      }
      def sum(c:Color): Int = {
        println(c)
          if(c.contain.isEmpty) c.qt
          else c.qt + c.qt*c.contain.map(_.map(sum).sum).get
      }
      println("part2")
      val c = dive(Color(1,"shiny gold",None))
      println(c)
      println(sum(c)-1)
  }
  s.close()

}
