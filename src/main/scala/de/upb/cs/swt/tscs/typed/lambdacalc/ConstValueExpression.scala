package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.{Expression, Value}
import de.upb.cs.swt.tscs.lambdacalc.LambdaExpression
import de.upb.cs.swt.tscs.typed.TypeInformation

/**
  * Represents a cons with values in the λ calculus
  */
case class ConsValueExpression(typeInfo: TypeInformation, v1: Value, v2: Value)
  extends Value("cons[" + typeInfo.toString + "] " + v1 + " " + v2)  with TypedLambdaExpression