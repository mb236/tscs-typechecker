package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.TypeInformation
import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression

/**
  * Represents a tail term in the λ calculus
  */
case class Tail(typeInfo: TypeInformation, t1: Expression) extends ListExpression with TypedLambdaExpression {
  override def toString: String = "tail[" + typeInfo + "] " + t1.toString
}