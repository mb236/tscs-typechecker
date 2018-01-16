package de.upb.cs.swt.tscs.tal.one

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success

/**
  * A few simple or complex test cases for TAL-0
  */
class TalOneCheck extends FlatSpec with Matchers {
  "Simple example program" should "parse" in {
    val program = "start: r3:=0; \n" +
                  "Mem[r1] := r3;\n" +
                  "r4 := Mem[r1];\n" +
                  "jump r4 "
    var parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]
  }

  "Aliasing example program" should "parse" in {
    val program = "start: r3:=0; \n" +
                  "Mem[r1] := r3;\n" +
                  "r4 := Mem[r2];\n" +
                  "jump r4 "
    var parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]
  }

  "Copy procedure" should "parse" in {
    val program = "copy: r2 := malloc 2;\n" +
                  "r3 := Mem[r1];\n" +
                  "Mem[r2] := r3;\n" +
                  "r3 := Mem[r1+1];\n" +
                  "Mem[r2+1] := r3;\n" +
                  "commit r2;\n" +
                  "jump r4";
    var parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]
  }
}
