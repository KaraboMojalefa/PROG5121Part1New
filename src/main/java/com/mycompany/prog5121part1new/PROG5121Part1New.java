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
       
        //shows the question to the user
        System.out.println("Enter your name: ");
        /*
        waits for the user to type their response and stores it in the variable
        */
        String firstName = input.nextLine();
        
        System.out.println("Enter your last name: ");
        String lastName = input.nextLine();
        
        System.out.println("Enter you username: ");
        String username = input.nextLine();
        
        System.out.println("Enter your password: ");
        String password = input.nextLine();
        
        System.out.println("Enter your cell phone number: ");
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
        System.out.println(registrationMessage);
    }
}
