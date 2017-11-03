package de.upb.cs.swt.tscs.typed.numexpr

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
    capture("true") ~> ValueExpr
  }

  def FalseTerm = rule {
    capture("false") ~> ValueExpr
  }

  def ZeroTerm = rule {
    capture("0") ~> ValueExpr
  }

  def SuccNvTerm : Rule1[SuccNvExpr] = rule {
    "succ " ~ ZeroTerm ~> SuccNvExpr | "succ " ~ SuccNvTerm ~> SuccNvExpr
  }

  def IfTerm = rule {
    "if " ~ Term ~ " then " ~ Term ~ " else " ~ Term ~> IfExpr
  }

  def SuccTerm = rule {
    "succ " ~ Term ~> SuccExpr
  }

  def PredTerm = rule {
    "pred " ~ Term ~> PredExpr
  }

  def IsZeroTerm = rule {
    "iszero " ~ Term ~> IsZeroExpr
  }

  def TypeInfo = rule {
    BoolTypeInfo | NatTypeInfo
  }

  def BoolTypeInfo = rule {
    capture("Bool") ~> TypeInformation
  }

  def NatTypeInfo = rule {
    capture("Nat") ~> TypeInformation
  }

}
