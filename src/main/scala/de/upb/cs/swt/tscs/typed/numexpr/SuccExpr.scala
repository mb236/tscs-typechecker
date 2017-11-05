package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a succ operation in the N language
  */
case class SuccExpr (subterm : Expression, typeAnnotation: Option[TypeInformation]) extends TypedNumericExpression
