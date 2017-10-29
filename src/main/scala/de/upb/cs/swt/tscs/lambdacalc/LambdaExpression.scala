package de.upb.cs.swt.tscs.lambdacalc

import de.upb.cs.swt.tscs.{Expression, Evaluation}

/**
  * Specifies the operation semantics of the λ calculus
  */
trait LambdaExpression extends Evaluation {
  override def -->(): Expression = -->(this)

  override def -->(expr: Expression): Expression = {
    expr match {
      case _ => null
    }
  }
}
