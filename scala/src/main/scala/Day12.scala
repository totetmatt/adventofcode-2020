import org.graalvm.compiler.loop.InductionVariable.Direction

import scala.io.Source

object Day12 extends App {
  trait Nav
  trait Direction
  trait Maneuver
  case class East(v: Int) extends Nav with Direction
  case class West(v: Int) extends Nav with Direction
  case class North(v: Int) extends Nav with Direction
  case class South(v: Int) extends Nav with Direction

  case class Forward(v: Int) extends Nav with Maneuver
  case class Left(v: Int) extends Nav with Maneuver
  case class Right(v: Int) extends Nav with Maneuver
  // lat: NS
  // long:/EW
  trait Mode
  case class Part1Mode() extends Mode
  case class Part2Mode(waypoint: Navcom = Navcom(0, 1, 10)) extends Mode

  case class Navcom(
      lookAt: Int,
      lat: Int,
      long: Int,
      mode: Mode = Part1Mode()
  ) {
    // Clockwise     0         90       180      270
    val _r = Array(East(1), South(1), West(1), North(1))
    private def _rot(i: Int) = {
      mode match {
        case _: Part1Mode => this.copy(lookAt = (lookAt + i) % 4)
        case Part2Mode(wp) =>
          if (i == 0) {
            this.copy()
          } else if (i == 3) {
            this.copy(mode = Part2Mode(wp.copy(lat = wp.long, long = -wp.lat)))
          } else if (i == 2) {
            this.copy(mode = Part2Mode(wp.copy(lat = -wp.lat, long = -wp.long)))
          } else {
            this.copy(mode = Part2Mode(wp.copy(lat = -wp.long, long = wp.lat)))
          }
      }

    }

    private def _dir(d: Direction): Navcom = {
      mode match {
        case _: Part1Mode =>
          d match {
            case West(v)  => this.copy(long = long - v)
            case East(v)  => this.copy(long = long + v)
            case North(v) => this.copy(lat = lat + v)
            case South(v) => this.copy(lat = lat - v)
          }
        case Part2Mode(wp) =>
          d match {
            case j: West  => this.copy(mode = Part2Mode(wp.update(j)))
            case j: East  => this.copy(mode = Part2Mode(wp.update(j)))
            case j: North => this.copy(mode = Part2Mode(wp.update(j)))
            case j: South => this.copy(mode = Part2Mode(wp.update(j)))
          }
      }

    }
    private def _for(i: Int): Navcom = {
      mode match {
        case _: Part1Mode =>
          _r(lookAt) match {
            case j: West  => _dir(j.copy(i))
            case j: East  => _dir(j.copy(i))
            case j: North => _dir(j.copy(i))
            case j: South => _dir(j.copy(i))
          }
        case Part2Mode(wp) =>
          this.copy(lat = lat + wp.lat * i, long = long + wp.long * i)
      }

    }
    def update(inst: Nav): Navcom = {
      inst match {
        case v: Direction => _dir(v)
        case Forward(v)   => _for(v)
        case Left(v)      => _rot(4 - (v / 90))
        case Right(v)     => _rot(v / 90)
      }

    }

    override def toString: String = {
      val sLat = if (lat <= 0) South(math.abs(lat)) else North(lat)
      val sLong = if (long <= 0) West(math.abs(long)) else East(long)
      val wp = mode match {
        case Part2Mode(wp) => s" >> Waypoint (($wp))"
        case _             => ""
      }
      s"$sLong $sLat  Facing: ${_r(lookAt)} Dist: $dist $wp"
    }
    def dist(): Int = {
      math.abs(lat) + math.abs(long)
    }
  }

  val filename = "../input12"
  val s = Source.fromFile(filename)

  val navdir = s
    .getLines()
    .map({ inst =>
      val i = inst.head
      val v = inst.tail.toInt
      i match {
        case 'N' => North(v)
        case 'S' => South(v)
        case 'E' => East(v)
        case 'W' => West(v)
        case 'L' => Left(v)
        case 'R' => Right(v)
        case 'F' => Forward(v)
      }
    })
    .toSeq
  val q = "F10\nN3\nF7\nR90\nF11"
    .split("\n")
    .map({ inst =>
      val i = inst.head
      val v = inst.tail.toInt
      i match {
        case 'N' => North(v)
        case 'S' => South(v)
        case 'E' => East(v)
        case 'W' => West(v)
        case 'L' => Left(v)
        case 'R' => Right(v)
        case 'F' => Forward(v)
      }
    })
    .toSeq
  val navcom = Navcom(0, 0, 0)
  val r = navdir.foldLeft(navcom)((agg, el) => agg.update(el))
  println(r)
  println("=== Part2 ===")

  val r2 = navdir
    .foldLeft(Navcom(0, 0, 0, Part2Mode()))((agg, el) => {
      agg.update(el)

    })
  s.close()

}
