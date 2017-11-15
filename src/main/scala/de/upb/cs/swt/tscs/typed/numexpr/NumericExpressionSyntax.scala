package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.typed.{BaseTypeInformation, TypeInformation}
import org.parboiled2.{Parser, ParserInput, Rule1}

/**
  * Syntax for the N language
  * @param input
  */
class NumericExpressionSyntax(val input : ParserInput) extends Parser {
  def InputLine = rule {
    Term | EOI
  }

  def Term: Rule1[TypedNumericExpression] = rule {
    ValueTerm | IfTerm | SuccTerm | PredTerm | IsZeroTerm
  }

  def ValueTerm = rule {
    TrueTerm | FalseTerm | NumericValueTerm
  }

  def NumericValueTerm = rule {
    run { ZeroTerm | SuccNvTerm }
  }

  def TrueTerm = rule {
    capture("true") ~ optional(" : " ~ TypeInfo) ~> ValueExpr
  }

  def FalseTerm = rule {
    capture("false") ~ optional(" : " ~ TypeInfo) ~> ValueExpr
  }

  def ZeroTerm = rule {
    capture("0") ~ optional(" : " ~ TypeInfo) ~> ValueExpr
  }

  def SuccNvTerm : Rule1[SuccNvExpr] = rule {
    "succ " ~ ZeroTerm  ~ optional(" : " ~ TypeInfo) ~> SuccNvExpr | "succ " ~ SuccNvTerm  ~ optional(" : " ~ TypeInfo) ~> SuccNvExpr
  }

  def IfTerm = rule {
    "if " ~ Term ~ " then " ~ Term ~ " else " ~ Term  ~ optional(" : " ~ TypeInfo) ~> IfExpr
  }

  def SuccTerm = rule {
    "succ " ~ Term  ~ optional(" : " ~ TypeInfo) ~> SuccExpr
  }

  def PredTerm = rule {
    "pred " ~ Term  ~ optional(" : " ~ TypeInfo) ~> PredExpr
  }

  def IsZeroTerm = rule {
    "iszero " ~ Term  ~ optional(" : " ~ TypeInfo) ~> IsZeroExpr
  }

  def TypeInfo = rule {
    BoolTypeInfo | NatTypeInfo
  }

  def BoolTypeInfo = rule {
    capture("Bool") ~> BaseTypeInformation
  }

  def NatTypeInfo = rule {
    capture("Nat") ~> BaseTypeInformation
  }

}
