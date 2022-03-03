package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {

    /**
     * 多条件课程查询
     * */
    public List<Course> findCourseByCondition(CourseVO courseVO);

    /**
     * 添加课程信息以及讲师信息
     * */
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    /**
     * 根據id查詢课程信息
     * */
    public CourseVO findCourseById(Integer id);

    /**
     * 修改课程信息以及讲师信息
     * */
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;


    /**
     * 课程状态变更
     * */
    public void updateCourseStatus(int courseId,int status);
}
