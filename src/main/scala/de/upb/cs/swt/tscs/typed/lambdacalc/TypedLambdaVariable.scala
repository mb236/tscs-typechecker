package de.upb.cs.swt.tscs.typed.lambdacalc

import de.upb.cs.swt.tscs.lambdacalc.LambdaVariable
import de.upb.cs.swt.tscs.typed.TypeInformation

/**
  * Represents a variable in the λ calculus
  */
case class TypedLambdaVariable(val variable : String, typeAnnotation: Option[TypeInformation]) extends LambdaVariable(variable) with TypedLambdaExpression {
  override def toString: String = { variable + {
    typeAnnotation match {
      case o if !o.isEmpty => " : " + o.get.toString
      case _ => ""
    }}
  }


  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that : TypedLambdaVariable =>  that.variable.equals(this.variable) && that.typeAnnotation==this.typeAnnotation
      case _ => false
    }
  }

  override def hashCode(): Int = {
    val prime = 179
    var result = 1
    result = prime * result + variable.hashCode
    return result
  }
}

