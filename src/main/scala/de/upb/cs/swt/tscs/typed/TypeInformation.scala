package de.upb.cs.swt.tscs.typed

/**
  * Created by benhermann on 02.11.17.
  */
class TypeInformation(typeInfo : String)

class SecTypeInformation(typeInfo : String, val secLevel : SecurityLevel) extends TypeInformation(typeInfo)

case class BaseTypeInformation(typeInfo : String, override val secLevel : SecurityLevel) extends SecTypeInformation(typeInfo, secLevel) {
  override def toString: String = typeInfo
}
case class FunctionTypeInformation(sourceType : SecTypeInformation, targetType : SecTypeInformation)
  extends SecTypeInformation(sourceType.toString + " -> " + targetType.toString, targetType.secLevel) {
  override def toString: String = "["+ sourceType + "->"+ targetType  + "]"
}

case class ListTypeInformation(listType : TypeInformation) extends TypeInformation("List " + listType.toString) {
  override def toString: String = "List " + listType.toString
}

object TypeInformations {
  def NatLow = new BaseTypeInformation("Nat", SecurityLevels.Low)
  def BoolLow = new BaseTypeInformation("Bool", SecurityLevels.Low)
  def AbstractBaseType = new BaseTypeInformation("A", SecurityLevels.Low)

  def NatHigh = new BaseTypeInformation("Nat", SecurityLevels.High)
  def BoolHigh = new BaseTypeInformation("Bool", SecurityLevels.High)
}

case class SecurityLevel(s: String)

object SecurityLevels {
  def Low = SecurityLevel("Low")
  def High = SecurityLevel("High")
}