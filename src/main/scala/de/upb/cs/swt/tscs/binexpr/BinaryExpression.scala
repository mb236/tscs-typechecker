package de.upb.cs.swt.tscs.binexpr

import de.upb.cs.swt.tscs.{Expression, Evaluation}

/**
  * Specifies the operational semantics of the B language
  */
trait BinaryExpression extends Evaluation {
  override def -->(): Expression = -->(this)

  override def -->(expr: Expression): Expression =
    expr match {
      case ValueExpr(v) => ValueExpr(v)
      /* E-IfTrue  */ case IfExpr(ValueExpr("true"), iftrue, iffalse) => -->(iftrue)
      /* E-IfFalse */ case IfExpr(ValueExpr("false"), iftrue, iffalse) => -->(iffalse)
      /* E-If      */ case IfExpr(cond, iftrue, iffalse) if progressPossible(cond) => IfExpr(-->(cond), iftrue, iffalse)
      case _ => null
    }

}
