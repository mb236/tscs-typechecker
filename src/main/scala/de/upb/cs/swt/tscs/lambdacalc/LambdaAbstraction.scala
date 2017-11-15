package de.upb.cs.swt.tscs.lambdacalc

import de.upb.cs.swt.tscs.{Expression, Value}

/**
  * Represents an abstraction in the λ calculus
  */
case class LambdaAbstraction(variable : LambdaVariable, term : Expression) extends Value("λ") with LambdaExpression {
  override def toString: String = "λ" + variable + "." + term.toString
}