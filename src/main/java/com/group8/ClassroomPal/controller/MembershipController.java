package com.group8.ClassroomPal.controller;

import com.group8.ClassroomPal.service.MembershipService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class MembershipController {

    public static void registerRoutes(Javalin app) {
        // POST /api/course/join - 加入课程
        app.post("/api/course/join", MembershipController::joinCourse);

        // GET /api/user/{user_uid}/courses - 获取用户所有课程
        app.get("/api/user/{user_uid}/courses", MembershipController::getUserCourses);

        // GET /api/course/{course_uid}/members - 获取课程所有成员
        app.get("/api/course/{course_uid}/members", MembershipController::getCourseMembership);

        // DELETE /api/course/{course_uid}/member/{user_uid} - 移除课程成员
        app.delete("/api/course/{course_uid}/member/{user_uid}", MembershipController::removeMember);

        // PUT /api/course/{course_uid}/member/{user_uid} - 更新成员角色
        app.put("/api/course/{course_uid}/member/{user_uid}", MembershipController::updateMemberRole);
    }

    /**
     * 用户加入课程
     * 期望 JSON: {"course_uid": "课程UID", "user_uid": "用户UID", "role": "TEACHER|STUDENT", "operator_uid": "操作者UID"}
     */
    public static void joinCourse(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String course_uid = body.get("course_uid");
            String user_uid = body.get("user_uid");
            String role = body.get("role");
            String operator_uid = body.get("operator_uid");

            MembershipService.joinCourse(course_uid, user_uid, role, operator_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User joined course");

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
     * 获取用户所有课程
     */
    public static void getUserCourses(Context ctx) {
        try {
            String user_uid = ctx.pathParam("user_uid");
            var memberships = MembershipService.getUserCourses(user_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("memberships", memberships);
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
     * 获取课程所有成员
     */
    public static void getCourseMembership(Context ctx) {
        try {
            String course_uid = ctx.pathParam("course_uid");
            var memberships = MembershipService.getCourseMembership(course_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("memberships", memberships);
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
     * 移除课程成员
     * 期望 JSON: {"operator_uid": "操作者UID"}
     */
    public static void removeMember(Context ctx) {
        try {
            String course_uid = ctx.pathParam("course_uid");
            String user_uid = ctx.pathParam("user_uid");
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String operator_uid = body.get("operator_uid");

            MembershipService.removeMember(course_uid, user_uid, operator_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Member removed");
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
     * 更新成员角色
     * 期望 JSON: {"role": "TEACHER|STUDENT", "operator_uid": "操作者UID"}
     */
    public static void updateMemberRole(Context ctx) {
        try {
            String course_uid = ctx.pathParam("course_uid");
            String user_uid = ctx.pathParam("user_uid");
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String role = body.get("role");
            String operator_uid = body.get("operator_uid");

            MembershipService.updateMemberRole(course_uid, user_uid, role, operator_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Member role updated");
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
