package de.upb.cs.swt.tscs.lambdacalc

/**
  * Represents an application in the λ calculus
  */
case class LambdaApplication(function : LambdaExpression, argument : LambdaExpression) extends LambdaExpression
