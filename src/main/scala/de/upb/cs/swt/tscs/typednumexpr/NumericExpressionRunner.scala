package de.upb.cs.swt.tscs.typednumexpr

import org.parboiled2.{ParseError, ParserInput}

import scala.util.{Failure, Success}

/**
  * A quick runner for the B language
  */
object NumericExpressionRunner {

  def main(args: Array[String]): Unit = {
    def input: ParserInput = "iszero true"

    val environment = new NumericExpressionSyntax(input)
    val result = environment.Term.run()

    result match {
      case Success(ast) => println(ast.toString + " -->* " + ast.-->*())
      case Failure(e: ParseError) => println("Expression is not valid: " + environment.formatError(e))
      case Failure(e) => println("Unexpected error during parsing run: " + e)
    }
  }

}
