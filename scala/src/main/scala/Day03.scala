import scala.io.Source
import scala.collection.parallel.CollectionConverters._
object Day03 extends App {
  val filename = "../input03"
  val s = Source.fromFile(filename)
  val map = s.getLines().toArray.map(_.toArray)
  val mapHeight = map.length
  val mapWidth = map(0).length
  println(mapWidth,mapHeight)
  println(map.map(_.mkString("Array(", ", ", ")\n")).mkString("Array(", ", ", ")"))
  val slopes = Vector((1,1),(3,1),(5,1),(7,1),(1,2))
  val res = slopes.par.map(slope => {
    (0 until mapHeight).par
      .map(step => (step * slope._2, (step * slope._1) % mapWidth))
      .takeWhile{ case (y,_) => y < mapHeight}
      .map { case (y, x) => map(y)(x) }
      .count(_ == '#')
  }).product
  println(res)
  s.close()
}
