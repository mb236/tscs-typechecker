package de.upb.cs.swt.tscs.binexpr

import de.upb.cs.swt.tscs.Value

/**
  * Represents values in the B language
  */
case class ValueExpr(v : String) extends Value(v) with BinaryExpression
