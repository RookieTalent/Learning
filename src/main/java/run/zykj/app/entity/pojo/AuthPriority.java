package run.zykj.app.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolin
 * @date 2020/12/30 17:02
 */
@Data
public class AuthPriority implements Serializable {

    /**
     * 主键id type=AUTO
     */
    private Long id;

    /**
     * 权限编号
     */
    private String code;

    /**
     * 权限对应的URL
     */
    private String url;

    /**
     * 权限的说明备注
     */
    private String priorityComment;

    /**
     * 权限类型 1菜单 2其他
     */
    private Integer priorityType;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 创建时间
     */
    private LocalDate gmtCreate;

    /**
     * 更新时间
     */
    private LocalDate gmtModified;

    /**
     * 子节点
     */
    private List<AuthPriority> children = new ArrayList<>();
}
