package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.{Expression, Value}

/**
  * Represents an abstraction in the λ calculus
  */
case class LambdaAbstraction(variable : String, term : Expression) extends Value("λ") with LambdaExpression {
  override def toString: String = "λ" + variable + "." + term.toString
}