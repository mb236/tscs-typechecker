package de.upb.cs.swt.tscs.typed.lambdacalc


import de.upb.cs.swt.tscs.lambdacalc._
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.conditional.ConditionalSyntax
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list._
import org.parboiled2.ParserInput

/**
  * A syntax definition for the typed λ calculus. The different syntax extensions are mixed in as traits to keep them
  * as separate as possible.
  */
class TypedLambdaCalculusSyntax(input : ParserInput) extends LambdaCalculusSyntax(input)
with ListSyntax with ConditionalSyntax
{

}
