package se.vorce.bearpipe

object Main extends App {
  lazy val docs = DocCreator.observableDocuments

  (docs take 10)
    .map(DocTransformer.transform).subscribe(od =>
      od.subscribe(d => println(d)))
}
