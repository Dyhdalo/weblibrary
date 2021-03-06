package server;
// Generated 23.11.2014 21:24:17 by Hibernate Tools 3.2.1.GA



/**
 * Books generated by hbm2java
 */
public class Books  implements java.io.Serializable {


     private int idofbook;
     private String author;
     private String name;
     private String description;

    public Books() {
    }

	
    public Books(int idofbook) {
        this.idofbook = idofbook;
    }
    public Books(int idofbook, String author, String name, String description) {
       this.idofbook = idofbook;
       this.author = author;
       this.name = name;
       this.description = description;
    }
   
    public int getIdofbook() {
        return this.idofbook;
    }
    
    public void setIdofbook(int idofbook) {
        this.idofbook = idofbook;
    }
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return "Book \""+name+"\" by author "+author+". Description: "+description;
    }


}


