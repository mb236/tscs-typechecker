package de.upb.cs.swt.tscs.typed.numexpr

import de.upb.cs.swt.tscs.typed.TypeInformations
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
    typeCheckResult.get shouldBe TypeInformations.Bool
  }

  "if true then true else false" should "typecheck" in {
    val parseResult = new NumericExpressionSyntax("if true then true else false").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe TypeInformations.Bool
  }

  "if true then 0 else false" should "not typecheck" in {
    val parseResult = new NumericExpressionSyntax("if true then 0 else false").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Failure[_]]
  }

  "true : Bool" should "be a valid term" in {
    val parseResult = new NumericExpressionSyntax("true : Bool").Term.run()
    parseResult shouldBe a [Success[_]]
  }

  it should "typecheck" in {
    val parseResult = new NumericExpressionSyntax("true : Bool").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
  }

  "true : Nat" should "be a valid term" in {
    val parseResult = new NumericExpressionSyntax("true : Nat").Term.run()
    parseResult shouldBe a [Success[_]]
  }

  it should "not typecheck" in {
    val parseResult = new NumericExpressionSyntax("true : Nat").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Failure[_]]
  }

  "if true : Bool then 0 else 0 : Nat : Bool" should " not typecheck" in {
    val parseResult = new NumericExpressionSyntax("if true : Bool then 0 else 0 : Nat : Bool").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Failure[_]]
  }

  "Term false" should "typecheck to Bool" in {
    val parseResult = new NumericExpressionSyntax("false").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe TypeInformations.Bool
  }

  "Nested if-expressions" should "typecheck" in {
    val parseResult = new NumericExpressionSyntax("if if true then false else true then false else true").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
  }


  "Term 0" should "typecheck to Nat" in {
    val parseResult = new NumericExpressionSyntax("0").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe TypeInformations.Nat
  }

  "Term succ succ 0" should "typecheck to Nat" in {
    val parseResult = new NumericExpressionSyntax("succ succ 0").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe TypeInformations.Nat
  }

  "Term iszero 0" should "typecheck to Bool" in {
    val parseResult = new NumericExpressionSyntax("iszero 0").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe TypeInformations.Bool
  }

  "Term iszero succ 0" should "typecheck to Bool" in {
    val parseResult = new NumericExpressionSyntax("iszero succ 0").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe TypeInformations.Bool
  }

  "Term iszero true" should "not typecheck" in {
    val parseResult = new NumericExpressionSyntax("iszero true").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Failure[_]]
  }

  "Term succ pred 0" should "typecheck to Nat" in {
    val parseResult = new NumericExpressionSyntax("succ pred 0").Term.run()
    parseResult shouldBe a [Success[_]]
    val typeCheckResult = parseResult.get.typecheck()
    typeCheckResult shouldBe a [Success[_]]
    typeCheckResult.get shouldBe TypeInformations.Nat
  }

}
