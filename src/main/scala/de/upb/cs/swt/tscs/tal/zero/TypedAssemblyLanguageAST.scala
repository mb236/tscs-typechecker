package de.upb.cs.swt.tscs.tal.zero

import de.upb.cs.swt.tscs.Expression
import de.upb.cs.swt.tscs.typed.{AsmTypes, TypeInformation, Typecheck}

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
  * Created by benhermann on 12.01.18.
  */
trait TypedAssemblyLanguageAST extends Expression with Semantics with Typecheck {
  override def -->(): Expression = -->(this)

  val Gamma = new mutable.HashMap[Expression, TypeInformation]
  val Psi = new mutable.HashMap[Expression, TypeInformation]
  var Program: Program = null

  override def typecheck(): Try[TypeInformation] = typecheck(this,
    new mutable.HashMap[Expression, TypeInformation](),
    new mutable.HashMap[Expression, TypeInformation]())

  override def typecheck(expr: Expression,
                         gamma: mutable.HashMap[Expression, TypeInformation],
                         psi: mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    expr match {
      case e: Program =>
        Program = e
        for (seq <- e.labeledSequences) {
          var current = seq.sequence
          while (current != null) {
            if (typecheck(current.instruction, gamma, psi).isFailure)
              return new Failure[TypeInformation](null)
            current = current.sequence.orNull
          }
        }
        return Success(AsmTypes.Null)

        // Idea: Typecheck labeled sequences in "jump-order".
        // If we jump from LS2 to LS1, we first need to typecheck LS2 so that
        // in LS1 the typing info from LS2 is available.

      case e: Jump =>
        if (typecheck(e.target, gamma, psi).get != AsmTypes.Label)
          return new Failure[TypeInformation](null)

        val targetLabel = e.target.asInstanceOf[LabelReference].label

        if (targetLabel == "exit") // HACK: Stop endless recursion at end of program
          return Success(AsmTypes.Null)

        val seq = Program.labeledSequences.find(s => s.labelDeclaration.label.equals(targetLabel)).get

        var current = seq.sequence
        while (current != null) {
          if (typecheck(current.instruction, gamma, psi).isFailure)
            return new Failure[TypeInformation](null)
          current = current.sequence.orNull
        }

        return Success(AsmTypes.Null)

      //case e: ConditionalJump =>
        // TODO

      case e: Mov =>
        val destinationType = typecheck(e.destinationRegister, gamma, psi)
        val valueType = typecheck(e.value, gamma, psi)

        destinationType match {
          // source, destination and value must be of same type
          case Success(t) => valueType match {
            case Success(t2) if t2 == t => Success(t)
            case _ => new Failure[TypeInformation](null)
          }

          // destination doesn't have a type yet
          case _ => valueType match {
            case Success(t) => storeAndWrap(e.destinationRegister, t, gamma) // assign type to register, can't change later
            case _ => new Failure[TypeInformation](null)
          }
        }

      case e: Add =>
        val sourceType = typecheck(e.sourceRegister, gamma, psi)
        val destinationType = typecheck(e.destinationRegister, gamma, psi)
        val valueType = typecheck(e.value, gamma, psi)

        destinationType match {
          case Success(t) =>
            // source, destination and value must be of same type
            sourceType match {
              case Success(t2) if t2 == t => valueType match {
                case Success(t3) if t3 == t => storeAndWrap(e, t, psi)
                case _ => new Failure[TypeInformation](null)
              }
              case _ => new Failure[TypeInformation](null)
            }

          // destination doesn't have a type yet
          case _ =>
            sourceType match {
              case Success(t) => valueType match {
                  case Success(t2) if t2 == t => storeAndWrap(e.destinationRegister, t, gamma) // assign type to register, can't change later
                  case _ => new Failure[TypeInformation](null)
              }
              case _ => new Failure[TypeInformation](null)
            }
        }

      // S-INT, S-VAL
      case e: IntegerValue =>
        storeAndWrap(e, AsmTypes.Int, psi)
        storeAndWrap(e, AsmTypes.Int, gamma)

      // S-LAB
      case e: LabelReference =>
        storeAndWrap(e, AsmTypes.Label, psi)
        storeAndWrap(e, AsmTypes.Label, gamma)

      // S-REG
      case e: Register =>
        gamma.get(e) match {
          case Some(t) => Success(t)
          case _ => new Failure[TypeInformation](null)
        }

      case _ => new Failure[TypeInformation](null)
    }
  }

  override def storeAndWrap(e: Expression, t: TypeInformation, typeEnv: mutable.HashMap[Expression, TypeInformation]): Try[TypeInformation] = {
    typeEnv.put(e, t)
    Success(t)
  }
}
