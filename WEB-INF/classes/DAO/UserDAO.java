/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.LazyInitializationException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author babusseuil
 */

/*
 * getFiles() : file
 * getFiles_1() : file_user
 * 
 * getGroups() : group_admin
 * getGroups_1 : asking_relationship_groups
 * getGroups_2 : group_user
 * 
 * usersForIdUser1 de user 1 => renvoie colonne 1 de asking_relationship_user où colonne 2 = 1
 * usersForIdUser2 de user 1 => renvoie colonne 2 de asking_relationship_user où colonne 1 = 1
 * 
 * usersForIdUser1_1 de user 1 => renvoie colonne 1 de user_collaborator où colonne 2 = 1
 * usersForIdUser2_1 de user 1 => renvoie colonne 2 de user_collaborator où colonne 1 = 1
 * 
 */
public class UserDAO {

    Session session = null;

    public UserDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public String subscription(String email, String password, String username, String firstName, String lastName) {
        
        EncryptMD5 encr = new EncryptMD5();
        password = encr.encryptMD5(password);

        System.out.println(password);
        org.hibernate.Transaction tx = session.beginTransaction();
        User user = new User(firstName, lastName, password, email, username);
        try {
            Query q = session.createQuery("from User user where user_name='" + username + "'");
            Iterator it = q.iterate();
            if (!it.hasNext()) {
                q = session.createQuery("from User user where email='" + email + "'");
                it = q.iterate();
                if (!it.hasNext()) {
                    session.save(user);
                    tx.commit();
                    return "";
                } else {
                    return "Email déjà utilisé";
                }
            } else {
                return "Username déjà utilisé";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public User getUser(int id) {
        User user = new User();
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from User user where user.id=" + id);
            user = (User) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public String editUser(int id, String firstName, String lastName, String email, String oldPassword, String password, String passwordConfirmation) {
        String s = "";
        User us = getUser(id);
        us.setFirstName(firstName);
        us.setLastName(lastName);
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from User user where user.id!=" + id+ "and user.email='"+email+"'");
        
        if (!q.list().isEmpty()) {
            s = "Email déjà utilisé";
            return s;
        } else {
            us.setEmail(email);
        }
        if (!oldPassword.isEmpty()) {
            if(!password.isEmpty() && !passwordConfirmation.isEmpty())
            {
            if (password.equals(passwordConfirmation)) {
                EncryptMD5 encr = new EncryptMD5();
                password = encr.encryptMD5(password);
                oldPassword = encr.encryptMD5(oldPassword);
                
                    System.out.println(us.getPassword());
                    System.out.println(oldPassword);
                if (!us.getPassword().equals(oldPassword)) {
                    s = "Password incorrect";
                    return s;
                } else {
                    us.setPassword(password);
                }
            } else {
                s = "Les deux passwords ne correspondent pas";
                return s;
            }
            } else
            {
                s = "Un des champ de password est vide";
                return s;
            }
        }
        //org.hibernate.Transaction tx = session.beginTransaction();
        session.saveOrUpdate(us);
        tx.commit();

        return s;
    }

    public List<User> getUsers() {
        List<User> userList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from User");
            userList = (List<User>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void askUserToCollaborator(int idUs1, int idUs2) {
        User us1 = getUser(idUs1);
        User us2 = getUser(idUs2);

        org.hibernate.Transaction tx = session.beginTransaction();
        us1.getUsersForIdUser1().add(us2);
        session.saveOrUpdate(us1);
        tx.commit();
    }

    public void removeAskingCollaborator(int idUs1, int idUs2){
        User us1 = getUser(idUs1);
        User us2 = getUser(idUs2);
        
        org.hibernate.Transaction tx = session.beginTransaction();
        us1.getUsersForIdUser1().remove(us2);
        us1.getUsersForIdUser2().remove(us2);
        session.save(us1);
        tx.commit();
    }
    
    public void addCollaborator(int id1, int id2) {

        org.hibernate.Transaction tx = session.beginTransaction();
        User us = getUser(id1);
        User us2 = getUser(id2);
        us.getUsersForIdUser2_1().add(us2);
        session.saveOrUpdate(us);
        tx.commit();
    }

    public List<User> getCollaborator(int userId) {
        List<User> listUser = new ArrayList<User>();
        Set<User> setUser = getUser(userId).getUsersForIdUser1_1();
        Iterator<User> it = setUser.iterator();
        while (it.hasNext()) {
            listUser.add(it.next());
        }
        setUser = getUser(userId).getUsersForIdUser2_1();
        it = setUser.iterator();
        while (it.hasNext()) {
            listUser.add(it.next());
        }
        return listUser;
    }

    public void removeCollaborator(int idus1, int idus2) {
        org.hibernate.Transaction tx = session.beginTransaction();
        User us1= getUser(idus1);
        User us2 = getUser(idus2);
        us1.getUsersForIdUser1_1().remove(us2);
        us1.getUsersForIdUser2_1().remove(us2);
        session.save(us1);
        tx.commit();
    }

    public User findUserByEmail(String email) {
        User user = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from User as user where user.email='" + email + "'");
            user = (User) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> findUserByUsernameOrEmail(String term) {
        List<User> userList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from User where user_name like '%" + term + "%' or email like '%" + term + "%'");
            userList = (List<User>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<User> findUserByFirstnameOrLastname(String term) {
        List<User> userList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from User where first_name like '%" + term + "%' or last_name like '%" + term + "%'");
            userList = (List<User>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean existUser(String password, String email) {
        User user = null;
        String pass;
        try {
            user = findUserByEmail(email);
            pass = user.getPassword();
        } catch (Exception e) {
            return false;
        }
        
        EncryptMD5 encr = new EncryptMD5();
        password = encr.encryptMD5(password);

        if (password.equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUserByMailAndPassword(String password, String email) {
        User user = null;
        String pass;
        if(existUser(password, email))
        {
            user = findUserByEmail(email);
        }
        return user;
    }

    public List<File> getGroupFilesByUser(User user) {
        List<File> listFiles = new ArrayList();

        Set setGroups = user.getGroups();
        Iterator<Group> it = setGroups.iterator();
        while (it.hasNext()) {
            Set<File> setFiles = it.next().getFiles();
            Iterator<File> it2 = setFiles.iterator();
            while (it2.hasNext()) {
                listFiles.add(it2.next());
            }
        }
        return listFiles;
    }

    public List[] removeGroupAddUserFiles(List[] lists) {
        Iterator<User> itUser = lists[0].iterator();

        org.hibernate.Transaction tx = session.beginTransaction();

        while (itUser.hasNext()) {
            Iterator<File> itFile = lists[1].iterator();
            System.out.println("18 : " + session.isOpen());
            User u = itUser.next();
            while (itFile.hasNext()) {
                System.out.println("19 : " + session.isOpen());
                File f = itFile.next();
                System.out.println("20 : " + session.isOpen());
                try {
                    addFileToUser(f, u);
                } catch (LazyInitializationException e) {
                    e.printStackTrace();
                }
                System.out.println("21 : " + session.isOpen());
            }
            System.out.println("22 : " + session.isOpen());
        }
        return lists;
    }

    public void addFileToUser(File f, User u) {
        u.getFiles_1().add(f);
        org.hibernate.Transaction tx = session.beginTransaction();
        session.update(u);
        tx.commit();
    }

    public void askToJoinGroup(int usId, Group gr) {
        User us = getUser(usId);
        org.hibernate.Transaction tx = session.beginTransaction();
        us.getGroups_1().add(gr);
        session.saveOrUpdate(us);
        tx.commit();
    }

    public User validateLogin(String mail, String password) {

        /** Get the session id value */
        FlexSession flexSession = FlexContext.getFlexSession();
        String sessionId = flexSession.getId();
        User user = new User();
        if (existUser(password, mail)) {
            user = getUserByMailAndPassword(password, mail);
            user.setSessionId(sessionId);

            org.hibernate.Transaction tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
        }
        return user;
    }
}
