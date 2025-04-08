/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.helper;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author student
 */
    @XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {

 @XmlTransient
    private boolean tobeHold;

    public boolean isTobeHold() {
        return tobeHold;
    }

    public void setTobeHold(boolean tobeHold) {
        this.tobeHold = tobeHold;
    }
    private String eventName; 
    private String category;
   
    ArrayList<Author> organizer ;

    public Book(){
        
    }
     public Book(String eventName, String category, Author organizer) {
        this.eventName = eventName;
        this.category = category;
       
        this.organizer= new ArrayList<Author>();
        this.organizer.add(new Author(organizer.getFirstName(), organizer.getLastName()));
    }


    public String getIsbn() {
        return eventName;
    }

    public String getTitle() {
        return category;
    }

   
    public ArrayList<Author> getAuthors() {
        return organizer;
    }
     public void addAuthor(ArrayList<Author> authors) {
         for(Author a:authors){
        this.organizer.add(new Author(a.getFirstName(),a.getLastName()));
         }
    }
}
