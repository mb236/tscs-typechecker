package de.upb.cs.swt.tscs.typed.lambdacalc

import org.parboiled2.{ParseError, ParserInput}

import scala.util.{Failure, Success}

/**
  * A quick runner for the λ calculus language
  */
object LambdaCalculusRunner {
  def main(args: Array[String]): Unit = {
    def input: ParserInput = "(λx.λy.((x y) x) c)"

    val environment = new LambdaCalculusSyntax(input)
    val result = environment.Term.run()

    result match {
      case Success(ast) => println(ast.toString + " -->* " + ast.-->*())
      case Failure(e: ParseError) => println("Expression is not valid: " + environment.formatError(e))
      case Failure(e) => println("Unexpected error during parsing run: " + e)
    }
  }
}
