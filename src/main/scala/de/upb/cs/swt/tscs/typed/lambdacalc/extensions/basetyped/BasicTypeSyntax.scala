package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.basetyped

import de.upb.cs.swt.tscs.lambdacalc._
import de.upb.cs.swt.tscs.typed.{SecTypeInformation, _}
import de.upb.cs.swt.tscs.typed.lambdacalc.{TypedLambdaExpression, TypedLambdaVariable}
import org.parboiled2.{CharPredicate, Rule1}

/**
  * Syntax definition for basic types. Was extracted to own file because [[de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list.ListSyntax]]
  * needs basic types and extends on them.
  */
trait BasicTypeSyntax extends LambdaCalculusSyntax{

  override def Term: Rule1[TypedLambdaExpression] = rule {
    LambdaAbstractionTerm ~> (widen(_ : LambdaAbstraction)) |
      LambdaApplicationTerm ~> (widen(_ : LambdaApplication)) |
      LambdaVariableTerm
  }

  override def LambdaVariableTerm : Rule1[TypedLambdaVariable] = rule {
    capture(oneOrMore(CharPredicate.Alpha)) ~ optional(" : " ~ TypeInfo) ~> TypedLambdaVariable
  }

  def TypeInfo : Rule1[SecTypeInformation] = rule {
    BaseTypeInfo | BoolTypeInfo | FunctionType
  }

  def BaseTypeInfo : Rule1[SecTypeInformation] = rule {
    capture("A") ~ SecLevel ~> BaseTypeInformation
  }

  def BoolTypeInfo : Rule1[SecTypeInformation] = rule {
    capture("Bool") ~ SecLevel ~> BaseTypeInformation
  }

  def FunctionType : Rule1[SecTypeInformation] = rule {
    "[" ~ TypeInfo ~ "->" ~ TypeInfo ~ "]" ~> FunctionTypeInformation
  }

  def SecLevel : Rule1[SecurityLevel] = rule {
    "(" ~ capture(oneOrMore(CharPredicate.Alpha)) ~ ")" ~> SecurityLevel
  }

  def widen(expression: LambdaExpression) : TypedLambdaExpression = {
    expression match {
      case UntypedLambdaVariable(v) => new TypedLambdaVariable(v, Option.empty)
      case LambdaApplication(f,a) => new LambdaApplication(f,a) with TypedLambdaExpression
      case LambdaAbstraction(v,t) => new LambdaAbstraction(v,t) with TypedLambdaExpression
    }
  }
}
