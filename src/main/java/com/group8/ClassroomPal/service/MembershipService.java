package com.group8.ClassroomPal.service;

import com.group8.ClassroomPal.dao.CourseDAO;
import com.group8.ClassroomPal.dao.MembershipDAO;
import com.group8.ClassroomPal.dao.UserDAO;
import com.group8.ClassroomPal.model.Membership;
import com.group8.ClassroomPal.model.User;
import com.group8.ClassroomPal.model.Course;

import java.util.List;

public class MembershipService {

    /**
     * 用户加入课程
     * @param course_uid 课程UID
     * @param user_uid 用户UID
     * @param role 角色 (TEACHER 或 STUDENT)
     * @param operator_uid 操作者UID（必须是课程教师）
     */
    public static void joinCourse(String course_uid, String user_uid, String role, String operator_uid) {
        // 验证课程存在
        Course course = CourseDAO.findByUid(course_uid);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        // 验证用户存在
        User user = UserDAO.findByUid(user_uid);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // 验证操作者是否为课程教师
        if (!MembershipDAO.hasRole(course_uid, operator_uid, "TEACHER")) {
            throw new IllegalArgumentException("Only course teachers can add members");
        }

        // 验证角色
        if (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // 检查用户是否已在课程中
        Membership existing = MembershipDAO.find(course_uid, user_uid);
        if (existing != null) {
            throw new IllegalArgumentException("User is already a member of this course");
        }

        // 添加为课程成员
        MembershipDAO.insert(course_uid, user_uid, role);
    }

    /**
     * 获取用户所有课程
     * @param user_uid 用户UID
     * @return Membership列表
     */
    public static List<Membership> getUserCourses(String user_uid) {
        User user = UserDAO.findByUid(user_uid);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return MembershipDAO.findByUser(user_uid);
    }

    /**
     * 获取课程所有成员
     * @param course_uid 课程UID
     * @return Membership列表
     */
    public static List<Membership> getCourseMembership(String course_uid) {
        Course course = CourseDAO.findByUid(course_uid);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }
        return MembershipDAO.findByCourse(course_uid);
    }

    /**
     * 移除课程成员
     * @param course_uid 课程UID
     * @param user_uid 用户UID
     * @param operator_uid 操作者UID（必须是课程教师）
     */
    public static void removeMember(String course_uid, String user_uid, String operator_uid) {
        // 验证操作者是否为课程教师
        if (!MembershipDAO.hasRole(course_uid, operator_uid, "TEACHER")) {
            throw new IllegalArgumentException("Only course teachers can remove members");
        }

        Membership membership = MembershipDAO.find(course_uid, user_uid);
        if (membership == null) {
            throw new IllegalArgumentException("User is not a member of this course");
        }

        MembershipDAO.delete(course_uid, user_uid);
    }

    /**
     * 更新成员角色
     * @param course_uid 课程UID
     * @param user_uid 用户UID
     * @param role 新角色
     * @param operator_uid 操作者UID（必须是课程教师）
     */
    public static void updateMemberRole(String course_uid, String user_uid, String role, String operator_uid) {
        // 验证操作者是否为课程教师
        if (!MembershipDAO.hasRole(course_uid, operator_uid, "TEACHER")) {
            throw new IllegalArgumentException("Only course teachers can update member roles");
        }

        Membership membership = MembershipDAO.find(course_uid, user_uid);
        if (membership == null) {
            throw new IllegalArgumentException("User is not a member of this course");
        }

        if (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        MembershipDAO.update(course_uid, user_uid, role);
    }
}
