import scala.io.Source
import scala.util.control.Breaks.{break, breakable}

object Day01 extends App {


  val filename = "../input01"
  val numbers = Source.fromFile(filename).getLines.map(_.toInt).toArray.sorted

  def part1() = {
    def bsearch(l:Array[Int],goal:Int,start:Int): Option[Int] = {
      var left = 0
      var right = l.length - 1
      while (right >= left) {
        val mid = left + ((right - left) / 2)
        val cur = l(mid) + start
          if(cur == goal) return Some(l(mid))
          if(cur > goal ) right = mid-1
          else left = mid+1
        }
      None
    }

    var res:Option[Int] = None
    var i = numbers.init
    var l = numbers.last

    while(res.isEmpty) {
    res = bsearch(i,2020,l)
      if(res.isDefined) {
        print(res.get,l,res.get+l,res.get*l)
      } else {
        i =  i.init
        l = i.last
      }
    }
  }

  def part2() ={

  }
  part1()
}
