package de.upb.cs.swt.tscs.typed

import de.upb.cs.swt.tscs.Expression

import scala.util.{Success, Try}

/**
  * Basic trait for type checking
  */
trait Typecheck extends Expression {

  def storeAndWrap(e: Expression, t: TypeInformation, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    gamma.put(e, t)
    Success(t)
  }

  def typecheck() : Try[_] = typecheck(this, scala.collection.mutable.HashMap())

  def typecheck(expr : Expression, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]) : Try[_]
}
