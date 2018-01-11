package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.conditional

import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.basetyped.BasicTypeSyntax
import org.parboiled2.Rule1

trait ConditionalSyntax extends BasicTypeSyntax {

  override def Term: Rule1[TypedLambdaExpression] =  rule {
    IfTerm | super.Term
  }

  def IfTerm = rule {
    "if " ~ Term ~ " then " ~ Term ~ " else " ~ Term ~> IfExpr
  }
}
