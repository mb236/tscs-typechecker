package de.upb.cs.swt.tscs.lambdacalc

import de.upb.cs.swt.tscs.typed.lambdacalc._
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list._
import de.upb.cs.swt.tscs.{Evaluation, Expression, Value}

/**
  * Specifies the operation semantics of the λ calculus
  */
trait LambdaExpression extends Evaluation {
  override def -->(): Expression = -->(this)

  override def -->(expr: Expression): Expression = {
    println("Evaluating: " + expr.toString)
    expr match {
      /* Const from Term to Value */ case ConsTermExpression(typeInfo, t1, t2) if isValue(t1) && isValue(t2)
        => ConsValueExpression(typeInfo, t1.asInstanceOf[Value], t2.asInstanceOf[Value])
      /* E-IsNil */ case IsNil(typeInfo,t1) if progressPossible(t1) => IsNil(typeInfo, -->(t1))
      /* E-IsNilNil */ case IsNil(typeInfo,t1) if t1.isInstanceOf[NilList] =>  TypedLambdaVariable("true", Option.empty)
      /* E-IsNilCons */ case IsNil(typeInfo,t1) if t1.isInstanceOf[ConsValueExpression] => TypedLambdaVariable("false", Option.empty)
      /* E-Cons1 */ case ConsTermExpression(typeInfo, t1, t2)  if progressPossible(t1) => ConsTermExpression(typeInfo, -->(t1), t2)
      /* E-Cons2 */ case ConsTermExpression(typeInfo, t1, t2)  if progressPossible(t2) && isValue(t1) => ConsTermExpression(typeInfo, t1, -->(t2))
      /* E-HeadCons */ case Head(typeInfo, t1) if t1.isInstanceOf[ConsValueExpression] => t1.asInstanceOf[ConsValueExpression].v1
      /* E-TailCons */ case Tail(typeInfo, t1) if t1.isInstanceOf[ConsValueExpression] => t1.asInstanceOf[ConsValueExpression].v2
      /* E-Head */ case Head(typeInfo, t1) if progressPossible(t1) => Head(typeInfo, -->(t1))
      /* E-Tail */ case Tail(typeInfo, t1) if progressPossible(t1) => Tail(typeInfo, -->(t1))


      /* E-App1   */ case LambdaApplication(t1, t2) if progressPossible(t1) && !isValue(t1) => LambdaApplication(-->(t1), t2)
      /* E-App2   */ case LambdaApplication(v1, t2) if isValue(v1) && progressPossible(t2) => LambdaApplication(v1, -->(t2))
      /* E-AppAbs */ case LambdaApplication(LambdaAbstraction(x,t12), v2) if isValue(v2) => substitute(matcher(x,v2), t12)
      case λ : LambdaAbstraction => λ
      case _ => null
    }
  }

  def substitute(f : PartialFunction[LambdaVariable, Expression], term : Expression) : Expression = {
    term match {
      case v : LambdaVariable if f.isDefinedAt(v) => f(v)
      case v : LambdaVariable => v
      case LambdaApplication(t1, t2) => LambdaApplication(substitute(f, t1), substitute(f, t2))
      case LambdaAbstraction(v, t) => LambdaAbstraction(v, substitute(f, t))
    }
  }

  def matcher(variable : LambdaVariable,  expression : Expression) : PartialFunction[LambdaVariable, Expression] =
    new PartialFunction[LambdaVariable, Expression] {
      def apply(pv: LambdaVariable) = if (pv == variable) expression else null
      override def isDefinedAt(pv: LambdaVariable): Boolean = (pv == variable)
    }


}
