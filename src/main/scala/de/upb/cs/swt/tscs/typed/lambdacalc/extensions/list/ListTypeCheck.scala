package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed._

import scala.util.{Failure, Success, Try}

trait ListTypeCheck extends Typecheck{
  override def typecheck(): Try[TypeInformation] = typecheck(this,scala.collection.mutable.HashMap())
  override def typecheck(expr: Expression, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    // Just an optimization
    if (gamma.contains(expr)) return Success(gamma(expr))

    val result = expr match {
      /* T-Nil */
      case n: NilList => storeAndWrap(n, ListTypeInformation(n.typeInfo), gamma)

      /* T-Cons */
      case cons: ConsTermExpression if T_Cons_Premise(cons, gamma)
      => storeAndWrap(cons, ListTypeInformation(cons.typeInfo), gamma)
      /*T-IsNil */
      case isNil: IsNil if T_IsNil_Premise(isNil, gamma)
      => storeAndWrap(isNil, TypeInformations.Bool, gamma)
      /*T-Head */
      case head: Head if T_Head_Premise(head, gamma)
      => storeAndWrap(head, head.typeInfo, gamma)
      /*T-Tail */
      case tail: Tail if T_Tail_Premise(tail, gamma)
      => storeAndWrap(tail, ListTypeInformation(tail.typeInfo), gamma)

      case _
      => super.typecheck(expr, gamma)
    }
    /*
    print(expr.toString + " --- ")
    println(result match {
      case Success(t) => "Successfully typed to " + t
      case Failure(e) => "Typing failed"
    })*/
    result
  }


  private def T_Tail_Premise(tail: Tail, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]) =
    typecheck(tail.t1, gamma) match {
      case Success(t1: ListTypeInformation) => t1.listType == tail.typeInfo
      case _ => false
    }

  private def T_Head_Premise(head: Head, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]) =
    typecheck(head.t1, gamma) match {
      case Success(t1: ListTypeInformation) => t1.listType == head.typeInfo
      case _ => false
    }

  private def T_IsNil_Premise(isNil: IsNil, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]) =
    typecheck(isNil.t1, gamma) match {
      case Success(t1: ListTypeInformation) => t1.listType == isNil.typeInfo
      case _ => false
    }

  private def T_Cons_Premise(cons: ConsTermExpression, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]) =
    (typecheck(cons.t1, gamma), typecheck(cons.t2, gamma)) match {
      case (Success(t1), Success(t2: ListTypeInformation)) => t1 == cons.typeInfo && t2.listType == cons.typeInfo
      case _ => false
    }
}
