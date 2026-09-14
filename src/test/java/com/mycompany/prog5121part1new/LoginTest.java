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
    
    //tests the cell phone number check
    @Test
    public void testCheckCellPhoneNumber() {
        
        Login validLogin = new Login (
        "Karabo",
        "Mojalefa",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27697324722"
        );
        
        Login invalidLogin = new Login(
        "Karabo",
        "Mojalefa",
        "kyl_1",
        "Ch&&sec@ke99!",
        "05896416"
        );
        
        assertTrue(validLogin.checkCellPhoneNumber());
        assertFalse(invalidLogin.checkCellPhoneNumber());
    }
    
    //tests the login check
    @Test
    public void testLoginUser() {
        
        Login login = new Login(
        "Karabo",
        "Mojalefa",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27697324722"
        );
        
        login.setLoginDetails("kyl_1", "Ch&&sec@ke99!"); //gives the login method the 
                                                         //correct login details
        
        assertTrue(login.loginUser());
        
        login.setLoginDetails("wrong", "password");
        
        assertFalse(login.loginUser());
    }
    
    //tests the login status message
    @Test
    public void testReturnLoginStatus() {
        
        Login login = new Login(
        "Karabo",
        "Mojalefa",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27697324722"
        );
        
        login.setLoginDetails("kyl_1", "Ch&&sec@ke99!");
        
        assertEquals(
            "Welcome Karabo, Mojalefa it is great to see you again.",
                login.returnLoginStatus()
        );
        
        login.setLoginDetails("wrong", "password");
        
        assertEquals(
            "Username or password incorrect, please try again.",
                login.returnLoginStatus()
        );
    }
    
    //tests the registration check
    @Test
    public void testRegisterUser() {
        
        Login validLogin = new Login(
        "Karabo",
        "Mojalefa",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27697324722"
        );//checks if all registration details are valid
        
        assertEquals(
            "Registration successful.",
                validLogin.registerUser()
        );
        
        Login invalidLogin = new Login (
        "Karabo",
        "Mojalefa",
        "kyle!!!!!!!!!",
        "password",
        "05896416" 
        );//this one gives invalid details, it should return a username error message
        
        assertEquals(
            "Username is not correctly formatted; please ensure that your username "
                    + "contains an underscore and is no more than five characters "
                    + "in length.",
                invalidLogin.registerUser()
        );
    }
}
