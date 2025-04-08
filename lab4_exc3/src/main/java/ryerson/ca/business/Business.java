/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
import ryerson.ca.helper.Book;
import ryerson.ca.helper.BooksXML;

/**
 *
 * @author student
 */
public class Business {

    public static boolean isAuthenticated(String username, String passwrod) {
        return true;
    }

    public static BooksXML getServices(String query, String token) throws IOException {

        Client searchclient = ClientBuilder.newClient();
        WebTarget searchwebTarget
                = searchclient.target("http://localhost:8080/SearchBooks/webresources/search/");
        InputStream is
                = searchwebTarget.path(query).request(MediaType.APPLICATION_XML).get(InputStream.class);
        String xml = IOUtils.toString(is, "utf-8");
        BooksXML books = bookxmltoObjects(xml);
        if (token != null) {
            Client holdclient = ClientBuilder.newClient();
            WebTarget holdwebTarget
                    = holdclient.target("http://localhost:8080/HoldBook/webresources/hold/isOnHold");
            for (Book book : books.getBooks()) {

                InputStream holddata
                        = holdwebTarget.path(book.getIsbn()).queryParam("token", token).
                                request(MediaType.APPLICATION_XML).get(InputStream.class);
                try{
                    Book a=bookholdxmltoObjects(IOUtils.toString(holddata, "utf-8"));
                    if(a!=null)
                        book.setTobeHold(true);
                    else
                        book.setTobeHold(false);
                }
                catch(Exception e){
                    book.setTobeHold(false);
                }
                
                
            }
        }

        return (books);

    }

    private static BooksXML bookxmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(BooksXML.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            BooksXML books = (BooksXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return books;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Book bookholdxmltoObjects(String xml) {
        if(xml.isEmpty())
            return null;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Book.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Book book = (Book) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return book;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
