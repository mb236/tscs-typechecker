package de.upb.cs.swt.tscs.tal

import scala.collection.mutable

/**
  * Created by benhermann on 12.01.18.
  */
package object zero {
  type Heap = mutable.HashMap[String, Sequence]
  type RegisterFile = mutable.HashMap[Integer, Value]
}
