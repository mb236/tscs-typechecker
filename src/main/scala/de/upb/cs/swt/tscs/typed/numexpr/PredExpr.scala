package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a pred operation in the N language
  */
case class PredExpr(subterm : Expression, typeAnnotation: Option[TypeInformation]) extends TypedNumericExpression
