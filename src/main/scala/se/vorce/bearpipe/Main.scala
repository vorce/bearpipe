package se.vorce.bearpipe

object Main extends App {
  lazy val docs = DocCreator.documentStream

  (docs take 10).foreach(d => println(DocTransformer.transform(d, DocTransformer.operations)))
}
