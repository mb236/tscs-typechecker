package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.tal.Value

case class IntegerValue(value : Int) extends Value  {

  def +(operand : IntegerValue) = {
    IntegerValue((this.value + operand.value))
  }
}
