package de.upb.cs.swt.tscs.lambdacalc

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success

/**
  * Checks for Lambda Calculus Expressions
  */
class LambdaExpressionCheck extends FlatSpec with Matchers {
  "λx.x" should "be valid" in {
    var parserResult = new LambdaCalculusSyntax("λx.x").Term.run()
    parserResult shouldBe a [Success[_]]
    parserResult.get shouldBe new LambdaAbstraction("x", new LambdaVariable("x"))
  }

  "λx.λy.((x y) x)" should "be valid" in {
    var parserResult = new LambdaCalculusSyntax("λx.λy.((x y) x)").Term.run()
    parserResult shouldBe a [Success[_]]
    parserResult.get shouldBe new LambdaAbstraction("x",
                                  new LambdaAbstraction("y",
                                    LambdaApplication(
                                        LambdaApplication(LambdaVariable("x"),LambdaVariable("y"))
                                              ,LambdaVariable("x"))))
  }

  "λz.λx.λy.(x (y z))" should "be valid" in {
    var parserResult = new LambdaCalculusSyntax("λz.λx.λy.(x (y z))").Term.run()
    parserResult shouldBe a [Success[_]]
    parserResult.get shouldBe
      new LambdaAbstraction("z",
        LambdaAbstraction("x",
        LambdaAbstraction("y",
          LambdaApplication(
            LambdaVariable("x"),
            LambdaApplication(LambdaVariable("y"),LambdaVariable("z"))))))
  }

  "(λx.x x)" should "be valid" in {
    var parserResult = new LambdaCalculusSyntax("(λx.x x)").Term.run()
    parserResult shouldBe a [Success[_]]
    parserResult.get shouldBe new LambdaApplication(
          LambdaAbstraction("x", LambdaVariable("x")),
          LambdaVariable("x"))
  }

}
