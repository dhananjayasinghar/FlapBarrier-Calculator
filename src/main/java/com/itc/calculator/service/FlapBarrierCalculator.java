package com.itc.calculator.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itc.calculator.exception.AppException;
import com.itc.calculator.utils.Constants;

/**
 *
 * @author Dhananjay Samanta
 */
public class FlapBarrierCalculator {

	public static String getCalculatedData(ArrayList<String> dataList) throws Exception {
		validateRecord(dataList);
		return getResult(dataList);

	}

	/**
	 * 
	 * @param dataList
	 * @return
	 * @throws ParseException
	 */
	private static String getResult(ArrayList<String> dataList) throws ParseException {
		ArrayList<String> timeDifferencesArray = new ArrayList<>();
		for (int i = 0; i < dataList.size(); i = i + 2) {
			Date inTime = getTime(dataList.get(i));
			Date outTime = getTime(dataList.get(i + 1));
			timeDifferencesArray.add(getTimeDiff(inTime, outTime));
		}
		return "Flap Barrier Time: " + calculate(timeDifferencesArray);
	}

	/**
	 * This method has been written for validating the time and take the latest if double entry is there
	 * @param dataList
	 * @throws ParseException 
	 */
	private static void validateRecord(List<String> dataList) throws ParseException {

		if(Pattern.compile(Constants.ENTRY_REGEX).matcher(dataList.get(dataList.size()-1)).find()){
			String currentTime = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT, Locale.ENGLISH).format(LocalDateTime.now());
			dataList.add("XXXXX XXXXXXXXXXX "+currentTime+" Valid Exit XXXXXX XXXX  ");
		}
		
		for (int i = 0; i < dataList.size(); i++) {
			String data = dataList.get(i);
			if (Pattern.compile(Constants.ENTRY_REGEX).matcher(data).find()
					&& Pattern.compile(Constants.ENTRY_REGEX).matcher(dataList.get(i + 1)).find() && dataList.size() <= i) {
				dataList.remove(i);
				i=0;
			}
		}

		long entryCount = dataList.stream().filter(e -> Pattern.compile(Constants.ENTRY_REGEX).matcher(e).find())
				.count();
		long exitCount = dataList.stream().filter(e -> Pattern.compile(Constants.EXIT_REGEX).matcher(e).find()).count();

		String lastEntry = dataList.get(dataList.size()-1);
		boolean lastEntryFlag = Pattern.compile(Constants.EXIT_REGEX).matcher(lastEntry).find();
		
		if (entryCount != exitCount && entryCount > exitCount){
			if(!lastEntryFlag){
				LocalDateTime ldt = LocalDateTime.now();
				dataList.add(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT, Locale.ENGLISH).format(ldt));
			}
			throw new AppException("Please Check Exit Information");
		}
		if (entryCount != exitCount)
			throw new AppException("Please enter valid Data");
	}

	/**
	 * This method has been written for get the difference between two time
	 * @param start
	 * @param last
	 * @return
	 * @throws ParseException
	 */
	private static String getTimeDiff(Date start, Date last) throws ParseException {
		long diff = last.getTime() - start.getTime();
		long diffSeconds = diff / 1000 % Constants.NUMBER_60;
		long diffMinutes = diff / (Constants.NUMBER_60 * 1000) % Constants.NUMBER_60;
		long diffHours = diff / (Constants.NUMBER_60 * Constants.NUMBER_60 * 1000) % 24;
		// long diffDays = diff / (24 * 60 * 60 * 1000);

		return String.format("%d:%d:%d", diffHours, diffMinutes, diffSeconds);
	}

	/**
	 * This method has been written for get the time information from content in dd/mm/yyyy hh:mm:ss AM/PM format
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	private static Date getTime(String data) throws ParseException {
		final Pattern pattern = Pattern.compile(Constants.REGEX_PATTERN);
		final Matcher matcher = pattern.matcher(data);
		while (matcher.find()) {
			data = matcher.group(0);
		}
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
		return format.parse(data);
	}

	private static String calculate(ArrayList<String> timeDifferenceList) {
		int hour = 0;
		int minute = 0;
		int second = 0;

		for (int i = 0; i < timeDifferenceList.size(); i++) {
			String[] timeArray = timeDifferenceList.get(i).split(":");

			hour = Integer.parseInt(timeArray[0]) + hour;
			minute = Integer.parseInt(timeArray[1]) + minute;
			second = Integer.parseInt(timeArray[2]) + second;
		}
		hour = hour + minute / Constants.NUMBER_60;
		minute = (minute % Constants.NUMBER_60) + second / Constants.NUMBER_60;
		second = second % Constants.NUMBER_60;

		return String.format(" %d Hours, %d minutes, %d seconds", hour, minute, second);

	}
	
}
