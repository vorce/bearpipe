package se.vorce.bearpipe

import scala.concurrent.duration.Duration

object DocTransformer {
  def transform(doc: BearDoc, ops: List[BearOp]): BearDoc = {
    ops.foldLeft(doc) {
      (d, op) => op(d)
    }
  }

  val operations =
    List(
      DocProcessors.nlp,
      DocProcessors.score(Duration.create("10s")),
      DocProcessors.categoryLimiter(10, 0.2))
}
