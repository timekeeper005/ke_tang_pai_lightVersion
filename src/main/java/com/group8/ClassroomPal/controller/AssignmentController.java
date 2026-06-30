package com.group8.ClassroomPal.controller;

import com.group8.ClassroomPal.model.Assignment;
import com.group8.ClassroomPal.service.AssignmentService;
import io.javalin.Javalin;

import java.util.Map;

public class AssignmentController {

    public static void register(Javalin app){

        app.post("/api/assignment",ctx->{

            Assignment assignment=ctx.bodyAsClass(Assignment.class);

            AssignmentService.publish(assignment);

            ctx.json(Map.of("status","success"));

        });

        app.get("/api/assignment/{uid}",ctx->{

            String uid=ctx.pathParam("uid");

            ctx.json(AssignmentService.get(uid));

        });

        app.get("/api/assignment/course/{courseUid}",ctx->{

            String courseUid=ctx.pathParam("courseUid");

            ctx.json(AssignmentService.list(courseUid));

        });

        app.put("/api/assignment/{uid}",ctx->{

            Assignment assignment=ctx.bodyAsClass(Assignment.class);

            assignment.uid=ctx.pathParam("uid");

            AssignmentService.update(assignment);

            ctx.json(Map.of("status","success"));

        });

        app.delete("/api/assignment/{uid}",ctx->{

            AssignmentService.delete(ctx.pathParam("uid"));

            ctx.json(Map.of("status","success"));

        });

    }

}
