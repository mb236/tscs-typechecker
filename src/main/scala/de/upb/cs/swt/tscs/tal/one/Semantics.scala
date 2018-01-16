package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.Machine
import de.upb.cs.swt.tscs.tal.zero.{LabelReference, Mov, Register, Sequence}

trait Semantics extends de.upb.cs.swt.tscs.tal.zero.Semantics {

  private val StackPointer = Register(-1)
  private val MaxStack = 512

  /**
    * Performs an evaluation step for the TAL-0 machine
    *
    * @param m The machine to be evaluated
    * @return The next machine state or null if it is stuck
    */
  override def -->(m: Machine): Machine = {
    m match {
        /* MOV-1 (inverse - supercedes MOV rule) */
      case Machine(heap, registerFile, Sequence(Mov(destinationRegister, value), nextInstructions))
        if (m.canResolveToUniquePointer(destinationRegister))
        => null
        /* MALLOC */
      case Machine(heap, registerFile, Sequence(MemoryAllocation(r, n),nextInstructions))
          if nextInstructions.isDefined
        => {
        registerFile.put(r.num, UniquePointer(new HeapTuple(n.value)))
        Machine(heap, registerFile, nextInstructions.get)
      }
        /* COMMIT */
      case Machine(heap, registerFile, Sequence(Commit(register), nextInstructions))
        if m.canResolveToUniquePointer(register) &&
           nextInstructions.isDefined &&
           register.num != StackPointer
        => {
        val label = m.generateFreshHeapLabel()
        heap.put(label.label, m.resolveUniquePointer(register))
        registerFile.put(register.num, label)
        Machine(heap, registerFile, nextInstructions.get)
      }
        /* LD-S */
      case Machine(heap, registerFile, Sequence(Load(register, memoryAccess),nextInstructions))
        if nextInstructions.isDefined &&
            m.canResolveToLabelReference(memoryAccess.register) &&
            m.canResolveToHeapTuple(m.resolveLabelReference(memoryAccess.register))
        => {
        val tuple = heap.get(m.resolveLabelReference(memoryAccess.register).label).asInstanceOf[HeapTuple]
        var offset = 0
        if (memoryAccess.n.isDefined) offset += memoryAccess.n.get.value
        registerFile.put(register.num, tuple(offset))
        Machine(heap, registerFile, nextInstructions.get)
      }
      /* LD-U */
      case Machine(heap, registerFile, Sequence(Load(register, memoryAccess),nextInstructions))
        if nextInstructions.isDefined &&
          m.canResolveToUniquePointer(memoryAccess.register) &&
          m.canResolveToHeapTuple(memoryAccess.register)
      => {
        val tuple = m.resolveUniquePointer(memoryAccess.register).asInstanceOf[HeapTuple]
        var offset = 0
        if (memoryAccess.n.isDefined) offset += memoryAccess.n.get.value
        registerFile.put(register.num, tuple(offset))
        Machine(heap, registerFile, nextInstructions.get)
      }
      /* ST-S */
      case Machine(heap, registerFile, Sequence(Store(memoryAccess, register),nextInstructions))
        if nextInstructions.isDefined &&
          !m.canResolveToUniquePointer(register) &&
          m.canResolveToLabelReference(memoryAccess.register) &&
          m.canResolveToHeapTuple(m.resolveLabelReference(memoryAccess.register))
      => {
        val tuple = heap.get(m.resolveLabelReference(memoryAccess.register).label).asInstanceOf[HeapTuple]
        var offset = 0
        if (memoryAccess.n.isDefined) offset += memoryAccess.n.get.value
        tuple(offset) = m.resolveValue(register)
        Machine(heap, registerFile, nextInstructions.get)
      }
      /* ST-U */
      case Machine(heap, registerFile, Sequence(Store(memoryAccess, register),nextInstructions))
        if nextInstructions.isDefined &&
          m.canResolveToUniquePointer(memoryAccess.register) &&
          m.canResolveToHeapTuple(memoryAccess.register)
      => {
        val tuple = m.resolveUniquePointer(memoryAccess.register).asInstanceOf[HeapTuple]
        var offset = 0
        if (memoryAccess.n.isDefined) offset += memoryAccess.n.get.value
        tuple(offset) = m.resolveValue(register)
        Machine(heap, registerFile, nextInstructions.get)
      }
        /* SALLOC */
      case Machine(heap, registerFile, Sequence(StackAllocation(amount), nextInstructions))
        if nextInstructions.isDefined &&
           m.canResolveToHeapTuple(StackPointer) &&
           m.resolveHeapTuple(StackPointer).length + amount.value <= MaxStack
        => {
        val newStack = m.resolveHeapTuple(StackPointer) ++ new HeapTuple(amount.value)
        registerFile.put(StackPointer.num, UniquePointer(newStack.asInstanceOf[HeapTuple]))
        Machine(heap, registerFile, nextInstructions.get)
      }
        /* SFREE */
      case Machine(heap, registerFile, Sequence(StackFree(amount), nextInstructions))
        if nextInstructions.isDefined &&
          m.canResolveToHeapTuple(StackPointer)
        => {
        val newStack = m.resolveHeapTuple(StackPointer).take(amount.value)
        registerFile.put(StackPointer.num, UniquePointer(newStack.asInstanceOf[HeapTuple]))
        Machine(heap, registerFile, nextInstructions.get)
      }
      case _ =>  super.-->(m)
    }
  }
}
