package cn.hba.config;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * es 配置初始化
 *
 * @author wbw
 * @date 2019/11/4 14:16
 */
@Slf4j
public class ElasticsearchBase {
    private JSONObject addr;

    /**
     * 初始化 es client
     *
     * @return TransportClient
     */
    public RestClient init() {
        try {

            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials("admin", "es@1312202"));

            // 该方法接收HttpAsyncClientBuilder的实例作为参数，对其修改后进行返回
            RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.202.13", 9210))
                    .setHttpClientConfigCallback(httpClientBuilder -> {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);//提供一个默认凭据
                    });
            return builder.build();
        } catch (Exception e) {
            log.error("加载es集群配置失败", e);
        }
        return null;
    }

    public TransportClient initLocal() {
        try {
            Settings.Builder builder = Settings.builder();
            this.loadEsProp(builder,ElasticsearchConstant.ES_PROP_LOCAL);
            TransportClient transportClient = new PreBuiltTransportClient(builder.build());
            addr.forEach((ip, port) -> {
                try {
                    transportClient.addTransportAddress(
                            new TransportAddress(InetAddress.getByName(ip), NumberUtil.parseInt(String.valueOf(port))));
                } catch (UnknownHostException e) {
                    log.error("加载es集群地址失败", e);
                }
            });
            return transportClient;
        } catch (Exception e) {
            log.error("加载es集群配置失败", e);
        }
        return null;
    }

    /**
     * 加载elasticsearch.properties 配置
     *
     * @param builder Settings.Builder
     */
    private void loadEsProp(Settings.Builder builder,String  pro) {
        Props prop = Props.getProp(pro);
        if (prop.containsKey(ElasticsearchConstant.NAME)) {
            builder.put(ElasticsearchConstant.NAME, prop.getStr(ElasticsearchConstant.NAME));
        }
        if (prop.containsKey(ElasticsearchConstant.SNIFF)) {
            builder.put(ElasticsearchConstant.SNIFF, prop.getBool(ElasticsearchConstant.SNIFF, true));
        }
        if (prop.containsKey(ElasticsearchConstant.INTERVAL)) {
            builder.put(ElasticsearchConstant.INTERVAL, prop.getInt(ElasticsearchConstant.INTERVAL, 5));
        }
        if (prop.containsKey(ElasticsearchConstant.TIMEOUT)) {
            builder.put(ElasticsearchConstant.TIMEOUT, prop.getInt(ElasticsearchConstant.TIMEOUT, 5));
        }
        if (prop.containsKey(ElasticsearchConstant.IGNORE)) {
            builder.put(ElasticsearchConstant.IGNORE, prop.getBool(ElasticsearchConstant.IGNORE, true));
        }
        if (prop.containsKey(ElasticsearchConstant.ES_UP)){
            builder.put(ElasticsearchConstant.ES_UP, prop.getStr(ElasticsearchConstant.ES_UP));
        }
        String addrStr = prop.getStr(ElasticsearchConstant.ADDR_MAP);
        addr = JSONUtil.parseObj(addrStr);
    }
}
