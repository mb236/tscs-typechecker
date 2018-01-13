package de.upb.cs.swt.tscs.typed.seclambda

import de.upb.cs.swt.tscs.typed.{TypeInformation, TypeInformations}
import de.upb.cs.swt.tscs.typed.lambdacalc.extensions.basetyped.BasicTypeSyntax
import org.parboiled2.{CharPredicate, Rule1}


trait SecSyntax extends BasicTypeSyntax {
  override def TypeInfo = rule {
    SecBaseTypeInfo | SecBoolTypeInfo | SecFunctionTypeInfo
  }

  def SecBaseTypeInfo = rule {
    "#" ~ BaseTypeInfo ~ "," ~ KappaInfo ~ "#" ~> SecTypeInformation
  }

  def SecBoolTypeInfo = rule {
    "#" ~ BoolTypeInfo ~ "," ~ KappaInfo ~ "#" ~> SecTypeInformation
  }

  def SecFunctionTypeInfo = rule {
    "#" ~ FunctionType ~ "," ~ KappaInfo ~ "#" ~> SecTypeInformation
  }

  def KappaInfo = rule {
    capture(oneOrMore(CharPredicate.Alpha)) ~> SecurityLevel
  }

}
