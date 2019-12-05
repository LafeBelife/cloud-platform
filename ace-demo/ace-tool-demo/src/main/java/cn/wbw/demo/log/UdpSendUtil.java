package cn.wbw.demo.log;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogIF;

import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.Scanner;

/**
 * udp 协议发送数据
 *
 * @author wbw
 * @date 2019/12/5 9:21
 */
public class UdpSendUtil {
    private Log log = Log.get(UdpSendUtil.class);
    private static final String IP = "10.0.1.89";
    private static InetAddress address = null;

    /**
     * 初始化 InetAddress
     *
     * @return InetAddress
     */
    private InetAddress initInAddress() {

        try {
            if (address == null) {
                address = InetAddress.getByName(IP);
            }
        } catch (UnknownHostException e) {
            log.error("初始化地址错误", e);
            return null;
        }
        return address;
    }

    private void sendUdpLog() {
        try {
            //获取syslog的操作类，使用udp协议。syslog支持"udp", "tcp", "unix_syslog", "unix_socket"协议
            SyslogIF syslog = Syslog.getInstance("udp");
            //设置syslog服务器端地址
            syslog.getConfig().setHost("127.0.0.1");
            //设置syslog接收端口，默认514
            syslog.getConfig().setPort(514);
            //拼接syslog日志，这个日志是自己定义的，通常我们定义成符合公司规范的格式就行，方便查询。例如 操作时间：2014年8月1日 操作者ID:张三 等。信息就是一个字符串。
            StringBuffer buffer = new StringBuffer();
            buffer.append("操作时间：" + new Date().toString().substring(4, 20) + ";");
            buffer.append("操作者ID:" + "张三" + ";");
            buffer.append("操作时间:" + new Date() + ";");
            buffer.append("日志类别:" + "22" + ";");
            buffer.append("执行动作:" + "动作" + ";");
            buffer.append("备注:" + "备注");
          /*
            发送信息到服务器，2表示日志级别 范围为0~7的数字编码，表示了事件的严重程度。0最高，7最低
            syslog为每个事件赋予几个不同的优先级：
            LOG_EMERG：紧急情况，需要立即通知技术人员。
            LOG_ALERT：应该被立即改正的问题，如系统数据库被破坏，ISP连接丢失。
            LOG_CRIT：重要情况，如硬盘错误，备用连接丢失。
            LOG_ERR：错误，不是非常紧急，在一定时间内修复即可。
            LOG_WARNING：警告信息，不是错误，比如系统磁盘使用了85%等。
            LOG_NOTICE：不是错误情况，也不需要立即处理。
            LOG_INFO：情报信息，正常的系统消息，比如骚扰报告，带宽数据等，不需要处理。
            LOG_DEBUG：包含详细的开发情报的信息，通常只在调试一个程序时使用。
          */
            syslog.log(0, URLDecoder.decode(buffer.toString(), "utf-8"));
        } catch (Exception e) {
            log.error("发送错误", e);
        }
    }

    /**
     * 发送数据
     *
     * @param data    数据
     * @param address InetAddress
     * @param port    端口
     */
    public void sendPacket(String data, InetAddress address, int port) {
        byte[] bytes = StrUtil.bytes(data);
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);
        try (DatagramSocket ds = new DatagramSocket()) {
            ds.send(dp);
        } catch (SocketException e) {
            log.error("对象创建失败", e);
        } catch (IOException e) {
            log.error("数据包发送失败", e);
        }
    }

    /**
     * 读取syslog 发送数据
     *
     * @param folder 文件夹
     * @param port   端口
     * @param suffix 文件名后缀
     */
    public void readSyslogSend(String folder, int port, String suffix, String conFileName) {
        if (!FileUtil.isDirectory(folder)) {
            log.debug("不是文件夹:\t{}", folder);
        }
        FileUtil.loopFiles(folder, pathname -> pathname.getName().endsWith(suffix) && pathname.getName().contains(conFileName))
                .forEach(e -> {
                    log.info("处理文件:\t{}", e.getName());
                    FileUtil.readLines(e, CharsetUtil.UTF_8).forEach(out -> this.sendPacket(out, this.initInAddress(), port));
                });
    }

    /**
     * 获取文件ip地址
     *
     * @param name   文件名
     * @param suffix 后缀
     * @return ip
     */
    private String getIpByFileName(String name, String suffix) {
        if (name.contains("-")) {
            String[] split = name.split("-");
            name = split[split.length - 1];
        }
        if (name.endsWith(suffix)) {
            name = name.substring(0, name.indexOf(suffix));
        }
        return name;
    }

    public static void main(String[] args) {
        UdpSendUtil udpSendUtil = new UdpSendUtil();
        String in;
        do {
            System.out.print("请输入日志名称:\t");
            Scanner scanner = new Scanner(System.in);
            in = scanner.next();
            udpSendUtil.readSyslogSend("F:\\Desktop\\2019-11-28-北京-安管\\北京-es-采集\\原始日志\\日志筛选\\已完成"
                    , 514, ".log", in);
        } while (StrUtil.isNotBlank(in));
    }
}