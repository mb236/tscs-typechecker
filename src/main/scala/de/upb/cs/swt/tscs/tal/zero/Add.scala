package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.Value

/**
  * Represents an ADD instruction in TAL-0
 *
  * @param destinationRegister The destination of the computation
  * @param sourceRegister The source register for the computation
  * @param value The value to be added to the value of the source register
  */
case class Add(destinationRegister : Register, sourceRegister : Register, value : Value) extends Instruction {

}
