
val a = 64d
var row = 63d

"BBFFBBF".zipWithIndex.foldLeft(63d){
  case (agg,('F',i) )=> {
    println(a/scala.math.pow(2,i+1))
    agg - a / scala.math.pow(2,i+1)

  }
  case (agg,('B',i)) => {
    println(a/scala.math.pow(2,i+1))
    agg + a / scala.math.pow(2,i+1)

  }

}.round

val b= 4d;
"RLL".zipWithIndex.foldLeft(3d){
  case (agg,('L',i) )=> {
    println(b/scala.math.pow(2,i+1))
    agg - b/ scala.math.pow(2,i+1)

  }
  case (agg,('R',i)) => {
    println(b/scala.math.pow(2,i+1))
    agg + b / scala.math.pow(2,i+1)

  }

}.round
