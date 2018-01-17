package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{Instruction, IntegerValue}

/**
  * Represents a stack free operation in TAL-1
  * @param amount The amount of words to be removed from the stack
  */
case class StackFree(amount : IntegerValue) extends Instruction {

}
