package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.conditional

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression

case class IfExpr(condition: Expression, IfTrue: Expression, IfFalse: Expression) extends TypedLambdaExpression
