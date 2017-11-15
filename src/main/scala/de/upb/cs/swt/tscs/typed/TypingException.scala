package de.upb.cs.swt.tscs.typed

import de.upb.cs.swt.tscs.Expression

/**
  * Created by benhermann on 15.11.17.
  */
class TypingException(failingExpression: Expression) extends RuntimeException("Typing failed for: " + failingExpression.toString())
