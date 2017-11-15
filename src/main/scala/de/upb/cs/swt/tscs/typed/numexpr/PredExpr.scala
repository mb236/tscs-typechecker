package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.TypeInformation

/**
  * Represents a pred operation in the N language
  */
case class PredExpr(subterm : Expression, typeAnnotation: Option[TypeInformation]) extends TypedNumericExpression
