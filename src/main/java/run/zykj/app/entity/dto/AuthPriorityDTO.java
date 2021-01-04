package run.zykj.app.entity.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lingSong
 * @date 2020/12/30 22:12
 */
@Data
public class AuthPriorityDTO {
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

    List<AuthPriorityDTO> children = new ArrayList<>();
}
