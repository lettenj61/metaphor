package metaphor

import org.scalajs.core.ir
import org.scalajs.core.ir.Position
import org.scalajs.core.tools.javascript.{ Trees => js }
import scala.{ meta => sc }

import TransformErrors._

object TransformErrors {
  sealed abstract class TransformationException(
    detail: String
  ) extends Exception {
    override def getMessage = detail
  }
  case class NoMatchingTree(detail: String)
      extends TransformationException(detail)
}

trait TransformerApi {
  implicit val jsPosition: Position = Position.NoPosition

  type Scala = sc.Tree
  type Js = js.Tree

  type Transformer[S <: Scala] = Function1[S, Js]

  // -- Utils
  class StringTreeOps(val string: String) {
    // TODO: move entire class to TreeDSL
    def i(implicit pos: Position): js.Ident =
      js.Ident(string, None)(pos)
  }
  implicit def stringTreeOps(s: String) = new StringTreeOps(s)

  // -- Errors
  def noMatchingTree(detail: String): Nothing =
    throw NoMatchingTree(detail)

  // -- Transformation
  object literals extends Transformer[sc.Lit] {
    import sc.Lit
    def apply(tree: Lit): Js = tree match {
      case Lit.Null()       => js.Null()
      case Lit.Int(i)       => js.IntLiteral(i)
      case Lit.Double(ds)   => js.DoubleLiteral(ds.toDouble)
      case Lit.Float(fs)    => js.DoubleLiteral(fs.toDouble) // TODO: consider Scala.js semantics
      case Lit.Byte(b)      => js.IntLiteral(b)
      case Lit.Short(s)     => js.IntLiteral(s)
      case Lit.Char(ch)     => js.IntLiteral(ch) // TODO: use string?
      case Lit.Long(l)      => js.DoubleLiteral(l.toDouble) // FIXME: preserve value
      case Lit.Boolean(b)   => js.BooleanLiteral(b)
      case Lit.Unit()       => js.Undefined()
      case Lit.String(s)    => js.StringLiteral(s)
      case Lit.Symbol(sym)  =>
        noMatchingTree(s"Metaphor does not support Scala symbol literal: $sym")
    }
  }

  object terms extends Transformer[sc.Term] {
    import sc.Term
    def apply(term: Term): Js = term match {
      case Term.This(_)   => js.This() // TODO: qual
      case Term.Super(_)  => js.Super() // TOOD: consider OutputMode
      case Term.Name(i)   => js.VarRef(js.Ident(i))
      // apply
      case Term.ApplyType(_, _) =>
        noMatchingTree("No type apply on JavaScript")
      case _              => ???
    }
  }
}

object Transform extends TransformerApi
