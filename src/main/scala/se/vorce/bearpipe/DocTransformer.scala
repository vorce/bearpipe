package se.vorce.bearpipe

import scala.concurrent.duration.Duration
import rx.lang.scala.Observable

object DocTransformer {
  def transform(doc: BearDoc): Observable[BearDoc] = {
    val enrichedDoc = DocProcessors.nlp(doc).map(DocProcessors.categoryLimiter(10, 0.2))
    val scoredDoc = DocProcessors.score(doc, Duration.create("10s"))
    scoredDoc.zip(enrichedDoc).map(ds => ds._1 ++ ds._2)
  }
}
