package edu.lambton.util;

import java.io.IOException;

public class MenuUtil {

    private static MenuUtil instance;
    private MenuUtil(){}

    public static MenuUtil getInstance() {
        if(instance == null) {
            instance = new MenuUtil();
        }
        return instance;
    }

    public void clearScreen() {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.print("\033\143");
        }

    }
}
