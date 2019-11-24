package run.fgf45.javaer.dao;

import org.apache.ibatis.annotations.Param;
import run.fgf45.javaer.nosql.elasticsearch.document.EsProduct;

import java.util.List;

public interface EsProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
