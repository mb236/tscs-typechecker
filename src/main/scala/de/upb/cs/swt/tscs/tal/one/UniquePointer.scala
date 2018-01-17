package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.{HeapElement, Value}

/**
  * Represents a unique pointer in TAL-1
  * @param value The value to be pointed to by the unique pointer
  */
case class UniquePointer(value : HeapElement) extends Value {

}
