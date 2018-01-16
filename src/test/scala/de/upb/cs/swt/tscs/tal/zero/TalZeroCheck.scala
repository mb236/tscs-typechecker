package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.Machine
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success

/**
  * A few simple or complex test cases for TAL-0
  */
class TalZeroCheck extends FlatSpec with Matchers {
  "Empty program" should "parse" in {
    val parserResult = new Syntax("").Input.run()
    parserResult shouldBe a [Success[_]]
  }

  it should "evaluate" in {
    val parserResult = new Syntax("").Input.run()
    parserResult shouldBe a [Success[_]]
    val evalResult = parserResult.get.-->*()
    evalResult shouldBe null
  }

  "Looping jump" should "parse" in {
    val parserResult = new Syntax("test: jump test").Input.run()
    parserResult shouldBe a [Success[_]]
  }

  "Simple multiline" should "parse" in {
    val program = "test: if r1 jump test; \n" +
                  "      jump test"

    val parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]
  }


  "Simple commented multiline" should "parse" in {
    val program = "test: if r1 jump test;  // test:=+ \n" +
      "      jump test"

    val parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]
  }

  "Instructions with whitespaces " should "parse" in {
    val program = "test: if    r1     jump     test; \n" +
                  "      r3  :=  r2  +   r3; \n" +
                  "      r1  :=  -1; \n" +
                  "      jump    test"

    val parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]
  }

  "Simple multiplication" should "parse" in {
    val program = "prod: r3:=0;                   // res := 0 \n" +
                  "jump loop \n" +
                  "\n" +
                  "loop: if r1 jump done;  // if a = 0 goto done\n" +
                  "  r3 := r2 + r3;        // res := res+b\n" +
                  "  r1 := r1 + -1;       // a := a - 1\n" +
                  "jump loop\n " +
                  "\n" +
                  "done: jump r4"

    val parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]
  }

  it should "multiply two numbers" in {
    val program = "prod: r3:=0;                   // res := 0 \n" +
      "jump loop \n" +
      "\n" +
      "loop: if r1 jump done;  // if a = 0 goto done\n" +
      "  r3 := r2 + r3;        // res := res+b\n" +
      "  r1 := r1 + -1;       // a := a - 1\n" +
      "jump loop\n " +
      "\n" +
      "done: jump r4"

    val parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]

    // set up initial machine
    val machine = parserResult.get.bootstrapInitialMachine()

    // provide some input values in registers
    machine.R.put(1, new IntegerValue(3))
    machine.R.put(2, new IntegerValue(2))

    // provide a jump point out of the program
    machine.R.put(4, new LabelReference("trampoline"))
    machine.H.put("trampoline", null)

    // let it run
    val finalMachine = parserResult.get.-->*(machine)

    // the result in register 3 should be 6
    finalMachine.asInstanceOf[Machine].R.get(3).get.asInstanceOf[IntegerValue].value shouldBe 6
  }


}
