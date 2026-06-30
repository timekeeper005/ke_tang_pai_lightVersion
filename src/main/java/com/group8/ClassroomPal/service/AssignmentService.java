package com.group8.ClassroomPal.service;


import com.group8.ClassroomPal.dao.AssignmentDAO;
import com.group8.ClassroomPal.model.Assignment;

import java.util.List;
import java.util.UUID;

public class AssignmentService {

    public static void publish(Assignment assignment){

        assignment.uid=UUID.randomUUID().toString();

        AssignmentDAO.insert(assignment);
    }

    public static Assignment get(String uid){

        return AssignmentDAO.findByUid(uid);

    }

    public static List<Assignment> list(String courseUid){

        return AssignmentDAO.findByCourse(courseUid);

    }

    public static void update(Assignment assignment){

        AssignmentDAO.update(assignment);

    }

    public static void delete(String uid){

        AssignmentDAO.delete(uid);

    }

}
