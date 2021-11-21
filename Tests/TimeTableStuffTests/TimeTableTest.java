package TimeTableStuffTests;

import TimeTableObjects.EventObjects.Activity;
import TimeTableObjects.Course;
import TimeTableObjects.EventObjects.Task;
import Helpers.Constants;

import TimeTableObjects.EventObjects.CourseSection;

import TimeTableContainers.TimeTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TimeTableTest {

    @Test
    public void schedule() {
        LocalTime time5 =  LocalTime.of(5,0,0);
        LocalTime time6 =  LocalTime.of(6,0,0);
        LocalTime time9 =  LocalTime.of(9,0,0);
        LocalTime time10 = LocalTime.of(10, 0,0);
        LocalTime time11 = LocalTime.of(11,0,0);

        CourseSection lecture1 = new CourseSection("MAT257", time9,time10,
                "SS100",Constants.MONDAY,Constants.YEAR,"LEC 0101","Gauss","Arts and Science","In Person", false);
        CourseSection lecture2 = new CourseSection("MAT157", time9,time11,
                "SS101",Constants.MONDAY,Constants.FALL,"LEC 0101", "Descartes","Arts and Science","Online", false);
        CourseSection lecture3 = new CourseSection("MAT137", time10,time11,
                "SS100",Constants.MONDAY,Constants.FALL,"LEC 0101", "Alphonso","Arts and Science","Online", false);
        Activity activity = new Activity(time6,time9,Constants.MONDAY,Constants.FALL,"nap");
        Task task= new Task(time5,time6,"home",Constants.MONDAY,Constants.FALL);
        TimeTable table = new TimeTable();
        assertTrue(table.schedule(lecture1));
        assertFalse(table.schedule(lecture2));
        assertTrue(table.schedule(lecture3));
        assertTrue(table.schedule(activity));
        assertTrue(table.schedule(task));
    }

    @Test
    public void testToString() {    //TODO needs to be reworked on
        LocalTime time5 =  LocalTime.of(5,0,0);
        LocalTime time6 =  LocalTime.of(6,0,0);
        LocalTime time9 =  LocalTime.of(9,0,0);
        LocalTime time10 = LocalTime.of(10, 0,0);
        LocalTime time11 = LocalTime.of(11,0,0);

        CourseSection lecture0 = new CourseSection("MAT257", time9,time10,
                "SS100", Constants.MONDAY,Constants.YEAR,"LEC 0101","Gauss","Arts and Science","In Person", false);
        CourseSection lecture1 = new CourseSection("MAT137", time10,time11,
                "SS100",Constants.THURSDAY,Constants.FALL,"LEC 0101", "Alphonso","Arts and Science","Online", false);
        Activity activity = new Activity(time6,time9,Constants.MONDAY,Constants.FALL,"nap");
        Task task= new Task(time5,time6,"home",Constants.MONDAY,Constants.FALL);

        TimeTable table = new TimeTable();
        table.schedule(lecture0);
        table.schedule(lecture1);
        table.schedule(activity);
        table.schedule(task);
        String actual = table.toString();

        String expectedCourse0 = "9:00 MAT257 of Arts and Science with Gauss by In Person at SS100";
        String expectedCourse1 = "10:00 MAT137 of Arts and Science with Alphonso by Online at SS100";
        String expectedActivity1 = "6:00 nap";
        String expectedActivity2 = "7:00 nap";
        String expectedActivity3 = "8:00 nap";
        String expectedTask= "5:00 ";
        assertTrue(actual.contains(expectedCourse0));
        assertTrue(actual.contains(expectedCourse1));
        assertTrue(actual.contains(expectedActivity1)
                && actual.contains(expectedActivity2) &&actual.contains(expectedActivity3));
        assertTrue(actual.contains(expectedTask));
    }

    @Test
    public void checkCourseSection() {
        LocalTime startTime1 = LocalTime.of(9, 0, 0);
        LocalTime startTime2 = LocalTime.of(10, 0, 0);
        LocalTime endTime1 = LocalTime.of(10, 0, 0);
        LocalTime endTime2 = LocalTime.of(11, 0, 0);

        Object[] date1 = {Constants.MONDAY, startTime1, endTime1};
        Object[] date2 = {Constants.THURSDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap1 = new HashMap<>();
        testDateTimeMap1.put(date1, "LM161");
        testDateTimeMap1.put(date2, "LM161");

        Course course1 = new Course("MAT257", "LEC0101", "Gausss", "A&S",
                "In Person", testDateTimeMap1, Constants.FALL, true);

        Object[] date3 = {Constants.TUESDAY, startTime2, endTime2};
        Object[] date4 = {Constants.THURSDAY, startTime2, endTime2};
        HashMap<Object[], String> testDateTimeMap2 = new HashMap<>();
        testDateTimeMap1.put(date3, "LM161");
        testDateTimeMap1.put(date4, "LM161");

        Course course2 = new Course("MAT257", "LEC0201", "Descartes", "A&S",
                "Online", testDateTimeMap2, Constants.FALL, false);

        TimeTable table = new TimeTable();

        ArrayList<CourseSection> split1 = course1.split();
        for (CourseSection splitSection1 : split1) {
            table.schedule(splitSection1);
        }

        Assertions.assertTrue(table.checkCourseSection(course1));
        Assertions.assertFalse(table.checkCourseSection(course2));

    }

}
