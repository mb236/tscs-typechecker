package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.Expression

/**
  * Represents an application in the λ calculus
  */
case class LambdaApplication(function : Expression, argument : Expression) extends LambdaExpression {
  override def toString: String = "(" + function.toString + " " + argument.toString + ")"
}
