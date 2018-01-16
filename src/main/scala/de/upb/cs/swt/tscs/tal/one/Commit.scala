package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{Instruction, Register}

/**
  * Represents a commit instruction
  * @param register The register to become shared
  */
case class Commit(register: Register) extends Instruction {

}
