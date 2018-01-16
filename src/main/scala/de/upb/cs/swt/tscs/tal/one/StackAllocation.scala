package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{Instruction, IntegerValue}

/**
  * Represents a stack allocation instruction
  * @param amount The amount of words to be allocated on the stack
  */
case class StackAllocation(amount : IntegerValue) extends Instruction {

}
