/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121part1new;

/*
allows us to use Scanner to get information typed by the user
*/
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class PROG5121Part1New {

    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       
        System.out.println("==============================");
        System.out.println("     MESSAGING APP");
        System.out.println("==============================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println();
        
        System.out.println("Choose an option: ");
        int option = input.nextInt();
        input.nextLine();
        
        if (option == 1) {
            
            System.out.println();
            System.out.println("--------- REGISTER ---------");
            System.out.println();
            
            //shows the question to the user
        System.out.print("Enter your name: ");
        /*
        waits for the user to type their response and stores it in the variable
        */
        String firstName = input.nextLine();
        
         System.out.print("Enter your last name: ");
        String lastName = input.nextLine();
        
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        
        System.out.print("Enter your cell phone number: ");
        String cellPhoneNumber = input.nextLine();
        
        Login login = new Login ( //Login object
        firstName,
        lastName,
        username,
        password,
        cellPhoneNumber
        );
        
         String registrationMessage = login.registerUser(); //calls the registerUser()
                                                           //from Login class
        
        System.out.println();                                                   
        System.out.println("Registration:");                                                   
        System.out.println(registrationMessage.replace("; ", ";\n"));
        
        if (registrationMessage.equals("Registration successful.")) {
                
                System.out.println();
                System.out.println("Registration successful!");
                System.out.println();
                System.out.println("--------- LOGIN ---------");
                System.out.println();
                
                //after registration the program will ask:
                System.out.print("Enter your username to login: ");
                String loginUsername = input.nextLine();
                    
                System.out.print("Enter your password to login: ");
                String loginPassword = input.nextLine();
                
                //we send the username & password typed by user into the setLoginDetails method
                login.setLoginDetails(loginUsername, loginPassword);
                
                System.out.println();
                //calls the method to check if the details are correct
                System.out.println(login.returnLoginStatus());
            }      
        } else if (option == 2) {
            
            System.out.println();
            System.out.println("---------   LOGIN ---------");
            System.out.println();
            System.out.println("Please register first before logging in.");
   
        } else {
            
            System.out.println();
            System.out.println("Invalid option. Please choose 1 or 2.");
        }

        input.close();
    }
}
