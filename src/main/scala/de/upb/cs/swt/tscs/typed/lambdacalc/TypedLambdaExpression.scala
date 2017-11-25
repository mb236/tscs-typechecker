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
    if (Gamma.contains(expr)) return new Success(Gamma.get(expr).get)

    val result = expr match {
      /* T-Var */
      //T-True
      case v: TypedLambdaVariable if T_True_Premise(v)
      => storeAndWrap(v, TypeInformations.Bool)

      //T-False
      case v: TypedLambdaVariable if T_False_Premise(v)
      => storeAndWrap(v, TypeInformations.Bool)

      /* T-Nil */
      case n: NilList => storeAndWrap(n, new ListTypeInformation(n.typeInfo))

      /* T-Cons */
      case cons: ConsTermExpression if typecheck(cons.t1).isSuccess && typecheck(cons.t1).get == cons.typeInfo
        && typecheck(cons.t2).isSuccess && typecheck(cons.t2).get.isInstanceOf[ListTypeInformation] &&
        typecheck(cons.t2).get.asInstanceOf[ListTypeInformation].listType == cons.typeInfo
      => storeAndWrap(cons, new ListTypeInformation(cons.typeInfo))
      /*T-IsNil */
      case isNil: IsNil if typecheck(isNil.t1).isSuccess && typecheck(isNil.t1).get.isInstanceOf[ListTypeInformation]
        && typecheck(isNil.t1).get.asInstanceOf[ListTypeInformation].listType == isNil.typeInfo
      => storeAndWrap(isNil, TypeInformations.Bool)
      /*T-Head */
      case head: Head if typecheck(head.t1).isSuccess && typecheck(head.t1).get.isInstanceOf[ListTypeInformation]
        && typecheck(head.t1).get.asInstanceOf[ListTypeInformation].listType == head.typeInfo
      => storeAndWrap(head, head.typeInfo)
      /*T-Tail */
      case tail: Tail if typecheck(tail.t1).isSuccess && typecheck(tail.t1).get.isInstanceOf[ListTypeInformation]
        && typecheck(tail.t1).get.asInstanceOf[ListTypeInformation].listType == tail.typeInfo
      => storeAndWrap(tail, new ListTypeInformation(tail.typeInfo))



      // Assume abstract base type for variables
      case v: TypedLambdaVariable if v.typeAnnotation.isEmpty && v.variable != "true" && v.variable != "false" => storeAndWrap(v, TypeInformations.AbstractBaseType)
      // Or simply trust the annotation
      case v: TypedLambdaVariable if !v.typeAnnotation.isEmpty => storeAndWrap(v, v.typeAnnotation.get)

      /* T-Abs */
      case abs: LambdaAbstraction
        if typecheck(abs.variable).isSuccess && typecheck(abs.term).isSuccess
      => storeAndWrap(abs, new FunctionTypeInformation(typecheck(abs.variable).get, typecheck(abs.term).get))

      /* T-App */
      case app: LambdaApplication
        if typecheck(app.function).isSuccess &&
          typecheck(app.function).get.isInstanceOf[FunctionTypeInformation] &&
          typecheck(app.argument).isSuccess &&
          typecheck(app.argument).get == typecheck(app.function).get.asInstanceOf[FunctionTypeInformation].sourceType
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
