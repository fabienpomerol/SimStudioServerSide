/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.transaction.Transaction;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babusseuil
 */

/*
 * getFiles() : file
 * getFiles_1() : file_user
 * 
 * getGroups() : group_admin
 * getGroups_1 : group
 */
public class UserDAO {

    Session session = null;

    public UserDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public String subscription(String email, String password, String username, String firstName, String lastName) {
        byte[] defaultBytes = password.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            password = hexString.toString() + "";
        } catch (NoSuchAlgorithmException nsae) {
        }

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

    // A voir le retour
//    public User getUser(String password, String email) {
//        if (existUser(password, email)) {
//            return findUserByEmail(email);
//        } else {
//            return null;
//        }
//    }
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

    public boolean existUser(String password, String email) {
        User user = null;
        String pass;
        try {
            user = findUserByEmail(email);
            pass = user.getPassword();
        } catch (Exception e) {
            return false;
        }


        byte[] defaultBytes = password.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            password = hexString.toString() + "";
        } catch (NoSuchAlgorithmException nsae) {
        }

        if (password.equals(pass)) {
            return true;
        } else {
            return false;
        }
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
}
