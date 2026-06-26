package com.group8.ClassroomPal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:h2:./keTangPai";

    // The database interface.Gets this in your DAO
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, "sa", "");
    }

    public static void init() {

    }
    }
