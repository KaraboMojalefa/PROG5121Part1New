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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class Message {
     
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    
    // Keep track of the messages sent in this run
    private static int totalMessagesSent = 0;

    // Keep the messages that were sent
    private static StringBuilder sentMessages = new StringBuilder();

    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;

        // Create a random ten-digit message ID
        Random random = new Random();
        long id = 1000000000L + random.nextInt(900000000);
        messageID = String.valueOf(id);

        // Create the message hash
        messageHash = createMessageHash();
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
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
    
    
    // Return how many messageshave been sent
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }
    
    // Add a message to the sent list and increase the count
    public void addSentMessage() {
        sentMessages.append(printMessages()).append("\n\n");
        totalMessagesSent++;
    }

    // Show all the messages sent so far
    public static String printAllSentMessages() {
        return sentMessages.toString();
    }
    
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
    
    // Save the message details in a JSON file
    public String storeMessage() {
        String fileName = "messages.json";
        String oldData = "[]";

        try {
            // Read the messages already saved
            if (Files.exists(Paths.get(fileName))) {
                String savedData = Files.readString(
                        Paths.get(fileName),
                        StandardCharsets.UTF_8
                ).trim();

                // Keep the old data if it is a JSON array
                if (savedData.startsWith("[") && savedData.endsWith("]")) {
                    oldData = savedData;
                }
            }

            // Remove the closing bracket before adding a message
            String content = oldData.substring(0, oldData.length() - 1);

            if (!content.trim().equals("[")) {
                content += ",";
            }

            content += "{"
                    + "\"messageID\":\"" + escapeJson(messageID) + "\","
                    + "\"messageHash\":\"" + escapeJson(messageHash) + "\","
                    + "\"recipient\":\"" + escapeJson(recipient) + "\","
                    + "\"message\":\"" + escapeJson(message) + "\""
                    + "}]";

            // Save all messages as one JSON array
            try (FileWriter writer = new FileWriter(fileName, false)) {
                writer.write(content);
            }

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
