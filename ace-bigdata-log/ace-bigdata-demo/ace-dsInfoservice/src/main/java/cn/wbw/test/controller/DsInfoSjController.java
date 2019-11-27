package cn.wbw.test.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 测试业务
 *
 * @author wbw
 * @date 2019-11-23 14:29
 */
@Log4j2
@Controller("/api")
public class DsInfoSjController {

    /**
     * @param json 数据
     */
    @PostMapping("/webInfoSJService")
    public void webInfoSj(@RequestBody String json, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("hello Jin coming...\t" + json);
        PrintWriter writer = this.getWriter(res);
        writer.write("success\n " + JSONUtil.parse(json).toJSONString(1));
        this.closeWriter(writer);
        res.setStatus(HttpStatus.HTTP_OK);
    }

    private PrintWriter getWriter(HttpServletResponse res) {
        res.setCharacterEncoding(CharsetUtil.UTF_8);
        res.setContentType("application/json");
        PrintWriter writer = null;
        try {
            ServletOutputStream outputStream = res.getOutputStream();
            outputStream.println("helloxxx");
            writer = new PrintWriter(outputStream);
        } catch (IOException e) {
            log.error("流转换异常:\t{}", e.getMessage());
        }
        return writer;
    }

    private void closeWriter(PrintWriter writer) {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }
}
