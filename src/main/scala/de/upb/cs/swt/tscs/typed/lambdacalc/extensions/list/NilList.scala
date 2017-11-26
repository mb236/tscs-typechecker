package de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list

import de.upb.cs.swt.tscs.Value
import de.upb.cs.swt.tscs.typed.TypeInformation
import de.upb.cs.swt.tscs.typed.lambdacalc.TypedLambdaExpression

/**
  * Represents an nil list in the λ calculus
  */
case class NilList(typeInfo: TypeInformation) extends Value("nil[" + typeInfo + "]")
  with TypedLambdaExpression with ListExpression