package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.Value

/**
  * Represents a jump instruction in TAL-0
 *
  * @param target The target of the jump
  */
case class Jump(target : Value) extends Instruction {

}
