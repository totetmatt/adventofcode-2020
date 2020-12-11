import scala.io.Source


object Day11 extends App {
  val filename = "../input11"
  val s = Source.fromFile(filename)
  val originalMapSeat = s.getLines().toArray
  val mapSize = Vec2(originalMapSeat(0).length, originalMapSeat.length)
  val n = neighbourg(mapSize) _

  def printMap(map: Array[String]) = {
    println("█" * (map(0).length + 2))
    map.foreach { x =>
      println(s"|$x|")
    }
    println("█" * (map(0).length + 2))
  }

  def neighbourg(mapSize: Vec2)(current: Vec2) = {
    (for {
      x <- (current.x - 1 to current.x + 1).filter(x => x >= 0 && x < mapSize.x)
      y <- (current.y - 1 to current.y + 1).filter(y => y >= 0 && y < mapSize.y)
    } yield Vec2(x, y)).filterNot(_ == current)
  }

  def raytracing(mapSize: Vec2)(mapSeat: Array[String], current: Vec2) = {
    val max = math.max(mapSize.x, mapSize.y)
    val dir = (for {
      x <- (-1 to 1)
      y <- (-1 to 1)
    } yield Vec2(x, y)).filter(_ != Vec2(0, 0))
    dir.flatMap(d => {
      (1 to max)
        .map(i => d.copy(d.x * i, d.y * i))
        .map(v => Vec2(v.x + current.x, v.y + current.y))
        .takeWhile(v => v.x >= 0 && v.x < mapSize.x && v.y >= 0 && v.y < mapSize.y)
        .find(v => mapSeat(v.y)(v.x) != '.')
    })
  }

  case class Vec2(x: Int, y: Int)

  println(n(Vec2(0, 0)))
  println(s"MapSize ${mapSize}")


  { // part 1
    printMap(originalMapSeat)
    var mapSeat = originalMapSeat
    var changed = true
    while (changed) {
      changed = false
      mapSeat = (0 until mapSize.y).map(y => {
        (0 until mapSize.x).map(x => {
          val curr = mapSeat(y)(x)


          curr match {
            case '.' => '.'
            case seat => {

              val s = n(Vec2(x, y))
                .map(s => mapSeat(s.y)(s.x))
              seat match {
                case 'L' => if (s.count(_ == '#') == 0) {
                  changed = true;
                  '#'
                } else 'L'
                case '#' => if (s.count(_ == '#') >= 4) {
                  changed = true;
                  'L'
                } else '#'
              }
            }
          }

        }).mkString
      }).toArray
      //printMap(mapSeat)
      //println(changed)
    }
    printMap(mapSeat)
    println(mapSeat.map(_.count(_ == '#')).sum)
  }
  // part 2
  // Build neighbourMap
  {
    val nMap = (0 until mapSize.x).flatMap { x =>
      (0 until mapSize.y).map { y =>
        originalMapSeat(y)(x) match {
          case '.' => Vec2(x, y) -> Nil
          case _ => Vec2(x, y) -> raytracing(mapSize)(originalMapSeat, Vec2(x, y))
        }

      }
    }.filter(_._2.nonEmpty).toMap
    var mapSeat = originalMapSeat
    var changed = true
    while (changed) {
      changed = false
      mapSeat = (0 until mapSize.y).map(y => {
        (0 until mapSize.x).map(x => {
          val curr = mapSeat(y)(x)
          curr match {
            case '.' => '.'
            case seat => {
              val s = nMap(Vec2(x, y))
                .map(s => mapSeat(s.y)(s.x))
              seat match {
                case 'L' => if (s.count(_ == '#') == 0) {
                  changed = true;
                  '#'
                } else 'L'
                case '#' => if (s.count(_ == '#') >= 5) {
                  changed = true;
                  'L'
                } else '#'
              }
            }
          }

        }).mkString
      }).toArray
      //printMap(mapSeat)
      //println(changed)
    }
    printMap(mapSeat)
    println(mapSeat.map(_.count(_ == '#')).sum)
  }


  s.close()

}
