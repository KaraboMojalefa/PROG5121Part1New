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

        // Load previously stored messages when the application starts
        Message.loadStoredMessages();

        System.out.println("==============================");
        System.out.println("          CHAT APP");
        System.out.println("==============================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println();

        System.out.println("Choose an option: ");

        // Stores the option the user chose
        int option = input.nextInt();
        input.nextLine();

        // Checks if the user chose to register
        if (option == 1) {

            // Keeps registration going until all details are correct
            boolean registered = false;
            Login login = null;

            // Registration continues until the details are correct
            while (!registered) {

                // Shows the registration section
                System.out.println();
                System.out.println("--------- REGISTER ---------");
                System.out.println();

                // Gets the user's first name
                System.out.print("Enter your name: ");
                String firstName = input.nextLine();

                // Gets the user's last name
                System.out.print("Enter your last name: ");
                String lastName = input.nextLine();

                // Gets the user's username
                System.out.print("Enter your username: ");
                String username = input.nextLine();

                // Creates a Login object to check the username
                Login usernameCheck = new Login(
                        firstName,
                        lastName,
                        username,
                        "",
                        ""
                );

                // Checks if the username is valid
                if (!usernameCheck.checkUserName()) {

                    System.out.println();
                    System.out.println("Username is not correctly formatted;");
                    System.out.println("please ensure that your username contains an underscore");
                    System.out.println("and is no more than five characters in length.");
                    System.out.println();
                    System.out.println("Please try again.");

                    // Starts registration again if username is incorrect
                    continue;
                }

                System.out.println();
                System.out.println("Username successfully captured.");

                // Gets the user's password
                System.out.println();
                System.out.print("Enter your password: ");
                String password = input.nextLine();

                // Creates a Login object to check the password
                Login passwordCheck = new Login(
                        firstName,
                        lastName,
                        username,
                        password,
                        ""
                );

                // Checks if the password is valid
                if (!passwordCheck.checkPasswordComplexity()) {

                    System.out.println();
                    System.out.println("Password is not correctly formatted;");
                    System.out.println("please ensure that the password contains at least eight");
                    System.out.println("characters, a capital letter, a number, and a special character.");
                    System.out.println();
                    System.out.println("Please try again.");

                    // Starts registration again if password is incorrect
                    continue;
                }

                System.out.println();
                System.out.println("Password successfully captured.");

                // Gets the cell phone number
                System.out.println();
                System.out.print("Enter your cell phone number (+27...): ");
                String cellPhoneNumber = input.nextLine();

                // Creates a Login object to check the cell phone number
                Login phoneCheck = new Login(
                        firstName,
                        lastName,
                        username,
                        password,
                        cellPhoneNumber
                );

                // Checks if the cell phone number is valid
                if (!phoneCheck.checkCellPhoneNumber()) {

                    System.out.println();
                    System.out.println("Cell phone number incorrectly formatted");
                    System.out.println("or does not contain international code.");
                    System.out.println();
                    System.out.println("Please try again.");

                    // Starts registration again if phone number is incorrect
                    continue;
                }

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

                // Confirms that registration was successful
                System.out.println(login.registerUser());

                System.out.println();
                System.out.println("--------- LOGIN ---------");
                System.out.println();

                // Asks the user to log in after registration
                System.out.print("Enter your username to login: ");
                String loginUsername = input.nextLine();

                System.out.print("Enter your password to login: ");
                String loginPassword = input.nextLine();

                // Sends the login details to the setLoginDetails method
                login.setLoginDetails(loginUsername, loginPassword);

                System.out.println();

                // Checks if the login details are correct
                System.out.println(login.returnLoginStatus());

                // Opens QuickChat only after a successful login
                if (login.loginUser()) {
                    System.out.println("Welcome to QuickChat.");
                    quickChatMenu(input, login.getFullName());
                }
            }

        } else if (option == 2) {

            System.out.println();
            System.out.println("--------- LOGIN ---------");
            System.out.println();
            System.out.println("Please register first before logging in.");

        } else {

            System.out.println();
            System.out.println("Invalid option. Please restart and choose 1 or 2.");
        }

        input.close();
    }

    // Shows the menu and keeps going until the user quits
    public static void quickChatMenu(Scanner scanner, String sender) {
        int choice = 0;

        while (choice != 3) {

            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            // Makes sure the user enters a number
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("How many messages do you want to send? ");

                // Checks that the user enters a number
                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number.");
                    scanner.nextLine();
                    continue;
                }

                int numberOfMessages = scanner.nextInt();
                scanner.nextLine();

                // Sends only the number of messages chosen
                for (int i = 0; i < numberOfMessages; i++) {

                    String recipient;
                    String messageText;
                    Message message;

                    // Keep asking until both the recipient and message are valid
                    while (true) {

                        System.out.print("Enter recipient cell number: ");
                        recipient = scanner.nextLine();

                        System.out.print("Enter your message: ");
                        messageText = scanner.nextLine();

                        // Check the message length
                        if (messageText.length() > 250) {
                            System.out.println("Please enter a message of 250 characters or less.");
                            System.out.println("Try again.\n");
                            continue;
                        }

                        // Create the message and check the recipient number
                        message = new Message(i, sender, recipient, messageText);

                        String recipientStatus = message.checkRecipientCell();

                        System.out.println(recipientStatus);

                        if (!recipientStatus.equals(
                                "Cell phone number successfully captured.")) {
                            System.out.println("Please enter the details again.\n");
                            continue;
                        }

                        // Both checks passed, so leave the retry loop
                        break;
                    }

                    System.out.println("1. Send Message");
                    System.out.println("2. Disregard Message");
                    System.out.println("3. Store Message");
                    System.out.print("Choose an option: ");

                    // Checks that the user enters a number
                    if (!scanner.hasNextInt()) {
                        System.out.println("Please enter a number.");
                        scanner.nextLine();
                        continue;
                    }

                    int messageChoice = scanner.nextInt();
                    scanner.nextLine();

                    // Records the ID and hash for every selected message
                    if (messageChoice == 1 || messageChoice == 2
                            || messageChoice == 3) {
                        message.addMessageDetails();
                    }

                    if (messageChoice == 1) {

                        System.out.println(message.SentMessage(1));
                        message.addSentMessage();
                        System.out.println(message.printMessages());

                    } else if (messageChoice == 2) {

                        System.out.println(message.SentMessage(2));
                        message.addDisregardedMessage();

                    } else if (messageChoice == 3) {

                        System.out.println(message.storeMessage());

                    } else {

                        System.out.println("Invalid option.");
                    }
                }

                // Shows how many messages were sent
                if (numberOfMessages > 0) {
                    Message counter = new Message(
                            0, "+27718693002", "Count"
                    );

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

            } else if (choice == 4) {

                storedMessagesMenu(scanner);

            } else {

                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Displays the Stored Messages submenu
    public static void storedMessagesMenu(Scanner scanner) {

        int choice = 0;

        while (choice != 7) {

            System.out.println("\n====== STORED MESSAGES ======");
            System.out.println("1. Display senders and recipients");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search by Message ID");
            System.out.println("4. Search by recipient");
            System.out.println("5. Delete message using Message Hash");
            System.out.println("6. Display full stored messages report");
            System.out.println("7. Back to main menu");

            System.out.print("Choose an option: ");

            // Makes sure the user enters a number
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println(
                            Message.displayStoredSendersAndRecipients()
                    );
                    break;

                case 2:
                    System.out.println(
                            Message.displayLongestStoredMessage()
                    );
                    break;

                case 3:
                    System.out.print("Enter Message ID: ");
                    String searchID = scanner.nextLine();

                    System.out.println(
                            Message.searchStoredMessageByID(searchID)
                    );
                    break;

                case 4:
                    System.out.print("Enter recipient cell number: ");
                    String searchRecipient = scanner.nextLine();

                    System.out.println(
                            Message.searchStoredMessagesByRecipient(
                                    searchRecipient
                            )
                    );
                    break;

                case 5:
                    System.out.print("Enter Message Hash: ");
                    String searchHash = scanner.nextLine();

                    System.out.println(
                            Message.deleteStoredMessageByHash(searchHash)
                    );
                    break;

                case 6:
                    System.out.println(
                            Message.displayFullStoredMessageReport()
                    );
                    break;

                case 7:
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}