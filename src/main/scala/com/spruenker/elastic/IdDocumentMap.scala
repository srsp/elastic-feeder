package com.spruenker.elastic

import com.sksamuel.elastic4s.source.DocumentMap

/**
 * Trait for classes that can be fed into ElasticSearch.
 *
 * @author Simon Sprünker
 */
trait IdDocumentMap extends DocumentMap {

  /**
   * Defines the id that is used as id for ElasticSearch.
   * @return id
   */
  def _id: String
  
}
