package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.Value

/**
  * Represents a MOV instruction in TAL-0
 *
  * @param destinationRegister The register where the value is to be stored
  * @param value The value to be stored
  */
case class Mov(destinationRegister : Register, value : Value) extends Instruction {

}
