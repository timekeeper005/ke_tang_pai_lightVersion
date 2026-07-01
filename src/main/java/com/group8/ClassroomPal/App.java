package com.group8.ClassroomPal;
import com.group8.ClassroomPal.controller.AssignmentController;
import com.group8.ClassroomPal.database.Database;
// import com.group8.ClassroomPal.

import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        // 初始化数据库
        Database.init();

        // 创建服务器
        Javalin app = Javalin.create(config -> {

            config.staticFiles.add("frontend");

        });

        // 注册Controller
        AssignmentController.register(app);
        // SubmissionController.register(app);

        // 以后继续注册
        // UserController.register(app);
        // CourseController.register(app);

        // 启动
        app.start(7070);

        System.out.println("Server Started");
        System.out.println("http://localhost:7070");
    }
}