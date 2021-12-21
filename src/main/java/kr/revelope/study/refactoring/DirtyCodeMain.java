package kr.revelope.study.refactoring;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * csv 파일을 읽어서 특정 컬럼명을 group by 하여 출력하는 프로그램이다.
 * args[0] : resources에 보관된 csv 파일명
 * args[1] : 카운트 할 컬럼명
 * <p>
 * 아래 코드를 리팩토링 해보시오
 */
public class DirtyCodeMain {
	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("File name and target column name is required.");
		}

		InputStream inputStream = DirtyCodeMain.class.getClassLoader().getResourceAsStream(args[0]);
		if (inputStream == null) {
			throw new IllegalArgumentException("'" + args[0] + "' file can not found.");
		}

		try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			 CSVReader csvReader = new CSVReader(streamReader)) {

			String[] header = csvReader.readNext();
			if (header == null || header.length == 0) {
				throw new IllegalArgumentException("First line must be columns. Column can not found.");
			}

			int columnIndex = ArrayUtils.indexOf(header, args[1]);
			if (columnIndex == -1) {
				throw new IllegalArgumentException("First line must be columns. Column can not found.");
			}

			List<String[]> dataList = csvReader.readAll();
			Map<String, List<String>> result = dataList.stream()
					.filter(data -> data.length == header.length)
					.map(data -> data[columnIndex])
					.collect(Collectors.groupingBy(column -> column));

			for (String column : result.keySet()) {
				System.out.println(column + " : " + result.get(column).size());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
