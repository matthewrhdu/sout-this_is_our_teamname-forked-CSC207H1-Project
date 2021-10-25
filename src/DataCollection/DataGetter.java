package DataCollection;

import EntitiesAndObjects.Course;

import java.util.LinkedHashMap;

/**
 * A data gathering class / database class
 */
public abstract class DataGetter {
    private final LinkedHashMap<String, Course> data;

    /**
     * A constructor for this class.
     * - data is a HashMap, where the keys are the section name, and the
     * values are the Course objects
     */
    public DataGetter(){
        this.data = new LinkedHashMap<>();
    }

    /**
     * Add the Course to the data HashMap
     *
     * @param name the name of the section
     * @param theCourse the course object
     */
    protected void placeToData(String name, Course theCourse) {
        this.data.put(name, theCourse);
    }

    /**
     * An abstract class to calibrate the data HashMap!
     *
     * @param courseName the Course Name
     */
    abstract void CalibrateData(String courseName);

    /**
     * A Getter class for the Data HashMap
     * @param courseName the name of the Course
     * @return the Data HashMap
     */
    public LinkedHashMap<String, Course> getData(String courseName){
        CalibrateData(courseName);
        return this.data;
    }
}