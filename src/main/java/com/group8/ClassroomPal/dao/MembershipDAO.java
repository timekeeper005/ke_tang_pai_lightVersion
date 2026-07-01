package com.group8.ClassroomPal.dao;

import com.group8.ClassroomPal.database.Database;
import com.group8.ClassroomPal.model.Membership;

import java.sql.*;
import java.util.*;

public class MembershipDAO {

    public static void insert(String course_uid, String user_uid, String role) {
        String sql = "INSERT INTO membership(course_uid, user_uid, role) VALUES(?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course_uid);
            ps.setString(2, user_uid);
            ps.setString(3, role);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Membership find(String course_uid, String user_uid) {
        String sql = "SELECT * FROM membership WHERE course_uid = ? AND user_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course_uid);
            ps.setString(2, user_uid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Membership> findByUser(String user_uid) {
        List<Membership> list = new ArrayList<>();
        String sql = "SELECT * FROM membership WHERE user_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user_uid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Membership> findByCourse(String course_uid) {
        List<Membership> list = new ArrayList<>();
        String sql = "SELECT * FROM membership WHERE course_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course_uid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Membership> findByCourseAndRole(String course_uid, String role) {
        List<Membership> list = new ArrayList<>();
        String sql = "SELECT * FROM membership WHERE course_uid = ? AND role = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course_uid);
            ps.setString(2, role);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean hasRole(String course_uid, String user_uid, String role) {
        Membership m = find(course_uid, user_uid);
        return m != null && m.getRole().equals(role);
    }

    public static void update(String course_uid, String user_uid, String role) {
        String sql = "UPDATE membership SET role = ? WHERE course_uid = ? AND user_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role);
            ps.setString(2, course_uid);
            ps.setString(3, user_uid);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String course_uid, String user_uid) {
        String sql = "DELETE FROM membership WHERE course_uid = ? AND user_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course_uid);
            ps.setString(2, user_uid);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Membership map(ResultSet rs) throws Exception {
        return new Membership(
                rs.getString("course_uid"),
                rs.getString("user_uid"),
                rs.getString("role")
        );
    }
}
