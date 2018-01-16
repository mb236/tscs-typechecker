package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{IntegerValue, Register}
import org.parboiled2.{ParserInput, Rule1}

/**
  * Describes the syntax of TAL-1 an implements a parser for it
  * @param input The program to be parsed
  */
class Syntax(input : ParserInput) extends de.upb.cs.swt.tscs.tal.zero.Syntax(input) {

  def AddedValue : Rule1[IntegerValue] = rule {
    "+" ~ Whitespace ~ IntegerLiteral ~> ((iv : IntegerValue) => iv)
  }

  def MemoryReference : Rule1[MemoryAccess] = rule {
    "Mem[" ~ Whitespace ~ RegisterLiteral ~ Whitespace ~ optional(AddedValue) ~ Whitespace ~ "]" ~> MemoryAccess
  }

  def InstructionLoad = rule {
    RegisterLiteral ~ Whitespace ~ ":=" ~ Whitespace ~ MemoryReference ~> Load
  }

  def InstructionStore = rule {
    MemoryReference ~ Whitespace ~ ":=" ~ Whitespace ~ RegisterLiteral ~> Store
  }

  def InstructionAlloc = rule {
    RegisterLiteral ~ Whitespace ~ ":=" ~ Whitespace ~ "malloc" ~ Whitespace ~ IntegerLiteral ~> MemoryAllocation
  }

  def InstructionCommit = rule {
    "commit" ~ Whitespace ~ RegisterLiteral ~> Commit
  }

  def InstructionStackAlloc = rule {
    "salloc" ~ Whitespace ~ IntegerLiteral ~> StackAllocation
  }

  def InstructionStackFree = rule {
    "sfree" ~ Whitespace ~ IntegerLiteral ~> StackFree
  }

  override def Instructions = rule {
    InstructionLoad |
    InstructionStore |
    InstructionAlloc |
    InstructionCommit |
    InstructionStackAlloc |
    InstructionStackFree |
    super.Instructions
  }
}
