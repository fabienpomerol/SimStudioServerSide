/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.LazyInitializationException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babusseuil
 */
/* getUsers = group_admin
 * getUsers_1 = ask_relationship_group 
 * getUsers_2 : group_users
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

    public List<User> getGroupCollaborators(int groupId) {
        Group g = getGroupById(groupId);
        Set<User> setUsers = g.getUsers_2();
        List<User> listUsers = new ArrayList();
        
        Iterator<User> it = setUsers.iterator();
        while (it.hasNext()) {
            listUsers.add(it.next());
        }
        return listUsers;
    }

    public List<File> getGroupFiles(Group g) {
        Set<File> setFiles = getGroupById(g.getId()).getFiles();
        List<File> listFiles = new ArrayList();
        Iterator<File> it = setFiles.iterator();
        while (it.hasNext()) {
            listFiles.add(it.next());
        }
        return listFiles;
    }

    public void addUserToGroup(User user, Group group) {
        group.getUsers_2().add(user);
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(group);
        tx.commit();
    }

    public boolean removeUserFromGroup(User user, int idGroup) {
        Group group = getGroupById(idGroup);
        if (group.getUsers().contains(user) && group.getUsers().size() == 1) {
            return false;
        } else {
            Set<User> setUsers = group.getUsers_2();
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

    public void addGroupAdmin(Group group, User user) {
        group.getUsers().add(user);
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(group);
        tx.commit();
    }

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

    public void removeGroup(Group group) {
        Set<File> setFiles = group.getFiles();
        Set<User> setUsers = group.getUsers_2();
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
        
        Iterator<File> itFile = lists[1].iterator();

        while (itFile.hasNext()) {
            Iterator<User> itUser = lists[0].iterator();
            File f = itFile.next();
            while (itUser.hasNext()) {
                User u = itUser.next();
                File fCopie = new File(u, f.getName() + "_" + u.getFirstName() + "_" + u.getLastName(), f.getExtension(), f.getSize(), f.getCreatedAt(), f.getUpdateAt());
                fCopie.getUsers().add(u);
                session.save(fCopie);
                session.delete(f);
            }
        }
        tx.commit();
    }
    
    public Message getLastGroupMessage(int groupId){
        Group g = getGroupById(groupId);
        Set<Message> messages = g.getMessages();
        Iterator<Message> itMess = messages.iterator();
        Message mess = new Message();
        long date = 1;
        Date d = new Date(date);
        while(itMess.hasNext())
        {
            Message m = itMess.next();
            if(m.getCreatedAt().after(d))
            {
                d=m.getCreatedAt();
                mess=m;
            }
        }
        return mess;
    }
}
