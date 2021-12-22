package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.common.FileUtils;
import kr.revelope.study.refactoring.model.Crime;

import java.io.IOException;

/**
 * csv 파일을 읽어서 특정 컬럼명을 group by 하여 출력하는 프로그램이다.
 * args[0] : resources에 보관된 csv 파일명
 * args[1] : 카운트 할 컬럼명
 *
 * 아래 코드를 리팩토링 해보시오
 */
public class DirtyCodeMain {

	public static void main(String[] args) throws IOException {

		//아규먼트 처리
		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("File name and target column name is required.");
		}
		String filePath = args[0];
		String targetText = args[1];

		//update : header, contents , targetText
		Crime crimeMap = new Crime();
		crimeMap.updateHeader(FileUtils.readHeader(filePath));
		crimeMap.updateContents(FileUtils.readMain(filePath));
		crimeMap.updateTargetHeader(targetText);

		//counting targetText
		crimeMap.counting();

	}
}
