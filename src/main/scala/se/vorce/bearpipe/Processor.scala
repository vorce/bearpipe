package se.vorce.bearpipe

trait Processor {
  def operations(doc: BearDoc): BearOp
  def applicable(doc: BearDoc): Boolean
}
