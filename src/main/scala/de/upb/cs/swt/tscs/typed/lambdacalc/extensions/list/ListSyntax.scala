package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list

import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.basetyped.BasicTypeSyntax
import org.parboiled2.Rule1

trait ListSyntax extends BasicTypeSyntax {

  override def Term: Rule1[TypedLambdaExpression] = rule {
      NilTerm | HeadTerm | TailTerm | ConsTerm | IsNilTerm |
      super.Term
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

}
