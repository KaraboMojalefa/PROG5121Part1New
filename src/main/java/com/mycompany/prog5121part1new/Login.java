/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121part1new;
import java.util.regex.Pattern;
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
    private String loginUsername;
    private String loginPassword;
    
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
    
    //checks if the cell phone number is in the correct format
    //Regex reference: Oracle Java Pattern documentation
    public boolean checkCellPhoneNumber() {
        
        String regex = "^\\+27\\d{9}$";
        //^ - starts at the beginning
        //\\+27 - must start with +27
        //\\d{9} - must have 9 digits after +27
        //$ - ends there
        return Pattern.matches(regex, cellPhoneNumber);
    }
    
    //registers the user details
    public String registerUser() {
        
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five"
                    + " characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that "
                    + "the password contains at least eight characters, a capital"
                    + " letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain "
                    + "international code.";
        }
        
        return "Registration successful.";
    }
    
    /*checks whether the username and password entered during 
      login match the details stored during registration.
    */
    //chechs if the login details are correct
    public boolean loginUser() {
        
        if (loginUsername.equals(username) //checks entered username & password 
            && loginPassword.equals(password)) {//against registered ones
            return true;//if both match
        }
        return false;//if either one is wrong
    }
    
    //so that loginUser() does not compare empty values
    /*this method stores the username and password entered when the user
      tries to login.
    */
    public void setLoginDetails(String loginUsername, String loginPassword) {
        
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }
    
    /*this method returns the correct message depending on whether loginUser() 
      is true or false.
    */
    public String returnLoginStatus() {
        
      if (loginUser()) {
          return "Welcome " + firstName + ", " + lastName
                  + " it is great to see you again.";
      } 
      return "Username or password incorrect, please try again.";
    }
}
