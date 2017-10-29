package de.upb.cs.swt.tscs.numexpr

import de.upb.cs.swt.tscs.{Expression, Value}

/**
  * Represents a term in the form of succ nv
  */
case class SuccNvExpr (nv : Expression) extends Value("succ " + nv.toString) with NumericExpression
