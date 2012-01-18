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
public class CommentDAO {

    Session session = null;

    public CommentDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public void addComment(User user, Message message, String content) {
        Date date = new Date();
        Comment comment = new Comment(user, message, content, date);

        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(comment);
        tx.commit();
    }
    
    public void removeComment(int id) {
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            String hql = "delete from Comment c where id=" + id;
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

    public List<Comment> getCommentByMessage(Message message) {
        List<Comment> listComments = new ArrayList<Comment>();
        Set<Comment> setComment = message.getComments();
        Iterator<Comment> it = setComment.iterator();
        while (it.hasNext()) {
            listComments.add(it.next());
        }
        return listComments;
    }
}
