package cn.hba.en.event.type;

import lombok.Getter;

/**
 * 策略规则
 *
 * @author wbw
 * @date 2019年11月5日11:18:45
 */
@Getter
public enum StrategyEnum {
    /**
     * 策略规则字段key及示意
     */
    RULES("rules", "规则"), POLICIES("policies", "策略"), AGENCY("agency", "代理"),
    PROTOCOL("protocol", "协议"), OTHER("other", "其他");

    private String key;
    private String value;

    StrategyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
