package cn.wbw.demo.log;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 读取flume原始日志
 *
 * @author wbw
 * @date 2019/12/2 9:00
 */
@Log4j2
public class FlumeLogUtil {

    /**
     * 根据文件夹路径写入数据
     *
     * @param folder 文件夹
     * @param map    数据
     */
    public static void writeTextLog(String folder, Map<String, List<String>> map) {
        if (!FileUtil.isDirectory(folder)) {
            log.info("文件夹路径错误:\t{}", folder);
            return;
        }
        final String finalFolder = FileUtil.getAbsolutePath(folder);
        map.forEach((k, v) -> {
            String path = finalFolder + File.separator + k + ".log";
            FileUtil.writeLines(v, path, CharsetUtil.UTF_8,true);
            log.info("文件写入成功:\t{}", path);
        });
    }

    /**
     * 读取每个文本数据
     *
     * @param path 路径
     * @return ip，文本
     */
    public static Map<String, List<String>> readLog(String path) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        if (!FileUtil.isFile(path)) {
            log.info("路径错误:\t{}", path);
            return map;
        }
        List<String> list = FileUtil.readLines(path, CharsetUtil.UTF_8);
        disReadLines(list, map, path);
        return map;
    }

    private static final String IP = "-  ip 地址:";
    private static final String SYSLOG = "-  syslog 信息:";

    /**
     * 处理每一行数据
     *
     * @param list 数据
     * @param map  结果
     */
    private static void disReadLines(List<String> list, Map<String, List<String>> map, String path) {
        for (int i = 0; i < list.size(); i++) {
            String logLine = list.get(i);
            if (logLine.contains("-  ip 地址:")) {
                String ip = StrUtil.trim(logLine.split(IP)[1]);
                if (!map.containsKey(ip)) {
                    map.put(ip, new LinkedList<>());
                }
                try {
                    do {
                        if (i == list.size() - 1) {
                            return;
                        }
                        logLine = list.get(++i);
                    } while (!logLine.contains("-  syslog 信息:"));
                    String syslog = StrUtil.trim(logLine.split(SYSLOG)[1]);
                    map.get(ip).add(syslog);
                } catch (Exception e) {
                    log.error("错误文件:\t{}", path);
                    log.error(e.getMessage() + ":\t{}", logLine);
                }
            }
        }
    }

    /**
     * 根据文件夹内文件日志生成新的数据到指定文件夹
     *
     * @param redFolder   读取文件夹
     * @param suffix      后缀
     * @param writeFolder 写入文件夹
     */
    public static void writeLogByFolder(String redFolder, String suffix, String writeFolder) {
        if (!FileUtil.isDirectory(redFolder)) {
            redFolder = FileUtil.mkdir(redFolder).getAbsolutePath();
        }
        if (StrUtil.isBlank(suffix)) {
            log.info("后缀错误:\t{}", suffix);
            return;
        }
        if (!FileUtil.isDirectory(writeFolder)) {
            writeFolder = FileUtil.mkdir(writeFolder).getAbsolutePath();
        }
        String finalWriteFolder = writeFolder;
        FileUtil.loopFiles(redFolder, pathname -> pathname.getName().endsWith(suffix))
                .forEach(e -> writeTextLog(finalWriteFolder, readLog(e.getAbsolutePath())));
    }

    public static void main(String[] args) {
        writeLogByFolder("F:\\Desktop\\2019-11-28-北京-安管\\北京-es-采集\\原始日志\\flume日志"
                , ".log", "F:\\Desktop\\2019-11-28-北京-安管\\北京-es-采集\\原始日志\\日志筛选-12-05");
    }
}
