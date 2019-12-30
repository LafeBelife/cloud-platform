package cn.wbw.test;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * ip地址排序
 *
 * @author wbw
 * @date 2019/12/29 15:13
 */
public class TestListIpSort {
    public static void main(String[] args) {
        String ips = "192.168.137.5\t192.168.130.3\t192.168.9.16\t192.168.9.15\t192.168.9.14\t192.168.9.11\t192.168.9.10\n" +
                "192.168.9.6\t192.168.9.5\t192.168.9.4\t192.168.9.2\t192.168.9.1\t192.168.9.8\t192.168.9.9\n" +
                "192.168.9.17\t192.168.9.18\t192.168.9.19\t192.168.9.20\t192.168.137.3\t\t\n" +
                "192.168.6.42\t192.168.6.38\t192.168.6.13\t192.168.248.2\t192.168.130.12\t192.168.9.12\t\n" +
                "192.168.9.7\t192.168.6.142\t192.168.6.106\t192.168.6.54\t192.168.6.50\t192.168.6.46\t\n";




        ips = ips.replaceAll("\n", "\t").replaceAll("\t+", "\t");
        List<String> list = Arrays.stream(ips.split("\t")).collect(Collectors.toList());
        list.stream().sorted((e1, e2) -> {

            StringTokenizer token = new StringTokenizer(e1, ".");
            StringTokenizer token2 = new StringTokenizer(e2, ".");
            while (token.hasMoreTokens() && token2.hasMoreTokens()) {
                int parseInt = Integer.parseInt(token.nextToken());
                int parseInt2 = Integer.parseInt(token2.nextToken());
                if (parseInt > parseInt2) {
                    return 1;
                }
                if (parseInt < parseInt2) {
                    return -1;
                }

            }
            if (token.hasMoreElements()) { // e1还有值，则e2已遍历完
                return 1;
            } else {
                return -1;
            }
        }).forEach(e -> System.out.print(e + "\t"));

    }
}
