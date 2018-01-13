package de.upb.cs.swt.tscs.tal.zero

/**
  * Represents a labeled sequence of instructions in TAL-0
  * @param labelDeclaration The declared label of this sequence
  * @param sequence The sequence of instructions
  */
case class LabeledSequence(labelDeclaration: LabelDeclaration, sequence: Sequence) {

}
