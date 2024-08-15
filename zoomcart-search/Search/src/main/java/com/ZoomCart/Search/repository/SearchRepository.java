package com.ZoomCart.Search.repository;

import com.ZoomCart.Search.entity.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository<Product,String> {
    @Query("{ \"multi_match\": { \"fields\":  [ \"productName\",\"brand\" ,\"os\", \"storage\",\"color\" ], \"query\": \"?0\", \"fuzziness\": \"AUTO\"}} , \"filter\": { \"range\": { \"price\": { \"gte\": 0 } } } ,\"sort\": [{ \"price\": { \"order\": \"asc\" } }] ")
    List<Product> findByProductNameOrBrandOrOsOrColorOrStorageFuzzyAndOrderByPriceAsc(String query);

}
