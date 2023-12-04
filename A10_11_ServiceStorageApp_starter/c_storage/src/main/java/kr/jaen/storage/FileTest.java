package kr.jaen.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileTest {
    public static void main(String[] args) throws IOException {
        File file = new File(".");
        System.out.println(file.getAbsolutePath());
        String [] fileList = file.list();

        for (String name: fileList) {
            System.out.println(name);
        }

        File newFile = new File("./abc.txt");
        newFile.createNewFile();

        InputStream in = System.in;
        InputStreamReader read = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(read);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String inString = bufferedReader.readLine();

        System.out.println(inString);
//        int read = in.read();
//        System.out.println(read);
    }
}
