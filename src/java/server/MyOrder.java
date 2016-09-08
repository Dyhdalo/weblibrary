/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Admin
 */
public class MyOrder {
    
    private int idOfOrder;
    private String userName;
    private String bookAuthor;
    private String bookName;
        
        public MyOrder(int idOfOrder, String userName, String bookAuthor, String bookName) {
		this.idOfOrder = idOfOrder;
		this.userName = userName;
		this.bookAuthor = bookAuthor;
		this.bookName = bookName;
	}
        
        public String toString() {
		return userName+" orders book \""+bookName+"\" by "+bookAuthor;
	}
        
        public int getId(){
            return idOfOrder;
        }
    
}
