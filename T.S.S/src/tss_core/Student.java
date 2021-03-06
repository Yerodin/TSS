package tss_core;

import java.util.ArrayList;

/**
 * @author Tevin Anderson 
 */

public class Student extends Person
{
    private ArrayList <String> coursespassed;
    private boolean cleared;
    private int ID;
    private ArrayList <String> registeredcourses;

    public Student(int id, String firstName, String lastName, ArrayList <String> coursesPassed, boolean financiallycleared, ArrayList <String> registeredCourses)
    {
    	super(firstName,lastName);
        ID = id;
        coursespassed = coursesPassed;
        cleared = financiallycleared;
        registeredcourses = registeredCourses;
    }

    /**
     * @return  the courses passed by the student
     */
    public ArrayList <String> getCoursepassed()
    {
        return coursespassed;
    }

    /**
     * @return true  if student is cleared otherwise false
     */
    public boolean isfinclear()
    {
        if (cleared == true){
         return true;
        }
        else{
        return false;
        }
    }

    
    /**
     * @return  the students ID
     */
    public int getid()
    {
        return ID;
    }
    
    public ArrayList <String> getregisteredCoursesCRN(){
         return registeredcourses;
    } 
}
