package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.zero.{IntegerValue, Register}

/**
  * Represents a memory access
  * @param register The register containing the base address of the memory location to be accesses
  * @param n The optional offset to be added to the base address
  */
case class MemoryAccess (register: Register, n: Option[IntegerValue]) {

}
