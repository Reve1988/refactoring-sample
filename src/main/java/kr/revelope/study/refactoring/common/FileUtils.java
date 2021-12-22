package kr.revelope.study.refactoring.common;

import kr.revelope.study.refactoring.DirtyCodeMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    private static BufferedReader load(String filePath) {
        InputStream is = DirtyCodeMain.class.getClassLoader().getResourceAsStream(filePath);
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    public static String readHeader(String filePath) throws IOException {
        BufferedReader bufferedReader = load(filePath);
        return bufferedReader.readLine();
    }

    public static List<String> readMain(String filePath) throws IOException {
        String line;
        List<String> mainContents = new ArrayList<>();

        BufferedReader bufferedReader = load(filePath);

        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            mainContents.add(line);
        }

        return mainContents;
    }

}
