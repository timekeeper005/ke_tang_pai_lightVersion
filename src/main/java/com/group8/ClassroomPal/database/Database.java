package com.group8.ClassroomPal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    private static final String URL="jdbc:h2:./keTangPai";

    public static Connection getConnection() throws Exception{
        return DriverManager.getConnection(URL,"sa","");
    }

    public static void init(){

        try(Connection conn=getConnection();
            Statement stmt=conn.createStatement()){

            stmt.execute(Schema.USER);
            stmt.execute(Schema.CREDENTIAL);
            stmt.execute(Schema.COURSE);
            stmt.execute(Schema.MEMBERSHIP);
            stmt.execute(Schema.ASSIGNMENT);
            stmt.execute(Schema.SUBMISSION);

            System.out.println("Database initialized.");

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
