package de.upb.cs.swt.tscs.typed.lambdacalc

import org.scalatest.{FlatSpec, Matchers}

class VariantsCheck extends FlatSpec with Matchers {
  // Doesn't work: The term is parsed as a LambdaVariable
  "This match expression" should "evaluate" in {
    var parserResult = new TypedLambdaCalculusSyntax(
      "case λx.x as <monday:Bool, tuesday:Nat, wednesday:Unit> of" +
      "    <monday=x> => 1" +
      "    <tuesday=i> => 2" +
      "    <wednesday=u> => 3"
    ).Term.run()

    parserResult.get.-->*() shouldBe 2
  }

  /*it should "evaluate to a substituted form" in {
    var parserResult = new LambdaCalculusSyntax("(λx.λy.((x y) x) c)").Term.run()

    parserResult.get.-->*() shouldBe new LambdaAbstraction(UntypedLambdaVariable("y"),
      LambdaApplication(
        LambdaApplication(
          UntypedLambdaVariable("c"),
          UntypedLambdaVariable("y")),
        UntypedLambdaVariable("c")))
  }*/
}
