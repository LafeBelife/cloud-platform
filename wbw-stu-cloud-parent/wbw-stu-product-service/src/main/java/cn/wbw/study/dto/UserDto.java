package cn.wbw.study.dto;

import lombok.Data;

/**
 * 用户传输对象
 *
 * @author wbw
 * @date 2019/12/18 17:26
 */
@Data
public class UserDto {
    private Long id;

    private String name;

    private String url;
}
