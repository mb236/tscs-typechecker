package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.Machine
import de.upb.cs.swt.tscs.tal.zero.{IntegerValue, LabelReference, Register}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success

/**
  * A few simple or complex test cases for TAL-1
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

  it should "copy the tuple from r1 to r2" in {
    val program = "copy: r2 := malloc 2;\n" +
      "r3 := Mem[r1];\n" +
      "Mem[r2] := r3;\n" +
      "r3 := Mem[r1+1];\n" +
      "Mem[r2+1] := r3;\n" +
      "commit r2;\n" +
      "jump r4";
    var parserResult = new Syntax(program).Input.run()
    parserResult shouldBe a [Success[_]]

    // set up initial machine
    val machine = parserResult.get.bootstrapInitialMachine()

    val inputTupleLocation : LabelReference = LabelReference("inputTuple")

    // provide some input values in registers
    machine.R.put(1, inputTupleLocation)

    val tuple = new HeapTuple(2)
    tuple(0) = IntegerValue(23)
    tuple(1) = IntegerValue(42)
    machine.H.put(inputTupleLocation.label, tuple)

    // provide a jump point out of the program
    machine.R.put(4, new LabelReference("trampoline"))
    machine.H.put("trampoline", null)

    // let it run
    val finalMachine = parserResult.get.-->*(machine).asInstanceOf[Machine]

    // the result in register 3 should be 6
    finalMachine.canResolveToLabelReference(Register(2)) shouldBe true
    val labelReference = finalMachine.resolveLabelReference(Register(2))
    finalMachine.canResolveToHeapTuple(labelReference) shouldBe true
    val heapValue = finalMachine.H.get(labelReference.label).get
    heapValue shouldBe a [HeapTuple]
    val heapTuple = heapValue.asInstanceOf[HeapTuple]
    heapTuple.length shouldBe 2
    heapTuple(0) shouldBe IntegerValue(23)
    heapTuple(1) shouldBe IntegerValue(42)

  }
}
