package edu.lambton.util;

import java.io.IOException;

public class MenuUtil {

    public static void clearScreen() {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
                //Runtime.getRuntime().exec("clear");
                System.out.print("\033\143");
        }

    }
}