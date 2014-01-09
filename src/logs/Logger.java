package logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    public void appendToFile(String data) {
        try {
            File file = new File("BanyanMailLogs.log");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(), true);
//            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//                bufferWritter.write(data);
//                bufferWritter.newLine();
            PrintWriter pw = new PrintWriter(fileWritter);
            pw.println(data);
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void clearLogs() {
        try {
            String data = "Banyan Tree Advisors - Client Mail Application Logs";
            File file = new File("BanyanMailLogs.log");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName());
            try (BufferedWriter bufferWritter = new BufferedWriter(fileWritter)) {
                bufferWritter.write(data);
                bufferWritter.newLine();
                bufferWritter.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
  
    
    public void errorLogger(Exception ex){
        try {
            String data = ex.getMessage();
            File file = new File("BanyanMailError.log");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(),true);
            try (BufferedWriter bufferWritter = new BufferedWriter(fileWritter)) {
                bufferWritter.write(data);
                bufferWritter.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
