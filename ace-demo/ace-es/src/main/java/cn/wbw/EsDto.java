package cn.wbw;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * 数据传输
 *
 * @author wbw
 * @date 2019/11/19 13:08
 */
@Data
public class EsDto {

    private String index;
    private String type = "doc";
    private String id;

    private Object data;


    private static final String INDEX_MAT = "{}/{}/{}";

    public String builderIndexId() {
        return StrUtil.format(INDEX_MAT, index, type, id);
    }
    public String toData(){
        return JSONUtil.parseObj(data).toJSONString(1);
    }
}
