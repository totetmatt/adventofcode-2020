import scala.io.Source

/**
 *
 * val identification = validTickets.flatMap(ticket => {

    ticket.map(id=>{
      labelRanges.filter{
        case (k,v) => {
          !v.forall(_(id)==false)
        }
      }.keys.toSeq
    }).zipWithIndex

  }).groupBy(_._2).map{ case (k,v) => {
    k -> v.map(_._1).reduce( (agg,el) => agg++el).groupBy(x=>x).view.mapValues(_.size).toMap
  }}
 * */
object Day16 extends App {

  case class Range(min:Int,max:Int){
    def apply(i:Int) : Boolean ={
      min <= i && i <= max
    }
  }
  val filename = "../input16"
  val s = Source.fromFile(filename)

  val Array(ranges,myTicket,nearbyTicket) = s.mkString.split("\\n\\n")
  val allRanges = ranges.split("\\n").flatMap(x=>{
    x.split(":").last.strip.split(" or ").map(y=>{
     val Array(min,max)  = y.split("-")
      Range(min.toInt,max.toInt)
    }
  )


  }).toSeq
  println(allRanges)
  println(
    nearbyTicket
      .split("\\n")
      .tail
      .flatMap(_
        .split(",")
        .map(_.toInt)
        .filter(x=>allRanges.forall(_(x) == false))
        .toSeq
      )
    .toSeq.sum)

  val validTickets =
    nearbyTicket
      .split("\\n")
      .tail
      .map(_
        .split(",")
        .map(_.toInt)
        .toSeq
      )
      .filter( x=> x.forall( y=>
        !allRanges.forall(_(y) == false)
      )
      )
      .toSeq

  val labelRanges = ranges.split("\\n").map(x=>{
    val Array(label,data)=  x.split(":")
    label.strip() ->
      data.strip.split(" or ").map(y=>{
      val Array(min,max)  = y.split("-")
      Range(min.toInt,max.toInt)
    }
    ).toSeq
  }).toMap


  var identification = validTickets.flatMap(ticket => {

    ticket.map(id=>{
      labelRanges.filter{
        case (k,v) => {
          !v.forall(_(id)==false)
        }
      }.keys.toSeq
    }).zipWithIndex

  }).groupBy(_._2).map{ case (k,v) => {
    k -> v.map(_._1).reduce( (agg,el) => agg++el).groupBy(x=>x).view.mapValues(_.size).filterNot(_._2<190).toMap
  }}
  //println(validTickets,labelRanges)

  var p =(identification.partition(_._2.size==1))
while(p._2.nonEmpty) {
  val cc = p._1.values.flatMap(_.keys).toSeq
  identification = (p._2.map { case (k, v) => {
    k -> v.filterNot({ case (i, j) => cc.contains(i) })
  }
  })

  p = (identification ++ p._1).partition(_._2.size == 1)
}
  p._1.toSeq.sortBy(_._1).map(x=>x._1 -> x._2.keys.head).filter(_._2.startsWith("departure")).foreach(println)
  val mtt = myTicket.split("\\n").last.split(",").map(_.toInt).toSeq
  println(mtt)

  println(p._1.toSeq.sortBy(_._1).map(x=>x._1 -> x._2.keys.head).filter(_._2.startsWith("departure")).map(x=>BigInt(mtt(x._1))).product)





  s.close()

}
