package com.example.mobiililabra2_1_async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetHTMLString {

    public static String htmlString(InputStream in) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;

        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
            stringBuilder.append(newLine);
        }
        return stringBuilder.toString();
    }
}
