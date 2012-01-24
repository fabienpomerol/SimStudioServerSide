/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babusseuil
 */
/* getUsers = group_admin
 * getUsers_1 = group 
 * 
 * 
 * 
 * 
 */
public class GroupDAO {

    Session session = null;

    public GroupDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public void addGroup(String name, User user) {
        Group gr = new Group(name);
        gr.getUsers().add(user);

        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(gr);
        tx.commit();

    }

    public List getGroups() {
        List<Group> groupList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Group");
            groupList = (List<Group>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupList;
    }

    public List<Group> getGroupsByOwnerId(int id) {
        List<Group> groupList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Group where owner_id=" + id);
            groupList = (List<Group>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupList;
    }

    public List<Group> findGroupByName(String term) {
        List<Group> groupList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Group where name like '%" + term + "%'");
            groupList = (List<Group>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupList;
    }

    public Group getGroupById(int id) {
        Group group = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Group where id=" + id);
            group = (Group) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    public List<User> getGroupCollaborators(Group g) {
        Set<User> setUsers = getGroupById(g.getId()).getUsers();
        List<User> listUsers = new ArrayList<User>();
        Iterator<User> it = setUsers.iterator();
        while (it.hasNext()) {
            listUsers.add(it.next());
        }
        return listUsers;
    }

    public List<File> getGroupFiles(Group g) {
        Set<File> setFiles = getGroupById(g.getId()).getFiles();
        List<File> listFiles = new ArrayList<File>();
        Iterator<File> it = setFiles.iterator();
        while (it.hasNext()) {
            listFiles.add(it.next());
        }
        return listFiles;
    }

    public void addUserToGroup(User user, Group group) {
        group.getUsers_1().add(user);
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(group);
        tx.commit();
    }

    public boolean removeUserFromGroup(User user, Group group) {
        if (group.getUsers().contains(user) && group.getUsers().size() == 1) {
            return false;
        } else {
            List<User> listUsers = new ArrayList<User>();
            Set<User> setUsers = group.getUsers_1();
            Iterator it = setUsers.iterator();
            System.out.println(setUsers.size());
            while (it.hasNext()) {
                User u = (User) it.next();
                if (user.equals(u)) {
                    it.remove();
                }
            }
            org.hibernate.Transaction tx = session.beginTransaction();
            session.save(group);
            tx.commit();

            return true;
        }
    }

    public void addFileToGroup(File file, Group group) {
        group.getFiles().add(file);
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(group);
        tx.commit();
    }

    /* a tester*/
    public void addGroupAdmin(Group group, User user) {
        group.getUsers().add(user);
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(group);
        tx.commit();
    }
    /* a tester */

    public boolean isGroupAdmin(User user, Group group) {
        if (group.getUsers().contains(user)) {
            return true;
        } else {
            return false;
        }
    }

    public void removeGroupAdmin(Group group, User user) {
        if (group.getUsers().size() > 1) {
            group.getUsers().remove(user);
            org.hibernate.Transaction tx = session.beginTransaction();
            session.save(group);
            tx.commit();
        }
    }

    public List[] removeGroup(Group group) {
        Set<File> setFiles = group.getFiles();
        Set<User> setUsers = group.getUsers_1();
        List[] lists = new List[2];

        Iterator<File> itFiles = setFiles.iterator();
        Iterator<User> itUsers = setUsers.iterator();
        List<File> listFiles = new ArrayList<File>();
        List<User> listUsers = new ArrayList<User>();
        while (itFiles.hasNext()) {
            listFiles.add(itFiles.next());
        }
        while (itUsers.hasNext()) {
            listUsers.add(itUsers.next());
        }

        lists[0] = listUsers;
        lists[1] = listFiles;

        org.hibernate.Transaction tx = session.beginTransaction();
        session.delete(group);
        tx.commit();

        return lists;
    }
    /*
    
    FileDAO fDAO = new FileDAO();
    //org.hibernate.Transaction tx2 = session.beginTransaction();
    //session.lock(fDAO.session, LockMode.NONE);
    
    for (int i = 0; i < userList.size(); i++) {
    for (int j = 0; j < fileList.size(); j++) {
    System.out.println("ok");
    fDAO.addFile(userList.get(i), fileList.get(j).getName(), fileList.get(j).getExtension(), fileList.get(j).getSize());
    }
    }*/
}
