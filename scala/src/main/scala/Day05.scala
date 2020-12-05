import scala.annotation.tailrec
import scala.io.Source

object Day05 extends App {
  val filename = "../input05"

  val s = Source.fromFile(filename)
  val ids = s.getLines().toSeq

  def charToLR(l: Char, r: Char)(c: Char): Either[Char, Char] = {
    if (c == l) Left(c)
    else Right(c)
  }
  val LR = charToLR('L', 'R')(_)
  val FB = charToLR('F', 'B')(_)

  @tailrec
  def binpart(
      fct: Char => Either[Char, Char]
  )(start: Int, end: Int)(seqChar: Seq[Char]): Int = {
    val mid = start + (end - start) / 2
    seqChar match {
      case Nil => start
      case h :: t =>
        fct(h) match {
          case Left(_)  => binpart(fct)(start, mid)(t)
          case Right(_) => binpart(fct)(mid + 1, end)(t)
        }
    }
  }

  def computeSeatId(row: Int, seat: Int): Int = row * 8 + seat
  val getRow = binpart(FB)(0, 127) _
  val getSeat = binpart(LR)(0, 7) _

  def getSeatId(seqChar: String): Int = {
    val (row, seat) = seqChar.splitAt(7)
    val rowId = getRow(row.toList)
    val seatNumber = getSeat(seat.toList)
    computeSeatId(rowId, seatNumber)
  }

  val idsParsed = ids.map(getSeatId)
  println(f"Max id ${idsParsed.max}")
  assert(idsParsed.max == 991)
  val mySeatId = idsParsed.sorted
    .sliding(2)
    .takeWhile(x => x.head + 1 == x.last)
    .toList
    .last
    .last + 1
  println(f"SeatId ${mySeatId}")
  assert(mySeatId == 534)
  s.close()

}
