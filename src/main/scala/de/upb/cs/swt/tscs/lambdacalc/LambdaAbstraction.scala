package de.upb.cs.swt.tscs.lambdacalc

/**
  * Represents an abstraction in the λ calculus
  */
case class LambdaAbstraction(variable : String, term : LambdaExpression) extends LambdaExpression