import scala.collection.parallel.CollectionConverters._
import scala.io.Source

object Day04 extends App {
  val filename = "../input04"

  val byr = (value:String) => {
    1920 <= value.toInt && value.toInt <= 2002
  }

  val iyr = (value:String) => {
    2010 <= value.toInt && value.toInt <= 2020
  }

  val eyr = (value:String) => {
    2020 <= value.toInt && value.toInt <= 2030
  }

  val hgt = (value:String) => {
    value match {
      case x if x.endsWith("in") => {
        val v = x.init.init.toInt
        59<=v && v <= 76
      }
      case x if x.endsWith("cm") =>{
        val v = x.init.init.toInt
        150 <=v && v <= 193
      }
      case _=> false
    }
  }

  val hcl = (value:String) => value.matches("#([0-9]|[a-f]){6}")

  val ecl = (value:String) => Set("amb","blu","brn","gry","grn","hzl","oth").contains(value)

  val pid = (value:String) => value.matches("[0-9]{9}")

  val cid = (value:String) => true

  val baseValidField = Map("byr" -> byr,"iyr" -> iyr,"eyr" -> eyr,"hgt" ->hgt,"hcl" -> hcl,"ecl" -> ecl,"pid" -> pid)
  val baseValidField2 = baseValidField + ("cid" -> cid)
  val validField = Seq(
    baseValidField,
    baseValidField2)


  val s = Source.fromFile(filename)
  val map = s.
    getLines()
    .mkString(System.lineSeparator)
    .split(System.lineSeparator*2)
    .toSeq
    .count {
      id =>
        val s = id.replace(System.lineSeparator, " ")
        .split(" ")
        .toSeq
        .map(_.split(":"))
        .toSet
        validField.map(_.keys.toSet).contains(s.map(_.head)) && s.forall {
            case Array(key, value) =>
              baseValidField2(key)(value)
          }

    }
  println(map)
  s.close()
}
