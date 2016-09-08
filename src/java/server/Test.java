/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Admin
 */
@WebServlet(name = "Test", urlPatterns = {"/Test"})
public class Test extends HttpServlet {

    ArrayList<Books> books;
    ArrayList<Users> users;
    ArrayList<Orders> orders;
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String name, password;
        name = request.getParameter("name");
        password = request.getParameter("pass");
        
        DAO dao = new DAO();
        users = dao.getUsers();
        
        int idOfUser = getUserId(users, name, password); //забираємо порядковий номер користувача з бд
	String roleOfUser = getUserRole(users, name, password); //забираємо роль користувача
        
        if(idOfUser != -1 && roleOfUser.equals("user")){

        //Query q1 = session.createQuery("from Books as book");
        //books = (ArrayList<Books>) q1.list();
            books = dao.getBooks();
            
            ArrayList<Books> myBooks = dao.getBooksOfUser(idOfUser);
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>All books</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"DoOrder\" method=\"post\">");
            out.println("<input type=\"text\" name=\"user\" value="+ idOfUser +" readonly>");
            out.println("<fieldset><legend>All books</legend>");
            for(Books b : books){
                out.println("<input type=\"checkbox\" name=\"checkbook\" value="+b.getIdofbook()+">");
                out.println("<b>"+b+"</b><br>");
            }
            out.println("<input type=\"submit\" value=\"Make order\">");
            out.println("</fieldset>");
            out.println("</form>");
            
            out.println("<fieldset><legend>My books</legend>");
            for(Books b : myBooks){
                out.println("<b>"+b+"</b><br>");
            }
            out.println("</fieldset>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
        }else if(idOfUser != -1 && roleOfUser.equals("librarian")){
            ArrayList<MyOrder> myOrder = dao.getOrders();
            
            try {
                
            out.println("<html>");
            out.println("<head>");
            out.println("<title>All orders</title>");            
            out.println("</head>");
            out.println("<body>");
            
            out.println("<form action=\"ReleaseOrder\" method=\"post\">");
            out.println("<fieldset><legend>All orders</legend>");
            for(MyOrder b : myOrder){
                out.println("<input type=\"checkbox\" name=\"checkorder\" value="+b.getId()+">");
                out.println("<b>"+b+"</b><br>");
            }
            out.println("<input type=\"submit\" value=\"Release orders\">");
            out.println("</fieldset>");
            out.println("</form>");
                
            out.println("</body>");
            out.println("</html>");
            } finally {            
            out.close();
            }
        }
    }
    
    private int getUserId(ArrayList<Users> users, String login, String password) {
		for (Users u : users) {
			if (u.getName().equals(login) && u.getPassword().equals(password))
				return u.getIdofuser();
		}
		return -1;
	}
	
	private String getUserRole(ArrayList<Users> users, String login, String password) {
		for (Users u : users) {
			if (u.getName().equals(login) && u.getPassword().equals(password))
				return u.getRole();
		}
		return null;
	}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
