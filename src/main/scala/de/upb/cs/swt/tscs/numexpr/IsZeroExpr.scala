package de.upb.cs.swt.tscs.numexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a iszero operation in the N language
  */
case class IsZeroExpr(subterm : Expression) extends NumericExpression
