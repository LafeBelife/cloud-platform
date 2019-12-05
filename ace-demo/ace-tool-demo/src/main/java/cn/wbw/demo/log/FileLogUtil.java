package cn.wbw.demo.log;

import cn.hutool.core.io.FileUtil;

import java.io.File;
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


    public static void main(String[] args) {
        updateFileName("F:\\Desktop\\2019-11-28-北京-安管\\北京-es-采集\\原始日志\\flume日志", ".log");
    }
}
