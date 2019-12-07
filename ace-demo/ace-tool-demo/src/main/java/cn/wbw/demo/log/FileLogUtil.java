package cn.wbw.demo.log;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 文件操作
 *
 * @author wbw
 * @date 2019/12/4 19:59
 */
public class FileLogUtil {

    /**
     * 对文件夹内文件修改后缀
     *
     * @param folder 文件夹
     * @param suffix 后缀
     */
    public static void updateFileName(String folder, String suffix) {
        for (File file : Objects.requireNonNull(FileUtil.file(folder).listFiles())) {
            if (!file.getName().endsWith(suffix)) {
                boolean b = file.renameTo(new File(file.getAbsolutePath() + suffix));
                System.out.println(file.getName() + "\t" + b);
            }
        }
    }

    private static Map<String, String> map = new HashMap<>();

    static {
        String h3cSecurityName = "H3C-安全产品-";
        String h3cSecurityIp = "192.168.137.5\t192.168.130.3\t192.168.9.16\t192.168.9.15\t192.168.9.14\t192.168.9.11\t192.168.9.10\t" +
                "192.168.9.6\t192.168.9.5\t192.168.9.4\t192.168.9.2\t192.168.9.1\t192.168.9.8\t192.168.9.9\t" +
                "192.168.9.17\t192.168.9.18\t192.168.9.19\t192.168.9.20\t192.168.137.3";
        for (String h3c : h3cSecurityIp.split("\t")) {
            map.put(h3c, h3cSecurityName);
        }
        String hscAuditName = "H3C-运维审计-";
        String hscAuditIp = "192.168.6.162\t192.168.134.154";
        for (String h3c : hscAuditIp.split("\t")) {
            map.put(h3c, hscAuditName);
        }
        String aptName = "APT-安全事件-";
        String aptIp = "192.168.81.203";
        for (String h3c : aptIp.split("\t")) {
            map.put(h3c, aptName);
        }
        String wsWaf = "网神-waf-";
        String wafIp = "192.168.124.251";
        for (String h3c : wafIp.split("\t")) {
            map.put(h3c, wsWaf);
        }
        String rsRaName = "瑞数防爬-";
        String rsRaIp = "192.168.109.40\t192.168.107.100\t192.168.103.60\t192.168.92.104\t192.168.92.103\t192.168.50.1\t192.168.101.18\t" +
                "192.168.113.203";
        for (String h3c : rsRaIp.split("\t")) {
            map.put(h3c, rsRaName);
        }
        String sxfName = "深信服-";
        String sxfIp = "192.168.3.10\t192.168.83.7";
        for (String h3c : sxfIp.split("\t")) {
            map.put(h3c, sxfName);
        }
        String kpName = "科博-安全-";
        String kbIp = "192.168.6.115\t192.168.6.114\t192.168.134.117\t192.168.134.116\t192.168.134.115\t192.168.134.114";
        for (String h3c : kbIp.split("\t")) {
            map.put(h3c, kpName);
        }
        String name360 = "360-跨网防火墙-";
        String ip360 = "192.168.131.9\t192.168.131.12";
        for (String h3c : ip360.split("\t")) {
            map.put(h3c, name360);
        }
    }

    /**
     * 根据已知ip修改文件名
     *
     * @param folder 文件夹
     */
    public static void updateFileByName(String folder) {
        for (File file : Objects.requireNonNull(FileUtil.file(folder).listFiles())) {
            String ip = file.getName().replace(".log", "");
            if (map.containsKey(ip)) {
                boolean b = file.renameTo(new File(file.getAbsolutePath().replace(ip, map.get(ip) + ip)));
                System.out.println(file.getName() + "\t" + b);
            }
        }
    }

    public static void main(String[] args) {
//        updateFileName("F:\\Desktop\\2019-11-28-北京-安管\\北京-es-采集\\原始日志\\flume日志", ".log");
        updateFileByName("F:\\Desktop\\2019-11-28-北京-安管\\北京-es-采集\\原始日志\\日志筛选-12-05");
    }
}
