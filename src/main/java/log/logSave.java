package log;

import java.io.*;

// наброски кода для сохранения лога чата....

public class logSave {
    public static void addMessageRecord(String strings, String fileName) {
        File file = new File(fileName);
        if(!(file.exists()) || (!file.isFile())) {
            try {
                try(
                        BufferedOutputStream friendsOut =
                                new BufferedOutputStream(new FileOutputStream(fileName));
                ){
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        try {
            try(
                    FileWriter writer = new FileWriter(file,true);
            ){
                writer.write(strings + "\r\n");
                writer.flush();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }
}