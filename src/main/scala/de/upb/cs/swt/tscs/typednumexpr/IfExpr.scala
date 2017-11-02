package de.upb.cs.swt.tscs.typednumexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents if-Expressions in the N language
  */
case class IfExpr(condition: Expression, IfTrue: Expression, IfFalse: Expression) extends NumericExpression
