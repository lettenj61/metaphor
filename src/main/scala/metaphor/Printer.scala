package metaphor

import scala.meta._
import scala.meta.dialects.Sbt1

import pprint.PPrinter

object Printer {
  type Result = Parsed[Tree]
  val renderer: PPrinter = PPrinter.BlackWhite.copy(
    defaultHeight = Int.MaxValue,
    defaultWidth = 80
  )
  def showTree(code: String, asScript: Boolean): String = {
    val result: Result =
      if (asScript) Sbt1(code).parse[Source]
      else code.parse[Source]

    result match {
      case Parsed.Success(tree) =>
        val pretty = renderer(tree).plainText
        List("Code:", tree.syntax, "AST:", pretty).mkString("\n")
      case _ =>
        result.toString
    }
  }
}

