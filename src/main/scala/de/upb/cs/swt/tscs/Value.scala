package de.upb.cs.swt.tscs

/**
  * Representation of a value
  * @param value the value to be represented
  */
class Value(value: String) extends Expression {
  override def toString: String = value
}
