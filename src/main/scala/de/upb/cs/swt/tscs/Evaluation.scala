package de.upb.cs.swt.tscs

/**
  * Extends expressions with a framework for small-step semantics style evaluation
  */
trait Evaluation extends Expression{

  def -->(): Expression

  def -->(expr: Expression): Expression

  def progressPossible(expr: Expression) : Boolean = {
    val nextProgression = -->(expr)
    (nextProgression != null)
  }

   def -->*(): Expression = {
    var expr = -->(this)

    while (true) {
      if (expr.isInstanceOf[Value]) return expr
      if (expr == null) {
        println("Term is stuck")
        return null
      }
      expr = -->(expr)
    }
    return null
  }
}
