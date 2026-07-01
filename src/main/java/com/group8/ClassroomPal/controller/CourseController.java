package com.group8.ClassroomPal.controller;

import com.group8.ClassroomPal.model.Course;
import com.group8.ClassroomPal.service.CourseService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class CourseController {

    public static void registerRoutes(Javalin app) {
        // POST /api/course/create - 创建课程
        app.post("/api/course/create", CourseController::createCourse);

        // GET /api/course/{uid} - 获取课程信息
        app.get("/api/course/{uid}", CourseController::getCourse);

        // PUT /api/course/{uid} - 更新课程信息
        app.put("/api/course/{uid}", CourseController::updateCourse);

        // DELETE /api/course/{uid} - 删除课程
        app.delete("/api/course/{uid}", CourseController::deleteCourse);

        // GET /api/course/status/{status} - 获取特定状态的课程
        app.get("/api/course/status/{status}", CourseController::getCoursesByStatus);
    }

    /**
     * 创建课程
     * 期望 JSON: {"name": "课程名", "sub_title": "副标题", "description": "描述", "creator_uid": "创建者UID", "school": "学校", "semester": "学期"}
     */
    public static void createCourse(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String name = body.get("name");
            String sub_title = body.get("sub_title");
            String description = body.get("description");
            String creator_uid = body.get("creator_uid");
            String school = body.get("school");
            String semester = body.get("semester");

            Course course = CourseService.createCourse(name, sub_title, description, creator_uid, school, semester);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("course_uid", course.getUid());
            response.put("course", course);

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
     * 获取课程信息
     */
    public static void getCourse(Context ctx) {
        try {
            String uid = ctx.pathParam("uid");
            Course course = CourseService.getCourseById(uid);

            if (course == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("status", "error");
                error.put("message", "Course not found");
                ctx.status(404);
                ctx.json(error);
                return;
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("course", course);
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
     * 更新课程信息
     * 期望 JSON: {"name": "新课程名", "description": "新描述", "operator_uid": "操作者UID", "school": "学校", "semester": "学期"}
     */
    public static void updateCourse(Context ctx) {
        try {
            String uid = ctx.pathParam("uid");
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String name = body.get("name");
            String description = body.get("description");
            String operator_uid = body.get("operator_uid");
            String school = body.get("school");
            String semester = body.get("semester");

            Course course = CourseService.updateCourse(uid, name, description, operator_uid, school, semester);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("course", course);
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
     * 删除课程
     * 期望 JSON: {"operator_uid": "操作者UID"}
     */
    public static void deleteCourse(Context ctx) {
        try {
            String uid = ctx.pathParam("uid");
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String operator_uid = body.get("operator_uid");

            CourseService.deleteCourse(uid, operator_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Course deleted");
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
     * 获取特定状态的课程列表
     */
    public static void getCoursesByStatus(Context ctx) {
        try {
            String status = ctx.pathParam("status");
            var courses = CourseService.getCoursesByStatus(status);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("courses", courses);
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
