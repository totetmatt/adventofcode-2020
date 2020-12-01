import scala.io.Source
import scala.util.control.Breaks.{break, breakable}

object Day01 extends App {


  val filename = "../input01"
  val numbers = Source.fromFile(filename).getLines.map(_.toInt).toArray.sorted

  def bsearch(l:Array[Int], goal:Int, start:Int): Option[Int] = {
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

  def part1() = {

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

    var ptrs = Array(0,1,2)

    while(true){
      if(ptrs.map(numbers).sum == 2020) {
        ptrs.map( x =>x -> numbers(x)).foreach(println)
        println(ptrs.map(numbers).product)
        println(numbers.slice(0, 12).mkString("Array(", ", ", ")"))
        System.exit(0)
      } else {
        ptrs(2)+=1
        if(ptrs(2)>= numbers.size) {
          ptrs(1) +=1
          if(ptrs(1)>= numbers.size) {
            ptrs(0) +=1
            ptrs(1) = ptrs(0)+1
          }
          ptrs(2) = ptrs(1)
        }
      }
    }

  }

  part2()
}
