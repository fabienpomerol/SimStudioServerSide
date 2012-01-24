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
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author babusseuil
 */
public class MessageDAO {

    Session session = null;

    public MessageDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public void addMessage(User user, Group group, String title, String content) {
        java.util.Date date = new java.util.Date();
        Message message = new Message(user, group, content, date);

        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(message);
        tx.commit();
    }
    
    public List<Comment> getCommentByMessage(Message message) {
        List<Comment> listComments = new ArrayList<Comment>();
        Set<Comment> setComment = message.getComments();
        Iterator<Comment> it = setComment.iterator();
        while (it.hasNext()) {
            listComments.add(it.next());
        }
        return listComments;
    }

    public List<Message> getMessagesByGroup(Group group) {
        List listMessages = new ArrayList();
        Set<Message> setMessages = group.getMessages();
        Iterator<Message> it = setMessages.iterator();
        while (it.hasNext()) {
            listMessages.add(it.next());
        }
        return listMessages;
    }
    
    public void removeMessage(int messageID) {
        try {
            org.hibernate.Transaction tx = session.beginTransaction();

            String hql = "delete from Message m where id=" + messageID;
            Query query = session.createQuery(hql);
            int row = query.executeUpdate();
            if (row == 0) {
                System.out.println("Doesn't deleted any row!");
            } else {
                System.out.println("Deleted Row: " + row);
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
