package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.lambdacalc.{LambdaAbstraction, LambdaApplication, LambdaExpression}
import de.upb.cs.swt.tscs.typed._
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list._

import scala.util.{Failure, Success, Try}

/**
  * Defines a type checker for the typed λ calculus
  */
trait TypedLambdaExpression extends LambdaExpression with Typecheck {

  override def typecheck(): Try[_] = typecheck(this)

  val Gamma = new scala.collection.mutable.HashMap[Expression, TypeInformation]

  def storeAndWrap(e: Expression, t: TypeInformation): Try[TypeInformation] = {
    Gamma.put(e, t)
    Success(t)
  }

  override def typecheck(expr: Expression): Try[TypeInformation] = {
    // Just an optimization
    if (Gamma.contains(expr)) return Success(Gamma(expr))

    val result = expr match {
      /* T-Var */
      //T-True
      case v: TypedLambdaVariable if T_True_Premise(v)
      => storeAndWrap(v, TypeInformations.Bool)

      //T-False
      case v: TypedLambdaVariable if T_False_Premise(v)
      => storeAndWrap(v, TypeInformations.Bool)

      /* T-Nil */
      case n: NilList => storeAndWrap(n, ListTypeInformation(n.typeInfo))

      /* T-Cons */
      case cons: ConsTermExpression if T_Cons_Premise(cons)
      => storeAndWrap(cons, ListTypeInformation(cons.typeInfo))
      /*T-IsNil */
      case isNil: IsNil if T_IsNil_Premise(isNil)
      => storeAndWrap(isNil, TypeInformations.Bool)
      /*T-Head */
      case head: Head if T_Head_Premise(head)
      => storeAndWrap(head, head.typeInfo)
      /*T-Tail */
      case tail: Tail if T_Tail_Premise(tail)
      => storeAndWrap(tail, ListTypeInformation(tail.typeInfo))



      // Assume abstract base type for variables
      case v: TypedLambdaVariable if v.typeAnnotation.isEmpty && v.variable != "true" && v.variable != "false" => storeAndWrap(v, TypeInformations.AbstractBaseType)
      // Or simply trust the annotation
      case v: TypedLambdaVariable if v.typeAnnotation.isDefined => storeAndWrap(v, v.typeAnnotation.get)

      /* T-Abs */
      case abs: LambdaAbstraction
        if typecheck(abs.variable).isSuccess && typecheck(abs.term).isSuccess
      => storeAndWrap(abs, FunctionTypeInformation(typecheck(abs.variable).get, typecheck(abs.term).get))

      /* T-App */
      case app: LambdaApplication if T_App_Premise(app)
      => storeAndWrap(app, typecheck(app.function).get.asInstanceOf[FunctionTypeInformation].targetType)
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


  private def T_App_Premise(app: LambdaApplication) =
    (typecheck(app.function), typecheck(app.argument)) match {
      case (Success(ft: FunctionTypeInformation), Success(at)) => at == ft.sourceType
      case _ => false
    }

  private def T_Tail_Premise(tail: Tail) =
    typecheck(tail.t1) match {
      case Success(t1: ListTypeInformation) => t1.listType == tail.typeInfo
      case _ => false
    }

  private def T_Head_Premise(head: Head) =
    typecheck(head.t1) match {
      case Success(t1: ListTypeInformation) => t1.listType == head.typeInfo
      case _ => false
    }

  private def T_IsNil_Premise(isNil: IsNil) =
    typecheck(isNil.t1) match {
      case Success(t1: ListTypeInformation) => t1.listType == isNil.typeInfo
      case _ => false
    }

  private def T_Cons_Premise(cons: ConsTermExpression) =
    (typecheck(cons.t1), typecheck(cons.t2)) match {
      case (Success(t1), Success(t2: ListTypeInformation)) => t1 == cons.typeInfo && t2.listType == cons.typeInfo
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
