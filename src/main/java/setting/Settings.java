package setting;

import java.io.*;
import java.util.Properties;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: Prekrasnov Sergei
 * \* Date: 20.06.2022
 * \* ----- group JAVA-27 -----
 */
public class Settings {
    private static final String SETTING_FILE = "src/main/resources/setting.txt";
    public static int PORT;
    public static String SERVERNAME;

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;
        try {
            propertiesFile = new FileInputStream(SETTING_FILE);
            properties.load(propertiesFile);
            PORT = Integer.parseInt(properties.getProperty("PORT"));
            SERVERNAME = properties.getProperty("SERVERNAME");
        } catch (FileNotFoundException ex) {
            System.err.println("Файл свойств отсуствует!");
        } catch (IOException ex) {
            System.err.println("Ошибка чтения файла");
        } finally {
            try {
                propertiesFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
