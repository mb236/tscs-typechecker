package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.Value

/**
  * Represents values in the N language
  */
case class ValueExpr(v : String, typeAnnotation: Option[TypeInformation]) extends Value(v) with TypedNumericExpression
