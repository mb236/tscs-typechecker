package de.upb.cs.swt.tscs.tal.zero

case class IntegerValue(value : Integer) extends Value  {

  def +(operand : IntegerValue) = {
    IntegerValue((this.value + operand.value))
  }
}
