/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121part1new;

/**
 *
 * @author Student
 */

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;

public class MessageTest {
    
     @Test
    public void testMessageLengthSuccess() {
        // This checks if a normal message is accepted
        Message msg = new Message(0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        assertEquals("Message ready to send.",
                msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // This message is one character too long
        String longMessage = "a".repeat(251);

        Message msg = new Message(0, "+27718693002", longMessage);

        // The method should tell us how many characters are extra
        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                msg.checkMessageLength());
    }

    @Test
    public void testRecipientNumberSuccess() {
        // This checks a phone number with an international code
        Message msg = new Message(0, "+27718693002", "Hello");

        assertEquals("Cell phone number successfully captured.",
                msg.checkRecipientCell());
    }

    @Test
    public void testRecipientNumberFailure() {
        // This number is not in the correct format
        Message msg = new Message(0, "08575975889", "Hello");

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    @Test
    public void testMessageID() {
        // The message ID should contain ten characters
        Message msg = new Message(0, "08575975889", "Hello");

        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.getMessageID().length());
    }

    @Test
    public void testMessageHashFormat() {
        // This checks that the hash has the right format
        Message msg = new Message(0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        String hash = msg.getMessageHash();

        assertTrue(hash.matches("\\d{2}:0:HITONIGHT"));
    }

    @Test
    public void testSendMessageOption() {
        // Option 1 should send the message
        Message msg = new Message(0, "+27718693002", "Hello");

        assertEquals("Message successfully sent.",
                msg.SentMessage(1));
    }

    @Test
    public void testDisregardMessageOption() {
        // Option 2 should give the delete message
        Message msg = new Message(0, "+27718693002", "Hello");

        assertEquals("Press 0 to delete the message.",
                msg.SentMessage(2));
    }

    @Test
    public void testStoreMessageOption() {
        // Option 3 should store the message
        Message msg = new Message(0, "+27718693002", "Hello");

        assertEquals("Message successfully stored.",
                msg.SentMessage(3));
    }
    
    @Test
    public void testMessageHashLoop() {

        // Create several messages and check their hashes
        for (int i = 0; i < 5; i++) {

            Message msg = new Message(
                    i, "+27718693002", "Hello world");

            String hash = msg.getMessageHash();

            // Check that every hash follows the correct format
            assertTrue(hash.matches("\\d{2}:" + i + ":HELLOWORLD"));
        }
    }
    
    @Test
    public void testMessageSender() {

        // Create a message with a sender name
        Message msg = new Message(
                1, "Mojalefa", "+27834557896",
                "Testing sender details");

        // Check that the sender and recipient are correct
        assertEquals("Mojalefa", msg.getSender());
        assertEquals("+27834557896", msg.getRecipient());
    }
    
    @Test
    public void testUnknownSenderForOlderMessages() {

        // Create a message using the older constructor
        Message msg = new Message(
                1, "+27834557896", "Hello");

        // Older messages should display Unknown as the sender
        assertEquals("Unknown", msg.getSender());
    }
    
    @Test
    public void testDisplayStoredMessagesWhenEmpty() {

        // Check the response when there are no stored messages
        assertEquals(
                "No stored messages found.",
                Message.displayStoredSendersAndRecipients());
    }
    
    @Test
    public void testDisplayLongestStoredMessage() throws Exception {

        // Access the private stored messages array
        java.lang.reflect.Field field =
                Message.class.getDeclaredField("storedMessages");

        field.setAccessible(true);

        // Save the original messages
        Message[] originalMessages =
                (Message[]) field.get(null);

        try {
            // Create two messages of different lengths
            Message shortMessage = new Message(
                    1, "+27834557896", "Hello");

            Message longMessage = new Message(
                    2, "+27834557896",
                    "This is the longest stored message");

            // Temporarily populate the stored messages array
            field.set(null, new Message[]{
                shortMessage, longMessage
            });

            // Get the longest message report
            String result = Message.displayLongestStoredMessage();

            // Check that the longest message is displayed
            assertTrue(result.contains(
                    "This is the longest stored message"));

            // Check that the correct message length is displayed
            assertTrue(result.contains(
                    "Message Length: "
                    + longMessage.getMessage().length()
                    + " characters."));

        } finally {
            // Restore the original stored messages
            field.set(null, originalMessages);
        }
    }
    
    @Test
    public void testDisplayLongestMessageWhenEmpty() throws Exception {

        // Access the private stored messages array
        java.lang.reflect.Field field =
                Message.class.getDeclaredField("storedMessages");

        field.setAccessible(true);

        // Save the original messages
        Message[] originalMessages =
                (Message[]) field.get(null);

        try {
            // Temporarily make the stored messages array empty
            field.set(null, new Message[0]);

            // Check the response when no messages exist
            assertEquals(
                    "No stored messages found.",
                    Message.displayLongestStoredMessage());

        } finally {
            // Restore the original messages
            field.set(null, originalMessages);
        }
    }
    
    @Test
    public void testSearchStoredMessageByID() throws Exception {

        // Access the private stored messages array
        java.lang.reflect.Field field =
                Message.class.getDeclaredField("storedMessages");

        field.setAccessible(true);

        // Save the original messages
        Message[] originalMessages =
                (Message[]) field.get(null);

        try {
            // Create a test message
            Message testMessage = new Message(
                    1, "Mojalefa",
                    "+27834557896",
                    "Testing message ID search");

            // Temporarily add the test message
            field.set(null, new Message[]{testMessage});

            // Search using the correct ID
            String result = Message.searchStoredMessageByID(
                    testMessage.getMessageID());

            assertTrue(result.contains("+27834557896"));
            assertTrue(result.contains("Testing message ID search"));

            // Search using an incorrect ID
            assertEquals(
                    "Message ID not found.",
                    Message.searchStoredMessageByID("0000000000"));

            // Check the empty storage response
            field.set(null, new Message[0]);

            assertEquals(
                    "No stored messages found.",
                    Message.searchStoredMessageByID("0000000000"));

        } finally {
            // Restore the original messages
            field.set(null, originalMessages);
        }
    }
    
    @Test
    public void testSearchStoredMessagesByRecipient() throws Exception {

        Field field = Message.class.getDeclaredField("storedMessages");
        field.setAccessible(true);

        Message[] originalMessages = (Message[]) field.get(null);

        try {
            Message first = new Message(
                    1, "Sender", "+27834557896", "First message");

            Message second = new Message(
                    2, "Sender", "+27834557896", "Second message");

            Message third = new Message(
                    3, "Sender", "+27838884567", "Other recipient");

            field.set(null, new Message[]{first, second, third});

            String result = Message.searchStoredMessagesByRecipient(
                    "+27834557896");

            assertTrue(result.contains("First message"));
            assertTrue(result.contains("Second message"));
            assertFalse(result.contains("Other recipient"));

            assertEquals(
                    "No messages found for this recipient.",
                    Message.searchStoredMessagesByRecipient("+27000000000"));

        } finally {
            field.set(null, originalMessages);
        }
    }

    @Test
    public void testDeleteStoredMessageByHash() throws Exception {

        Field storedField =
                Message.class.getDeclaredField("storedMessages");
        Field idsField =
                Message.class.getDeclaredField("messageIDs");
        Field hashesField =
                Message.class.getDeclaredField("messageHashes");

        storedField.setAccessible(true);
        idsField.setAccessible(true);
        hashesField.setAccessible(true);

        Message[] originalMessages = (Message[]) storedField.get(null);
        String[] originalIDs = (String[]) idsField.get(null);
        String[] originalHashes = (String[]) hashesField.get(null);

        boolean fileExisted = Files.exists(Paths.get("messages.json"));
        String originalFile = fileExisted
                ? Files.readString(Paths.get("messages.json"))
                : null;

        try {
            Message first = new Message(
                    1, "Sender", "+27834557896", "Keep this message");

            Message second = new Message(
                    2, "Sender", "+27834557896", "Delete this message");

            storedField.set(null, new Message[]{first, second});
            idsField.set(null, new String[]{
                first.getMessageID(), second.getMessageID()
            });
            hashesField.set(null, new String[]{
                first.getMessageHash(), second.getMessageHash()
            });

            String result = Message.deleteStoredMessageByHash(
                    second.getMessageHash());

            assertEquals(
                    "Stored message successfully deleted.", result);

            Message[] remaining = (Message[]) storedField.get(null);

            assertEquals(1, remaining.length);
            assertEquals(first.getMessageID(), remaining[0].getMessageID());

            assertEquals(1, ((String[]) idsField.get(null)).length);
            assertEquals(1, ((String[]) hashesField.get(null)).length);

            assertEquals(
                    "Message hash not found.",
                    Message.deleteStoredMessageByHash("INVALIDHASH"));

        } finally {
            storedField.set(null, originalMessages);
            idsField.set(null, originalIDs);
            hashesField.set(null, originalHashes);

            if (fileExisted) {
                Files.writeString(
                        Paths.get("messages.json"),
                        originalFile,
                        StandardCharsets.UTF_8);
            } else {
                Files.deleteIfExists(Paths.get("messages.json"));
            }
        }
    }

    @Test
    public void testFullStoredMessageReport() throws Exception {

        Field field = Message.class.getDeclaredField("storedMessages");
        field.setAccessible(true);

        Message[] originalMessages = (Message[]) field.get(null);

        try {
            Message testMessage = new Message(
                    1, "Mojalefa", "+27834557896",
                    "Testing the full stored message report");

            field.set(null, new Message[]{testMessage});

            String result = Message.displayFullStoredMessageReport();

            assertTrue(result.contains("FULL STORED MESSAGES REPORT"));
            assertTrue(result.contains("Mojalefa"));
            assertTrue(result.contains("+27834557896"));
            assertTrue(result.contains(testMessage.getMessageID()));
            assertTrue(result.contains(testMessage.getMessageHash()));
            assertTrue(result.contains("Testing the full stored message report"));
            assertTrue(result.contains("Total Stored Messages: 1"));

        } finally {
            field.set(null, originalMessages);
        }
    }

    @Test
    public void testLoadStoredMessagesFromJSON() throws Exception {

        Field storedField =
                Message.class.getDeclaredField("storedMessages");
        Field idsField =
                Message.class.getDeclaredField("messageIDs");
        Field hashesField =
                Message.class.getDeclaredField("messageHashes");

        storedField.setAccessible(true);
        idsField.setAccessible(true);
        hashesField.setAccessible(true);

        Message[] originalMessages = (Message[]) storedField.get(null);
        String[] originalIDs = (String[]) idsField.get(null);
        String[] originalHashes = (String[]) hashesField.get(null);

        boolean fileExisted = Files.exists(Paths.get("messages.json"));
        String originalFile = fileExisted
                ? Files.readString(Paths.get("messages.json"))
                : null;

        try {
            Message testMessage = new Message(
                    1, "Mojalefa", "+27834557896",
                    "Testing JSON loading");

            Gson gson = new Gson();

            Files.writeString(
                    Paths.get("messages.json"),
                    gson.toJson(new Message[]{testMessage}),
                    StandardCharsets.UTF_8
            );

            Message.loadStoredMessages();

            Message[] loaded = (Message[]) storedField.get(null);

            assertEquals(1, loaded.length);
            assertEquals(testMessage.getMessageID(), loaded[0].getMessageID());
            assertEquals(testMessage.getMessage(), loaded[0].getMessage());
            assertEquals(1, ((String[]) idsField.get(null)).length);
            assertEquals(1, ((String[]) hashesField.get(null)).length);

        } finally {
            storedField.set(null, originalMessages);
            idsField.set(null, originalIDs);
            hashesField.set(null, originalHashes);

            if (fileExisted) {
                Files.writeString(
                        Paths.get("messages.json"),
                        originalFile,
                        StandardCharsets.UTF_8);
            } else {
                Files.deleteIfExists(Paths.get("messages.json"));
            }
        }
    }
}
