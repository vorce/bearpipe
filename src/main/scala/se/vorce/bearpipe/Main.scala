package se.vorce.bearpipe

object Main extends App {
  lazy val docs = DocCreator.observableDocuments

  (docs take 10)
    .map(d => DocTransformer.transform(d, DocTransformer.operations))
    .subscribe(n => println(n))
}
