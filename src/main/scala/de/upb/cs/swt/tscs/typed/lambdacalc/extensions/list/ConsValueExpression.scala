package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list

import de.upb.cs.swt.tscs.Value
import de.upb.cs.swt.tscs.typed.TypeInformation
import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression

/**
  * Represents a cons with values in the λ calculus
  */
case class ConsValueExpression(typeInfo: TypeInformation, v1: Value, v2: Value)
  extends Value("cons[" + typeInfo.toString + "] " + v1 + " " + v2)
    with TypedLambdaExpression with ListExpression