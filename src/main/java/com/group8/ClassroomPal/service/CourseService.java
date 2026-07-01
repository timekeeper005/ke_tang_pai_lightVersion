package com.group8.ClassroomPal.service;

import com.group8.ClassroomPal.dao.CourseDAO;
import com.group8.ClassroomPal.dao.MembershipDAO;
import com.group8.ClassroomPal.dao.UserDAO;
import com.group8.ClassroomPal.model.Course;
import com.group8.ClassroomPal.model.User;

import java.util.List;
import java.util.UUID;

public class CourseService {

    /**
     * 创建课程（仅教师可以创建）
     * @param name 课程名称
     * @param sub_title 课程副标题
     * @param description 课程描述
     * @param creator_uid 创建者UID
     * @param school 学校
     * @param semester 学期
     * @return 返回创建的课程对象
     */
    public static Course createCourse(String name, String sub_title, String description,
                                     String creator_uid, String school, String semester) {
        // 验证创建者是否存在且为教师
        User creator = UserDAO.findByUid(creator_uid);
        if (creator == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!creator.getRole().equals("TEACHER")) {
            throw new IllegalArgumentException("Only teachers can create courses");
        }

        // 验证输入
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }

        // 生成唯一UID
        String uid = UUID.randomUUID().toString();
        Course course = new Course(uid, name, sub_title, description, school, semester, null, "ACTIVE");

        CourseDAO.insert(course);

        // 创建者自动成为课程成员（角色为教师）
        MembershipDAO.insert(uid, creator_uid, "TEACHER");

        return course;
    }

    /**
     * 获取课程信息
     * @param uid 课程UID
     * @return 课程对象，如果不存在返回null
     */
    public static Course getCourseById(String uid) {
        if (uid == null || uid.trim().isEmpty()) {
            throw new IllegalArgumentException("UID cannot be empty");
        }
        return CourseDAO.findByUid(uid);
    }

    /**
     * 更新课程信息（仅课程创建者/教师可以更新）
     * @param uid 课程UID
     * @param name 新课程名称
     * @param description 新描述
     * @param operator_uid 操作者UID
     * @return 更新后的课程对象
     */
    public static Course updateCourse(String uid, String name, String description,
                                     String operator_uid, String school, String semester) {
        Course course = CourseDAO.findByUid(uid);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        // 验证操作者是否为课程的教师
        if (!MembershipDAO.hasRole(uid, operator_uid, "TEACHER")) {
            throw new IllegalArgumentException("Only course teachers can update");
        }

        course.setName(name);
        course.setDescription(description);
        course.setSchool(school);
        course.setSemester(semester);
        CourseDAO.update(course);
        return course;
    }

    /**
     * 删除课程（仅课程创建者/教师可以删除）
     * @param uid 课程UID
     * @param operator_uid 操作者UID
     */
    public static void deleteCourse(String uid, String operator_uid) {
        Course course = CourseDAO.findByUid(uid);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        // 验证操作者是否为课程的教师
        if (!MembershipDAO.hasRole(uid, operator_uid, "TEACHER")) {
            throw new IllegalArgumentException("Only course teachers can delete");
        }

        CourseDAO.delete(uid);
    }

    /**
     * 获取特定状态的课程列表
     * @param status 课程状态
     * @return 课程列表
     */
    public static List<Course> getCoursesByStatus(String status) {
        return CourseDAO.findByStatus(status);
    }

    /**
     * 获取学校的课程列表
     * @param school 学校名称
     * @return 课程列表
     */
    public static List<Course> getCoursesBySchool(String school) {
        return CourseDAO.findBySchool(school);
    }
}
