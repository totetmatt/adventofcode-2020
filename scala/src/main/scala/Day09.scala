import scala.collection.immutable
import scala.io.Source


object Day09 extends App {

  val filename = "../input09"
  val s = Source.fromFile(filename)
  val data =s.getLines().map(BigInt.apply).toSeq

  def valid(s:Seq[BigInt]): Boolean = {
    val init = s.init
    val last = s.last
    init.exists(x => init.contains(last - x))
  }
  val q = data.sliding(26).filterNot(valid).toSeq


  val toFind = q.head.last
  println(toFind)
  val idx = data.indexOf(toFind)

  var start=0
  var end=1

  while(start<idx){
    val slice = data.slice(start,end)
    val sum = slice.sum
    if(sum == toFind ) {
      print(slice.max + slice.min)
      start = idx
    }
    else if(sum < toFind) {
      end +=1
    } else {
      start +=1
      end = start +1
    }
  }
  s.close()

}
