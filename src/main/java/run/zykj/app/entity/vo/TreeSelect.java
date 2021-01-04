package run.zykj.app.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.zykj.app.entity.pojo.AuthPriority;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lingSong
 * @date 2020/12/30 22:13
 */
@Data
@NoArgsConstructor
public class TreeSelect implements Serializable {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect(AuthPriority authPriority){
        this.id = authPriority.getId();
        this.label = authPriority.getPriorityComment();
        this.children = authPriority.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
