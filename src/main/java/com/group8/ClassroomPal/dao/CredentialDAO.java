package com.group8.ClassroomPal.dao;

import com.group8.ClassroomPal.database.Database;
import com.group8.ClassroomPal.model.Credential;

import java.sql.*;

public class CredentialDAO {

    public static void insert(Credential c) {
        String sql = "INSERT INTO credential(uid, email, password_hash) VALUES(?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.uid);
            ps.setString(2, c.email);
            ps.setString(3, c.password_hash);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Credential findByUid(String uid) {
        String sql = "SELECT * FROM credential WHERE uid = ?";

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

    public static Credential findByEmail(String email) {
        String sql = "SELECT * FROM credential WHERE email = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void update(Credential c) {
        String sql = "UPDATE credential SET email = ?, password_hash = ? WHERE uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.email);
            ps.setString(2, c.password_hash);
            ps.setString(3, c.uid);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String uid) {
        String sql = "DELETE FROM credential WHERE uid = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uid);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Credential map(ResultSet rs) throws Exception {
        return new Credential(
                rs.getString("uid"),
                rs.getString("email"),
                rs.getString("password_hash")
        );
    }
}
