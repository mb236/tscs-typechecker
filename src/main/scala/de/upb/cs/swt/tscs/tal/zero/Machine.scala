package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.Expression

/**
  * Represents a machine in TAL-0
  * @param H The current state of the heap
  * @param R The current state of the register file
  * @param I The current instruction sequence to be processed
  */
case class Machine (H : Heap, R : RegisterFile, I : Sequence) extends Expression {

  /**
    * Resolves a jump target
    * @param target The target to be resolved
    * @return An instruction sequence loaded from the heap
    */
  def resolveJumpTarget(target: Value): Sequence = {
    target match {
      case IntegerValue(value) if H.contains(value.toString) => H.get(value.toString).get
      case LabelReference(label) if H.contains(label) => H.get(label).get
      case Register(num) if R.contains(num) => resolveJumpTarget(R.get(num).get)
      case _ => throw new Exception("Could not resolve jump target: " + target)
    }
  }

  /**
    * Determines if a jump target can be resolved
    * @param target The target to be resolved
    * @return The information if the target can be resolved
    */
  def canResolveJumpTarget(target: Value) : Boolean = {
    target match {
      case IntegerValue(value) => H.contains(value.toString)
      case LabelReference(label) => H.contains(label)
      case Register(num) => canResolveJumpTarget(R.get(num).get)
      case _ => false
    }
  }

  /**
    * Resolves a value from the register file
    * @param register The register to be resolved
    * @return The value contained in the register
    */
  def resolveValue(register: Register) : Value = {
    R.get(register.num).get
  }

  /**
    * Determines if a value can be resolved to an integer value
    * @param value The value to be resolved
    * @return The information if the value can be resolved to an integer value
    */
  def canResolveToIntegerValue(value: Value) : Boolean = {
    value match {
      case Register(num) => canResolveToIntegerValue(R.get(num).get)
      case IntegerValue(_) => true
      case _ => false
    }
  }

  /**
    * Resolves a value to an integer value
    * @param value The value to be resolved
    * @return The integer value resolved by this value
    */
  def resolveIntegerValue(value: Value) : IntegerValue = {
    value match {
      case Register(num) if R.contains(num) => resolveIntegerValue(R.get(num).get)
      case i : IntegerValue => i
      case _ => throw new Exception("Could not resolve integer value: " + value)
    }

  }
}
