package de.upb.cs.swt.tscs.typed

/**
  * Created by benhermann on 02.11.17.
  */
class TypeInformation(typeInfo : String)

case class BaseTypeInformation(typeInfo : String) extends TypeInformation(typeInfo)
case class FunctionTypeInformation(sourceType : TypeInformation, targetType : TypeInformation)
  extends TypeInformation(sourceType.toString + " -> " + targetType.toString)

object TypeInformations {
  def Nat = new BaseTypeInformation("Nat")
  def Bool = new BaseTypeInformation("Bool")
  def AbstractBaseType = new BaseTypeInformation("A")
}
