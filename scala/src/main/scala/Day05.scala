import scala.annotation.tailrec
import scala.io.Source

object Day05 extends App {
  val filename = "../input05"

  val s = Source.fromFile(filename)
  val ids = s.getLines().toSeq
  def binpart(
      lChar: Char,
      rChar: Char
  )(start: Int, end: Int, seqChar: Seq[Char]): Int = {
    val mid = start + (end - start) / 2

    seqChar match {
      case last :: Nil => if (last == lChar) start else end
      case h :: t =>
        if (h == lChar) binpart(lChar, rChar)(start, mid, t)
        else binpart(lChar, rChar)(mid + 1, end, t)
    }
  }
  def computeSeatId(row: Int, seat: Int): Int = row * 8 + seat
  val getRow = binpart('F', 'B')(0, 127, _)
  val getSeat = binpart('L', 'R')(0, 7, _)

  def getSeatId(seqChar: String): Int = {
    val (row, seat) = seqChar.splitAt(7)
    val rowId = getRow(row.toList)
    val seatNumber = getSeat(seat.toList)
    computeSeatId(rowId, seatNumber)
  }

  val idsParsed = ids.map(getSeatId)
  println(f"Max id ${idsParsed.max}")
  val mySeatId = idsParsed.sorted
    .sliding(2)
    .takeWhile(x => x.head + 1 == x.last)
    .toList
    .last
    .last + 1
  println(f"SeatId ${mySeatId}")
  s.close()
}
