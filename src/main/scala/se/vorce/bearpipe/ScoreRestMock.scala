package se.vorce.bearpipe

import scala.util.Random
import scala.concurrent._
import scala.concurrent.duration.Duration
import ExecutionContext.Implicits.global

object ScoreRestMock {
  def get(host: String, timeout: Duration): Long = {
    val cf: Future[Long] = future {
      blocking(Thread.sleep((Random.nextFloat() * 1000).toLong))
      Math.abs(Random.nextLong())
    }

    Await.result(cf, timeout)
  }
}
