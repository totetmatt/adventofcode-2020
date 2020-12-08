import scala.annotation.tailrec
import scala.collection.{immutable, mutable}
import scala.io.Source



object Day08 extends App {
  trait Ops
  case class Nop(v:Int) extends Ops
  case class Jmp(v:Int) extends Ops
  case class Acc(v:Int) extends Ops
  case class Ret(finished:Boolean,acc:Int)
  val filename = "../input08"
  val s = Source.fromFile(filename)

  val instructions = s.getLines().map{
    i=> val Array(op,va)= i.split(" ")

    op match {
      case "nop" => Nop(va.toInt)
      case "jmp" => Jmp(va.toInt)
      case "acc" => Acc(va.toInt)
    }
  }.toArray[Ops]

  case class StateMachine(acc:Int=0,
                          visited:immutable.HashSet[Int]= immutable.HashSet.empty[Int],
                          pointer:Int=0,
                          changed: Boolean=false)

  def run(instructions:Array[Ops],state:StateMachine) : Ret = {
    if(state.visited.contains(state.pointer)) Ret(finished = false,state.acc)
    else if (state.pointer>=instructions.length) Ret(finished = true,state.acc)
    else {
      val visited = state.visited + state.pointer
      instructions(state.pointer) match {
        case Jmp(v) => val r = run(instructions,state.copy(visited=visited, pointer = state.pointer+v))
          if(!r.finished && !state.changed)  run(instructions,state.copy(visited=visited, pointer = state.pointer+1,changed=true))
          else r
        case Nop(v) => val r = run(instructions,state.copy(visited=visited, pointer = state.pointer+1))
          if(!r.finished && !state.changed)  run(instructions,state.copy(visited=visited, pointer = state.pointer+v,changed=true))
          else r
        case Acc(v) => {
          run(instructions,state.copy(visited=visited, pointer = state.pointer+1,acc=state.acc+v))
        }
      }
    }
  }
  println(run(instructions,StateMachine()))
  s.close()

}
