/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Admin
 */
public class DAO {
    
    
    private ArrayList<Books> books;
    private ArrayList<Users> users;
    private ArrayList<Orders> orders;
    
    /*SessionFactory sf = new Configuration().configure().buildSessionFactory();
    Session session = sf.openSession();
        
    Transaction tr = session.beginTransaction();*/
    
    public ArrayList<Books> getBooks(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        
        Query q = session.createQuery("from Books as books");
        books = (ArrayList<Books>) q.list();
        
        session.close();
        return books;
    }
    
    public ArrayList<Books> getBooksOfUser(int idOfUser){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        
        Query q = session.createQuery("from Books as books WHERE books.idofbook in (select orders.idofbook from Orders as orders where orders.idofuser = "+idOfUser+")");
        books = (ArrayList<Books>) q.list();
        
        session.close();
        return books;
    }
    
    public ArrayList<Users> getUsers(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        
        Query q = session.createQuery("from Users as users");
        users = (ArrayList<Users>) q.list();
        
        session.close();
        return users;
    }
    
    public void makeOrder(ArrayList<Integer> index){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        
        Transaction tr = session.beginTransaction();
        
        for(int i = 1; i < index.size(); i++){
        Orders order = new Orders(getNextOrderId(session), index.get(i), index.get(0));
        session.save(order);
        tr.commit();
        }
        
        session.close();
    }
    
    private int getNextOrderId(Session s){
        Query q = s.createQuery("SELECT max(orders.idoforder) from Orders as orders");
        
        ArrayList<Integer> maximum = (ArrayList<Integer>) q.list();
        int max = maximum.get(0);
        
        return max+1;
    }
    
    public ArrayList<MyOrder> getOrders(){
        ArrayList<MyOrder> myOrder = new ArrayList<MyOrder>();
        
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        
        Query q = session.createQuery("from Orders as orders");
        orders = (ArrayList<Orders>) q.list();
        
        for(Orders o:orders){
            Query q1 = session.createQuery("select users.name from Users as users where users.idofuser = "+o.getIdofuser());
            ArrayList<String> names = (ArrayList<String>) q1.list();
            Query q2 = session.createQuery("select books.author from Books as books where books.idofbook = "+o.getIdofbook());
            ArrayList<String> authors = (ArrayList<String>) q2.list();
            Query q3 = session.createQuery("select books.name from Books as books where books.idofbook = "+o.getIdofbook());
            ArrayList<String> nameBooks = (ArrayList<String>) q3.list();
            
            myOrder.add(new MyOrder(o.getIdoforder(), names.get(0), authors.get(0), nameBooks.get(0)));
        }
        
        session.close();
        return myOrder;
    }
    
    public void deleteOrders(ArrayList<Integer> words){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        
        Transaction tr = session.beginTransaction();
        
        for (int i = 0; i < words.size(); i++){
            Orders order = (Orders) session.get(Orders.class, words.get(i));
            session.delete(order);
            tr.commit();
        }
        
        session.close();
    }
}
