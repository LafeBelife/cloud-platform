package cn.hba.copy;

import cn.hba.config.ElasticSearchUtil;
import cn.hba.config.ElasticsearchBase;
import cn.hba.config.ElasticsearchConstant;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import lombok.extern.log4j.Log4j2;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 复制数据
 *
 * @author wbw
 * @date 2019-11-24 12:36
 */
@Log4j2
public class EsCopyMain {
    private static RestClient es = new ElasticsearchBase().authInit();
    private static ElasticSearchUtil.Builder es_local = new ElasticSearchUtil.Builder(new ElasticsearchBase().esInit());

    private static final String INDEX_SIZE;
    private static List<String> excludeIndex;
    private static List<String> includeIndex;

    static {
        Props props = new Props(ElasticsearchConstant.ES_COPY);
        INDEX_SIZE = props.getStr(ElasticsearchConstant.INDEX_SIZE, "300");
        excludeIndex.addAll(Arrays.asList(props.getStr(ElasticsearchConstant.EXCLUDE_INDEX, "").split(",")));
        includeIndex.addAll(Arrays.asList(props.getStr(ElasticsearchConstant.INCLUDE_INDEX, "").split(",")));
    }

    public static void setIncludeIndex(List<String> includeIndex) {
        EsCopyMain.includeIndex = includeIndex;
    }

    /**
     * 是否为需要索引
     *
     * @param index 索引
     * @return flag
     */
    private boolean isNeedIndex(String index) {
        // 排除
        for (String ex : excludeIndex) {
            if (index.contains(ex)) {
                return false;
            }
        }
        // 需要
        for (String in : includeIndex) {
            if (index.contains(in)) {
                return true;
            }
        }
        return false;
    }

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
                if (!this.isNeedIndex(a)) {
                    return;
                }
                log.debug("索引：{}", a);
                try {
                    if (!a.endsWith(today) || !a.endsWith(yesterday) || !a.endsWith(beforeDay) || !a.endsWith(month)) {
                       continue;
                    }
                    Map<String, String> map = new HashMap<>(1);
                    map.put("size", INDEX_SIZE);
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
                } catch (Exception e) {
                    log.error("错误索引:{},异常信息:{}", a, e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EsCopyMain().copyIndexData();
    }
}
