package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.{HeapElement, Value}

import scala.collection.mutable

/**
  * Represents a heap stored tuple of a certain length
  * @param length The length of the tuple
  */
class HeapTuple(override val length: Int) extends mutable.ArraySeq[Value](length) with HeapElement {

}
