package de.upb.cs.swt.tscs.typed

/**
  * Created by benhermann on 02.11.17.
  */
class TypeInformation(typeInfo : String)

case class BaseTypeInformation(typeInfo : String) extends TypeInformation(typeInfo) {
  override def toString: String = typeInfo
}
case class FunctionTypeInformation(sourceType : TypeInformation, targetType : TypeInformation)
  extends TypeInformation(sourceType.toString + " -> " + targetType.toString) {
  override def toString: String = "["+ sourceType + "->"+ targetType  + "]"
}

case class ListTypeInformation(listType : TypeInformation) extends TypeInformation("List " + listType.toString) {
  override def toString: String = "List " + listType.toString
}

object TypeInformations {
  def Nat = new BaseTypeInformation("Nat")
  def Bool = new BaseTypeInformation("Bool")
  def AbstractBaseType = new BaseTypeInformation("A")
}
