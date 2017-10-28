package de.upb.cs.swt.tscs.binexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents if-Expressions in the B language
  */
case class IfExpr(condition: Expression, IfTrue: Expression, IfFalse: Expression) extends BinaryExpression
