package com.group8.ClassroomPal.controller;

import com.group8.ClassroomPal.model.Submission;
import com.group8.ClassroomPal.service.SubmissionService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class SubmissionController {

    public static void registerRoutes(Javalin app) {
        // POST /api/submission/submit - 学生提交作业
        app.post("/api/submission/submit", SubmissionController::submitAssignment);

        // GET /api/submission/{id} - 获取提交信息
        app.get("/api/submission/{id}", SubmissionController::getSubmission);

        // POST /api/submission/{id}/grade - 教师批改作业
        app.post("/api/submission/{id}/grade", SubmissionController::gradeSubmission);

        // GET /api/assignment/{assignment_uid}/submissions - 获取作业的所有提交
        app.get("/api/assignment/{assignment_uid}/submissions", SubmissionController::getSubmissionsByAssignment);

        // GET /api/student/{student_uid}/submissions - 获取学生的所有提交
        app.get("/api/student/{student_uid}/submissions", SubmissionController::getSubmissionsByStudent);

        // DELETE /api/submission/{id} - 删除提交
        app.delete("/api/submission/{id}", SubmissionController::deleteSubmission);
    }

    /**
     * 学生提交作业
     * 期望 JSON: {"assignment_uid": "作业UID", "student_uid": "学生UID", "content": "提交内容"}
     */
    public static void submitAssignment(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String assignment_uid = body.get("assignment_uid");
            String student_uid = body.get("student_uid");
            String content = body.get("content");

            Submission submission = SubmissionService.submitAssignment(assignment_uid, student_uid, content);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("submission", submission);

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
     * 获取提交信息
     */
    public static void getSubmission(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            Submission submission = SubmissionService.getSubmissionById(id);

            if (submission == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("status", "error");
                error.put("message", "Submission not found");
                ctx.status(404);
                ctx.json(error);
                return;
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("submission", submission);
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
     * 教师批改作业
     * 期望 JSON: {"score": 85, "feedback": "很好", "teacher_uid": "教师UID"}
     */
    public static void gradeSubmission(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            Map<String, Object> body = ctx.bodyAsClass(Map.class);
            int score = ((Number) body.get("score")).intValue();
            String feedback = (String) body.get("feedback");
            String teacher_uid = (String) body.get("teacher_uid");

            Submission submission = SubmissionService.gradeSubmission(id, score, feedback, teacher_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("submission", submission);
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
     * 获取作业的所有提交
     */
    public static void getSubmissionsByAssignment(Context ctx) {
        try {
            String assignment_uid = ctx.pathParam("assignment_uid");
            var submissions = SubmissionService.getSubmissionsByAssignment(assignment_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("submissions", submissions);
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
     * 获取学生的所有提交
     */
    public static void getSubmissionsByStudent(Context ctx) {
        try {
            String student_uid = ctx.pathParam("student_uid");
            var submissions = SubmissionService.getSubmissionsByStudent(student_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("submissions", submissions);
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
     * 删除提交
     * 期望 JSON: {"operator_uid": "操作者UID"}
     */
    public static void deleteSubmission(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String operator_uid = body.get("operator_uid");

            SubmissionService.deleteSubmission(id, operator_uid);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Submission deleted");
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
