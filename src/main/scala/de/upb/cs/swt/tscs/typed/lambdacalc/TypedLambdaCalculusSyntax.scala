package de.upb.cs.swt.tscs.typed.lambdacalc


import de.upb.cs.swt.tscs.lambdacalc._
import org.parboiled2.ParserInput

/**
  * A syntax definition for the typed λ calculus. The different syntax extensions are mixed in as traits to keep them
  * as separate as possible.
  */
class TypedLambdaCalculusSyntax(input : ParserInput) extends LambdaCalculusSyntax(input)
