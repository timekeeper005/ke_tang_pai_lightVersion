package com.group8.ClassroomPal.database;


public final class Schema {

    private Schema(){}

    public static final String USER = """
        CREATE TABLE IF NOT EXISTS users(
            uid VARCHAR(36) PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            role VARCHAR(20) NOT NULL
        );
        """;

    public static final String CREDENTIAL = """
        CREATE TABLE IF NOT EXISTS credentials(
            uid VARCHAR(36) PRIMARY KEY,
            email VARCHAR(100) UNIQUE NOT NULL,
            password_hash VARCHAR(255) NOT NULL
        );
        """;

    public static final String COURSE = """
        CREATE TABLE IF NOT EXISTS courses(
            uid VARCHAR(36) PRIMARY KEY,
            name VARCHAR(100),
            sub_title VARCHAR(100),
            description CLOB,
            school VARCHAR(100),
            semester VARCHAR(50),
            cover_image VARCHAR(255),
            status VARCHAR(20)
        );
        """;

    public static final String MEMBERSHIP = """
        CREATE TABLE IF NOT EXISTS memberships(
            course_uid VARCHAR(36),
            user_uid VARCHAR(36),
            role VARCHAR(20),
            PRIMARY KEY(course_uid,user_uid)
        );
        """;

    public static final String ASSIGNMENT = """
        CREATE TABLE IF NOT EXISTS assignments(
            uid VARCHAR(36) PRIMARY KEY,
            course_uid VARCHAR(36),
            title VARCHAR(100),
            content_type VARCHAR(20),
            content_data CLOB
        );
        """;

    public static final String SUBMISSION = """
        CREATE TABLE IF NOT EXISTS submissions(
            id INT AUTO_INCREMENT PRIMARY KEY,
            assignment_uid VARCHAR(36),
            student_uid VARCHAR(36),
            content CLOB,
            score DOUBLE,
            feedback CLOB,
            status VARCHAR(20)
        );
        """;
}
