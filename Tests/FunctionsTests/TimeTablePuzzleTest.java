package FunctionsTests;

import Functions.TimeTablePuzzle;
import TimeTableContainers.TimeTable;
import TimeTableObjects.Course;
import Helpers.Constants;

import TimeTableObjects.EventObjects.Activity;
import TimeTableObjects.EventObjects.CourseSection;

import TimeTableContainers.TimeTableManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TimeTablePuzzleTest {

    @Test
    public void isSolved() {
        TimeTableManager manager = new TimeTableManager();

        LocalTime startTime1 = LocalTime.of(9, 0, 0);
        LocalTime startTime2 = LocalTime.of(10, 0, 0);
        LocalTime endTime1 = LocalTime.of(10, 0, 0);
        LocalTime endTime2 = LocalTime.of(11, 0, 0);


        Object[] testDateTime1 = {Constants.MONDAY, startTime1, endTime1};
        Object[] testDateTime2 = {Constants.THURSDAY, startTime1, endTime1};

        HashMap<Object[], String> testDateTimeMap1 = new HashMap<>();
        testDateTimeMap1.put(testDateTime1, "LM161");
        testDateTimeMap1.put(testDateTime2, "LM161");
        Course A = new Course("CSC207","LEC0101", "Gries", "A&S", "In-Person",
                testDateTimeMap1, Constants.FALL, false);
        ArrayList<CourseSection> split = A.split();
        for (CourseSection section : split) {
            manager.getTimetable(Constants.FALL).schedule(section);
        }


        Object[] testDateTime3 = {Constants.WEDNESDAY, startTime2, endTime2};
        HashMap<Object[], String> testDateTimeMap2 = new HashMap<>();
        testDateTimeMap2.put(testDateTime3, "LM161");
        Course B = new Course("CSC207", "LEC0201", "Calver", "A&S", "In-Person",
                testDateTimeMap2, Constants.FALL, false);


        Object[] testDateTime4 = {Constants.TUESDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap3 = new HashMap<>();
        testDateTimeMap3.put(testDateTime4, "LM161");
        Course C = new Course("CSC207", "TUT0101", "TA", "A&S", "In-Person",
                testDateTimeMap3, Constants.FALL, false);
        for (CourseSection section : C.split()) {
            manager.getTimetable(Constants.FALL).schedule(section);
        }


        Object[] testDateTime5 = {Constants.FRIDAY, startTime2, endTime2};
        HashMap<Object[], String> testDateTimeMap4 = new HashMap<>();
        testDateTimeMap4.put(testDateTime5, "LM161");
        Course D = new Course("CSC207", "TUT0201", "TA", "A&S", "In-Person",
                testDateTimeMap4, Constants.FALL, false);


        Object[] testDateTime6 = {Constants.FRIDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap5 = new HashMap<>();
        testDateTimeMap5.put(testDateTime6, "LM161");
        Course E = new Course("MAT157", "LEC0101", "Prof", "A&S", "In-Person",
                testDateTimeMap5, Constants.FALL, false);
        for (CourseSection section : E.split()) {
            manager.getTimetable(Constants.FALL).schedule(section);
        }


        Object[] testDateTime7 = {Constants.WEDNESDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap6 = new HashMap<>();
        testDateTimeMap6.put(testDateTime7, "LM161");
        Course F = new Course("MAT157", "LEC0201", "Prof", "A&S", "In-Person",
                testDateTimeMap6, Constants.FALL, false);


        HashMap<String, HashMap<String, ArrayList<Course>>> courses = new HashMap<>();
        HashMap<String, ArrayList<Course>> csc = new HashMap<>();
        ArrayList<Course> cscLec = new ArrayList<>(Arrays.asList(A, B));
        ArrayList<Course> cscTut = new ArrayList<>(Arrays.asList(C, D));
        csc.put("LEC", cscLec);
        csc.put("TUT", cscTut);

        HashMap<String, ArrayList<Course>> mat = new HashMap<>();
        ArrayList<Course> matLec = new ArrayList<>(Arrays.asList(E, F));
        mat.put("LEC", matLec);

        courses.put("CSC207", csc);
        courses.put("MAT157", mat);

        TimeTablePuzzle ttPuzzle = new TimeTablePuzzle(courses, manager);
        boolean truth = ttPuzzle.isSolved();
        Assertions.assertTrue(truth);

    }

    @Test
    public void failFast() {
        TimeTableManager manager = new TimeTableManager();

        LocalTime startTime1 = LocalTime.of(9, 0, 0);
        LocalTime startTime2 = LocalTime.of(10, 0, 0);
        LocalTime endTime1 = LocalTime.of(10, 0, 0);
        LocalTime endTime2 = LocalTime.of(11, 0, 0);


        Object[] testDateTime1 = {Constants.MONDAY, startTime1, endTime1};
        Object[] testDateTime2 = {Constants.THURSDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap1 = new HashMap<>();
        testDateTimeMap1.put(testDateTime1, "LM161");
        testDateTimeMap1.put(testDateTime2, "LM161");
        Course A = new Course("CSC207", "LEC0101", "Gries", "A&S", "In-Person",
                testDateTimeMap1, Constants.FALL, false);
        ArrayList<CourseSection> split = A.split();
        for (CourseSection section : split) {
            manager.getTimetable(Constants.FALL).schedule(section);
        }


        Object[] testDateTime3 = {Constants.WEDNESDAY, startTime2, endTime2};
        HashMap<Object[], String> testDateTimeMap2 = new HashMap<>();
        testDateTimeMap2.put(testDateTime3, "LM161");
        Course B = new Course("CSC207", "LEC0201", "Calver", "A&S", "In-Person",
                testDateTimeMap2, Constants.FALL, false);


        Object[] testDateTime4 = {Constants.TUESDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap3 = new HashMap<>();
        testDateTimeMap3.put(testDateTime4, "LM161");
        Course C = new Course("CSC207", "TUT0101", "TA", "A&S", "In-Person",
                testDateTimeMap3, Constants.FALL, false);


        Object[] testDateTime5 = {Constants.FRIDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap4 = new HashMap<>();
        testDateTimeMap4.put(testDateTime5, "LM161");
        Course D = new Course("CSC207", "TUT0201", "TA", "A&S", "In-Person",
                testDateTimeMap4, Constants.FALL, false);


        Object[] testDateTime6 = {Constants.FRIDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap5 = new HashMap<>();
        testDateTimeMap5.put(testDateTime6, "LM161");
        Course E = new Course("MAT157", "LEC0101", "Prof", "A&S", "In-Person",
                testDateTimeMap5, Constants.FALL, false);
        for (CourseSection section : E.split()) {
            manager.getTimetable(Constants.FALL).schedule(section);
        }


        Object[] testDateTime7 = {Constants.WEDNESDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap6 = new HashMap<>();
        testDateTimeMap6.put(testDateTime7, "LM161");
        Course F = new Course("MAT157", "LEC0201", "Prof", "A&S", "In-Person",
                testDateTimeMap6, Constants.FALL, false);

        HashMap<String, HashMap<String, ArrayList<Course>>> courses = new HashMap<>();
        HashMap<String, ArrayList<Course>> csc = new HashMap<>();
        ArrayList<Course> cscLec = new ArrayList<>(Arrays.asList(A, B));
        ArrayList<Course> cscTut = new ArrayList<>(Arrays.asList(C, D));
        csc.put("LEC", cscLec);
        csc.put("TUT", cscTut);

        HashMap<String, ArrayList<Course>> mat = new HashMap<>();
        ArrayList<Course> matLec = new ArrayList<>(Arrays.asList(E, F));
        mat.put("LEC", matLec);

        courses.put("CSC207", csc);
        courses.put("MAT157", mat);

        TimeTablePuzzle ttPuzzle = new TimeTablePuzzle(courses, manager);
        Assertions.assertFalse(ttPuzzle.failFast());


        for (CourseSection section : C.split()) {
            manager.getTimetable(Constants.FALL).schedule(section);
        }
        Assertions.assertTrue(ttPuzzle.failFast());

    }

    @Test
    public void extensions() {
        TimeTableManager manager = new TimeTableManager();

        TimeTableManager managerC = new TimeTableManager();
        TimeTableManager managerD = new TimeTableManager();
        TimeTableManager managerE = new TimeTableManager();
        TimeTableManager managerF = new TimeTableManager();

        LocalTime startTime1 = LocalTime.of(9, 0, 0);
        LocalTime startTime2 = LocalTime.of(10, 0, 0);
        LocalTime endTime1 = LocalTime.of(10, 0, 0);
        LocalTime endTime2 = LocalTime.of(11, 0, 0);


        Object[] testDateTime1 = {Constants.MONDAY, startTime1, endTime1};
        Object[] testDateTime2 = {Constants.THURSDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap1 = new HashMap<>();
        testDateTimeMap1.put(testDateTime1, "LM161");
        testDateTimeMap1.put(testDateTime2, "LM161");
        Course A = new Course("CSC207", "LEC0101", "Gries", "A&S", "In-Person",
                testDateTimeMap1, Constants.FALL, false);
        ArrayList<CourseSection> split = A.split();
        for (CourseSection section : split) {
            manager.getTimetable(Constants.FALL).schedule(section);
            managerC.getTimetable(Constants.FALL).schedule(section);
            managerD.getTimetable(Constants.FALL).schedule(section);
            managerE.getTimetable(Constants.FALL).schedule(section);
            managerF.getTimetable(Constants.FALL).schedule(section);
        }


        Object[] testDateTime3 = {Constants.WEDNESDAY, startTime2, endTime2};
        HashMap<Object[], String> testDateTimeMap2 = new HashMap<>();
        testDateTimeMap2.put(testDateTime3, "LM161");
        Course B = new Course("CSC207", "LEC0201", "Calver", "A&S", "In-Person",
                testDateTimeMap2, Constants.FALL, false);


        Object[] testDateTime4 = {Constants.TUESDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap3 = new HashMap<>();
        testDateTimeMap3.put(testDateTime4, "LM161");
        Course C = new Course("CSC207", "TUT0101", "TA", "A&S", "In-Person",
                testDateTimeMap3, Constants.FALL, false);


        Object[] testDateTime5 = {Constants.FRIDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap4 = new HashMap<>();
        testDateTimeMap4.put(testDateTime5, "LM161");
        Course D = new Course("CSC207", "TUT0201", "TA", "A&S", "In-Person",
                testDateTimeMap4, Constants.FALL, false);


        Object[] testDateTime6 = {Constants.FRIDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap5 = new HashMap<>();
        testDateTimeMap5.put(testDateTime6, "LM161");
        Course E = new Course("MAT157", "LEC0101", "Prof", "A&S", "In-Person",
                testDateTimeMap5, Constants.FALL, false);


        Object[] testDateTime7 = {Constants.WEDNESDAY, startTime1, endTime1};
        HashMap<Object[], String> testDateTimeMap6 = new HashMap<>();
        testDateTimeMap6.put(testDateTime7, "LM161");
        Course F = new Course("MAT157", "LEC0201", "Prof", "A&S", "In-Person",
                testDateTimeMap6, Constants.FALL, false);

        HashMap<String, HashMap<String, ArrayList<Course>>> courses = new HashMap<>();
        HashMap<String, ArrayList<Course>> csc = new HashMap<>();
        ArrayList<Course> cscLec = new ArrayList<>(Arrays.asList(A, B));
        ArrayList<Course> cscTut = new ArrayList<>(Arrays.asList(C, D));
        csc.put("LEC", cscLec);
        csc.put("TUT", cscTut);

        HashMap<String, ArrayList<Course>> mat = new HashMap<>();
        ArrayList<Course> matLec = new ArrayList<>(Arrays.asList(E, F));
        mat.put("LEC", matLec);

        courses.put("CSC207", csc);
        courses.put("MAT157", mat);
        TimeTablePuzzle ttPuzzle = new TimeTablePuzzle(courses, manager);

        TimeTablePuzzle ttPuzzleC = new TimeTablePuzzle(courses, managerC);
        ArrayList<CourseSection> splitC = C.split();
        for (CourseSection section : splitC) {
            String term = section.getTerm();
            ttPuzzleC.getManager().getTimetable(term).schedule(section);
        }

        TimeTablePuzzle ttPuzzleD = new TimeTablePuzzle(courses, managerD);
        ArrayList<CourseSection> splitD = D.split();
        for (CourseSection section : splitD) {
            String term = section.getTerm();
            ttPuzzleD.getManager().getTimetable(term).schedule(section);
        }

        TimeTablePuzzle ttPuzzleE = new TimeTablePuzzle(courses, managerE);
        ArrayList<CourseSection> splitE = E.split();
        for (CourseSection section : splitE) {
            String term = section.getTerm();
            ttPuzzleE.getManager().getTimetable(term).schedule(section);
        }

        TimeTablePuzzle ttPuzzleF = new TimeTablePuzzle(courses, managerF);
        ArrayList<CourseSection> splitF = F.split();
        for (CourseSection section : splitF) {
            String term = section.getTerm();
            ttPuzzleF.getManager().getTimetable(term).schedule(section);
        }

        TimeTablePuzzle[] expected = {ttPuzzleC, ttPuzzleD, ttPuzzleE, ttPuzzleF};

        TimeTablePuzzle[] actual = ttPuzzle.extensions();

        System.out.println("hi");

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void schedulePuzzle() {

        LocalTime startTime1 = LocalTime.of(9, 0, 0);
        LocalTime startTime2 = LocalTime.of(10, 0, 0);
        LocalTime endTime1 = LocalTime.of(10, 0, 0);
        LocalTime endTime2 = LocalTime.of(11, 0, 0);

        CourseSection lecture1 = new CourseSection("MAT257", startTime1,endTime1,
                "SS100",Constants.MONDAY,Constants.YEAR,"LEC 0101","Gauss","Arts and Science","In Person", false);
        CourseSection lecture2 = new CourseSection("MAT157", startTime1,endTime2,
                "SS101",Constants.TUESDAY,Constants.FALL,"LEC 0101", "Descartes","Arts and Science","Online", false);
        CourseSection lecture3 = new CourseSection("MAT137", startTime1,endTime1,
                "SS100",Constants.WEDNESDAY,Constants.FALL,"LEC 0101", "Alphonso","Arts and Science","Online", false);

        TimeTableManager manager1 = new TimeTableManager();
        manager1.getTimetable(Constants.FALL).schedule(lecture1);
        manager1.getTimetable(Constants.FALL).schedule(lecture2);
        manager1.getTimetable(Constants.FALL).schedule(lecture3);

        TimeTableManager manager2 = new TimeTableManager();
        manager2.getTimetable(Constants.FALL).schedule(lecture1);

        HashMap<String, HashMap<String, ArrayList<Course>>> courses = new HashMap<>();

        TimeTablePuzzle puzzle1 = new TimeTablePuzzle(courses, manager1);
        TimeTablePuzzle puzzle2 = new TimeTablePuzzle(courses, manager2);
        puzzle2.schedulePuzzle(puzzle1);

        Assertions.assertEquals(puzzle1, puzzle2);

    }
}
