package com.group8.ClassroomPal.dao;

import com.group8.ClassroomPal.database.Database;
import com.group8.ClassroomPal.model.Submission;

import java.sql.*;
import java.util.*;

public class SubmissionDAO {

    public static void insert(Submission s) {
        String sql = "INSERT INTO submission(id, assignment_uid, student_uid, content, score, feedback, status) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.id);
            ps.setString(2, s.assignment_uid);
            ps.setString(3, s.student_uid);
            ps.setString(4, s.content);
            ps.setInt(5, s.score);
            ps.setString(6, s.feedback);
            ps.setString(7, s.status);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Submission findById(String id) {
        String sql = "SELECT * FROM submission WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Submission> findByAssignment(String assignment_uid) {
        List<Submission> list = new ArrayList<>();
        String sql = "SELECT * FROM submission WHERE assignment_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, assignment_uid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Submission> findByStudent(String student_uid) {
        List<Submission> list = new ArrayList<>();
        String sql = "SELECT * FROM submission WHERE student_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student_uid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Submission findByAssignmentAndStudent(String assignment_uid, String student_uid) {
        String sql = "SELECT * FROM submission WHERE assignment_uid = ? AND student_uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, assignment_uid);
            ps.setString(2, student_uid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Submission> findByStatus(String status) {
        List<Submission> list = new ArrayList<>();
        String sql = "SELECT * FROM submission WHERE status = ?";

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

    public static void update(Submission s) {
        String sql = "UPDATE submission SET content = ?, score = ?, feedback = ?, status = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.content);
            ps.setInt(2, s.score);
            ps.setString(3, s.feedback);
            ps.setString(4, s.status);
            ps.setString(5, s.id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String id) {
        String sql = "DELETE FROM submission WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Submission map(ResultSet rs) throws Exception {
        return new Submission(
                rs.getString("id"),
                rs.getString("assignment_uid"),
                rs.getString("student_uid"),
                rs.getString("content"),
                rs.getInt("score"),
                rs.getString("feedback"),
                rs.getString("status")
        );
    }
}
