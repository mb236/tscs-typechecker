package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.Expression

/**
  * Created by benhermann on 12.01.18.
  */
trait TypedAssemblyLanguageAST extends Expression with Semantics {
  override def -->(): Expression = -->(this)
}
