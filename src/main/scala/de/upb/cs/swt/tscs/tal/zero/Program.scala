package de.upb.cs.swt.tscs.tal.zero

/**
  * Represents a program for TAL-0
  * @param labeledSequences The labeled instruction sequences that the program is made up from
  */
case class Program(labeledSequences: Seq[LabeledSequence]) extends TypedAssemblyLanguageAST {

}
