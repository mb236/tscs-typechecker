package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.lambdacalc.{LambdaAbstraction, LambdaApplication, LambdaExpression}
import de.upb.cs.swt.tscs.typed._
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list._

import scala.util.{Failure, Success, Try}

/**
  * Defines a type checker for the typed λ calculus
  */
trait TypedLambdaExpression extends LambdaExpression with Typecheck with ListTypeCheck {

  override def typecheck(): Try[_] = typecheck(this,scala.collection.mutable.HashMap())

  override def typecheck(expr: Expression, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    println("TypedLambdaExpression")
    // Just an optimization
    if (gamma.contains(expr)) return Success(gamma(expr))

    val result = expr match {
      /* T-Var */
      //T-True
      case v: TypedLambdaVariable if T_True_Premise(v)
      => storeAndWrap(v, TypeInformations.Bool, gamma)

      //T-False
      case v: TypedLambdaVariable if T_False_Premise(v)
      => storeAndWrap(v, TypeInformations.Bool, gamma)

      case le: ListExpression => super.typecheck(le, gamma)

      // Assume abstract base type for variables
      case v: TypedLambdaVariable if v.typeAnnotation.isEmpty && v.variable != "true" && v.variable != "false"
      => storeAndWrap(v, TypeInformations.AbstractBaseType, gamma)
      // Or simply trust the annotation
      case v: TypedLambdaVariable if v.typeAnnotation.isDefined => storeAndWrap(v, v.typeAnnotation.get, gamma)

      /* T-Abs */
      case abs: LambdaAbstraction
        if typecheck(abs.variable, gamma).isSuccess && typecheck(abs.term, gamma).isSuccess
      => storeAndWrap(abs, FunctionTypeInformation(typecheck(abs.variable, gamma).get, typecheck(abs.term, gamma).get), gamma)

      /* T-App */
      case app: LambdaApplication if T_App_Premise(app, gamma)
      => storeAndWrap(app, typecheck(app.function, gamma).get.asInstanceOf[FunctionTypeInformation].targetType, gamma)
      case _
      => Failure[TypeInformation](new TypingException(expr))
    }
    /*
    print(expr.toString + " --- ")
    println(result match {
      case Success(t) => "Successfully typed to " + t
      case Failure(e) => "Typing failed"
    })*/
    result
  }


  private def T_App_Premise(app: LambdaApplication, gamma: scala.collection.mutable.HashMap[Expression, TypeInformation]) =
    (typecheck(app.function, gamma), typecheck(app.argument, gamma)) match {
      case (Success(ft: FunctionTypeInformation), Success(at)) => at == ft.sourceType
      case _ => false
    }

  private def T_False_Premise(v: TypedLambdaVariable) =
    v.variable == "false" &&
      (v.typeAnnotation match {
        case None => true
        case Some(t) => t == TypeInformations.Bool
        case _ => false
      })

  private def T_True_Premise(v: TypedLambdaVariable) =
    v.variable == "true" &&
      (v.typeAnnotation match {
        case None => true
        case Some(t) => t == TypeInformations.Bool
        case _ => false
      })
}
