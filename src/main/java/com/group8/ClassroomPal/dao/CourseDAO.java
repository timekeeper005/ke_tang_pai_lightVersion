package com.group8.ClassroomPal.dao;

import com.group8.ClassroomPal.database.Database;
import com.group8.ClassroomPal.model.Course;

import java.sql.*;
import java.util.*;

public class CourseDAO {

    public static void insert(Course c) {
        String sql = "INSERT INTO course(uid, name, sub_title, description, school, semester, cover_image, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.uid);
            ps.setString(2, c.name);
            ps.setString(3, c.sub_title);
            ps.setString(4, c.description);
            ps.setString(5, c.school);
            ps.setString(6, c.semester);
            ps.setString(7, c.cover_image);
            ps.setString(8, c.status);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Course findByUid(String uid) {
        String sql = "SELECT * FROM course WHERE uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Course> findByStatus(String status) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE status = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Course> findBySchool(String school) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE school = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, school);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void update(Course c) {
        String sql = "UPDATE course SET name = ?, sub_title = ?, description = ?, school = ?, semester = ?, cover_image = ?, status = ? WHERE uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.name);
            ps.setString(2, c.sub_title);
            ps.setString(3, c.description);
            ps.setString(4, c.school);
            ps.setString(5, c.semester);
            ps.setString(6, c.cover_image);
            ps.setString(7, c.status);
            ps.setString(8, c.uid);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String uid) {
        String sql = "DELETE FROM course WHERE uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uid);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Course map(ResultSet rs) throws Exception {
        return new Course(
                rs.getString("uid"),
                rs.getString("name"),
                rs.getString("sub_title"),
                rs.getString("description"),
                rs.getString("school"),
                rs.getString("semester"),
                rs.getString("cover_image"),
                rs.getString("status")
        );
    }
}
