package com.spruenker.elastic

import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.{BulkDefinition, ElasticClient, IndexDefinition}

/**
 * Feeds objects into ElasticSearch.
 *
 * @author Simon Sprünker
 *
 * @param _host Host where ElasticSearch is running. Default is 'localhost'.
 * @param _port Port where ElasticSearch is running. Default is '9300'.
 * @param _index Name of the Index where objects should be put.
 */
class ElasticFeeder(_host: String = "localhost", _port: Integer = 9300, _index: String) {

  val client = ElasticClient.remote(_host, _port)
  client.execute { create index _index }

  val sliceSize = 1000

  /**
   * Puts a single object into elastic search.
   *
   * @param obj Object to put.
   */
  def put(obj: IdDocumentMap): Unit = {
    put(List(obj))
  }

  /**
   * Puts a list of objects into elastic search.
   *
   * @param objs Objects to save into elastic search
   * @return Time in milliseconds it took to do the job.
   */
  def put(objs: Seq[IdDocumentMap]): Long = {

    def putSlice(slice: Seq[IdDocumentMap]) = {

      val result = client.execute {
        indexDefs(slice)
      }

      // If we don't await the result and feed another bulk operation into elastic4s, things get lost.
      result.await

    }

    // Take the time
    val start = System.nanoTime()

    // Put stuff into elastic search.
    val results = objs.sliding(sliceSize,sliceSize).toList.map(group =>
      putSlice(group)
    )

    // Take the time
    (System.nanoTime() - start) / 1000000
  }

  private def indexDefs(objs: Seq[IdDocumentMap]): BulkDefinition = {
    new BulkDefinition(objs.map(indexDef))
  }

  private def indexDef(obj: IdDocumentMap): IndexDefinition = {
    val indexType = _index + "/" + obj.getClass.getSimpleName
    index into indexType doc obj id obj._id
  }


}
