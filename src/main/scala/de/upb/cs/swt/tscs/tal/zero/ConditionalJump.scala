package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.Value

/**
  * Represents a conditional jump in TAL-0
 *
  * @param register The register to be checked
  * @param jumpTarget The target to be jumped to if the contents of the register is 0.
  */
case class ConditionalJump(register : Register, jumpTarget : Value) extends Instruction {

}
