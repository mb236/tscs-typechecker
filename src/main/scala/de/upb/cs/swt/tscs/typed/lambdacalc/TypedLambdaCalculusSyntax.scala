package de.upb.cs.swt.tscs.typed.lambdacalc


import de.upb.cs.swt.tscs.lambdacalc._
import de.upb.cs.swt.tscs.typed.{BaseTypeInformation, FunctionTypeInformation, ListTypeInformation, TypeInformation}
import org.parboiled2.{CharPredicate, Parser, ParserInput, Rule1}

/**
  * A syntax definition for the typed λ calculus
  */
class TypedLambdaCalculusSyntax(input : ParserInput) extends LambdaCalculusSyntax(input) {

  override def Term: Rule1[TypedLambdaExpression] = rule {
      LambdaAbstractionTerm ~> (widen(_ : LambdaAbstraction)) |
      LambdaApplicationTerm ~> (widen(_ : LambdaApplication)) |
      NilTerm | HeadTerm | TailTerm | ConsTerm | IsNilTerm |
      LambdaVariableTerm
  }

  override def LambdaVariableTerm : Rule1[TypedLambdaVariable] = rule {
    capture(oneOrMore(CharPredicate.Alpha)) ~ optional (" : " ~ TypeInfo) ~> TypedLambdaVariable
  }

  def IsNilTerm: Rule1[TypedLambdaExpression] = rule {
    "isnil[" ~ TypeInfo ~ "] " ~ Term ~> IsNil
  }

  def NilTerm: Rule1[TypedLambdaExpression] = rule {
    "nil[" ~ TypeInfo ~ "]" ~> NilList
  }

  def HeadTerm: Rule1[TypedLambdaExpression] = rule {
    "head[" ~ TypeInfo ~ "] " ~ Term ~> Head
  }

  def TailTerm: Rule1[TypedLambdaExpression] = rule {
    "tail[" ~ TypeInfo ~ "] " ~ Term ~> Tail
  }

  def ConsTerm: Rule1[TypedLambdaExpression] = rule {
    "cons[" ~ TypeInfo ~ "] " ~ Term ~ " " ~ Term ~> ConsTermExpression
  }


  def TypeInfo = rule {
    BaseTypeInfo | BoolTypeInfo | FunctionType | ListTypeInfo
  }

  def ListTypeInfo : Rule1[TypeInformation] = rule {
    "List " ~ TypeInfo ~> ListTypeInformation
  }

  def BaseTypeInfo : Rule1[TypeInformation] = rule {
    capture("A") ~> BaseTypeInformation
  }

  def BoolTypeInfo : Rule1[TypeInformation] = rule {
    capture("Bool") ~> BaseTypeInformation
  }

  def FunctionType : Rule1[TypeInformation] = rule {
    "[" ~ TypeInfo ~ "->" ~ TypeInfo ~ "]" ~> FunctionTypeInformation
  }


  def widen(expression: LambdaExpression) : TypedLambdaExpression = {
    expression match {
      case UntypedLambdaVariable(v) => new TypedLambdaVariable(v, Option.empty)
      case LambdaApplication(f,a) => new LambdaApplication(f,a) with TypedLambdaExpression
      case LambdaAbstraction(v,t) => new LambdaAbstraction(v,t) with TypedLambdaExpression
    }
  }
}
