package cn.itrip.search;

import cn.itrip.search.bean.ItripHotelVO;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class ItripSearchApplicationTests {
    @Resource
    private SolrClient client;

    @Test
    public void test01() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery("*:*");
        query.setStart(0);
        query.setRows(5);
        QueryResponse response = client.query(query);
        List<ItripHotelVO> list = response.getBeans(ItripHotelVO.class);
        System.out.println(list);

    }

}
