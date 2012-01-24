package DAO;
// Generated 24 janv. 2012 14:13:23 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * File generated by hbm2java
 */
public class File  implements java.io.Serializable {


     private Integer id;
     private User user;
     private String name;
     private String extension;
     private int size;
     private Date createdAt;
     private Date updateAt;
     private Set groups = new HashSet(0);
     private Set users = new HashSet(0);

    public File() {
    }

	
    public File(User user, String name, String extension, int size, Date createdAt, Date updateAt) {
        this.user = user;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    public File(User user, String name, String extension, int size, Date createdAt, Date updateAt, Set groups, Set users) {
       this.user = user;
       this.name = name;
       this.extension = extension;
       this.size = size;
       this.createdAt = createdAt;
       this.updateAt = updateAt;
       this.groups = groups;
       this.users = users;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getExtension() {
        return this.extension;
    }
    
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public int getSize() {
        return this.size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdateAt() {
        return this.updateAt;
    }
    
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public Set getGroups() {
        return this.groups;
    }
    
    public void setGroups(Set groups) {
        this.groups = groups;
    }
    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }




}


