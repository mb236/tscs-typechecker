package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.numexpr.NumericExpression
import de.upb.cs.swt.tscs.typed.{TypeInformation, TypeInformations, Typecheck}
import de.upb.cs.swt.tscs.Expression

import scala.util.{Failure, Success, Try}

/**
  * Reuses the operation semantics of the N language and extends it with type checking
  */
trait TypedNumericExpression extends NumericExpression with Typecheck {

  override def typecheck(): Try[_] = typecheck(this)

  val Gamma = new scala.collection.mutable.HashMap[Expression, TypeInformation]

  override def typecheck(expr: Expression, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation] = scala.collection.mutable.HashMap()): Try[TypeInformation] = {
    expr match {
      /* T-True */
      case e: ValueExpr
        if e.v == "true" && e.typeAnnotation.getOrElse(TypeInformations.Bool) == TypeInformations.Bool
          => storeAndWrap(e, TypeInformations.Bool)

      /* T-False */
      case e: ValueExpr
        if e.v == "false" && e.typeAnnotation.getOrElse(TypeInformations.Bool) == TypeInformations.Bool
          => storeAndWrap(e, TypeInformations.Bool)

      /* T-Zero */
      case e: ValueExpr
        if e.v == "0" && e.typeAnnotation.getOrElse(TypeInformations.Nat) == TypeInformations.Nat
          => storeAndWrap(e, TypeInformations.Nat)

      /* T-Succ1 */
      case e: SuccNvExpr
        if typecheck(e.nv).isSuccess &&
          e.typeAnnotation.getOrElse(TypeInformations.Nat) == TypeInformations.Nat &&
           Gamma.get(e.nv).get == TypeInformations.Nat
                => storeAndWrap(e, Gamma.get(e.nv).get)

      /* T-Succ2 */
      case e: SuccExpr
        if typecheck(e.subterm).isSuccess &&
          e.typeAnnotation.getOrElse(TypeInformations.Nat) == TypeInformations.Nat &&
          Gamma.get(e.subterm).get == TypeInformations.Nat
                => storeAndWrap(e, Gamma.get(e.subterm).get)

      /* T-Pred */
      case e: PredExpr
        if typecheck(e.subterm).isSuccess &&
          e.typeAnnotation.getOrElse(TypeInformations.Nat) == TypeInformations.Nat &&
          Gamma.get(e.subterm).get == TypeInformations.Nat
            => storeAndWrap(e, Gamma.get(e.subterm).get)

      /* T-IsZero */
      case e: IsZeroExpr
        if typecheck(e.subterm).isSuccess &&
          e.typeAnnotation.getOrElse(TypeInformations.Nat) == TypeInformations.Nat &&
            Gamma.get(e.subterm).get == TypeInformations.Nat
              => storeAndWrap(e, TypeInformations.Bool)

      /* T-If */
      case e: IfExpr
        if typecheck(e.condition).isSuccess &&
          Gamma.get(e.condition).get == TypeInformations.Bool &&
          typecheck(e.IfTrue).isSuccess &&
            (Gamma.getOrElse(e.IfTrue, typecheck(e.IfTrue).get) == Gamma.getOrElse(e.IfFalse, typecheck(e.IfFalse).get)) &&
            (e.typeAnnotation.getOrElse(typecheck(e.IfTrue).get) == typecheck(e.IfTrue).get)
              => storeAndWrap(e, Gamma.get(e.IfTrue).get)

      case _ => new Failure[TypeInformation](null)
    }
  }

  def storeAndWrap(e: Expression, t: TypeInformation): Try[TypeInformation] = {
    Gamma.put(e, t)
    Success(t)
  }
}
