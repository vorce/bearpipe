package se.vorce.bearpipe

import scala.concurrent.duration.Duration
import scala.util.Random

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
      doc.get(categories) match {
      case Some(xs: List[Category]) => doc.updated(categories, xs.filter(onThreshold).sortBy(c => -c._2).take(amount))
      case _ => doc
    }
  }

  def score(timeout: Duration): BearOp = {
    val urlField: String = "source.url"
    val scoreField: String = "enrichments.score"

    (doc: BearDoc) =>
      doc.get(urlField) match {
        case Some(url: String) => fieldSetter(scoreField, ScoreRestMock.get(url, timeout))(doc)
        case _ => doc
      }
  }

  def nlp: BearOp = {
    def cat = () => ("Category" + Math.abs(Random.nextInt()), Random.nextDouble())
    def cats = for(i <- List.range(0, Random.nextInt(50))) yield cat()

    val sentiments = List("POSITIVE", "NEGATIVE", "NEUTRAL")
    def sentiment = () => Random.shuffle(sentiments).head

    (doc: BearDoc) =>
      doc.updated("enrichments.categories", cats)
         .updated("enrichments.sentiment", sentiment())
         .updated("enrichments.language", Random.shuffle(List("en", "jp", "sv", "foo")).head)
  }
}
