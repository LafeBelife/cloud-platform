package cn.hba.config;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import lombok.extern.slf4j.Slf4j;
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
class ElasticsearchBase {
    private JSONObject addr;

    /**
     * 初始化 es client
     *
     * @return TransportClient
     */
    TransportClient init() {
        try {
            Settings.Builder builder = Settings.builder();
            this.loadEsProp(builder);
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
    private void loadEsProp(Settings.Builder builder) {
        Props prop = Props.getProp(ElasticsearchConstant.ES_PROP);
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
        String addrStr = prop.getStr(ElasticsearchConstant.ADDR_MAP);
        addr = JSONUtil.parseObj(addrStr);
    }
}
