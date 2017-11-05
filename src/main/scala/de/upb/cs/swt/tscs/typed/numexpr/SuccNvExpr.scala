package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.{Expression, Value}

/**
  * Represents a term in the form of succ nv
  */
case class SuccNvExpr (nv : Expression, typeAnnotation: Option[TypeInformation]) extends Value("succ " + nv.toString) with TypedNumericExpression
