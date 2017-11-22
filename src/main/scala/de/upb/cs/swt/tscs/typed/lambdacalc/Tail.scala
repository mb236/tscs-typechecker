package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.lambdacalc.LambdaExpression
import de.upb.cs.swt.tscs.typed.TypeInformation

/**
  * Represents a tail term in the λ calculus
  */
case class Tail(typeInfo: TypeInformation, t1: Expression) extends Expression with TypedLambdaExpression {
  override def toString: String = "tail[" + typeInfo + "] " + t1.toString
}