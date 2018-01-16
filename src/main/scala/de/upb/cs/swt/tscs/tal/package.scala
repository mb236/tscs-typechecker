package de.upb.cs.swt.tscs

import scala.collection.mutable

package object tal {
  type Heap[HT <: HeapElement] = mutable.HashMap[String, HT]
  type RegisterFile = mutable.HashMap[Integer, de.upb.cs.swt.tscs.tal.Value]
}
