import scala.io.Source


object Day14 extends App {


  val filename = "../input14"
  val s = Source.fromFile(filename)

  val sLines = s.getLines().toSeq

  def itob(s: String): BigInt = {
    s.reverse.zipWithIndex.map {
      case ('1', i) => BigInt(2).pow(i)
      case _ => BigInt(0)
    }.sum
  }

  def v1(s: Seq[String]) = {
    def applyMask(mask: String, value: String) = {
      val v = value.reverse
      mask.reverse.zipWithIndex.map {
        case (c, i) if i < v.length && c == 'X' => v(i)
        case (c, _) if c == 'X' => '0'
        case (c, _) => c
      }.reverse.mkString

    }


    var mask = ""
    val memory = collection.mutable.HashMap.empty[String, String]
    val data = s.foreach {
      case x if x.startsWith("mask") => mask = x.split("=").last.trim
      case x =>
        val a = x.split("=").toSeq
        val addr = a(0).replace("]", "").split("\\[")(1)
        memory.put(addr, applyMask(mask, a(1).trim().toInt.toBinaryString))

    }

    println(memory.values.map(v =>
      itob(v)

    ).sum)
  }
  def v2(s: Seq[String]) ={
    def qq(s:String,idx:Int) : Seq[String] = {

        if(idx>=s.length) Seq(s)
        else s(idx) match {
          case '0' | '1' => qq(s,idx+1)
          case _ =>   {
            val u : Array[Char] = Array.from(s)

            u(idx) = '1'

            val z : Array[Char] = Array.from(s)

            z(idx) = '0'

            qq(z.mkString,idx+1) ++ qq(u.mkString,idx+1)
          }
        }
    }
    def applyMask(mask: String, value: String) = {
      val v = value.reverse

      val m = mask.reverse.zipWithIndex.map {
        case (c, i) if i < v.length && c == '1' => '1'
        case (c, i) if i < v.length && c == '0' => v(i)


        case (c, _) => c
      }

      qq(m.reverse.mkString,0)
    }

    var mask = ""
    val memory = collection.mutable.HashMap.empty[String, BigInt]
    val data = s.foreach {
      case x if x.startsWith("mask") => mask = x.split("=").last.trim
      case x =>
        val a = x.split("=").toSeq
        val addr = a(0).replace("]", "").split("\\[")(1).trim
        applyMask(mask,addr.toInt.toBinaryString).foreach(add=>{
          memory.put(add,BigInt(a.last.trim))
        })

    }
    println(memory.values.sum)
  }
  v2(sLines)
  println(42.toBinaryString)
  s.close()

}
