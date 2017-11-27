package de.upb.cs.swt.tscs.typed.lambdacalc.extensions

import de.upb.cs.swt.tscs.typed.TypeInformations
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.list.{ConsValueExpression, NilList}
import de.upb.cs.swt.tscs.typed.lambdacalc.{TypedLambdaCalculusSyntax, TypedLambdaVariable}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

class ListExtensionCheck extends FlatSpec with Matchers {

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
}
