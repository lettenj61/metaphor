import $ivy.`org.scala-js::scalajs-tools:0.6.26`

@

// This will become `Ast_2`

import java.io.Writer
import java.io.StringWriter

import org.scalajs.core.ir
import org.scalajs.core.ir.Position
import org.scalajs.core.tools.javascript.{ Trees, Printers }

val js: Trees.type = Trees

private object W extends Writer {
  var debug: Boolean = _
  private[this] var sw: StringWriter = null
  def write(cbuf: Array[Char], off: Int, pos: Int): Unit = {
    if (sw == null) refresh
    sw.write(cbuf, off, pos)
    if (debug) println(sw.toString)
  }
  def flush(): Unit = {
    if (sw == null) refresh
    sw.flush()
  }
  def close(): Unit = {}
  def refresh(): Unit = {
    sw = new StringWriter
  }
  def dump(): String = sw.toString
}

implicit class IdentString(val s: String) extends AnyVal {
  def i(implicit pos: Position): js.Ident = js.Ident(s, None)(pos)
}

implicit val noPosition: Position = Position.NoPosition

implicit def string(s: String): js.Tree =
  js.StringLiteral(s)

implicit def boolean(b: Boolean): js.Tree =
  js.BooleanLiteral(b)

implicit def double(n: Double): js.Tree =
  js.DoubleLiteral(n)

def printJs(tree: js.Tree): String = {
  val p = new Printers.JSTreePrinter(W)
  p.printTree(tree, true)
  val o = W.dump()
  W.refresh()
  o
}

