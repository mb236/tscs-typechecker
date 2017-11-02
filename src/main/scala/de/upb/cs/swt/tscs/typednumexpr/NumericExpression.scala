package de.upb.cs.swt.tscs.typednumexpr

import de.upb.cs.swt.tscs.{Evaluation, Expression, Typecheck}

import scala.util.{Failure, Success, Try}

/**
  * Specifies the operation semantics of the N language
  */
trait NumericExpression extends Evaluation with Typecheck {
  override def -->(): Expression = -->(this)

  override def -->(expr: Expression): Expression = {
    println("Evaluating: " + expr)
    expr match {
      case ValueExpr(v) => ValueExpr(v)
      case SuccNvExpr(nv) => SuccNvExpr(nv)
      case SuccExpr(subExpr) if isValue(subExpr) => SuccNvExpr(subExpr)
      /* E-IfTrue      */ case IfExpr(ValueExpr("true"), iftrue, iffalse) => -->(iftrue)
      /* E-IfFalse     */ case IfExpr(ValueExpr("false"), iftrue, iffalse) => -->(iffalse)
      /* E-If          */ case IfExpr(cond, iftrue, iffalse) => IfExpr(-->(cond), iftrue, iffalse)
      /* E-Succ        */ case SuccExpr(subexpr) if !isValue(subexpr) && progressPossible(subexpr) => SuccExpr(-->(subexpr))
      /* E-PredZero    */ case PredExpr(ValueExpr("0")) => ValueExpr("0")
      /* E-PredSucc    */ case PredExpr(SuccNvExpr(nv)) => nv
      /* E-Pred        */ case PredExpr(subexpr) if !isValue(subexpr) && progressPossible(subexpr) => PredExpr(-->(subexpr))
      /* E-IsZeroZero  */ case IsZeroExpr(ValueExpr("0")) => ValueExpr("true")
      /* E-IsZeroSucc  */ case IsZeroExpr(SuccNvExpr(nv)) => ValueExpr("false")
      /* E-IsZero      */ case IsZeroExpr(subexpr) if !isValue(subexpr) && progressPossible(subexpr) => IsZeroExpr(-->(subexpr))
      case _ => null
    }

  }

  override def typecheck(expr: Expression): Try[Types.Value] = {
    val result = expr match {
      case ValueExpr(v) => v match {
        case "true" => Types.bool
        case "false" => Types.bool
        case "0" => Types.nat
        case _: Any => return Failure[Types.Value](null)
      }
      case SuccNvExpr(nv) => typecheck(nv) match {
        case Success(Types.nat) => Types.nat
        case _: Any => return Failure[Types.Value](null)
      }
      case IfExpr(cond, iftrue, iffalse) => (typecheck(cond), typecheck(iftrue), typecheck(iffalse)) match {
        case (Success(Types.bool), Success(t1), Success(t2)) if t1 == t2 => t1
        case _: Any => return Failure[Types.Value](null)
      }
      case SuccExpr(subexpr) => typecheck(subexpr) match {
        case Success(Types.nat) => Types.nat
        case _: Any => return Failure[Types.Value](null)
      }
      case PredExpr(subexpr) => typecheck(subexpr) match {
        case Success(Types.nat) => Types.nat
        case _: Any => return Failure[Types.Value](null)
      }
      case IsZeroExpr(subexpr) => typecheck(subexpr) match {
        case Success(Types.nat) => Types.bool
        case _: Any => return Failure[Types.Value](null)
      }
    }
    Success(result)
  }
}

object Types extends Enumeration {
  val bool, nat = Value
}
