package kr.revelope.study.refactoring.model;

import java.util.*;
import java.util.stream.Collectors;

public class Crime {

    private static String targetHeader;

    private static Map<String, Integer> crimeColumns = new HashMap<>();

    private List<CrimeObject> crimeObjects = new ArrayList<>();

    public void updateHeader(String readHeader) {
        String[] columns = readHeader.split(",");
        if (columns.length == 0) {
            throw new IllegalArgumentException("First line must be columns. Column can not found.");
        }

        for( int idx = 0; idx < columns.length ; idx++){
            crimeColumns.put(columns[idx], idx);
        }
    }

    public void updateContents(List<String> contents) {
        contents.forEach( text -> {
            String[] mainContents = text.split(",");
            if (mainContents.length != crimeColumns.size()) {
                System.out.println("Column count is not matched. must be " + crimeColumns.size() + ", but " + mainContents.length);
                return;
            }
            crimeObjects.add(new CrimeObject(Arrays.asList(mainContents)));
        });
    }

    public void updateTargetHeader(String targetText) {
        targetHeader = targetText;
    }

    public void counting() {
        Map<String, Integer> crimeColumnTextCount = new HashMap<>();

        //타겟 컬럼 내역 모두 가져오기
        int index = crimeColumns.get(targetHeader);
        List<String> collect =
                crimeObjects.stream().map(crimeObject -> crimeObject.contents.get(index)).collect(Collectors.toList());

        //컬럼별 카운팅
        collect.forEach( text -> {
            int count = 1;
            if(crimeColumnTextCount.get(text) != null) {
                count = crimeColumnTextCount.get(text) + 1 ;
            }
            crimeColumnTextCount.put(text, count);
        });

        //출력
        crimeColumnTextCount.entrySet().forEach(System.out::println);
    }



}
