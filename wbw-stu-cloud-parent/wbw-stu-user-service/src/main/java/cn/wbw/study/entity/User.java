package cn.wbw.study.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户类
 *
 * @author wbw
 * @date 2019/12/17 13:02
 */
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;
}
