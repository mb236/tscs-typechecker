package de.upb.cs.swt.tscs.lambdacalc

import de.upb.cs.swt.tscs.Value

/**
  * Represents a variable in the λ calculus
  */
case class LambdaVariable(variable : String) extends Value(variable) with LambdaExpression {
  override def toString: String = variable
}

