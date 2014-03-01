package se.vorce.bearpipe

import scala.util.Random

object DocCreator {
  def content: String = {
    Random.nextString(Random.nextInt(10000))
  }

  def title: String = {
    val titleWords = List("The zoo is a little cold".split(" "),
      "Putin ask for Authorization to send Russian troops to the Ukraine".split(" "),
      "Valve VR prototype is “lightyears ahead of the original Oculus Dev Kit,” says dev after studio visit".split(" "),
      "Student Loans Are Ruining Your Life. Now They’re Ruining the Economy, Too (TIME)".split(" "),
      "Scientists propose teaching reproducibility to aspiring scientists using software to make concepts feel logical rather than cumbersome: Ability to duplicate an experiment and its results is a central tenet of scientific method, but recent research shows a lot of research results to be irreproducible".split(" "),
      "'Vikings' Gets 3.6M Viewers In Season 2 Debut".split(" "),
      "35 high school students in Georgia just released a book together titled \"I Will Make A Difference,\" which takes a look at their collective plans to make a positive impact on the world as they become adults. The book features a chapter from each of the student authors.".split(" "),
      "27 dead in attack by knife-wielding men at train station in Kunming in south-west China, state news agency says".split(" "),
      "Full draft text of the Transatlantic Trade and Investment Partnership leaked".split(" "),
      "Israeli military kill Palestinian woman near Gaza border: hospital | The Israeli military shot and killed a Palestinian woman in the Gaza Strip in an area near the border that Israel has declared a no-go zone for Palestinians, , local hospital officials said".split(" "),
      "South Korea sends formal proposal to North Korea for regular family reunions of families separated since the Korean War.".split(" "),
      "Egyptian army's AIDS cure claim met with ridicule".split(" "),
      "Polio vaccination team ambushed in Pakistan with IED's. Over 12 killed.".split(" "),
      "Gold Fix Study Shows Signs of Decade of Bank Manipulation".split(" "),
      "Scotland to offer gay Ugandans asylum".split(" "),
      "Russia has brought in extra 6,000 troops to Ukraine".split(" "),
      "Canada really set a standard in 2010".split(" ")).flatten
    Random.shuffle(titleWords).take(Math.max(3, Random.nextInt(35))).mkString(" ")
  }

  def author: String = {
    Random.shuffle(List("John", "Lisa", "Martin", "Natasha", "Rich",
      "Kim", "Lee", "Mohamed", "Fatima", "Juan", "Mia", "Cheng", "Fang")).head + " " +
    Random.shuffle(List("Smith", "Wang", "Li", "Svensson", "Azzi", "Schmidt", "Bernard", "Garcia", "Silva")).head
  }

  def url: String = {
    "http://" + (Random.alphanumeric take Random.nextInt(300)).mkString + ".com"
  }

  def doc: BearDoc = {
    Map.empty[String, Any]
      .updated("id", Random.nextLong())
      .updated("source.url", url)
      .updated("body.title", title)
      .updated("meta.author", author)
      .updated("body.content", content)
  }

  def documentStream: Stream[BearDoc] = {
    Stream.cons(doc, documentStream)
  }
}
