package com.group8.ClassroomPal.dao;

import com.group8.ClassroomPal.database.Database;
import com.group8.ClassroomPal.model.Assignment;

import java.sql.*;
import java.util.*;


public class AssignmentDAO {

    public static void insert(Assignment a){

        String sql="INSERT INTO assignment(uid,course_uid,title,content_type,content_data) VALUES(?,?,?,?,?)";

        try(Connection conn= Database.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1,a.uid);
            ps.setString(2,a.course_uid);
            ps.setString(3,a.title);
            ps.setString(4,a.content_type);
            ps.setString(5,a.content_data);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Assignment findByUid(String uid){

        String sql="SELECT * FROM assignment WHERE uid=?";

        try(Connection conn=Database.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1,uid);

            ResultSet rs=ps.executeQuery();

            if(rs.next()) return map(rs);

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static List<Assignment> findByCourse(String courseUid){

        List<Assignment> list=new ArrayList<>();

        String sql="SELECT * FROM assignment WHERE course_uid=?";

        try(Connection conn=Database.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1,courseUid);

            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                list.add(map(rs));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public static void update(Assignment a){

        String sql="UPDATE assignment SET title=?,content_type=?,content_data=? WHERE uid=?";

        try(Connection conn=Database.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1,a.title);
            ps.setString(2,a.content_type);
            ps.setString(3,a.content_data);
            ps.setString(4,a.uid);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void delete(String uid){

        String sql="DELETE FROM assignment WHERE uid=?";

        try(Connection conn=Database.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1,uid);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static Assignment map(ResultSet rs)throws Exception{

        return new Assignment(
                rs.getString("uid"),
                rs.getString("course_uid"),
                rs.getString("title"),
                rs.getString("content_type"),
                rs.getString("content_data")
        );
    }

}
