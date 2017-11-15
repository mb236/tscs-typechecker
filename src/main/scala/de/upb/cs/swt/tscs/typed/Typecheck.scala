package de.upb.cs.swt.tscs.typed

import de.upb.cs.swt.tscs.Expression

import scala.util.Try

/**
  * Basic trait for type checking
  */
trait Typecheck extends Expression {
  def typecheck() : Try[_] = typecheck(this)

  def typecheck(expr : Expression) : Try[_]
}
