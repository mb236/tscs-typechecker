package de.upb.cs.swt.tscs.typed.lambdacalc.extensions

import de.upb.cs.swt.tscs.typed.TypeInformations
import de.upb.cs.swt.tscs.typed.lambdacalc.{TypedLambdaCalculusSyntax, TypedLambdaExpression}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

class ConditionalCheck extends FlatSpec with Matchers {
  "if true then false else true" should "parse" in {
    var parserResult = new TypedLambdaCalculusSyntax("if true then false else true").Term.run()
    parserResult shouldBe a[Success[_]]
  }

  it should "typecheck to boolean" in {
    var parserResult = new TypedLambdaCalculusSyntax("if true then false else true").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
    parserResult.get.typecheck().get shouldBe TypeInformations.Bool
  }
  /*
  it should "evaluate to false" in {
    var parserResult = new TypedLambdaCalculusSyntax("if true then false else true").Term.run()
    parserResult shouldBe a[Success[_]]
    println("lambcond:    " +parserResult.get.-->*())
  }*/

  "if true then false else λx.x" should "parse" in {
    var parserResult = new TypedLambdaCalculusSyntax("if true then false else λx.x").Term.run()
    parserResult shouldBe a[Success[_]]
  }

  "if true then false else λx.x" should "not typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("if true then false else λx.x").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Failure[_]]
  }

}
