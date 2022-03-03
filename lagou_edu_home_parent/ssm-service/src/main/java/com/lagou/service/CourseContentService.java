package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;
import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface CourseContentService {

    /**
     * 根据课程id查询对应的课程信息
     */
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /**
     * 回显章节对应的课程信息
     * */
    public Course findCourseByCourseId(Integer courseId);

    /**
     * 新增章节信息
     * */
    public void saveSection(CourseSection courseSection);

    /**
     * 更新章节信息
     * */
    void updateSection(CourseSection courseSection);

    /**
     * 修改课程状态
     * */
    void updateSectionStatus(int id,int status);

}
