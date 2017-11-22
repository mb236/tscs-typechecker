package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.Value
import de.upb.cs.swt.tscs.lambdacalc.LambdaExpression
import de.upb.cs.swt.tscs.typed.TypeInformation

/**
  * Represents an nil list in the λ calculus
  */
case class NilList(typeInfo: TypeInformation) extends Value("nil[" + typeInfo + "]") with TypedLambdaExpression