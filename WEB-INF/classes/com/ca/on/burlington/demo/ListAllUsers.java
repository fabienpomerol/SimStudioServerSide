/**

 * 

 */ 

package com.ca.on.burlington.demo;



import java.util.List;   

import org.apache.commons.lang.StringUtils;   

import org.hibernate.HibernateException;     

import org.hibernate.SessionFactory;   

import org.hibernate.Transaction;     

import org.hibernate.cfg.Configuration;   

import org.hibernate.classic.Session;  

   

import org.hibernate.criterion.Restrictions;  





/**

 * @author David Welford-Costelloe

 *

 */ 

public class ListAllUsers {



    /**

     * 

     */

    public ListAllUsers() {

        // TODO Auto-generated constructor stub

    }

    

    @SuppressWarnings("unchecked")

    public List<Users> getUserList(String sUser, String sPassword)  

    {  

    List<Users> allUsers = null;      

  

    try {  

      //Set up Hibernate Session    

      Configuration config = new Configuration();  

      config.configure("hibernate.cfg.xml");  

  

      SessionFactory sessionFactory = config.buildSessionFactory();  

                    Session session = sessionFactory.getCurrentSession();  

  

    

        @SuppressWarnings("unused")

            Transaction tx = session.beginTransaction();        



// I like to use apache Common Language      

if (StringUtils.isBlank(sUser))  

{  

  

       // If null passed return all records  

       allUsers = session.createCriteria(Users.class).list();  

  

      }  

        else  

      {  

        // Use Property not column names    

        allUsers = session.createCriteria(Users.class).add(Restrictions.eq("loginName",sUser)).add(Restrictions.eq("loginPassword",sPassword)).list();  

      }  

  

    } catch (HibernateException e) {  

  

      e.printStackTrace();  

    }    



    return allUsers;  

  }  



} 