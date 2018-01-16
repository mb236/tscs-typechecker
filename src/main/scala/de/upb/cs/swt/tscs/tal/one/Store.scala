package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{Instruction, Register}

/**
  * Represents a store instruction
  * @param memoryAccess The memory location where the value will be stored
  * @param register The register containing the value to be stored
  */
case class Store(memoryAccess: MemoryAccess, register: Register) extends Instruction {

}
