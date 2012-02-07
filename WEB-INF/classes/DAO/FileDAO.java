/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babusseuil
 */
public class FileDAO {

    Session session = null;

    public FileDAO() {
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    

    public void addFile(User user, String name, String extension, int size) {
        Date date = new Date();
        File file = new File(user, name, extension, size, date, date);

        /*org.hibernate.Transaction tx = session.beginTransaction();*/
        session.save(file);
        /*tx.commit();*/
    }

    public List getFiles() {
        List<File> fileList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from File");
            fileList = (List<File>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public File getFileByID(int id) {
        File f = new File();
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from File as file where file.id=" + id);
            f = (File) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public void editFile(File file) {
        Date date = new Date();
        try {
            File f = getFileByID(file.getId());
            f.setUpdateAt(date);

            org.hibernate.Transaction tx = session.beginTransaction();
            session.save(file);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renameFile(File file, String name) {
        try {
            File f = getFileByID(file.getId());
            f.setName(name);

            org.hibernate.Transaction tx = session.beginTransaction();
            session.save(file);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<File> findFileByName(String term) {
        List<File> fileList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from File as file where file.name like '%" + term + "%'");
            fileList = (List<File>) q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public List<File> getFilesByUser(User user) {
        List<File> listFiles = new ArrayList();
        Set<File> setFiles = user.getFiles();
        return listFiles;
    }

    public void shareFileWithUser(User user, int idFile) {
        File f = getFileByID(idFile);
        user.getFiles().add(f);
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
    }
    
    public void removeFile(int id){
        org.hibernate.Transaction tx = session.beginTransaction();
        File f=  getFileByID(id);
        session.delete(f);
        tx.commit();
    }
    
    public List[] removeGroupAddFiles(List[] lists) {
        System.out.println("5 : " + session.isOpen());
        System.out.println("6 : " + session.isOpen());
        Iterator<File> itFile = lists[1].iterator();
        System.out.println("7 : " + session.isOpen());

        org.hibernate.Transaction tx = session.beginTransaction();
        while (itFile.hasNext()) {
            System.out.println("8 : " + session.isOpen());
            Iterator<User> itUser = lists[0].iterator();
            System.out.println("9 : " + session.isOpen());
            File f = itFile.next();
            while (itUser.hasNext()) {
                System.out.println("10 : " + session.isOpen());
                System.out.println("11 : " + session.isOpen());
                User u = itUser.next();
                System.out.println("12 : " + session.isOpen());
                File fCopie = new File(u, f.getName() + "_" + u.getFirstName() + "_" + u.getLastName(), f.getExtension(), f.getSize(), f.getCreatedAt(), f.getUpdateAt());
                System.out.println("13 : " + session.isOpen());
                session.save(fCopie);
                System.out.println("14 : " + session.isOpen());
            }
        }
        tx.commit();
        return lists;
    }
}
