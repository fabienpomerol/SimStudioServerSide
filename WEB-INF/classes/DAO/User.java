package DAO;
// Generated 24 janv. 2012 14:13:23 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User  implements java.io.Serializable {


     private Integer id;
     private String firstName;
     private String lastName;
     private String password;
     private String email;
     private String userName;
     private Set groups = new HashSet(0);
     private Set usersForIdUser1 = new HashSet(0);
     private Set files = new HashSet(0);
     private Set usersForIdUser2 = new HashSet(0);
     private Set files_1 = new HashSet(0);
     private Set comments = new HashSet(0);
     private Set messages = new HashSet(0);
     private Set groups_1 = new HashSet(0);

    public User() {
    }

	
    public User(String firstName, String lastName, String password, String email, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.userName = userName;
    }
    public User(String firstName, String lastName, String password, String email, String userName, Set groups, Set usersForIdUser1, Set files, Set usersForIdUser2, Set files_1, Set comments, Set messages, Set groups_1) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.password = password;
       this.email = email;
       this.userName = userName;
       this.groups = groups;
       this.usersForIdUser1 = usersForIdUser1;
       this.files = files;
       this.usersForIdUser2 = usersForIdUser2;
       this.files_1 = files_1;
       this.comments = comments;
       this.messages = messages;
       this.groups_1 = groups_1;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Set getGroups() {
        return this.groups;
    }
    
    public void setGroups(Set groups) {
        this.groups = groups;
    }
    public Set getUsersForIdUser1() {
        return this.usersForIdUser1;
    }
    
    public void setUsersForIdUser1(Set usersForIdUser1) {
        this.usersForIdUser1 = usersForIdUser1;
    }
    public Set getFiles() {
        return this.files;
    }
    
    public void setFiles(Set files) {
        this.files = files;
    }
    public Set getUsersForIdUser2() {
        return this.usersForIdUser2;
    }
    
    public void setUsersForIdUser2(Set usersForIdUser2) {
        this.usersForIdUser2 = usersForIdUser2;
    }
    public Set getFiles_1() {
        return this.files_1;
    }
    
    public void setFiles_1(Set files_1) {
        this.files_1 = files_1;
    }
    public Set getComments() {
        return this.comments;
    }
    
    public void setComments(Set comments) {
        this.comments = comments;
    }
    public Set getMessages() {
        return this.messages;
    }
    
    public void setMessages(Set messages) {
        this.messages = messages;
    }
    public Set getGroups_1() {
        return this.groups_1;
    }
    
    public void setGroups_1(Set groups_1) {
        this.groups_1 = groups_1;
    }




}


