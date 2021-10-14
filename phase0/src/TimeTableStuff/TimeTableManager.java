package TimeTableStuff;

import TimeTableObjects.CourseStuff.Course;
import TimeTableObjects.CourseStuff.Section;

import ConstantsAndExceptions.Constants;
import TimeTableObjects.Life;

// Importing HashMap class
import java.util.ArrayList;
import java.util.HashMap;

public class TimeTableManager {
    private final HashMap<String, TimeTable> timetables;

    /**
     * Creates a new TimeTableManager with two default TimeTables for Fall and Winter.
     *
     */
    public TimeTableManager() {
        this.timetables = new HashMap<>(){{
            put("Fall", new TimeTable());
            put("Winter", new TimeTable());
        }};
    }

    /**
     * Add a new empty TimeTable with the given term.
     * @param term given term the of timetable
     * @return true if we successfully add a new TimeTable, else false.
     */
    public boolean addTimeTable(String term) {
        timetables.put(term, new TimeTable());
        // TODO Failed to add a new timetable.
        return true;
    }

    /**
     * Remove an existing TimeTable with the given term.
     * @param term given term the of timetable
     * @return true if we successfully remove a TimeTable, else false.
     */
    public boolean removeTimeTable(String term) {
        timetables.remove(term, new TimeTable());
        // TODO Failed to remove a new timetable.
        return true;
    }

    /**
     * Get the timetable object for the given term.
     *
     * @return the corresponding TimeTable object.
     */
    public TimeTable getTimetable(String term) {
        return timetables.get(term);
        // TODO What if the timetable is not found.
    }

    /**
     * Get the course from interface and schedule it to the corresponding timetable(s).
     *
     * @param c a Course object passed from user interface
     */
    public void schedule(Course c) {
        ArrayList<Section> list = c.split();
        if (c.getTerm().equals(Constants.FALL)) {
            for (Section x : list) {
                timetables.get(Constants.FALL).schedule(x);
            }
        } if (c.getTerm().equals(Constants.WINTER)) {
            for (Section x : list) {
                timetables.get(Constants.FALL).schedule(x);
            }
        } else {
            for (Section x : list) {
                timetables.get("Fall").schedule(x);
                timetables.get("Winter").schedule(x);
            }
        }
    }

    /**
     * Get the life from interface and schedule it to the corresponding timetable(s).
     *
     * @param l a Life object passed from user interface
     */
    public void schedule(Life l) {
        timetables.get(Constants.FALL).schedule(l);
        timetables.get(Constants.WINTER).schedule(l);
    }

    /**
     * Returns an array of timetables with all the timetables.
     *
     * @return an array of timetables with all the timetables
     */
    public TimeTable[] getAllTimeTables(){
        TimeTable[] theTimes = new TimeTable[this.timetables.keySet().size()];
        int i = 0;
        for (String term : this.timetables.keySet()){
            theTimes[i] = this.timetables.get(term);
        }
        return theTimes;
    }
}
