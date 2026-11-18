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
        Message msg = new Message(0, "12345", "Hello");

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
}
