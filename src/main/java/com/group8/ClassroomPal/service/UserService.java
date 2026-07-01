package com.group8.ClassroomPal.service;

import com.group8.ClassroomPal.dao.UserDAO;
import com.group8.ClassroomPal.model.User;

import java.util.List;
import java.util.UUID;

public class UserService {

    /**
     * 创建新用户
     * @param name 用户名
     * @param role 角色 (TEACHER 或 STUDENT)
     * @return 返回创建的用户对象
     */
    public static User createUser(String name, String role) {
        // 验证角色
        if (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // 验证用户名
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }

        // 检查用户名是否已存在
        User existing = UserDAO.findByName(name);
        if (existing != null) {
            throw new IllegalArgumentException("User name already exists");
        }

        // 生成唯一UID
        String uid = UUID.randomUUID().toString();
        User user = new User(uid, name, role);

        UserDAO.insert(user);
        return user;
    }

    /**
     * 获取用户信息
     * @param uid 用户UID
     * @return 用户对象，如果不存在返回null
     */
    public static User getUserById(String uid) {
        if (uid == null || uid.trim().isEmpty()) {
            throw new IllegalArgumentException("UID cannot be empty");
        }
        return UserDAO.findByUid(uid);
    }

    /**
     * 更新用户信息
     * @param uid 用户UID
     * @param name 新用户名
     * @param role 新角色
     * @return 更新后的用户对象
     */
    public static User updateUser(String uid, String name, String role) {
        User user = UserDAO.findByUid(uid);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        user.setName(name);
        user.setRole(role);
        UserDAO.update(user);
        return user;
    }

    /**
     * 删除用户
     * @param uid 用户UID
     */
    public static void deleteUser(String uid) {
        User user = UserDAO.findByUid(uid);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        UserDAO.delete(uid);
    }

    /**
     * 根据角色查询用户
     * @param role 角色
     * @return 用户列表
     */
    public static List<User> getUsersByRole(String role) {
        if (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        return UserDAO.findByRole(role);
    }
}
