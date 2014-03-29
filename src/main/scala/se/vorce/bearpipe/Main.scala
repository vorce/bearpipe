package se.vorce.bearpipe

import rx.lang.scala.Scheduler
import rx.lang.scala.schedulers.ExecutorScheduler
import java.util.concurrent.{TimeUnit, ScheduledThreadPoolExecutor, ScheduledExecutorService}

object Main extends App {
  lazy val docs = DocCreator.observableDocuments

  val executor: ScheduledExecutorService = new ScheduledThreadPoolExecutor(5)
  val scheduler: Scheduler = ExecutorScheduler.apply(executor)

  (docs take 50)
    .map(DocTransformer.transform)
      .subscribeOn(scheduler)
      .observeOn(scheduler)
      .doOnCompleted(() => executor.shutdown())
      .flatMap(o => o)
      .subscribe(od => println(od))
}
