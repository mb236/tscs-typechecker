package de.upb.cs.swt.tscs.typednumexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a pred operation in the N language
  */
case class PredExpr(subterm : Expression) extends NumericExpression
