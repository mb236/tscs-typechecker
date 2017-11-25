package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.TypeInformation
import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression

/**
  * Represents a cons with terns in the λ calculus
  */
case class ConsTermExpression(typeInfo: TypeInformation, t1: Expression, t2: Expression) extends Expression with TypedLambdaExpression {
  override def toString: String = "cons[" + typeInfo + "] " + t1.toString + " " + t2.toString

}