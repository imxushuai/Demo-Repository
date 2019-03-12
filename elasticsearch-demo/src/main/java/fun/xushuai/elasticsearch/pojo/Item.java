package fun.xushuai.elasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "goods", type = "item", shards = 1)
public class Item {
    @Id
    Long id;

    @Field(type = FieldType.text, analyzer = "ik_smart")
    String title; //标题

    @Field(type = FieldType.keyword)
    String category;// 分类

    @Field(type = FieldType.keyword)
    String brand; // 品牌

    @Field(type = FieldType.Double)
    Double price; // 价格

    @Field(type = FieldType.keyword, index = false)
    String images; // 图片地址
}
