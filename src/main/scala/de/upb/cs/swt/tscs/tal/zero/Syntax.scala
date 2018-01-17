package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.Value
import org.parboiled2._

/**
  * Describes the syntax of TAL-0 an implements a parser for it
  * @param input The program to be parsed
  */
class Syntax(val input : ParserInput) extends Parser {

  def RegisterLiteral : Rule1[Register] = rule {
    "r" ~ capture(oneOrMore(CharPredicate.Digit)) ~> ((s : String) => Register(s.toInt))
  }

  def IntegerLiteral : Rule1[IntegerValue] = rule {
     capture(optional("-") ~ oneOrMore(CharPredicate.Digit)) ~> ((s : String) => IntegerValue(s.toInt))
  }

  def LabelLiteral : Rule1[LabelReference] = rule {
    capture(oneOrMore(CharPredicate.Alpha)) ~> LabelReference
  }

  def ValueLiteral : Rule1[Value] = rule {
    RegisterLiteral | IntegerLiteral | LabelLiteral
  }

  def InstructionMov : Rule1[Mov] = rule {
    RegisterLiteral ~ Whitespace ~ ":=" ~ Whitespace ~ ValueLiteral ~> Mov
  }

  def InstructionAdd : Rule1[Add] = rule {
    RegisterLiteral ~ Whitespace ~ ":=" ~ Whitespace ~ RegisterLiteral ~ Whitespace ~ "+" ~ Whitespace ~ ValueLiteral ~> Add
  }

  def InstructionConditionalJump : Rule1[ConditionalJump] = rule {
    "if" ~ OneOrMoreWhitespace ~ RegisterLiteral ~ OneOrMoreWhitespace ~ "jump" ~ OneOrMoreWhitespace ~ ValueLiteral ~> ConditionalJump
  }

  def Instructions : Rule1[Instruction] = rule {
    InstructionAdd | InstructionMov | InstructionConditionalJump
  }

  def InstructionJump : Rule1[Jump] = rule {
    "jump " ~ Whitespace ~ ValueLiteral ~ Whitespace ~> Jump
  }

  def OnlyJumpSequence : Rule1[Sequence] = rule {
    InstructionJump ~> ((i : Instruction)  => Sequence(i, Option.empty))
  }

  def ActualSequence : Rule1[Sequence] = rule {
    Instructions ~ ";" ~ optional(Whitespace) ~ optional(Comment) ~ optional(Whitespace) ~ InstructionSequence ~> ((i : Instruction, seq : Sequence) => Sequence(i, Option(seq)))
  }

  def InstructionSequence : Rule1 [Sequence] = rule {
   OnlyJumpSequence | ActualSequence
  }

  def DeclaredLabel : Rule1[LabelDeclaration] = rule {
    capture(oneOrMore(CharPredicate.Alpha)) ~ ":" ~ optional(Whitespace) ~> LabelDeclaration
  }

  def LabeledInstructionSequence : Rule1[LabeledSequence] = rule {
    DeclaredLabel ~ InstructionSequence ~> LabeledSequence
  }

  def Input : Rule1[TypedAssemblyLanguageAST] = rule {
    capture(zeroOrMore(LabeledInstructionSequence)) ~ EOI ~> ((s : Seq[LabeledSequence], t : String) => Program(s))
  }

  def Whitespace = rule {
    zeroOrMore(anyOf(" \n \r"))
  }

  def OneOrMoreWhitespace = rule {
    oneOrMore(anyOf(" \n \r"))
  }

  def Comment = rule {
    "//" ~ zeroOrMore(CharPredicate.Printable | " ") ~ "\n"
  }

}
