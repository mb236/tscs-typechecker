package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.conditional

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.{TypeInformation, TypeInformations, Typecheck}

import scala.collection.mutable
import scala.util.{Success, Try}

trait ConditionalTypeCheck extends Typecheck {
  override def typecheck(): Try[TypeInformation] = typecheck(this,scala.collection.mutable.HashMap())

  def T_If_Premise(cond: IfExpr, gamma: mutable.HashMap[Expression, TypeInformation]) : Boolean = {
    var conditionType = typecheck(cond.condition, gamma)
    var truePartType = typecheck(cond.IfTrue, gamma)
    var falsePartType = typecheck(cond.IfFalse, gamma)

    if (!(conditionType.isSuccess && truePartType.isSuccess && falsePartType.isSuccess)) return false
    if (!conditionType.get.equals(TypeInformations.Bool)) return false
    if (!truePartType.get.equals(falsePartType.get)) return false

    true
  }

  override def typecheck(expr: Expression, gamma: mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    // Just an optimization
    if (gamma.contains(expr)) return Success(gamma(expr))

    var result = expr match {
      case cond : IfExpr if T_If_Premise(cond, gamma) => storeAndWrap(cond, gamma.get(cond.IfTrue).get, gamma)

      case _ => super.typecheck(expr, gamma)
    }

    result
  }


}


