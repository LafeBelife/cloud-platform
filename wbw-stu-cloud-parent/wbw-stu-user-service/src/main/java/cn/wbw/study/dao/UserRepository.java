package cn.wbw.study.dao;

import cn.wbw.study.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户仓库
 *
 * @author wbw
 * @date 2019/12/17 13:22
 */
public interface UserRepository extends JpaRepository<User, Long> {
}