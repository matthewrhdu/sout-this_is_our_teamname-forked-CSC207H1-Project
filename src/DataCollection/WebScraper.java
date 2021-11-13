package DataCollection;

import EntitiesAndObjects.Course;
import GlobalHelpers.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;

public class WebScraper extends DataGetter{

    /**
     * Constructor of the CSVScraper. Reads and filters the data correctly
     * into the data hashmap.
     *
     * @param courseName the name of the course
     * @param theTerm the term of the course
     * @param theYear the course starts.
     */
    //TODO FileNotOFoundException
    @Override
    public void CalibrateData(String courseName, String theTerm,
                              String theYear) throws FileNotFoundException{
        try {
            // format the url and connect to the coursefinder
            if (Integer.parseInt(theYear) == 2021){
                theYear = theYear + "9";
            }
            else{
                theYear = theYear + "1";
            }

            Document doc = Jsoup.connect(
                    "https://coursefinder.utoronto.ca/course-search/search/courseInquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId="+ courseName + theTerm.charAt(0)+ theYear)
                    .get();

            // If the html has no infomation about term. i.e. files not found.
            if (doc.select("span#u158").text().isEmpty()){
                throw new FileNotFoundException();
            }

            // find element by combination of elements with id.
            String term = doc.select("span#u158").text();
            String faculty = doc.select("span#u13").text();
            String coursecode = doc.select("div#u19").text();

            // clean up the string
            term = removeCss(term);
            faculty = removeCss(faculty);
            coursecode = removeCss(coursecode);

            // loop over the rows in the html and add corresponding sections.
            int i = 0;
            while(i <= 100){
                String section = doc.select("span#u245_line"+ i).text();
                String prof = doc.select("span#u263_line"+ i).text();
                String time = doc.select("span#u254_line"+ i).text();
                String location = doc.select("span#u272_line"+ i).text();
                String delmethod = doc.select("span#u314_line"+ i).text();
                if (section.equals("")){
                    break;
                }

                // clean up the string.
                section = removeCss(section);
                prof = removeCss(prof);
                delmethod = removeCss(delmethod);
                if (prof.isEmpty()){
                    prof = Constants.TBA;
                }
                ArrayList<Object[]> times = splitDateTime(removeCss(time));
                ArrayList<String> locations = splitLocations(removeCss(location));

                // create the location time map.
                HashMap<Object[], String> locationTimeMap = new HashMap<>();
                int j = 0;
                while (j < locations.size()){
                    locationTimeMap.put(times.get(j), locations.get(j));
                    j++;
                }

                //TODO Waitlist and summer course?
                if (coursecode.contains("Y1")){
                    addYearCourseToData(section, faculty, locationTimeMap, prof, delmethod, false);
                }
                else if (term.contains("Fall")){
                    addTermedCourseToData("Fall", section, faculty, locationTimeMap, prof, delmethod, false);
                }
                else if (term.contains("Winter")){
                    addTermedCourseToData("Winter", section, faculty, locationTimeMap, prof, delmethod, false);
                }
                i++;
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File Not Found");
            System.exit(1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //TODO Waitlist always false
    /**
     * Add the given data to self.data
     * @param term the term of course
     * @param sectionName the name of the section
     * @param faculty the associated faculty
     * @param timeToLocationMap the current time and location. Given as a
     *                         HashMap of the Time -> Location
     * @param theInstructor the instructor of the course section
     * @param theDeliveryMethod the delivery method of the course.
     * @param theWaitlist whether the course is waitlisted or not.
     */
    private void addTermedCourseToData(String term,
                                       String sectionName,
                                       String faculty,
                                       HashMap<Object[],
                                               String> timeToLocationMap,
                                       String theInstructor,
                                       String theDeliveryMethod,
                                       boolean theWaitlist){
        Course theCourse = new Course(sectionName, theInstructor, faculty,
                theDeliveryMethod, timeToLocationMap, term, theWaitlist);
        placeToData(sectionName, theCourse);
    }

    /**
     * Add the given data to super.data.
     *
     * @param sectionName the name of the section
     * @param faculty the associated faculty
     * @param timeToLocationMap the current time and location. Given as a
     *                         HashMap of the Time -> Location
     * @param theInstructor the instructor of the course section
     * @param theDeliveryMethod the delivery method of the course.
     * @param theWaitlist whether the course is waitlisted or not.
     */
    private void addYearCourseToData(String sectionName,
                                     String faculty,
                                     HashMap<Object[], String> timeToLocationMap,
                                     String theInstructor,
                                     String theDeliveryMethod,
                                     boolean theWaitlist){
        Course theCourse = new Course(sectionName, theInstructor, faculty,
                theDeliveryMethod, timeToLocationMap, Constants.YEAR, theWaitlist);
        placeToData(sectionName, theCourse);
    }

    /**
     * Clean up the strings with Css tags.
     *
     * @param dirty the string being cleaned.
     * @return a string without css tags
     */
    private String removeCss(String dirty){
        String cleaned;
        cleaned = dirty.replaceAll("(?is)<style.*?>.*?</style>", "");
        return cleaned;
    }

    /**
     * Splits the formattedTimeString into the date, start time, end time in
     * that order
     *
     * If the time is TBA, assign the time to be 00:00:00
     *
     * @param formattedTimeString the formattedTimeString of the date, start,
     *                           and end times
     * @return nested arraylist with the form [{date, start time, end time}, {date, start time, end time}]
     */
    private ArrayList<Object[]> splitDateTime(String formattedTimeString){
        String[] times = formattedTimeString.split("(?=\\s[A-Z])");
        ArrayList<Object[]> retList = new ArrayList<>();
        if (times.length != 0){
            for (String element : times) {
                element = element.trim();
                String[]elementl = element.split(" ");
                Object[] l = {formatDate(elementl[0]), elementl[1].split("-")[0],
                        elementl[1].split("-")[1]};
                retList.add(l);
            }
        }
        else{
            Object[] l = {Constants.TBA, LocalTime.of(0, 0, 0),
                    LocalTime.of(0, 0, 0)};
            retList.add(l);
        }
        return retList;
    }

    /**
     * Splits the formattedLocationString into an arraylist with [location, location]
     * If there is no location then the location is TBA.
     *
     * @param formattedLocationString the formattedLocationString of the "Location Location"
     * @return arraylist with the form [location, location]
     */
    private ArrayList<String> splitLocations(String formattedLocationString){
        ArrayList<String> retList = new ArrayList<>();
        if (!formattedLocationString.isEmpty()){
            String[] locations = formattedLocationString.split("(?=\\s[A-Z])");
            for (String element : locations) {
                element = element.trim();
                retList.add(element);
            }
        }
        else{
            retList.add(Constants.TBA);
        }
        return retList;
    }

    /**
     * Change the letter case of the given date.
     *
     * @param date a string of date with all upper case letter.
     * @return new string of date with the upper case at the front and lower case follows.
     */
    private String formatDate(String date){
        String firstLet = date.substring(0, 1);
        String remLet = date.substring(1);
        remLet = remLet.toLowerCase();
        return firstLet + remLet;
    }

    /**
     * A main method to develop this module
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        WebScraper a = new WebScraper();
        try {
            LinkedHashMap<String, ArrayList<Course>> got = a.getData("CSC207H1", "Fall", "2021");
            System.out.println(got);
        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }
}


