package metaphor

import scala.scalajs.js
import scala.meta._

object Cli extends js.JSApp {

  type P0 = Parsed[Tree]
  def extractResult(p0: P0): String = p0 match {
    case Parsed.Success(tree) =>
      List("Code: ", tree.syntax, "AST: ", tree.structure).mkString("\n")
    case _ => p0.toString
  }

  def collectArgs(): js.Array[String] =
    js.Dynamic.global.process.argv.asInstanceOf[js.Array[String]]

  def main(): Unit = {
    val args = collectArgs()
    if (args.length < 3) {
      println("Please input some code to extract")
    } else {
      val code = args(2)
      val parsed = code.parse[Stat]
      println(extractResult(parsed))
    }
  }
}

