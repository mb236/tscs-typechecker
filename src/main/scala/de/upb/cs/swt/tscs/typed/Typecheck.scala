package de.upb.cs.swt.tscs.typed

import de.upb.cs.swt.tscs.Expression

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
  * Basic trait for type checking
  */
trait Typecheck extends Expression {

  def storeAndWrap(e: Expression, t: TypeInformation, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    gamma.put(e, t)
    Success(t)
  }

  def typecheck() : Try[TypeInformation] = typecheck(this, scala.collection.mutable.HashMap())

  def typecheck(expr : Expression, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]) : Try[TypeInformation] = {
    Failure(new TypingException(expr))
  }

  def typecheck(expr : Expression,
                gamma: mutable.HashMap[Expression, TypeInformation],
                psi: mutable.HashMap[Expression, TypeInformation]) : Try[TypeInformation] = {
    Failure(new TypingException(expr))
  }
}
