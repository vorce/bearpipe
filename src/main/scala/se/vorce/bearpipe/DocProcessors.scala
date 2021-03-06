package se.vorce.bearpipe

import scala.concurrent.duration.Duration
import scala.util.Random
import rx.lang.scala.Observable

object DocProcessors {
  def fieldSetter(fieldId: String, fieldValue: Any): BearOp = {
    (doc: BearDoc) => doc.updated(fieldId, fieldValue)
  }

  def categoryLimiter(amount: Int, threshold: Double): BearOp = {
    val categories: String = "enrichments.categories"

    def onThreshold(c: Category): Boolean = {
      c._2 > threshold
    }

    (doc: BearDoc) =>
      println(Thread.currentThread().toString + ": category limit")

      doc.get(categories) match {
      case Some(xs: List[Category]) => doc.updated(categories, xs.filter(onThreshold).sortBy(c => -c._2).take(amount))
      case _ => doc
    }
  }

  def score(doc: BearDoc, timeout: Duration): Observable[BearDoc] = {
    Observable.items(scoreFn(timeout)(doc))
  }

  private def scoreFn(timeout: Duration): BearOp = {
    val urlField: String = "source.url"
    val scoreField: String = "enrichments.score"

    (doc: BearDoc) =>
      println(Thread.currentThread().toString + ": score")

      doc.get(urlField) match {
        case Some(url: String) => fieldSetter(scoreField, ScoreRestMock.get(url, timeout))(doc)
        case _ => doc
      }
  }

  def nlp(in: BearDoc): Observable[BearDoc] = {
    def cat = () => ("Category" + Math.abs(Random.nextInt()), Random.nextDouble())
    def cats = for(i <- List.range(0, Random.nextInt(50))) yield cat()

    val sentiments = List("POSITIVE", "NEGATIVE", "NEUTRAL")
    def sentiment = () => Random.shuffle(sentiments).head

    Observable.items(
      in.updated("enrichments.categories", cats)
        .updated("enrichments.sentiment", sentiment())
        .updated("enrichments.language", Random.shuffle(List("en", "jp", "sv", "foo")).head))
      .doOnEach((_: BearDoc) => println(Thread.currentThread().toString + ": nlp"))
  }
}
