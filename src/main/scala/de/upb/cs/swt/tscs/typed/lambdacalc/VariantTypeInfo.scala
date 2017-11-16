package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.typed.TypeInformation

// Example: "monday:Unit"
case class VariantTypeInformationPart(val fieldLabel: String, val typeAnnotation: TypeInformation) {
  override def toString: String = fieldLabel + ":" + typeAnnotation.typeInfo
}

// Example: "<monday:Unit, tuesday:Unit, wednesday:Unit>"
case class VariantTypeInformation(val types: Seq[VariantTypeInformationPart]) extends TypeInformation(toString) {
  override def toString: String = "<" + types.toStream.map(t => t.toString).mkString(", ") + ">"
}
