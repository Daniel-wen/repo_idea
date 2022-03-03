package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo findAllUserByPage(UserVO userVO) {

        PageHelper.startPage(userVO.getCurrentPage(),userVO.getPageSize());

        List<User> userList = userMapper.findAllUserByPage(userVO);

        PageInfo<User> pageInfo = new PageInfo<>(userList);

        return pageInfo;
    }

    @Override
    public void updateUserStatus(int id, String status) {

        userMapper.updateUserStatus(id,status);
    }

    @Override
    public User login(User user) throws Exception {

        User user1 = userMapper.login(user);
        if (user1 != null&& Md5.verify(user.getPassword(),"l",user1.getPassword())){
            return user1;
        }else {
            return null;
        }
    }

    @Override
    public List<Role> findUserRelationRoleById(Integer id) {

        List<Role> roleList = userMapper.findUserRelationRoleById(id);
        return roleList;
    }

    @Override
    public void userContextRole(UserVO userVO) {

        //先清空关联表关系
        userMapper.deleteUserContextRole(userVO.getUserId());

        //再重新建立关系
        for (Integer roleId : userVO.getRoleIdList()) {

            //封装数据
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVO.getUserId());
            user_role_relation.setRoleId(roleId);

            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);

        }
    }

    @Override
    public ResponseResult getUserPermissions(Integer userId) {

        //1.获取当前用户拥有的角色
        List<Role> roleById = userMapper.findUserRelationRoleById(userId);

        //2.获取角色id，保存到List集合中
        List<Integer> roleIds = new ArrayList<>();

        for (Role role : roleById) {
            roleIds.add(role.getId());
        }

        //3.根据角色ID查询父菜单
        List<Menu> parentMenuById = userMapper.findParentMenuById(roleIds);

        //4.查询父菜单关联的子菜单
        for (Menu menu : parentMenuById) {

            List<Menu> subMenuByPid = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenuByPid);

        }

        //5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);

        //6.封装数据并返回
        Map<String, Object> map = new HashMap<>();
        map.put("menuList",parentMenuById);
        map.put("resourceList",resourceList);

        return new ResponseResult(true,200,"获取用户权限信息成功",map);
    }
}
