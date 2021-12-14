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
	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("File name and target column name is required.");
		}

		InputStream is = DirtyCodeMain.class.getClassLoader().getResourceAsStream(args[0]);
		if (is == null) {
			throw new IllegalArgumentException("'" + args[0] + "' file can not found.");
		}

		Map<String, Integer> result = new HashMap<>();
		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
			 BufferedReader reader = new BufferedReader(streamReader)) {
			String line;
			int columnCount = -1;
			int targetColumnIndex = -1;
			boolean isHeader = true;
			while ((line = reader.readLine()) != null) {
				if (isHeader) {
					String[] columns = line.split(",");
					if (columns.length == 0) {
						throw new IllegalArgumentException("First line must be columns. Column can not found.");
					}

					columnCount = columns.length;
					for (int i = 0; i < columnCount; i++) {
						if (columns[i].equals(args[1])) {
							targetColumnIndex = i;
						}
					}

					if (targetColumnIndex < 0) {
						throw new IllegalStateException("Can not found target column '" + args[1] + "'");
					}

					isHeader = false;
					continue;
				}

				String[] columns = line.split(",");
				if (columns.length != columnCount) {
					System.out.println("Column count is not matched. must be " + columnCount + ", but " + columns.length);
					continue;
				}

				result.put(columns[targetColumnIndex], result.get(columns[targetColumnIndex]) != null ? result.get(columns[targetColumnIndex]) + 1 : 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Map.Entry<String, Integer> entry : result.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
