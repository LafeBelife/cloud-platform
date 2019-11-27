package cn.hba.config;

/**
 * es 常量
 *
 * @author wbw
 * @date 2019/11/4 14:19
 */
public interface ElasticsearchConstant {
    /**
     * es 配置文件
     */
    String ES_PROP = "elasticsearch.properties";
    String ES_PROP_LOCAL = "local-es.properties";
    /**
     * 登录信息
     */
    String ES_UP = "xpack.security.user";
    /**
     * 集群节点地址
     */
    String ADDR_MAP = "cluster.addr_map";
    /**
     * 嗅探
     */
    String SNIFF = "client.transport.sniff";
    String NAME = "cluster.name";
    String INTERVAL = "client.transport.nodes_sampler_interval";
    String TIMEOUT = "client.transport.ping_timeout";
    String IGNORE = "client.transport.ignore_cluster_name";
}
