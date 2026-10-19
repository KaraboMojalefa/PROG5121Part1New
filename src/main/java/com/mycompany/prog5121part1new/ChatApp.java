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
public class ChatApp {

    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       
        System.out.println("==============================");
        System.out.println("          CHAT APP");
        System.out.println("==============================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println();
        
        System.out.println("Choose an option: ");
        //stores the option the user chose to register
        int option = input.nextInt();
        input.nextLine();
        
        //checks if the user chose to register
        if (option == 1) {
            
            //keeps registration going until all details are correct
            boolean registered = false;
            Login login = null;
            
            //the registration will keep running until the details are correct
            while (!registered) { 
            
            //shows the registration section
            System.out.println();
            System.out.println("--------- REGISTER ---------");
            System.out.println();
            
        //gets the user's first name
        System.out.print("Enter your name: ");
        /*
        waits for the user to type their response and stores it in the variable
        */
        String firstName = input.nextLine();
        
        //gets the user's last name
        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();
        
        //gets the user's username to check
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        
        //creates a Login object to check the username
        Login usernameCheck = new Login(
            firstName,
            lastName,
            username,
            "",
            ""
        );
        
        //checks if the username is valid
        if (!usernameCheck.checkUserName()) {
            
            //shows the username error message
            System.out.println();
            System.out.println("Username is not correctly formatted;");
            System.out.println("please ensure that your username contains an underscore");
            System.out.println("and is no more than five characters in length.");
            System.out.println();
            System.out.println("Please try again.");
            //starts the registration again if the username is incorrect
            continue;
        }
        
        //shows the username was accepted
        System.out.println();
        System.out.println("Username successfully captured.");
        
        //gets the user's password to check
        System.out.println();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        
        //creates a Login object to check the password
        Login passwordCheck = new Login(
            firstName,
            lastName,
            username,
            password,
            ""
        );
        
        //checks if the password is valid
        if (!passwordCheck.checkPasswordComplexity()) {
            
            //shows the password error message
            System.out.println();
            System.out.println("Password is not correctly formatted;");
            System.out.println("please ensure that the password contains at least eight");
            System.out.println("characters, a capital letter, a number, and a special character.");
            System.out.println();
            System.out.println("Please try again.");
            //starts the registration again if the password is incorrect
            continue;
        }
        
        //shows that the password was accepted
        System.out.println();
        System.out.println("Password successfully captured.");
        
        System.out.println();
        //gets the cell phone number to check
        System.out.print("Enter your cell phone number (+27...): ");
        String cellPhoneNumber = input.nextLine();
        
        //creates a Login object to check cell phone number
        Login phoneCheck = new Login(
            firstName,
            lastName,
            username,
            password,
            cellPhoneNumber
);
        
        //checks if the cell phone number is valid
        if (!phoneCheck.checkCellPhoneNumber()) {
            
            //shows the cell phone number error message
            System.out.println(); 
            System.out.println("Cell phone number incorrectly formatted");
            System.out.println("or does not contain international code.");
            System.out.println();
            System.out.println("Please try again.");
            //starts the registration again if the cell phone number is incorrect
            continue;
        }
            
            //shows that the cell phone number was accepted
            System.out.println();
            System.out.println("Cell phone number successfully added.");
        
        login = new Login(
            firstName,
            lastName,
            username,
            password,
            cellPhoneNumber
        );
        
        registered = true;
        
        System.out.println(); 
        //uses registerUser to confirm that the registration was successful
        System.out.println(login.registerUser());                                                   
      
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
                
                // Open QuickChat only after a successful login
                if (login.loginUser()) {
                    System.out.println("Welcome to QuickChat.");
                        quickChatMenu(input);
                }
            }      
        } else if (option == 2) {
            
            System.out.println();
            System.out.println("---------   LOGIN ---------");
            System.out.println();
            System.out.println("Please register first before logging in.");
   
        } else {
            
            System.out.println();
            System.out.println("Invalid option. Please restart and choose 1 or 2.");
        }

        input.close();
    }
    
    // Show the menu and keep going until the user quits
    public static void quickChatMenu(Scanner scanner) {
        int choice = 0;

        while (choice != 3) {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");
            
            // Make sure the user enters a number
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }
            
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("How many messages do you want to send? ");

                // Check that the user enters a number
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number.");
                    scanner.nextLine();
                    continue;
                }

                int numberOfMessages = scanner.nextInt();
                scanner.nextLine();

                // Send only the number of messages chosen
                for (int i = 0; i < numberOfMessages; i++) {
                    System.out.print("Enter recipient cell number: ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter your message: ");
                    String messageText = scanner.nextLine();

                    Message message = new Message(i, recipient, messageText);

                    System.out.println(message.checkMessageLength());

                    if (messageText.length() > 250) {
                        continue;
                    }

                    System.out.println(message.checkRecipientCell());

                    if (!message.checkRecipientCell().equals(
                            "Cell phone number successfully captured.")) {
                        continue;
                    }

                    System.out.println("1. Send Message");
                    System.out.println("2. Disregard Message");
                    System.out.println("3. Store Message");
                    System.out.print("Choose an option: ");
                    
                    // Check that the user enters a number
                    if (!scanner.hasNextInt()) {
                        System.out.println("Please enter a number.");
                        scanner.nextLine();
                        continue;
                    }

                    int messageChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (messageChoice == 1) {
                        System.out.println(message.SentMessage(1));
                        message.addSentMessage();
                        System.out.println(message.printMessages());
                    } else if (messageChoice == 2) {
                        System.out.println(message.SentMessage(2));
                    } else if (messageChoice == 3) {
                        System.out.println(message.storeMessage());
                    } else {
                        System.out.println("Invalid option.");
                    }
                }

                // Show how many messages were sent
                if (numberOfMessages > 0) {
                    Message counter = new Message(0, "+27718693002", "Count");
                    System.out.println("Total messages sent: "
                            + counter.returnTotalMessagess());
                }

            } else if (choice == 2) {
                String messages = Message.printAllSentMessages();
                
                if (messages.isEmpty()) {
                    System.out.println("No messages have been sent yet.");
                } else {
                    System.out.println(messages);
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
