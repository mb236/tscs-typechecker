package de.upb.cs.swt.tscs.typed.numexpr

/**
  * Created by benhermann on 02.11.17.
  */
case class TypeInformation(typeInfo : String)

object TypeInformations {
  def Nat = new TypeInformation("Nat")
  def Bool = new TypeInformation("Bool")
}
