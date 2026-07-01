package com.group8.ClassroomPal.controller;

import com.group8.ClassroomPal.service.AuthService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class AuthController {

    public static void registerRoutes(Javalin app) {
        // POST /api/auth/register - 用户注册
        app.post("/api/auth/register", AuthController::register);

        // POST /api/auth/login - 用户登录
        app.post("/api/auth/login", AuthController::login);
    }

    /**
     * 用户注册
     * 期望 JSON: {"name": "用户名", "email": "邮箱", "password": "密码", "role": "TEACHER|STUDENT"}
     */
    public static void register(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String name = body.get("name");
            String email = body.get("email");
            String password = body.get("password");
            String role = body.get("role");

            String uid = AuthService.register(name, email, password, role);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("uid", uid);

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
     * 用户登录
     * 期望 JSON: {"email": "邮箱", "password": "密码"}
     */
    public static void login(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String email = body.get("email");
            String password = body.get("password");

            Map<String, Object> user = AuthService.login(email, password);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.putAll(user);

            ctx.json(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            ctx.status(401);
            ctx.json(error);
        }
    }
}
