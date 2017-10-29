package de.upb.cs.swt.tscs.binexpr

import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

/**
  * Test for the B language
  */
class BinaryExpressionCheck extends FlatSpec with Matchers {

  "Term true" should "be valid" in {
    val parseResult = new BinaryExpressionSyntax("true").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }

  it should "evaluate to true" in {
    val parseResult = new BinaryExpressionSyntax("true").Term.run()
    parseResult shouldBe a [Success[_]]
    assertResult ("true") { parseResult.get.-->*.toString() }
  }

  "Term false" should "be valid" in {
    val parseResult = new BinaryExpressionSyntax("false").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }

  it should "evaluate to false" in {
    val parseResult = new BinaryExpressionSyntax("false").Term.run()
    parseResult shouldBe a [Success[_]]
    assertResult ("false") { parseResult.get.-->*.toString() }
  }

  "Term x" should "not be valid" in {
    val parseResult = new BinaryExpressionSyntax("x").InputLine.run()
    parseResult shouldBe a [Failure[_]]
  }

  "If expressions" should "be valid" in {
    val parseResult = new BinaryExpressionSyntax("if true then false else true").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }

  it should "evaluate to false" in {
    val parseResult = new BinaryExpressionSyntax("if true then false else true").Term.run()
    parseResult shouldBe a [Success[_]]
    assertResult ("false") { parseResult.get.-->*.toString() }
  }

  "Nested if-expressions" should "be valid" in {
    val parseResult = new BinaryExpressionSyntax("if if true then false else true then false else true").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }

  it should "evaluate in one step to if false then false else true" in {
    val parseResult = new BinaryExpressionSyntax("if if true then false else true then false else true").Term.run()
    parseResult shouldBe a [Success[_]]
    assertResult (new IfExpr(new ValueExpr("false"), new ValueExpr("false"), new ValueExpr("true"))) { parseResult.get.--> }
  }


  it should "evaluate to true" in {
    val parseResult = new BinaryExpressionSyntax("if if true then false else true then false else true").Term.run()
    parseResult shouldBe a [Success[_]]
    assertResult ("true") { parseResult.get.-->*.toString() }
  }

}
