package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.numexpr.NumericExpression
import de.upb.cs.swt.tscs.{Expression, Typecheck}

import scala.util.{Failure, Success, Try}

/**
  * Reuses the operation semantics of the N language and extends it with type checking
  */
trait TypedNumericExpression extends NumericExpression with Typecheck {

  override def typecheck(): Try[_] = typecheck(this)

  val Gamma = new scala.collection.mutable.HashMap[Expression, TypeInformation]

  override def typecheck(expr: Expression): Try[TypeInformation] = {
    expr match {
      /* T-True */
      case e: ValueExpr
        if e.v == "true"
          => storeAndWrap(e, TypeInformation("Bool"))

      /* T-False */
      case e: ValueExpr
        if e.v == "false"
          => storeAndWrap(e, TypeInformation("Bool"))

      /* T-Zero */
      case e: ValueExpr
        if e.v == "0"
          => storeAndWrap(e, TypeInformation("Nat"))

      /* T-Succ1 */
      case e: SuccNvExpr
        if typecheck(e.nv).isSuccess &&
           Gamma.get(e.nv).get == TypeInformation("Nat")
                => storeAndWrap(e, Gamma.get(e.nv).get)

      /* T-Succ2 */
      case e: SuccExpr
        if typecheck(e.subterm).isSuccess &&
          Gamma.get(e.subterm).get == TypeInformation("Nat")
                => storeAndWrap(e, Gamma.get(e.subterm).get)

      /* T-Pred */
      case e: PredExpr
        if typecheck(e.subterm).isSuccess &&
          Gamma.get(e.subterm).get == TypeInformation("Nat")
            => storeAndWrap(e, Gamma.get(e.subterm).get)

      /* T-IsZero */
      case e: IsZeroExpr
        if typecheck(e.subterm).isSuccess &&
            Gamma.get(e.subterm).get == TypeInformation("Nat")
              => storeAndWrap(e, Gamma.get(e.subterm).get)

      /* T-If */
      case e: IfExpr
        if typecheck(e.condition).isSuccess &&
          Gamma.get(e.condition).get == TypeInformation("Bool") &&
          typecheck(e.IfTrue).isSuccess &&
            (Gamma.getOrElse(e.IfTrue, typecheck(e.IfTrue).get) == Gamma.getOrElse(e.IfFalse, typecheck(e.IfFalse).get))
              => storeAndWrap(e, Gamma.get(e.IfTrue).get)

      case _ => new Failure[TypeInformation](null)
    }
  }

  def storeAndWrap(e: Expression, t: TypeInformation): Try[TypeInformation] = {
    Gamma.put(e, t)
    Success(t)
  }
}
