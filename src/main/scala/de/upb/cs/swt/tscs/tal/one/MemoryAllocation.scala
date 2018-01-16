package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{Instruction, IntegerValue, Register}

/**
  * Represents a memory allocation instruction
  * @param register The register which will contain the new unique pointer
  * @param amount The amount of heap words to be allocated
  */
case class MemoryAllocation(register: Register, amount : IntegerValue) extends Instruction {

}
