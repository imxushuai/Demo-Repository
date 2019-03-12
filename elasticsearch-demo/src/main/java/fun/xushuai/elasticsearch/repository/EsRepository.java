package fun.xushuai.elasticsearch.repository;

import fun.xushuai.elasticsearch.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsRepository extends ElasticsearchRepository<Item, Long> {

    /**
     * 按title查询
     * @param title title
     * @return ItemList
     */
    List<Item> findByTitle(String title);

}
