package de.upb.cs.swt.tscs.typed.seclambda

import de.upb.cs.swt.tscs.typed.TypeInformation


case class SecTypeInformation(innerType : TypeInformation, kappa : SecurityLevel) extends TypeInformation(innerType.toString) {

}
