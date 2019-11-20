package cn.wbw;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取excel数据，存储到es中
 *
 * @author wbw
 * @date 2019/11/19 11:31
 */
public class EsExcelUtil {
    /**
     * excel 文件路径
     */
    private static final String EXCEL_PATH = "F:\\Desktop\\Top\\网络攻击专题\\日志列表(1).xls";

    private static List<EsDto> readExcel() {
        List<EsDto> dtos = CollUtil.newArrayList();
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(EXCEL_PATH));
        List<List<Object>> data = reader.read(0);
        List<Object> title = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            Map<String, String> source = new LinkedHashMap<>(title.size());
            List<Object> list = data.get(i);
            EsDto dto = new EsDto();
            for (int j = 0; j < list.size(); j++) {

                if ("_index".equals(title.get(j))) {
                    dto.setIndex(list.get(j).toString());
                } else if ("_id".equals(title.get(j))) {
                    dto.setId(list.get(j).toString());
                } else {
                    source.put(String.valueOf(title.get(j)), String.valueOf(list.get(j)));
                }
            }
            dto.setData(source);
            System.out.println(dto.builderIndexId());
            System.out.println();
            System.out.println(dto.toData());
            System.out.println();
            System.out.println();
            dtos.add(dto);
        }
        return dtos;
    }

    public static void main(String[] args) {
        List<EsDto> dtos = readExcel();
//        System.out.println(JSONUtil.parse(dtos).toJSONString(1));
    }
}
