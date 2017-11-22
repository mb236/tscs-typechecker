package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.TypeInformation

// Example: "<tuesday=unit> as Weekday"
case class VariantExpression(val fieldLabel: String, val expr : Expression, val typeAnnotation: TypeInformation) extends TypedLambdaExpression {
  override def toString: String = "<" + fieldLabel + "=" + expr + "> as " + typeAnnotation.typeInfo
}
