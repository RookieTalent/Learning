package run.zykj.app.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xiaolin
 * @date 2020/12/30 16:29
 */
@Data
public class AuthAccount implements Serializable {

    /**
     * 主键id type=AUTO
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 用户备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDate gmtCreate;

    /**
     * 更新时间
     */
    private LocalDate gmtModified;
}
