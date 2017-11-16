package de.upb.cs.swt.tscs.typed.lambdacalc

case class MatchCase(val fieldLabel: String, val variable: TypedLambdaVariable, val term: TypedLambdaExpression) {
  override def toString: String = "<" + fieldLabel + "=" + variable.variable + "> => "+ term
}

case class MatchExpression(val term: TypedLambdaExpression, val cases: Seq[MatchCase]) extends TypedLambdaExpression {
  override def toString: String = "case" + term + " of " + cases.toStream.map(c => c.toString).mkString("|")
}