package cn.hba.copy;

import cn.hba.config.ElasticSearchUtil;
import cn.hba.config.ElasticsearchBase;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 复制数据
 *
 * @author wbw
 * @date 2019-11-24 12:36
 */
@Log4j2
public class EsCopyMain {
    private static RestClient es = new ElasticsearchBase().init();
    private static ElasticSearchUtil.Builder es_local = new ElasticSearchUtil.Builder(new ElasticsearchBase().initLocal());

    /**
     * 查询最近三天索引数据
     */
    public void copyIndexData() throws IOException {
        String today = DateUtil.today();
        String yesterday = DateUtil.yesterday().toDateStr();
        String beforeDay = DateUtil.offsetDay(DateUtil.yesterday(), -1).toDateStr();
        String month = DateUtil.date().toString("yyyy-MM");
        Response indices = es.performRequest("GET", "/_cat/indices?v");
        String entity = EntityUtils.toString(indices.getEntity());
        for (String en : entity.split("\n")) {
            for (String a : en.trim().split(" ")) {
                if (!a.startsWith("system-log") && !a.contains("cascading") && a.contains("2019-")) {
                    log.info("索引：{}", a);
                    try {
                        if (a.endsWith(today) || a.endsWith(yesterday) || a.endsWith(beforeDay) || a.endsWith(month)) {
                            Map<String, String> map = new HashMap<>(1);
                            map.put("size", "300");
                            Response response = es.performRequest("POST", a + "/_search", map);
                            entity = EntityUtils.toString(response.getEntity());

                            JSONObject hits = JSONUtil.parseObj(entity).getJSONObject("hits");
                            if (hits.getInt("total", 0) < 1) {
                                continue;
                            }
                            JSONArray hitsArray = hits.getJSONArray("hits");
                            int count = 0;
                            // 批量插入
                            BulkRequestBuilder bulkRequest = es_local.client.prepareBulk();
                            for (Object arr : hitsArray) {
                                try {
                                    if (arr.toString().contains("null")) {
                                        arr = arr.toString().replaceAll(":null,", ":\"\",")
                                                .replaceAll(": null,", ":\"\",");
                                    }
                                    JSONObject jsonObject = JSONUtil.parseObj(arr);
                                    Map source = jsonObject.get("_source", Map.class);
                                    if (source.containsValue(null)) {
                                        continue;
                                    }


                                    bulkRequest.add(es_local.client.prepareIndex(jsonObject.getStr("_index")
                                            , jsonObject.getStr("_type"), jsonObject.getStr("_id"))
                                            .setSource(source));
                                    count++;
                                    if (count % 10 == 0) {
                                        bulkRequest.execute().actionGet();
                                        bulkRequest = es_local.client.prepareBulk();
                                        count = 0;
                                    }
                                } catch (Exception e) {
                                    log.error("错误索引:{},异常信息:{},原始数据:{}", a, e.getMessage(), arr.toString());
                                }
                            }
                            if (count != 0) {
                                bulkRequest.execute().actionGet();
                            }
                        }
                    } catch (Exception e) {
                        log.error("错误索引:{},异常信息:{}", a, e.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EsCopyMain().copyIndexData();
    }
}
