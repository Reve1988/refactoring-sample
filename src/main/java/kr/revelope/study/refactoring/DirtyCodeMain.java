package kr.revelope.study.refactoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * csv 파일을 읽어서 특정 컬럼명을 group by 하여 출력하는 프로그램이다.
 * args[0] : resources에 보관된 csv 파일명
 * args[1] : 카운트 할 컬럼명
 *
 * 아래 코드를 리팩토링 해보시오
 */
public class DirtyCodeMain {

	private static Map<String, Integer> crimeColumn = new HashMap<>();

	private static String targetText;

	private static Map<String, Integer> crimeColumnTextCount = new HashMap<>();

	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("File name and target column name is required.");
		}

		InputStream is = DirtyCodeMain.class.getClassLoader().getResourceAsStream(args[0]);
		if (is == null) {
			throw new IllegalArgumentException("'" + args[0] + "' file can not found.");
		}

		targetText = args[1];

		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(streamReader)) {

			readHeaderCSV(reader);

			String line;
			while (( line = reader.readLine()) != null) {
				countingTargetText(getTargetColumnText(readMainCSV(line)));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		countingToString();
	}

	private static void countingTargetText(String targetColumnText) {
		int count = 1;

		if(crimeColumnTextCount.get(targetColumnText) != null) {
			count = crimeColumnTextCount.get(targetColumnText) + 1 ;
		}

		crimeColumnTextCount.put(targetColumnText, count);
	}

	private static void countingToString(){
		crimeColumnTextCount.entrySet().forEach(System.out::println);
	}

	public static void readHeaderCSV(BufferedReader reader) throws IOException {

		//read only first Line
		String line = reader.readLine();

		String[] columns = line.split(",");
		if (columns.length == 0) {
			throw new IllegalArgumentException("First line must be columns. Column can not found.");
		}

		for( int idx = 0; idx < columns.length ; idx++){
			crimeColumn.put(columns[idx], idx);
		}

	}

	public static String[] readMainCSV(String line){

		String[] mainTexts = line.split(",");

		if (mainTexts.length != crimeColumn.size()) {
			System.out.println("Column count is not matched. must be " + crimeColumn.size() + ", but " + mainTexts.length);
			return null;
		}

		return mainTexts;
	}

	public static String getTargetColumnText(String[] mainTexts) {

		if(mainTexts != null) {
			return mainTexts[crimeColumn.get(targetText)];
		}
		return null;
	}

}
