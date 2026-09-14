/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.prog5121part1new;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    //tests the username check
    @Test
    public void testCheckUserName() {
        
        Login validLogin = new Login(
        "Karabo",
        "Mojalefa",
        "kyl_1",//true
        "Ch&&sec@ke99!",
        "+27697324722"
        );
        
        Login invalidLogin = new Login (
        "Karabo",
        "Mojalefa",
        "kyle!!!!!!!!!",//false
        "Ch&&sec@ke99!",
        "+27697324722"
        );
        
        assertTrue(validLogin.checkUserName());
        assertFalse(invalidLogin.checkUserName());
    }
    
    //tests the password check
    @Test
    public void testCheckPasswordComplexity() {
        
        Login validLogin = new Login (
        "Karabo",
        "Mojalefa",
        "kyl_1",
        "Ch&&sec@ke99!",/*should pass because it has 8+ characters, a capital letter,
                          a number, a special character*/
        "+27697324722"
        );
        
        Login invalidLogin = new Login (
        "Karabo",
        "Mojalefa",
        "kyl_1",
        "password",//should fail because it does not meet the complexity requirements
        "+27697324722"
        );
        
        assertTrue(validLogin.checkPasswordComplexity());
        assertFalse(invalidLogin.checkPasswordComplexity());
    }
}
