package run.zykj.app.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.zykj.app.entity.pojo.AuthPriority;

import java.util.List;

/**
 * @author lingSong
 * @date 2020/12/30 21:00
 */
@Repository
public interface AuthPriorityMapper {

    /**
     * 查询系统权限列表
     *
     * @return
     */
    List<AuthPriority> selectPriorityList();

    /**
     * 根据账户id查询所拥有权限
     *
     * @param accountId
     * @return
     */
    List<AuthPriority> selectPriorityListByUserId(Long accountId);

    /**
     * 根据id查询是否有子节点
     *
     * @param priority
     * @return
     */
    boolean hasChild(Long priorityId);

    /**
     * 删除单权限
     *
     * @param priorityId
     */
    boolean delete(Long priorityId);

    /**
     * 根据id查询返回结果
     *
     * @param priorityId
     * @return
     */
    AuthPriority selectById(Long priorityId);

    /**
     * 查询父级节点是parentId的节点
     *
     * @param parentId
     * @return
     */
    List<AuthPriority> selectByParentId(Long parentId);

    /**
     * 校验权限参数的唯一性
     *
     * @param code
     * @param priorityComment
     * @param parentId
     * @return
     */
    AuthPriority checkPriorityUnquie(@Param("code") String code, @Param("priorityComment") String priorityComment, @Param("parentId") Long parentId);

    /**
     * 更新权限
     *
     * @param updateObj
     * @return
     */
    boolean update(AuthPriority updateObj);

    /**
     * 新增权限
     * 需要关联中间表
     * @param insertObj
     * @return
     */
    boolean insert(AuthPriority insertObj);
}
