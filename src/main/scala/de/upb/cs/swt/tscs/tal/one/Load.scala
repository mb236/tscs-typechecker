package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{Instruction, Register}

/**
  * Represents a load instruction
  * @param register The register where the loaded value should be stored
  * @param memoryAccess The memory location to be loaded
  */
case class Load(register: Register, memoryAccess: MemoryAccess) extends Instruction {

}
