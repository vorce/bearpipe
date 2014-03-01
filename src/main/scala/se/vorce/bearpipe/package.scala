package se.vorce

package object bearpipe {
  type BearDoc = Map[String, Any]
  type BearOp = BearDoc => BearDoc
  type Category = (String, Double)
}
