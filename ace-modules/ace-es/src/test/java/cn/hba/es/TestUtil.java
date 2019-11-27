package cn.hba.es;

import cn.hba.config.ElasticSearchUtil;
import cn.hba.config.ElasticsearchBase;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.Map;

/**
 * @author wbw
 * @date 2019/11/4 13:58
 */
public class TestUtil {
//    @Test
//    public void test01() {
//        new ElasticSearchUtil.Builder(new ElasticsearchBase().init()).getClusterInfo();
//    }


    @Test
    public void test02(){
        String addrStr = "{'127.0.0.1':9310,'10.0.1.89':9310}";
        System.out.println(JSONUtil.parseObj(addrStr).toJSONString(2));

        System.out.println(DateUtil.date().toString("yyyy-MM"));
    }
}
