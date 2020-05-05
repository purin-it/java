package com.example.demo;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Component
public class DemoComponent {

    public String getTextString() throws IOException{
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new FileReader("C:\\tmp\\test.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }catch (FileNotFoundException ex1){
            throw ex1;
        }catch (IOException ex2){
            throw ex2;
        }
        return sb.toString();
    }

}
