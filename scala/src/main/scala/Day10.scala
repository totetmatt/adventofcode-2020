import scala.io.Source


object Day10 extends App {
def fact(n:Int) = {
  (1 to n).product
}
  val filename = "../input10"
  val s = Source.fromFile(filename)
  val data =s.getLines().map(BigInt.apply).toSeq
  //val data = "16\n10\n15\n5\n1\n11\n7\n19\n6\n12\n4".split("\n").map(BigInt.apply)
  //val data = "28\n33\n18\n42\n31\n14\n46\n20\n48\n47\n24\n23\n49\n45\n19\n38\n39\n11\n1\n32\n25\n35\n8\n17\n7\n9\n4\n2\n34\n10\n3".split("\n").map(BigInt.apply)
  val nData =data.sorted.toSeq
  val sorted = BigInt(0)+:nData:+nData.last+3
  val order= sorted.sliding(2).toSeq

  val diff = order.
    map(x=> x.last - x.head)

  val result = diff.groupBy(x=>x )
    .view.mapValues(_.size)
    .toMap

case class Yolo(master:Seq[Int],cpt:Int)
  val pos = diff.foldLeft(Yolo(Seq.empty[Int],0))((agg,el)=>{
        if(el == 3) {
          agg.copy(agg.master:+agg.cpt,0)

        } else {
          agg.copy(cpt=agg.cpt+1)
        }
  })


    val pp = pos.master.map{
    case 0 => BigInt(1)
    case 1 => BigInt(1)
    case 2 => BigInt(2)
    case z => {
      (2 to z).map(x=>BigInt(z)-BigInt(x)+BigInt(1)).sum +BigInt(1)
    } }.product

  println(pp)


}
