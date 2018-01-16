package de.upb.cs.swt.tscs.tal.one

import de.upb.cs.swt.tscs.tal.{HeapElement, Value}

import scala.collection.mutable

class HeapTuple(override val length: Int) extends mutable.ArraySeq[Value](length) with HeapElement {

}
