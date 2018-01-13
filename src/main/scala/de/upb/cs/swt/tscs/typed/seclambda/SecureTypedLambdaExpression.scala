package de.upb.cs.swt.tscs.typed.seclambda

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.TypeInformation
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.conditional.IfExpr
import de.upb.cs.swt.tscs.typed.lambdacalc.{TypedLambdaExpression, TypedLambdaVariable}

import scala.collection.mutable
import scala.util.Try

trait SecureTypedLambdaExpression extends TypedLambdaExpression {
  override def typecheck(): Try[TypeInformation] = super.typecheck()

  override def typecheck(expr: Expression, gamma: mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    var standardType = super.typecheck(expr, gamma)

    var result = expr match {
      case v : TypedLambdaVariable
        => super.typecheck(v, gamma).get match {
        case t : SecTypeInformation => super.typecheck(v, gamma)
        case _ => super.typecheck(v, gamma)
      }
      case cond : IfExpr => super.typecheck(cond, gamma)
      case _ => super.typecheck(expr, gamma)
    }

    result
  }
}
