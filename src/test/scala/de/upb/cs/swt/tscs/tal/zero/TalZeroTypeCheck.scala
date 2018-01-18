package de.upb.cs.swt.tscs.tal.zero

import org.scalatest.{FlatSpec, Matchers}

import scala.util.{Failure, Success}

class TalZeroTypeCheck extends FlatSpec with Matchers {

    "Simple multiline" should "typecheck" in {
      val program =
        "test: r1 := 5;\n" +
          "      r2 := exit;\n" +
          "      r1 := r2 + 2;\n" + // This should make the test FAIL!
          "      jump exit\n" +
          "exit: jump exit"

      val parserResult = new Syntax(program).Input.run()

      parserResult match {
        case Success(ast) => ast.typecheck() shouldBe a[Failure[_]]
        case _ => fail("Test program parsing failed")
      }
    }
      "Antoher Simple multiline" should "typecheck" in {
        val program =
          "test: r1 := 5;\n" +
            "      r2 := 2;\n" +
            "      r1 := r2 + 2;\n" +
            "      jump exit\n" +
            "exit: jump exit"

        val parserResult = new Syntax(program).Input.run()

        parserResult match {
          case Success(ast) => ast.typecheck() shouldBe a[Success[_]]
          case _ => fail("Test program parsing failed")
        }
      }
        "More complex multiline" should "typecheck" in {
          val program =
            "test: r1 := 5;\n" +
              "      r2 := 2;\n" +
              "      r1 := r2 + 2;\n" +
              "      jump b\n" +
              "a: r1 := test;\n" + // This should make the test FAIL!
              "      jump exit\n" +
              "b: r3 := 5;\n" +
              "      jump a\n" +
              "exit: jump exit"

          val parserResult = new Syntax(program).Input.run()

          parserResult match {
            case Success(ast) => ast.typecheck() shouldBe a [Failure[_]]
            case _ => fail("Test program parsing failed")
          }

        }

}
