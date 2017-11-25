package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.typed.TypeInformations
import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

/**
  * Checks for the type checker
  */
class TypedLambdaExpressionCheck extends FlatSpec with Matchers {
  "λx.x : A" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("λx.x : A").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "λx : [A->A].λy : A.((x y) x)" should "successfully parse" in {
    var parserResult = new TypedLambdaCalculusSyntax("λx : [A->A].λy : A.((x y) x)").Term.run()
    parserResult shouldBe a[Success[_]]
  }

  it should "not successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("λx : [A->A].λy : A.((x y) x)").Term.run()
    parserResult.get.typecheck() shouldBe a[Failure[_]]
  }

  "λz : A.λx : [A->A].λy : [A->A].(x (y z))" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("λz : A.λx : [A->A].λy : [A->A].(x (y z))").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "(λx.x x)" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("(λx.x x)").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "λx : [[A->A]->[A->A]].λy : [A->A].((x y) c)" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("λx : [[A->A]->[A->A]].λy : [A->A].((x y) c)").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "cons[Bool] true cons[Bool] false cons[Bool] true nil[Bool]" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("cons[Bool] true cons[Bool] false cons[Bool] true nil[Bool]").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "isnil[Bool] cons[Bool] true nil[Bool]" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("isnil[Bool] cons[Bool] true nil[Bool]").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "isnil[A] cons[Bool] true nil[Bool]" should "not successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("isnil[A] cons[Bool] true nil[Bool]").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Failure[_]]
  }

  "head[Bool] cons[Bool] true nil[Bool]" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("head[Bool] cons[Bool] true nil[Bool]").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "head[A] cons[Bool] true nil[Bool]" should "not successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("head[A] cons[Bool] true nil[Bool]").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Failure[_]]
  }

  "tail[Bool] cons[Bool] true nil[Bool]" should "successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("tail[Bool] cons[Bool] true nil[Bool]").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Success[_]]
  }

  "tail[A] cons[Bool] true nil[Bool]" should "not successfully typecheck" in {
    var parserResult = new TypedLambdaCalculusSyntax("tail[A] cons[Bool] true nil[Bool]").Term.run()
    parserResult shouldBe a[Success[_]]
    parserResult.get.typecheck() shouldBe a[Failure[_]]
  }

  "cons[Bool] true cons[Bool] false cons[Bool] true nil[Bool]" should "evaluate correctly" in {
    val environment = new TypedLambdaCalculusSyntax("cons[Bool] true cons[Bool] false cons[Bool] true nil[Bool]").Term.run()
    environment shouldBe a[Success[_]]
    environment.get.-->*() shouldEqual
      ConsValueExpression(TypeInformations.Bool, TypedLambdaVariable("true", None),
        ConsValueExpression(TypeInformations.Bool, TypedLambdaVariable("false", None),
          ConsValueExpression(TypeInformations.Bool, TypedLambdaVariable("true", None),
            NilList(TypeInformations.Bool))))
  }

  "isnil[A] cons[Bool] true nil[Bool]" should "evaluate to false" in {
    val environment = new TypedLambdaCalculusSyntax("isnil[A] cons[Bool] true nil[Bool]").Term.run()
    environment shouldBe a[Success[_]]
    environment.get.-->*() shouldEqual TypedLambdaVariable("false", None)
  }

  "λx : [[A->A]->[A->A]].λy : [A->A].((x y) c)" should "evaluate correctly" in {
    val environment = new TypedLambdaCalculusSyntax("λx : [[A->A]->[A->A]].λy : [A->A].((x y) c)").Term.run()
    environment shouldBe a[Success[_]]
    val expression = environment.get.-->*()
    expression shouldNot be(null)
  }
}
