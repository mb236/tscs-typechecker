package de.upb.cs.swt.tscs.typed.numexpr

import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

/**
  * Created by benhermann on 02.11.17.
  */
class TypedNumExprCheck extends FlatSpec with Matchers {
  "true" should "typecheck" in {
    val parseResult = new NumericExpressionSyntax("true").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe new TypeInformation("Bool")
  }

  "if true then true else false" should "typecheck" in {
    val parseResult = new NumericExpressionSyntax("if true then true else false").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe new TypeInformation("Bool")
  }

  "if true then 0 else false" should "not typecheck" in {
    val parseResult = new NumericExpressionSyntax("if true then 0 else false").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Failure[_]]
  }
}
