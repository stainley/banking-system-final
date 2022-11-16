package edu.lambton;

import edu.lambton.screen.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean keepRunning = true;

        MainMenu mainMenu = new MainMenu();
        while(keepRunning) {
            mainMenu.createMainScreen();
            Scanner selectOption = new Scanner(System.in);
            System.out.print("Select an option: ");
            int option = selectOption.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Entering");
                    break;
                case 2:
                    System.out.println("Exiting....");
                    keepRunning = false;
                    System.exit(9);
                    break;
                default:
                    System.out.println("Invalid option");
                    keepRunning = false;
                    break;
            }
        }
    }
}