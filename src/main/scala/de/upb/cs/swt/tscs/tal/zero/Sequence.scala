package de.upb.cs.swt.tscs.tal.zero

/**
  * Represents a sequence of instruction in TAL-0
  * @param instruction The next instruction
  * @param sequence The following instructions, if present
  */
case class Sequence (instruction: Instruction, sequence: Option[Sequence]) {

}
