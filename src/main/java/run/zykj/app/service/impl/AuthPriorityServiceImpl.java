package run.zykj.app.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import run.zykj.app.commons.Constants;
import run.zykj.app.entity.dto.AuthPriorityDTO;
import run.zykj.app.entity.pojo.AuthPriority;
import run.zykj.app.entity.vo.TreeSelect;
import run.zykj.app.mapper.AuthPriorityMapper;
import run.zykj.app.service.AuthPriorityService;
import run.zykj.app.utils.CGlibUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lingSong
 * @date 2020/12/30 21:57
 */
@Service
public class AuthPriorityServiceImpl implements AuthPriorityService {

    @Autowired
    private AuthPriorityMapper priorityMapper;

    @Override
    public List<AuthPriority> selectPriorityList() {
        return priorityMapper.selectPriorityList();
    }

    @Override
    public List<TreeSelect> selectPriorityListByUserId(Long accountId) {
        List<AuthPriority> authPriorityList = priorityMapper.selectPriorityListByUserId(accountId);
        return selectPriorityTreeByUserId(accountId, authPriorityList);
    }

    @Override
    public List<TreeSelect> selectPriorityTreeByUserId(Long accountId, List<AuthPriority> list) {
        return getChildPerms(list, Constants.PARENT_ID);
    }

    private List<TreeSelect> getChildPerms(List<AuthPriority> list, Long parentId) {
        List<AuthPriority> returnList = new ArrayList<>();
        for (Iterator<AuthPriority> iterator = list.iterator(); iterator.hasNext();){
            AuthPriority next = iterator.next();
            if(next.getParentId() == parentId){
                recursionFn(list, next);
                returnList.add(next);
            }
        }
        return returnList.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<TreeSelect> buildPriorityTreeSelect(List<AuthPriority> priorityList) {
        List<AuthPriority> priorities =  buildPriorityTree(priorityList);
        return priorities.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<AuthPriority> buildPriorityTree(List<AuthPriority> list) {
        List<AuthPriority> returnList = new ArrayList<>();
        for (Iterator<AuthPriority> iterator = list.iterator(); iterator.hasNext();){
            AuthPriority next = iterator.next();
            if(next.getParentId() == Constants.PARENT_ID){
                recursionFn(list, next);
                returnList.add(next);
            }
        }
        if(returnList.isEmpty()){
            returnList = list;
        }
        return returnList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean delete(Long priorityId) {
        // 判断
        boolean flag = false;
        AuthPriority priority = selectByIdOfNonNull(priorityId);

        // 如果不存在子节点的话
        if(!hasChild(priorityId)){
            flag = priorityMapper.delete(priorityId);
        }else{
            // 存在节点 删除
            flag = BatchDelete(priority);
        }

        if(!flag){
            throw new RuntimeException("[AuthPriorityService-delete]:删除权限失败!");
        }

        return flag;
    }

    /**
     * 最简便的方法 去遍历 得到子集ID
     *
     * @param priority
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean BatchDelete(AuthPriority priority) {
        boolean flag = false;
        List<AuthPriority> childList = new ArrayList<>();
        List<Long> deleteIds = new ArrayList<>();
        List<AuthPriority> tempList = selectPriorityList();

        if(priority.getParentId().longValue() == Constants.PARENT_ID){
            // 如果当前要删除的节点是根节点的话
            // 3级遍历 提取id
            childList = getChildList(tempList, priority);
            for (AuthPriority ap : childList) {
                if(hasChild(tempList, ap)){
                    deleteIds = getChildList(tempList, ap).stream().map(t -> t.getId()).collect(Collectors.toList());
                }
            }
            deleteIds.addAll(childList.stream().map(t -> t.getId()).collect(Collectors.toList()));
            deleteIds.add(priority.getId());
        }else{
            // 这里是2级节点
            childList = priorityMapper.selectByParentId(priority.getId());
            deleteIds = childList.stream().map(child -> child.getId()).collect(Collectors.toList());
            deleteIds.add(priority.getId());
        }

        if(deleteIds.size() != 0)
            flag = true;
        for (Long priorityId : deleteIds) {
            priorityMapper.delete(priorityId);
        }

        return flag;
    }

    @Override
    public AuthPriority selectByIdOfNonNull(Long priorityId) {
        return Optional.ofNullable(priorityMapper.selectById(priorityId)).orElseThrow(
                () -> new RuntimeException("The Priority not exist")
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean update(AuthPriorityDTO priorityDTO) {
        boolean flag = false;
        AuthPriority updateObj = CGlibUtils.mapper(priorityDTO, AuthPriority.class);

        //TODO 校验
        flag = priorityMapper.update(updateObj);
        return flag;
    }

    @Override
    public boolean checkPriorityUnquie(AuthPriority priority) {
        boolean flag = false;
        AuthPriority info = priorityMapper.checkPriorityUnquie(priority.getCode(), priority.getPriorityComment(), priority.getParentId());
        if(info != null && info.getId().longValue() != priority.getId().longValue()){
            return flag=true;
        }
        return flag;
    }

    /**
     * 递归查询子集
     *
     * @param list
     * @param next
     */
    private void recursionFn(List<AuthPriority> list, AuthPriority next) {
        List<AuthPriority> chillList = getChildList(list, next);
        next.setChildren(chillList);

        for (AuthPriority tChild : chillList) {
            // 判断是否有子节点
            if(hasChild(list, tChild)){
                Iterator<AuthPriority> it = chillList.iterator();
                while (it.hasNext()){
                    AuthPriority n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     *
     * @param list
     * @param next
     * @return
     */
    private List<AuthPriority> getChildList(List<AuthPriority> list, AuthPriority next) {
        List<AuthPriority> tempList = new ArrayList<>();
        Iterator<AuthPriority> iterator = list.iterator();
        while (iterator.hasNext()){
            AuthPriority tt = iterator.next();
            if(tt.getParentId().longValue() == next.getId().longValue()){
                tempList.add(tt);
            }
        }
        return tempList;
    }

    /**
     * 判断是否有子节点
     *
     * @param list
     * @param next
     * @return
     */
    private boolean hasChild(List<AuthPriority> list, AuthPriority next){
        return getChildList(list, next).size() > 0 ? true : false;
    }

    /**
     * 根据id判断是否有子节点
     *
     * @param priority
     * @return
     */
    private boolean hasChild(Long priority){
        return priorityMapper.hasChild(priority);
    }

}
