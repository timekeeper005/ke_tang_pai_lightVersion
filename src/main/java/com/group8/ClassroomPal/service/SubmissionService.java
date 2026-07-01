package com.group8.ClassroomPal.service;

import com.group8.ClassroomPal.dao.SubmissionDAO;
import com.group8.ClassroomPal.dao.AssignmentDAO;
import com.group8.ClassroomPal.dao.MembershipDAO;
import com.group8.ClassroomPal.dao.UserDAO;
import com.group8.ClassroomPal.model.Submission;
import com.group8.ClassroomPal.model.Assignment;
import com.group8.ClassroomPal.model.User;

import java.util.List;
import java.util.UUID;

public class SubmissionService {

    /**
     * 学生提交作业
     * @param assignment_uid 作业UID
     * @param student_uid 学生UID
     * @param content 提交内容
     * @return 返回提交的对象
     */
    public static Submission submitAssignment(String assignment_uid, String student_uid, String content) {
        // 验证作业存在
        Assignment assignment = AssignmentDAO.findByUid(assignment_uid);
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }

        // 验证学生存在
        User student = UserDAO.findByUid(student_uid);
        if (student == null) {
            throw new IllegalArgumentException("User not found");
        }

        // 验证学生是否在该课程中
        if (!MembershipDAO.hasRole(assignment.course_uid, student_uid, "STUDENT") &&
            !MembershipDAO.hasRole(assignment.course_uid, student_uid, "TEACHER")) {
            throw new IllegalArgumentException("Student is not a member of this course");
        }

        // 检查是否已提交
        Submission existing = SubmissionDAO.findByAssignmentAndStudent(assignment_uid, student_uid);
        if (existing != null) {
            throw new IllegalArgumentException("You have already submitted this assignment");
        }

        // 验证内容
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }

        // 创建提交
        String id = UUID.randomUUID().toString();
        Submission submission = new Submission(id, assignment_uid, student_uid, content, 0, "", "SUBMITTED");
        SubmissionDAO.insert(submission);

        return submission;
    }

    /**
     * 获取提交信息
     * @param id 提交ID
     * @return 提交对象
     */
    public static Submission getSubmissionById(String id) {
        return SubmissionDAO.findById(id);
    }

    /**
     * 教师批改作业
     * @param id 提交ID
     * @param score 成绩
     * @param feedback 反馈
     * @param teacher_uid 教师UID
     * @return 更新后的提交对象
     */
    public static Submission gradeSubmission(String id, int score, String feedback, String teacher_uid) {
        Submission submission = SubmissionDAO.findById(id);
        if (submission == null) {
            throw new IllegalArgumentException("Submission not found");
        }

        // 验证作业
        Assignment assignment = AssignmentDAO.findByUid(submission.assignment_uid);
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }

        // 验证教师是否在该课程中且有教师角色
        if (!MembershipDAO.hasRole(assignment.course_uid, teacher_uid, "TEACHER")) {
            throw new IllegalArgumentException("Only course teachers can grade submissions");
        }

        // 验证成绩
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }

        submission.setScore(score);
        submission.setFeedback(feedback);
        submission.setStatus("GRADED");
        SubmissionDAO.update(submission);

        return submission;
    }

    /**
     * 获取作业的所有提交
     * @param assignment_uid 作业UID
     * @return 提交列表
     */
    public static List<Submission> getSubmissionsByAssignment(String assignment_uid) {
        Assignment assignment = AssignmentDAO.findByUid(assignment_uid);
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }
        return SubmissionDAO.findByAssignment(assignment_uid);
    }

    /**
     * 获取学生的所有提交
     * @param student_uid 学生UID
     * @return 提交列表
     */
    public static List<Submission> getSubmissionsByStudent(String student_uid) {
        User student = UserDAO.findByUid(student_uid);
        if (student == null) {
            throw new IllegalArgumentException("User not found");
        }
        return SubmissionDAO.findByStudent(student_uid);
    }

    /**
     * 删除提交
     * @param id 提交ID
     * @param operator_uid 操作者UID（必须是作业所在课程的教师）
     */
    public static void deleteSubmission(String id, String operator_uid) {
        Submission submission = SubmissionDAO.findById(id);
        if (submission == null) {
            throw new IllegalArgumentException("Submission not found");
        }

        Assignment assignment = AssignmentDAO.findByUid(submission.assignment_uid);
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }

        // 验证操作者是否为课程教师
        if (!MembershipDAO.hasRole(assignment.course_uid, operator_uid, "TEACHER")) {
            throw new IllegalArgumentException("Only course teachers can delete submissions");
        }

        SubmissionDAO.delete(id);
    }
}
