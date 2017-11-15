package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.TypeInformation

/**
  * Represents if-Expressions in the N language
  */
case class IfExpr(condition: Expression, IfTrue: Expression, IfFalse: Expression, typeAnnotation: Option[TypeInformation]) extends TypedNumericExpression
