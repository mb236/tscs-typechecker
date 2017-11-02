package de.upb.cs.swt.tscs.typednumexpr

import de.upb.cs.swt.tscs.Value

/**
  * Represents values in the N language
  */
case class ValueExpr(v : String) extends Value(v) with NumericExpression
