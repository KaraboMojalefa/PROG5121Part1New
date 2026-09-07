/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121part1new;

/**
 *
 * @author Student
 */
public class Login {
    
    //stores the user's details needed for the login and registration parts
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;//it will contain the + international code

    //constructor for the login details
    public Login(String firstName, String lastName, String username,
            String password, String cellPhoneNumber) {
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;   
    }
    
    //checks if the username is in the correct format
    public boolean checkUserName() {
        return username.contains("_") && username.length() <=5;
    }
    
    //checks if the password is in the correct format
    public boolean checkPasswordComplexity() {
        
        //they start as false because we havent found any of them yet
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        //loops through every character in the password
        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }
            
            if (Character.isDigit(character)) {
                hasNumber = true;
            }
            
            if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }
        return password.length() >= 8
                && hasCapital
                && hasNumber
                && hasSpecial;
    }
}
