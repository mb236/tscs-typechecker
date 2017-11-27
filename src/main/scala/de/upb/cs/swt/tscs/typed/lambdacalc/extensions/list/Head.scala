package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.TypeInformation
import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression

/**
  * Represents a head term in the λ calculus
  */
case class Head(typeInfo: TypeInformation, t1: Expression) extends ListExpression with TypedLambdaExpression {
  override def toString: String = "head[" + typeInfo + "] " + t1.toString
}