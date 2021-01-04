package run.zykj.app.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xiaolin
 * @date 2020/12/30 17:05
 */
@Data
public class AuthRole implements Serializable {

    /**
     * 主键id type=AUTO
     */
    private Long id;

    /**
     * 角色编号
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色的说明备注
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
