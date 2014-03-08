package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;
    private static long authorID = 1;
	private static long reviewID = 1;
    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }
    
    //****VIEWING A BOOK****
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	BookDto bookResponse = new BookDto(book);
    	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
    	bookResponse.addLink(new LinkDto("update-book", "/books/" + book.getIsbn(), "POST"));
    	return bookResponse;
    }

    //****CREATING A BOOK****
    @POST
    @Timed(name = "create-book")
    public Response createBook(Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);

	String location = "/books/" + savedBook.getIsbn();
	BookDto bookResponse = new BookDto(savedBook);
	
	ArrayList<LinkDto> links = new ArrayList<LinkDto>();
	links.add(new LinkDto("view-book", location, "GET"));
	links.add(new LinkDto("update-book", location, "PUT"));
	links.add(new LinkDto("add-book", location, "POST"));
	links.add(new LinkDto("delete-book", location, "DELETE"));
	return Response.status(201).entity(links).build();
    }
    
    //****DELETING THE BOOK****
    @DELETE
    @Path ("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBook(@PathParam("isbn") long isbn) {
    	// Delete the book from the BookRepository.
    	bookRepository.deleteBook(isbn);
    	LinksDto links = new LinksDto();
    	links.addLink(new LinkDto("add-book", "\book", "POST"));
    	// Add other links if needed

    	return Response.ok(links).build();
    }

    //****UPDATING THE BOOK****
    @PUT
    @Path ("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBook(@PathParam("isbn") long isbn, @QueryParam("status") String status, Book request) {
    	Book book = bookRepository.getBookByISBN(isbn);
    	Book book1 = new Book();
    	if(book.getIsbn() == isbn)
    	book1 = bookRepository.updateBook(isbn, status );

    	String location = "/books/" + book1.getIsbn();
    	BookDto bookResponse = new BookDto(book1);
    	
    	ArrayList<LinkDto> links = new ArrayList<LinkDto>();
    	links.add(new LinkDto("view-book", location, "GET"));
    	links.add(new LinkDto("update-book", location, "PUT"));
    	links.add(new LinkDto("add-book", location, "POST"));
    	links.add(new LinkDto("delete-book", location, "DELETE"));

    	return Response.status(201).entity(links).build();
    }

    //****CREATING REVIEWS****
    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
    public Response createReview(@Valid Review reviews, @PathParam("isbn") long isbn) {

		Book retrieveBook = bookRepository.getBookByISBN(isbn);

		reviews.setId((int) reviewID);
		retrieveBook.getReviews().add(reviews);
		reviewID++;

		ReviewDto reviewResponse = new ReviewDto();
		reviewResponse.addLink(new LinkDto("view-review", "/books/" + retrieveBook.getIsbn() + "/reviews/" + reviews.getId(), "GET"));

	return Response.status(201).entity(reviewResponse.getLinks()).build();
    }
    
    //****DISPLAYING A REVIEWS****
    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-review")
    public Response displayReview(@PathParam("isbn") long isbn, @PathParam("id") long id) {
		int i=0;
		Book book = bookRepository.getBookByISBN(isbn);
		ReviewDto response = new ReviewDto(book.getoneReview(i));
		LinksDto links = new LinksDto();
    	links.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + book.getoneReview(i).getId(), "GET"));
    	return Response.ok(links).build();
    }

    //****DISAPLAYING ALL REVIEWS****
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-all-reviews")
    public ReviewsDto displayReviews(@PathParam("isbn") long isbn) {

		Book book = bookRepository.getBookByISBN(isbn);
		ReviewsDto response = new ReviewsDto(book.getReviews());

	return response;
    }
}