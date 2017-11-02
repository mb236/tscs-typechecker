package de.upb.cs.swt.tscs.typednumexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a succ operation in the N language
  */
case class SuccExpr (subterm : Expression) extends NumericExpression
