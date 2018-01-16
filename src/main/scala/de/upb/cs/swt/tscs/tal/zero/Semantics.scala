package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.{Heap, HeapElement, Machine, RegisterFile}
import de.upb.cs.swt.tscs.{Evaluation, Expression}

/**
  * Describes the semantics of TAL-0
  */
trait Semantics extends Evaluation{

  /**
    * Evaluates the current object (works only on Program)
    * @return The next evaluation step
    */
  override def -->(): Expression = {
    -->(bootstrapInitialMachine())
  }

  /**
    * Performs a single step evaluation
    * @param expr The expression to be evaluated
    * @return The next evaluation step
    */
  override def -->(expr: Expression): Expression = {
    expr match {
      case m : Machine => -->(m)
      case p : Program => -->(bootstrapInitialMachine())
      case _ => null
    }
  }

  /**
    * Bootstraps the machine for a program
    * @return A first machine containing the program with initialed heap and register file
    */
  def bootstrapInitialMachine() : Machine = {
    val program = this.asInstanceOf[Program]
    val firstSequence = program.labeledSequences.isEmpty match {
      case true => null
      case false => program.labeledSequences.head.sequence
    }
    val machine = new Machine(new Heap[HeapElement], new RegisterFile, firstSequence)
    program.labeledSequences.foreach(ls => machine.H.put(ls.labelDeclaration.label, ls.sequence))

    machine
  }


  /**
    * Defines the final condition for the evaluation.
    * @param expr The expression to be evaluted
    * @return The information if the evaluation is in the final state
    */
  override def isFinalResult(expr: Expression): Boolean = {
    expr match {
      case Machine(_, _, null) => true
      case _ => super.isFinalResult(expr)
    }
  }

  /**
    * Performs an evaluation step for the TAL-0 machine
    * @param m The machine to be evaluated
    * @return The next machine state or null if it is stuck
    */
  def -->(m : Machine) : Machine = {
    m match {
        /* JUMP */
      case Machine(heap, registerFile, Sequence(Jump(target), _))
        if m.canResolveJumpTarget(target)
        => Machine(heap, registerFile, m.resolveJumpTarget(target))
        /* MOV */
      case Machine(heap, registerFile, Sequence(Mov(destinationRegister, value), remainingInstructions))
        if remainingInstructions.isDefined
        => {
          registerFile.put(destinationRegister.num, value)
          Machine(heap, registerFile, remainingInstructions.get)
        }
       /* ADD */
      case Machine(heap, registerFile, Sequence(Add(destinationRegister, sourceRegister, value), remainingInstructions))
        if remainingInstructions.isDefined &&
           m.canResolveToIntegerValue(sourceRegister) &&
           m.canResolveToIntegerValue(value)
        => {
          val result : IntegerValue = m.resolveIntegerValue(sourceRegister) + m.resolveIntegerValue(value)
          registerFile.put(destinationRegister.num, result)
          Machine(heap, registerFile, remainingInstructions.get)
        }
      /* IF-EQ */
      case Machine(heap, registerFile, Sequence(ConditionalJump(register, jumpTarget), remainingInstruction))
        if remainingInstruction.isDefined &&
           m.resolveValue(register).isInstanceOf[IntegerValue] &&
           m.resolveValue(register).asInstanceOf[IntegerValue].value == 0 &&
           m.canResolveJumpTarget(jumpTarget)
        => Machine(heap, registerFile, m.resolveJumpTarget(jumpTarget))
       /* IF-NEQ */
      case Machine(heap, registerFile, Sequence(ConditionalJump(register, jumpTarget), remainingInstruction))
        if remainingInstruction.isDefined &&
          m.resolveValue(register).isInstanceOf[IntegerValue] &&
          m.resolveValue(register).asInstanceOf[IntegerValue].value != 0 &&
          remainingInstruction.isDefined
      => Machine(heap, registerFile, remainingInstruction.get)
      case _ => {
        null
      }
    }
  }

}
