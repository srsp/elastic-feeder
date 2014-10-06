# ElasticFeeder

[![Build Status](https://api.travis-ci.org/srsp/elastic-feeder.png)](https://travis-ci.org/srsp/elastic-feeder)

## What does it do?

ElasticFeeder is a very small library that helps you to get data into ElasticSearch quickly. It is written in Scala, so
it should be usable by every language on the JVM.

## How does it work?

Create a new elastic-feeder object:
```scala
val feeder = new ElasticFeeder(_index = "my-index")
```
This will generate give you a feeder that tries to use an ElasticSearch server on localhost:9300. If you want to use another
server:
```scala
val feeder = new ElasticFeeder("example.org", 9123, "my-index")
```
Now you have to give the feeder some data. You can feed it with Sequences (e.g. List) of objects, that implement the
__IdDocumentMap__ trait, which is really simple. It needs implementations of two methods:
```scala
trait IdDocumentMap extends DocumentMap {
  def _id: String
  def map: Map[String, Any]
}
```
The result of the _id-function will be used as the id of the document in ElasticSearch. The map-function just maps all
the properties of your model to the properties you want in your ElasticSearch document. Here is an example:
```scala
case class Order(contractNumber: Long, eventDate: Date, clerkId: String, brandId: Integer, signed: String, url: String) extends IdDocumentMap {

  override def _id: String = contractNumber.toString

  override def map: Map[String, Any] = {
    Map(
    "contractNumber" -> contractNumber,
    "eventDate" -> eventDate,
    "clerkId" -> clerkId,
    "brandId" -> brandId,
    "signed" -> { if (signed.equals("N")) false else true },
    "url" -> url
    )
  }
}
```
As you can see, you can even transform properties from your case class to something you want in your ElasticSearch
document.

Let's save some Orders into ElasticSearch:
```scala
  val orders: List[Order] = getOrders
  feeder.put(orders)
}
```
That's it. ElasticFeeder will take your list of Orders and feed it to ElasticSearch. It uses one or more bulk operations
to do that to keep things fast.

# Developers

ElasticFeeder is written in Scala and built with sbt:
```shell
sbt compile
```




