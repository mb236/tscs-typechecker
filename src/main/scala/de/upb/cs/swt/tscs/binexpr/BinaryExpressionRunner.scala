package de.upb.cs.swt.tscs.binexpr

import org.parboiled2.{ParseError, ParserInput}

import scala.util.{Failure, Success}

/**
  * A quick runner for the B language
  */
object BinaryExpressionRunner {

  def main(args: Array[String]): Unit = {
    def input: ParserInput = "if if true then false else true then false else true"

    val environment = new BinaryExpressionSyntax(input)
    val result = environment.Term.run()

    result match {
      case Success(ast) => println(ast.toString + " -->* " + ast.-->*())
      case Failure(e: ParseError) => println("Expression is not valid: " + environment.formatError(e))
      case Failure(e) => println("Unexpected error during parsing run: " + e)
    }
  }

}
