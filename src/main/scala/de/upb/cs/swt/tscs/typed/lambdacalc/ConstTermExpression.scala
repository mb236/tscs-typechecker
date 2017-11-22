package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.{Expression, Value}
import de.upb.cs.swt.tscs.lambdacalc.LambdaExpression
import de.upb.cs.swt.tscs.typed.TypeInformation

/**
  * Represents a cons with terns in the λ calculus
  */
case class ConsTermExpression(typeInfo: TypeInformation, t1: Expression, t2: Expression) extends Expression with TypedLambdaExpression {
  override def toString: String = "cons[" + typeInfo + "] " + t1.toString + " " + t2.toString

}