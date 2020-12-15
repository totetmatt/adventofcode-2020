import scala.io.Source


object Day15 extends App {

val in = "15,12,0,14,3,1"
  .split(",")
  .map(_.toInt)
  .toSeq

  var last:Int= in.last

  val p:collection.mutable.HashMap[Int,Seq[Int]] =
    collection.mutable.HashMap.from(in.zipWithIndex.map(x => x._1 -> Seq(x._2+1)))
  var currentTurn:Int = in.size+1
  (0 to 30000000).foreach(x=> {

    //println(p)
  if(currentTurn == 30000000) print(s"Turn : $currentTurn > ")

  p.get(last) match {
    case Some(x) if x.size == 1 => {
      val t : Seq[Int] = p(0)
      p.put(0,Seq(t.last,currentTurn))
      last = 0
    }
    case Some(x) => {
      val nb = x.last - x.head

      p.get(nb) match {
        case Some(t) =>    p.put(nb,Seq(t.last,currentTurn))
        case _ => p.put(nb,Seq(currentTurn))
      }
      if(currentTurn == 30000000) println(nb)
        last = nb
    }
  }
  currentTurn +=1
  })



}
