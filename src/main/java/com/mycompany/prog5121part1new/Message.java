/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121part1new;

/**
 *
 * @author Student
 */

import java.util.Random;  
import java.util.Arrays;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class Message {
     
    private String messageID;
    private int messageNumber;
    private String sender;
    private String recipient;   
    private String message;
    private String messageHash;
    
    // Keep track of the messages sent in this run
    private static int totalMessagesSent = 0;

    // Dynamic arrays for the different message categories
    private static Message[] sentMessages = new Message[0];
    private static Message[] disregardedMessages = new Message[0];
    private static Message[] storedMessages = new Message[0];

    // Dynamic arrays for message IDs and hashes
    private static String[] messageIDs = new String[0];
    private static String[] messageHashes = new String[0];

    // Existing constructor, retained for compatibility with current tests
    public Message(int messageNumber, String recipient, String message) {
        this(messageNumber, "Unknown", recipient, message);
    }

    // Constructor that records the sender of the message
    public Message(int messageNumber, String sender,
            String recipient, String message) {

        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;

        // Create a random ten-digit message ID using string manipulation
        Random random = new Random();

        String randomDigits = String.valueOf(
                1000000000L + random.nextInt(900000000)
        );

        // Use substring to create the ten-digit message ID
        messageID = randomDigits.substring(0, 10);

        // Create the message hash
        messageHash = createMessageHash();
    }
    
    public boolean checkMessageID() {
        return messageID.matches("\\d{10}");
    }

    public String checkRecipientCell() {
        if (recipient.matches("^\\+\\d{1,3}\\d{1,9}$")
                && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. "
                + "Please correct the number and try again.";
    }

        // Put the message ID, number and words together
        public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String firstWord = "";
        String lastWord = "";

        String trimmed = message.trim();

        if (!trimmed.isEmpty()) {
            String[] words = trimmed.split("\\s+");
            
            // Get the first and last words
            firstWord = words[0];
            lastWord = words[words.length - 1];
            
            // Remove punctuation from those words
            firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
            lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");
            
        }

        return (firstTwo + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }
    
    // Check if the message is short enough
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int extraCharacters = message.length() - 250;

        return "Message exceeds 250 characters by "
                + extraCharacters
                + "; please reduce the size.";
    }
    
    public String SentMessage(int choice) {
        if (choice == 1) {
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            return "Message successfully stored.";
        }

        return "Invalid option.";
    }

    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }
    
    
    // Returns the total number of messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    // Add a message to the sent list and increase the count
   // Add a message to the sent messages array
   public void addSentMessage() {

        // Add the complete message object to the dynamic array
        sentMessages = addMessageToArray(sentMessages, this);
        // Increase the total number of sent messages
        totalMessagesSent++;
    }// End of addSentMessage()
   
   // Record the ID and hash of every message
    public void addMessageDetails() {

        messageIDs = addStringToArray(messageIDs, messageID);
        messageHashes = addStringToArray(messageHashes, messageHash);
    }
    
    // Add a message to the disregarded messages array
    public void addDisregardedMessage() {

        disregardedMessages = addMessageToArray(
                disregardedMessages, this);
    }

    // Show all the messages sent so far
    // Display all messages in the sent messages array
    public static String printAllSentMessages() {

        StringBuilder output = new StringBuilder();

        for (Message sentMessage : sentMessages) {
            output.append(sentMessage.printMessages())
                    .append("\n\n");
        }

        return output.toString();
    }
    
        // Add a message to an array by expanding the array
    private static Message[] addMessageToArray(
            Message[] oldArray, Message newMessage) {

        Message[] newArray = Arrays.copyOf(
                oldArray, oldArray.length + 1);

        newArray[newArray.length - 1] = newMessage;

        return newArray;
    }

    // Add a string to an array by expanding the array
    private static String[] addStringToArray(
            String[] oldArray, String newString) {

        String[] newArray = Arrays.copyOf(
                oldArray, oldArray.length + 1);

        newArray[newArray.length - 1] = newString;

        return newArray;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }
    
    // Return the sender's name, or Unknown for older stored messages
    public String getSender() {
        if (sender == null || sender.trim().isEmpty()) {
            return "Unknown";
        }

        return sender;
    }
    
    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
    
    // Load previously stored messages from the JSON file.
    // Gson converts JSON data into Java objects.
    // Source: Google Gson User Guide:
    // https://github.com/google/gson/blob/main/UserGuide.md
    public static void loadStoredMessages() {

        String fileName = "messages.json";

        // Check whether the JSON file exists
        if (!Files.exists(Paths.get(fileName))) {
            return;
        }

        try (java.io.Reader reader = Files.newBufferedReader(
                Paths.get(fileName), StandardCharsets.UTF_8)) {

            // Use Gson to convert JSON data into Message objects
            Gson gson = new Gson();

            Message[] loadedMessages = gson.fromJson(
                    reader, Message[].class);

            // Restore the stored messages array
            if (loadedMessages == null) {
                storedMessages = new Message[0];
            } else {
                storedMessages = loadedMessages;
            }

            // Restore the message IDs and hashes
            messageIDs = new String[0];
            messageHashes = new String[0];

            for (Message storedMessage : storedMessages) {
                messageIDs = addStringToArray(
                        messageIDs, storedMessage.getMessageID());

                messageHashes = addStringToArray(
                        messageHashes, storedMessage.getMessageHash());
            }

        } catch (IOException | JsonParseException e) {
            System.out.println("Could not load stored messages: "
                    + e.getMessage());
        }
    }
    
    // Display the sender and recipient of all stored messages
    public static String displayStoredSendersAndRecipients() {

        StringBuilder output = new StringBuilder();

        // Check whether there are any stored messages
        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        // Display the sender and recipient of each stored message
        for (Message storedMessage : storedMessages) {

            output.append("Sender: ")
                    .append(storedMessage.getSender())
                    .append("\n");

            output.append("Recipient: ")
                    .append(storedMessage.getRecipient())
                    .append("\n\n");
        }

        return output.toString();
    }
    
        // Find and display the longest stored message
    public static String displayLongestStoredMessage() {

        // Check whether there are stored messages
        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        Message longestMessage = storedMessages[0];

        // Compare the length of each stored message
        for (Message storedMessage : storedMessages) {

            if (storedMessage.getMessage().length()
                    > longestMessage.getMessage().length()) {

                longestMessage = storedMessage;
            }
        }

        return "Longest Stored Message:\n"
                + longestMessage.printMessages()
                + "\nMessage Length: "
                + longestMessage.getMessage().length()
                + " characters.";
    }
    // Search for a stored message using its Message ID
    public static String searchStoredMessageByID(String searchID) {

        // Check whether stored messages exist
        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        // Search through all stored messages
        for (Message storedMessage : storedMessages) {

            if (storedMessage.getMessageID().equals(searchID)) {

                return "Message found!\n"
                        + "Recipient: "
                        + storedMessage.getRecipient()
                        + "\nMessage: "
                        + storedMessage.getMessage();
            }
        }

        // Display this if no matching ID is found
        return "Message ID not found.";
    }
    
    // Search for all stored messages belonging to a recipient
    public static String searchStoredMessagesByRecipient(String searchRecipient) {

        StringBuilder output = new StringBuilder();

        boolean found = false;

        // Search through all stored messages
        for (Message storedMessage : storedMessages) {

            if (storedMessage.getRecipient().equals(searchRecipient)) {

                output.append("Message ID: ")
                        .append(storedMessage.getMessageID())
                        .append("\nSender: ")
                        .append(storedMessage.getSender())
                        .append("\nRecipient: ")
                        .append(storedMessage.getRecipient())
                        .append("\nMessage: ")
                        .append(storedMessage.getMessage())
                        .append("\n\n");

                found = true;
            }
        }

        if (!found) {
            return "No messages found for this recipient.";
        }

        return output.toString();
    }
    
    // Delete a stored message using its Message Hash
    public static String deleteStoredMessageByHash(String searchHash) {

        Message[] updatedMessages = new Message[0];
        Message deletedMessage = null;

        // Find the message that must be deleted
        for (Message storedMessage : storedMessages) {

            if (deletedMessage == null
                    && storedMessage.getMessageHash().equals(searchHash)) {

                deletedMessage = storedMessage;

            } else {

                updatedMessages = addMessageToArray(
                        updatedMessages, storedMessage);
            }
        }

        if (deletedMessage == null) {
            return "Message hash not found.";
        }

        try {
            Gson gson = new Gson();

            String jsonData = gson.toJson(updatedMessages);

            Files.writeString(
                    Paths.get("messages.json"),
                    jsonData,
                    StandardCharsets.UTF_8
            );

            storedMessages = updatedMessages;

            // Remove only the deleted message's ID and hash
            for (int i = 0; i < messageHashes.length; i++) {

                if (messageHashes[i].equals(deletedMessage.getMessageHash())
                        && messageIDs[i].equals(deletedMessage.getMessageID())) {

                    messageHashes = removeStringAtIndex(messageHashes, i);
                    messageIDs = removeStringAtIndex(messageIDs, i);
                    break;
                }
            }

            return "Stored message successfully deleted.";

        } catch (IOException e) {

            return "Message could not be deleted.";
        }
    }

    // Remove one item from a string array
    private static String[] removeStringAtIndex(
            String[] oldArray, int index) {

        if (index < 0 || index >= oldArray.length) {
            return oldArray;
        }

        String[] newArray = new String[oldArray.length - 1];

        System.arraycopy(oldArray, 0, newArray, 0, index);

        System.arraycopy(
                oldArray,
                index + 1,
                newArray,
                index,
                oldArray.length - index - 1
        );

        return newArray;
    }
    
    // Generate a full report of all stored messages
    public static String displayFullStoredMessageReport() {

        StringBuilder report = new StringBuilder();

        // Check whether stored messages exist
        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        report.append("FULL STORED MESSAGES REPORT\n");
        report.append("===========================\n\n");

        // Display the details of every stored message
        for (int i = 0; i < storedMessages.length; i++) {

            Message storedMessage = storedMessages[i];

            report.append("Message ")
                    .append(i + 1)
                    .append("\n");

            report.append("Sender: ")
                    .append(storedMessage.getSender())
                    .append("\n");

            report.append("Recipient: ")
                    .append(storedMessage.getRecipient())
                    .append("\n");

            report.append("Message ID: ")
                    .append(storedMessage.getMessageID())
                    .append("\n");

            report.append("Message Hash: ")
                    .append(storedMessage.getMessageHash())
                    .append("\n");

            report.append("Message: ")
                    .append(storedMessage.getMessage())
                    .append("\n\n");
        }

        report.append("Total Stored Messages: ")
                .append(storedMessages.length);

        return report.toString();
    }

    // Save the message details in a JSON file using Gson
    public String storeMessage() {

        String fileName = "messages.json";

        try {
            // Add this message to the existing stored messages
            Message[] updatedMessages = addMessageToArray(
                    storedMessages, this);

            // Convert the message array into JSON
            Gson gson = new Gson();

            String jsonData = gson.toJson(updatedMessages);

            // Write the JSON data to the file
            Files.writeString(
                    Paths.get(fileName),
                    jsonData,
                    StandardCharsets.UTF_8
            );

            // Update the stored messages array
            storedMessages = updatedMessages;

            return "Message successfully stored.";

        } catch (IOException e) {

            return "Message could not be stored.";
        }
    }

    // Fix characters that could break the JSON format
    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
