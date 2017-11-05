package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a iszero operation in the N language
  */
case class IsZeroExpr(subterm : Expression, typeAnnotation: Option[TypeInformation]) extends TypedNumericExpression
