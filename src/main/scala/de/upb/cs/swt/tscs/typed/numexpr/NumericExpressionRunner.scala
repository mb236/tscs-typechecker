package de.upb.cs.swt.tscs.typed.numexpr

import org.parboiled2.{ParseError, ParserInput}

import scala.util.{Failure, Success}

/**
  * A quick runner for the B language
  */
object NumericExpressionRunner {

  def main(args: Array[String]): Unit = {
    def input: ParserInput = "if true then false else 0"

    val environment = new NumericExpressionSyntax(input)
    val result = environment.Term.run()
    result match {
      case Success(ast) => {
        println("Term parsed: " +ast.toString)
        println("Term evaluates to: " + ast.-->*())

        val typecheck = ast.typecheck()
        typecheck match {
          case Success(result) => println("Term type checked successfully as " + result)
          case Failure(e) => println("Term failed to typecheck.")
        }

      }
      case Failure(e: ParseError) => println("Expression is not valid: " + environment.formatError(e))
      case Failure(e) => println("Unexpected error during parsing run: " + e)
    }
  }

}
