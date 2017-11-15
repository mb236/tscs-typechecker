package de.upb.cs.swt.tscs.typed.lambdacalc

import org.parboiled2.{ParseError, ParserInput}

import scala.util.{Failure, Success}

/**
  * A quick runner for the λ calculus language
  */
object TypedLambdaCalculusRunner {
  def main(args: Array[String]): Unit = {
    def input: ParserInput = "λx : [[A->A]->[A->A]].λy : [A->A].((x y) c)"

    val environment = new TypedLambdaCalculusSyntax(input)
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
