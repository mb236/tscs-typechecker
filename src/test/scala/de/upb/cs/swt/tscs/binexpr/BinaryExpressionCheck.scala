package de.upb.cs.swt.tscs.binexpr

import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}
import scala.xml.dtd.ContentModel._labelT

/**
  * Created by benhermann on 28.10.17.
  */
class BinaryExpressionCheck extends FlatSpec with Matchers {

  "Value true " should " be valid" in {
    val parseResult = new BinaryExpressionSyntax("true").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }

  "Value false " should " be valid" in {
    val parseResult = new BinaryExpressionSyntax("false").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }

  "Value x " should " not be valid" in {
    val parseResult = new BinaryExpressionSyntax("x").InputLine.run()
    parseResult shouldBe a [Failure[_]]
  }

  "If expressions " should " be valid" in {
    val parseResult = new BinaryExpressionSyntax("if true then false else true").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }

  "Nested if-expressions " should " be valid" in {
    val parseResult = new BinaryExpressionSyntax("if if true then false else true then false else true").InputLine.run()
    parseResult shouldBe a [Success[_]]
  }
}
