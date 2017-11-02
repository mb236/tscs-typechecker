package de.upb.cs.swt.tscs.typednumexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a iszero operation in the N language
  */
case class IsZeroExpr(subterm : Expression) extends NumericExpression
