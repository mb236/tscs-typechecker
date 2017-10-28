package de.upb.cs.swt.tscs.numexpr

import org.parboiled2.{Parser, ParserInput, Rule1}

/**
  * Created by benhermann on 28.10.17.
  */
class NumericExpressionSyntax(val input : ParserInput) extends Parser {
  def InputLine = rule {
    Term | EOI
  }

  def Term: Rule1[NumericExpression] = rule {
    ValueTerm | IfTerm | SuccTerm | PredTerm | IsZeroTerm
  }

  def ValueTerm = rule {
    TrueTerm | FalseTerm | NumericValueTerm
  }

  def NumericValueTerm : Rule1[NumericValueExpr] = rule {
    run { ZeroTerm | SuccNvTerm }
  }

  def TrueTerm = rule {
    capture("true") ~> ValueExpr
  }

  def FalseTerm = rule {
    capture("false") ~> ValueExpr
  }

  def ZeroTerm = rule {
    capture("0") ~> NumericValueExpr
  }

  def SuccNvTerm = rule {
    "succ " ~ capture(NumericValueTerm) ~> NumericValueExpr
  }

  def IfTerm = rule {
    "if " ~ Term ~ " then " ~ Term ~ " else " ~ Term ~> IfExpr
  }

  def SuccTerm = rule {
    "succ " ~ Term ~> SuccExpr
  }

  def PredTerm = rule {
    "succ " ~ Term ~> PredExpr
  }

  def IsZeroTerm = rule {
    "succ " ~ Term ~> IsZeroExpr
  }
}
