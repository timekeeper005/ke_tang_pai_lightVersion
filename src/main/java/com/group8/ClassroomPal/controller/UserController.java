package com.group8.ClassroomPal.controller;

import com.group8.ClassroomPal.model.User;
import com.group8.ClassroomPal.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    public static void registerRoutes(Javalin app) {
        // POST /api/user/create - 创建新用户
        app.post("/api/user/create", UserController::createUser);

        // GET /api/user/{uid} - 获取用户信息
        app.get("/api/user/{uid}", UserController::getUser);

        // PUT /api/user/{uid} - 更新用户信息
        app.put("/api/user/{uid}", UserController::updateUser);

        // DELETE /api/user/{uid} - 删除用户
        app.delete("/api/user/{uid}", UserController::deleteUser);

        // GET /api/user/role/{role} - 获取特定角色的用户
        app.get("/api/user/role/{role}", UserController::getUsersByRole);
    }

    /**
     * 创建新用户
     * 期望 JSON: {"name": "用户名", "role": "TEACHER|STUDENT"}
     */
    public static void createUser(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String name = body.get("name");
            String role = body.get("role");

            User user = UserService.createUser(name, role);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("uid", user.getUid());
            response.put("user", user);

            ctx.status(201);
            ctx.json(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            ctx.status(400);
            ctx.json(error);
        }
    }

    /**
     * 获取用户信息
     */
    public static void getUser(Context ctx) {
        try {
            String uid = ctx.pathParam("uid");
            User user = UserService.getUserById(uid);

            if (user == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("status", "error");
                error.put("message", "User not found");
                ctx.status(404);
                ctx.json(error);
                return;
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("user", user);
            ctx.json(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            ctx.status(400);
            ctx.json(error);
        }
    }

    /**
     * 更新用户信息
     * 期望 JSON: {"name": "新用户名", "role": "TEACHER|STUDENT"}
     */
    public static void updateUser(Context ctx) {
        try {
            String uid = ctx.pathParam("uid");
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String name = body.get("name");
            String role = body.get("role");

            User user = UserService.updateUser(uid, name, role);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("user", user);
            ctx.json(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            ctx.status(400);
            ctx.json(error);
        }
    }

    /**
     * 删除用户
     */
    public static void deleteUser(Context ctx) {
        try {
            String uid = ctx.pathParam("uid");
            UserService.deleteUser(uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User deleted");
            ctx.json(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            ctx.status(400);
            ctx.json(error);
        }
    }

    /**
     * 获取特定角色的用户列表
     */
    public static void getUsersByRole(Context ctx) {
        try {
            String role = ctx.pathParam("role");
            var users = UserService.getUsersByRole(role);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("users", users);
            ctx.json(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            ctx.status(400);
            ctx.json(error);
        }
    }
}
