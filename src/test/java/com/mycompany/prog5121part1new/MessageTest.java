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
        Message msg = new Message(0, "+27718693002", "Hello");

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
}
