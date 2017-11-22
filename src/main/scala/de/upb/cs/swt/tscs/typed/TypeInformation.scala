package de.upb.cs.swt.tscs.typed

/**
  * Created by benhermann on 02.11.17.
  */
class TypeInformation(val typeInfo : String)

case class BaseTypeInformation(override val typeInfo : String) extends TypeInformation(typeInfo) {
  override def toString: String = typeInfo
}
case class FunctionTypeInformation(sourceType : TypeInformation, targetType : TypeInformation)
  extends TypeInformation(sourceType.toString + " -> " + targetType.toString) {
  override def toString: String = "["+ sourceType + "->"+ targetType  + "]"
}

object TypeInformations {
  def Nat = new BaseTypeInformation("Nat")
  def Bool = new BaseTypeInformation("Bool")
  def Unit = new BaseTypeInformation("Unit")
  def AbstractBaseType = new BaseTypeInformation("A")
}
