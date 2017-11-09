package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.{Evaluation, Expression, Typecheck}

import scala.util.{Failure, Success, Try}

/**
  * Specifies the operation semantics of the λ calculus
  */
trait LambdaExpression extends Evaluation with Typecheck {

  override def typecheck(expr: Expression): Try[String] = {
    val result = expr match {
      case LambdaVariable(variable) => "ChurchNum"
      case LambdaAbstraction(variable, term) => {
        val termType = typecheck(term)
          // TODO FInish this

        "T1->T2"
      }
      case LambdaApplication(function, argument) => {
        val argType = typecheck(argument) match {
          case Success(t) => t
          case _ => return Failure(null)
        }
        val funcSplitType = typecheck(function) match {
          case Success(it) => it.split("->")
          case _ => return Failure(null)
        }
        if (funcSplitType(0) == argType) {
          Success(funcSplitType(1))
        } else {
          Failure(null)
        }
      }
    }
  }

  override def -->(): Expression = -->(this)

  override def -->(expr: Expression): Expression = {
    println("Evaluating: " + expr.toString)
    expr match {
      /* E-App1   */ case LambdaApplication(t1, t2) if progressPossible(t1) && !isValue(t1) => LambdaApplication(-->(t1), t2)
    /* E-App2   */ case LambdaApplication(v1, t2) if isValue(v1) && progressPossible(t2) => LambdaApplication(v1, -->(t2))
    /* E-AppAbs */ case LambdaApplication(LambdaAbstraction(x, t12), v2) if isValue(v2) => substitute(matcher(new LambdaVariable(x), v2), t12)
    case λ: LambdaAbstraction => λ
    case _ => null
    }
  }

  def substitute(f: PartialFunction[LambdaVariable, Expression], term: Expression): Expression = {
    term match {
      case v: LambdaVariable if f.isDefinedAt(v) => f(v)
      case v: LambdaVariable => v
      case LambdaApplication(t1, t2) => LambdaApplication(substitute(f, t1), substitute(f, t2))
      case LambdaAbstraction(v, t) => LambdaAbstraction(v, substitute(f, t))
    }
  }

  def matcher(variable: LambdaVariable, expression: Expression): PartialFunction[LambdaVariable, Expression] =
    new PartialFunction[LambdaVariable, Expression] {
      def apply(pv: LambdaVariable) = if (pv == variable) expression else null

      override def isDefinedAt(pv: LambdaVariable): Boolean = (pv == variable)
    }


}
