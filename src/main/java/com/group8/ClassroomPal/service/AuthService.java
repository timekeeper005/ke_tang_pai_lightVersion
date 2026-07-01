package com.group8.ClassroomPal.service;

import com.group8.ClassroomPal.dao.CredentialDAO;
import com.group8.ClassroomPal.dao.UserDAO;
import com.group8.ClassroomPal.model.Credential;
import com.group8.ClassroomPal.model.User;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthService {

    /**
     * 用户注册
     * @param name 用户名
     * @param email 邮箱
     * @param password 密码（明文）
     * @param role 角色 (TEACHER 或 STUDENT)
     * @return 返回用户UID
     */
    public static String register(String name, String email, String password, String role) {
        // 验证输入
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // 检查用户名是否已存在
        User existingUser = UserDAO.findByName(name);
        if (existingUser != null) {
            throw new IllegalArgumentException("User name already exists");
        }

        // 检查邮箱是否已存在
        Credential existingCred = CredentialDAO.findByEmail(email);
        if (existingCred != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        // 生成唯一UID
        String uid = UUID.randomUUID().toString();

        // 创建用户
        User user = new User(uid, name, role);
        UserDAO.insert(user);

        // 创建凭证
        String passwordHash = hashPassword(password);
        Credential credential = new Credential(uid, email, passwordHash);
        CredentialDAO.insert(credential);

        return uid;
    }

    /**
     * 用户登录
     * @param email 邮箱
     * @param password 密码（明文）
     * @return 返回包含用户信息的Map
     */
    public static Map<String, Object> login(String email, String password) {
        // 验证输入
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // 查询凭证
        Credential credential = CredentialDAO.findByEmail(email);
        if (credential == null) {
            throw new IllegalArgumentException("Email or password incorrect");
        }

        // 验证密码
        String passwordHash = hashPassword(password);
        if (!passwordHash.equals(credential.getPassword_hash())) {
            throw new IllegalArgumentException("Email or password incorrect");
        }

        // 获取用户信息
        User user = UserDAO.findByUid(credential.getUid());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("uid", user.getUid());
        result.put("role", user.getRole());
        result.put("name", user.getName());

        return result;
    }

    /**
     * 密码哈希（使用SHA-256）
     * @param password 明文密码
     * @return 哈希值
     */
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
