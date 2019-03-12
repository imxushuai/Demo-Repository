package fun.xushuai.elasticsearch;

import fun.xushuai.elasticsearch.pojo.Item;
import fun.xushuai.elasticsearch.repository.EsRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Data的子项目的使用方式基本上都是大同小异
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Autowired
    private ElasticsearchTemplate esClient;

    @Autowired
    private EsRepository esRepository;

    /**
     * 创建索引以及映射规则
     */
    @Test
    public void testCreateIndex() {
        // 创建索引
        esClient.createIndex(Item.class);
        // 创建映射
        esClient.putMapping(Item.class);
    }

    /**
     * 删除索引
     */
    @Test
    public void testDeleteIndex() {
        // 删除索引
        esClient.deleteIndex(Item.class);
    }

    /**
     * 新增/更新 数据
     * 若指定id的记录已存在，则为更新，否则为新增
     */
    @Test
    public void testSave() {
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item(1L, "小米8", "手机", "小米", 2999d, "https://xiaomi.com"));
        items.add(new Item(2L, "华为8", "手机", "华为", 2999d, "https://xiaomi.com"));
        items.add(new Item(3L, "小米 MIX2", "手机", "小米", 3999d, "https://xiaomi.com"));
        // 批量保存
        esRepository.saveAll(items);
        // 保存单个 esRepository.save(item1);
    }

    /**
     * 查询
     */
    public void testFindOne() {
        // 按ID查询
        esRepository.findById(1L);
        // 按条件查询,需要到接口自定义查询方法
        esRepository.findByTitle("小米");
    }

    /**
     * 删除数据
     */
    @Test
    public void testDelete() {
        esRepository.deleteById(1L);
    }

    /**
     * 借助Spring Data Elasticsearch使用原生查询
     */
    @Test
    public void testNativeSearch() {
        // 得到原生查询生成器对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米"));
        // 结果过滤 FetchSourceFilter的参数可以任填其一
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"title", "price", "images"}, null));
        // 分页 注意：页码从0开始，即0为第一页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 2));
        // 排序 fieldSort：按字段排序 scoreSort：按得分排序
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

        // 调用查询
        Page<Item> result = esRepository.search(nativeSearchQueryBuilder.build());

        /*
         * 获取查询结果:
         *      result.getTotalElements() : 获取总记录数
         *      result.getTotalPages() : 获取总页数
         *      result.getContent() : 获取查询结果
         *      result.getNumber() : 获取当前页码
         *      result.getPageable() : 获取分页对象
         *      result.getSize() : 获取每页记录数
         *      result.getSort() : 获取排序对象
         *
         */
        List<Item> content = result.getContent();
        content.forEach(item -> System.out.println(item.getTitle()));
    }

    /**
     * 聚合查询
     */
    @Test
    public void testAggregation() {
        // 得到原生查询生成器对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 聚合
        String termAggregationName = "aggregation_brand";
        String avgAggregationName = "aggregation_brand_sum_price";
        // 统计所有brand并得到每个品牌手机的总和
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(termAggregationName).field("brand")
                .subAggregation(AggregationBuilders.sum(avgAggregationName).field("price")));

        // 调用查询
        AggregatedPage<Item> result = esClient.queryForPage(nativeSearchQueryBuilder.build(), Item.class);

        // 获取查询结果
        Aggregations aggregations = result.getAggregations();
        StringTerms aggregationBrandResult = aggregations.get(termAggregationName);
        List<StringTerms.Bucket> buckets = aggregationBrandResult.getBuckets();
        // 输出结果
        System.out.println("品牌 | 款式数量 | 价格和");
        buckets.forEach(bucket -> {
            // 获取每个品牌手机总和
            InternalSum sumPrice = bucket.getAggregations().get(avgAggregationName);
            double sum = sumPrice.getValue();
            System.out.println(bucket.getKey() + " | " + bucket.getDocCount() + "款 | sum = " + sum);
        });

    }

}
