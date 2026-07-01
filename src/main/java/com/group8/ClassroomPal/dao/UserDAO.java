package com.group8.ClassroomPal.dao;

import com.group8.ClassroomPal.database.Database;
import com.group8.ClassroomPal.model.User;

import java.sql.*;
import java.util.*;

public class UserDAO {

    public static void insert(User u) {
        String sql = "INSERT INTO user(uid, name, role) VALUES(?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.uid);
            ps.setString(2, u.name);
            ps.setString(3, u.role);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User findByUid(String uid) {
        String sql = "SELECT * FROM user WHERE uid = ?";

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

    public static User findByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<User> findByRole(String role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void update(User u) {
        String sql = "UPDATE user SET name = ?, role = ? WHERE uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.name);
            ps.setString(2, u.role);
            ps.setString(3, u.uid);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String uid) {
        String sql = "DELETE FROM user WHERE uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uid);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static User map(ResultSet rs) throws Exception {
        return new User(
                rs.getString("uid"),
                rs.getString("name"),
                rs.getString("role")
        );
    }
}
