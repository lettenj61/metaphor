package metaphor

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{ global => g }

object Cli {

  def main(args: Array[String]): Unit = {
    jsMain()
  }

  def jsMain(): Unit = {
    val argv = g.process.argv.asInstanceOf[js.Array[String]]
    if (argv.length < 3) {
      println("Please input some code to extract")
    } else {
      val code = argv(2)
      println { Printer.showTree(code, true) }
    }
  }
}

