package tss_timetableProcessor;

import java.util.ArrayList;
import java.util.Scanner;

import tss_core.Course;

/**
 * 
 * @author Yerodin Richards
 *
 */
public class TimetableGenerator
{
	public static ArrayList<Timetable> generateTimetables(ArrayList<Course> courses,
			ArrayList<Filter> filters)
	{
		return null;
	}

	public static ArrayList<Timetable> generateAllPossibleTimetables(ArrayList<Course> courses)
	{
		ArrayList<Timetable> returnList = new ArrayList<Timetable>();
		ArrayList<ArrayList<Course>> coursePool = new ArrayList<ArrayList<Course>>();
		ArrayList<String> temp = new ArrayList<String>();
		
		//get amt of pools
		for (int i = 0; i < courses.size(); ++i)
		{
			Course c = courses.get(i);
			String toCheck = c.getCode() + String.valueOf(c.getType().charAt(0));
			if (!temp.contains(toCheck))
				temp.add(toCheck);
		}
		for (int i = 0; i < temp.size(); ++i)
			coursePool.add(new ArrayList<Course>());
		for (int i = 0; i < courses.size(); ++i)
		{
			Course c = courses.get(i);
			String toCheck = c.getCode() + String.valueOf(c.getType().charAt(0));
			coursePool.get(temp.indexOf(toCheck)).add(c);
		}
		ArrayList<Integer> eachPoolSize = new ArrayList<Integer>();
		for(int i = 0; i < coursePool.size(); ++ i)
			eachPoolSize.add(coursePool.get(i).size());
		
		ArrayList<String> allPossiblePermutes = getPermutes2(eachPoolSize);
		ArrayList<Timetable> clashesTimetables = new ArrayList<Timetable>();
		
		boolean atLeastOne = false;
		for(int i = 0; i < allPossiblePermutes.size(); ++i)
		{
			String tempP = allPossiblePermutes.get(i);
			ArrayList<TimeSlot> slots = new ArrayList<TimeSlot>();
			for(int j = 0; j < coursePool.size(); ++ j)
			{
				slots.addAll(coursePool.get(j).get(Integer.parseInt(String.valueOf(tempP.charAt(j)))).getTimeslot());
			} 
			int beginTime = slots.get(0).getBeginTime();
			int endTime = slots.get(0).getEndTime();
			for(int j = 1; j < slots.size(); ++j)
			{
				if(beginTime > slots.get(j).getBeginTime())
					beginTime = slots.get(j).getBeginTime();
				if(endTime < slots.get(j).getEndTime())
					endTime = slots.get(j).getEndTime();
			}
			Timetable t = new Timetable(beginTime, endTime);
			for(int j = 0; j < slots.size(); ++ j)
			{
				t.addTimeSlot(slots.get(j), true);
			}
			if(t.hasClashes())
			{
				if(!atLeastOne)
					clashesTimetables.add(t);
			}
			else
			{
				atLeastOne = true;
				returnList.add(t);
			}
		}
		if(returnList.size() == 0)
			returnList.addAll(clashesTimetables);
		return returnList;
		
	}

	/**
	 * A function that is used to get all the combinations of courses from all the pools.
	 * @param eachPoolSize The amount of course streams in each pool
	 * @author Yerodin
	 * @return An arrayList of String with all the combinations in the form "xxxx" where x is the xth index of the pool.
	 */
	public static ArrayList<String> getPermutes2(ArrayList<Integer> eachPoolSize)
	{
		String current = "";
		for(int i = 0; i < eachPoolSize.size(); ++ i)
			current += "0";
		return recursivePermutes(eachPoolSize,current);
	}
	
	/**
	 * A recursive function that calculates all the permutations of an integer string by specifying the maximum number from
	 * 0 in which each number in the string can get.
	 * @param eachSize a list with the maximum of each integer in the string from 0;
	 * @param current the string to be processed
	 * @author Yerodin Richards
	 * @return the permutations.
	 */
	public static ArrayList<String> recursivePermutes(ArrayList<Integer> eachSize, String current)
	{
		if(current.length() == 0)
		{
			ArrayList<String> ret = new ArrayList<String>();
			ret.add("");
			return ret;
		}
		ArrayList<Integer> temp = new ArrayList<Integer>(eachSize);
		int size = temp.remove(temp.size()-1);
		ArrayList<String> ret = recursivePermutes(temp,current.substring(0, current.length()-1));
		ArrayList<String> ret2 = allPerms(size);
		
		ArrayList<String> finalRet = new ArrayList<String>();
		for(int i = 0; i < ret.size(); ++i)
			for(int j = 0; j < ret2.size(); ++j)
				finalRet.add(ret.get(i)+ret2.get(j));
		return finalRet;
	}
	
	public static ArrayList<String> allPerms(int size)
	{
		ArrayList<String> returnArray = new ArrayList<String>();
		for(int i = 0; i < size; ++i)
			returnArray.add(String.valueOf(i));
		return returnArray;
	}
}
