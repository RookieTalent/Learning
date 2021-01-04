package run.zykj.app.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xiaolin
 * @date 2020/12/30 17:08
 */
@Data
public class AuthAccountPriorityRelationship implements Serializable {

    /**
     * 主键id type=AUTO
     */
    private Long id;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 权限id
     */
    private Long priorityId;

    /**
     * 创建时间
     */
    private LocalDate gmtCreate;

    /**
     * 更新时间
     */
    private LocalDate gmtModified;

}
