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
}
