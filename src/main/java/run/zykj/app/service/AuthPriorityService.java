package run.zykj.app.service;

import run.zykj.app.entity.dto.AuthPriorityDTO;
import run.zykj.app.entity.pojo.AuthPriority;
import run.zykj.app.entity.vo.TreeSelect;

import java.util.List;

/**
 * @author lingSong
 * @date 2020/12/30 21:57
 */
public interface AuthPriorityService {

    /**
     * 查询系统权限列表
     *
     * @return
     */
    List<AuthPriority> selectPriorityList();

    /**
     * TODO 根据账户id查询所拥有权限 BUG
     *
     * @param accountId
     * @return
     */
    List<TreeSelect> selectPriorityListByUserId(Long accountId);

    /**
     * TODO 根据账号id查询所用用权限 构建前端所需下拉树 BUG
     *
     * @param accountId
     * @param list
     * @return
     */
    List<TreeSelect> selectPriorityTreeByUserId(Long accountId, List<AuthPriority> list);

    /**
     * 构建前端所需下拉树结构
     *
     * @param priorityList
     * @return
     */
    List<TreeSelect> buildPriorityTreeSelect(List<AuthPriority> priorityList);

    /**
     * 构建前端所需树结构
     *
     * @param list
     * @return
     */
    List<AuthPriority> buildPriorityTree(List<AuthPriority> list);

    /**
     * 根据id删除
     *
     * @param priorityId
     * @return
     */
    boolean delete(Long priorityId);

    /**
     * 批量删除
     *
     * @param priority
     * @return
     */
    boolean BatchDelete(AuthPriority priority);

    /**
     * 根据id查询  返回结果不能为空
     *
     * @param priorityId
     * @return
     */
    AuthPriority selectByIdOfNonNull(Long priorityId);

    /**
     * 更新数据
     *
     * @param priorityDTO
     * @return
     */
    boolean update(AuthPriorityDTO priorityDTO);

    /**
     * 校验权限参数的唯一性
     *
     * @param priority
     * @return
     */
    boolean checkPriorityUnquie(AuthPriority priority);

}
