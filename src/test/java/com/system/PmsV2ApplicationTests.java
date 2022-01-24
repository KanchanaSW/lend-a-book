package com.system;

import com.system.DTO.BookDTO;
import com.system.DTO.MovieDTO;
import com.system.models.*;
import com.system.repository.*;
import com.system.security.jwt.JwtUtils;
import com.system.security.services.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PmsV2ApplicationTests {
	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private IssuedBookService issuedBookService;
	@Autowired
	private IssuedMovieService issuedMovieService;
	@Autowired
	private IssueService issueService;
	@Autowired
	private MovieService movieService;
	@Autowired
	private SubscriptionService subscriptionService;

	@MockBean
	private RoleService roleService;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private AuthenticationManager authenticationManager;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private JwtUtils jwtUtils;
	//@MockBean
	//private SubscriptionService subscriptionService;
	@MockBean
	private OTPRepository otpRepository;
	@MockBean
	private BookRepository bookRepository;
	@MockBean
	private IssuedBookRepository issuedBookRepository;
	@MockBean
	private IssuedMovieRepository issuedMovieRepository;
	@MockBean
	private IssueRepository issueRepository;
	@MockBean
	private MovieRepository movieRepository;
	@MockBean
	private SubscriptionRepository subscriptionRepository;

	/* user service*/
	@Test
	public void registerUser(){
		User user=new User("User124","usertest@gmail.com","password","26/05/2000","imageUrl",false);
		authService.registerUserService(user);
		verify(userRepository,times(1)).save(any(User.class));
	}
	@Test
	public void getAllUsers(){
		Role role = roleService.getRoleByName("ROLE_USER");
		Subscription subscription= subscriptionService.get("Free");
		User user=new User(222L,"User124","usertest@gmail.com","password","26/05/2000","imageUrl",false,role,subscription);
		User user2=new User(322L,"User324","user3test@gmail.com","password","26/05/2000","imageUrl",false,role,subscription);
		when(userRepository.findAll()).thenReturn(Stream.of(user,user2).collect(Collectors.toList()));
		assertEquals(2,userService.getAllUsers().size());
	}
	@Test
	public void findUser(){
		Role role = roleService.getRoleByName("ROLE_USER");
		Subscription subscription= subscriptionService.get("Free");
		User user=new User(222L,"User124","usertest@gmail.com","password","26/05/2000","imageUrl",false,role,subscription);
		when(userRepository.findById(222L)).thenReturn(java.util.Optional.of(user));
		assertEquals(user,userService.findUser(222L));
	}
	@Test
	public void directUserByTypr(){
		Role role = roleService.getRoleByName("ROLE_USER");
		Subscription subscription= subscriptionService.get("Free");
		String email="usertest@gmail.com";
		User user=new User(222L,"usertest@gmail.com","password","User134","26/05/2000","imageUrl",false,role,subscription);
		when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(user));
		assertEquals(user,userService.directUserType(email));
	}
	@Test
	public void deleteUser(){
		Role role = roleService.getRoleByName("ROLE_USER");
		Subscription subscription= subscriptionService.get("Free");
		User user=new User(222L,"usertest@gmail.com","password","User134","26/05/2000","imageUrl",false,role,subscription);
		userService.deleteByUser(user);
		verify(userRepository,times(1)).delete(user);
	}
	@Test
	public void searchName(){
		Role role = roleService.getRoleByName("ROLE_USER");
		Subscription subscription= subscriptionService.get("Free");
		String name="user";
		User user=new User(222L,"usertest@gmail.com","password","user1234","26/05/2000","imageUrl",false,role,subscription);
		User user2=new User(322L,"usertest1@gmail.com","password","user154","26/05/2000","imageUrl",false,role,subscription);
		when(userRepository.name(name)).thenReturn(Stream.of(user,user2).collect(Collectors.toList()));
		assertEquals(2,userService.searchName(name).size());
	}
	/*end*/

	/*movie service*/
	@Test
	public void getAllMovies(){
		Movie movie=new Movie();
		Movie movie1=new Movie();
		when(movieRepository.findAll()).thenReturn(Stream.of(movie,movie1).collect(Collectors.toList()));
		assertEquals(2,movieService.getAllMovies().size());
	}
	@Test
	public void searchMovis(){
		Movie movie=new Movie();
		Movie movie1=new Movie();
		String title="movie";
		when(movieRepository.name(title)).thenReturn(Stream.of(movie,movie1).collect(Collectors.toList()));
		assertEquals(2,movieService.searchMovies(title).size());
	}
	@Test
	public void addMovie(){
		MovieDTO movieDTO=new MovieDTO();
		movieService.addMovie(movieDTO);
		verify(movieRepository,times(1)).save(any(Movie.class));
	}
	@Test
	public void findMovie(){
		Movie movie=new Movie();
		when(movieRepository.findById(111)).thenReturn(java.util.Optional.of(movie));
		assertEquals(movie,movieService.findMovie(111));
	}
	@Test
	public void findMovieByTit(){
		Movie movie=new Movie();
		when(movieRepository.findByTitle("tit")).thenReturn(movie);
		assertEquals(movie,movieService.findMovieByTitle("tit"));
	}
	@Test
	public void deleteMove(){
		movieService.deleteMovie(111);
		verify(movieRepository,times(1)).deleteById(111);
	}
	/*end*/

	/*Book service*/
	@Test
	public void getAllBooks(){
		Book book=new Book(111,111L,"bookTitle","author","pub","avai","image","summary",20);
		Book book1=new Book(112,112L,"book2Title","author","pub","avai","image","summary",20);
		when(bookRepository.findAll()).thenReturn(Stream.of(book,book1).collect(Collectors.toList()));
		assertEquals(2,bookService.getALlBooks().size());
	}
	@Test
	public void searchBooks(){
		Book book=new Book(111,111L,"bookTitle","author","pub","avai","image","summary",20);
		Book book1=new Book(112,112L,"book2Title","author","pub","avai","image","summary",20);
		String title="book";
		when(bookRepository.name(title)).thenReturn(Stream.of(book,book1).collect(Collectors.toList()));
		assertEquals(2,bookService.searchBooks(title).size());
	}
	@Test
	public void addBook(){
		BookDTO book=new BookDTO(111L,"bookTitle","author","pub","avai","image","summary",20);
		bookService.addBook(book);
		verify(bookRepository,times(1)).save(any(Book.class));
	}
	@Test
	public void findBook(){
		Book book=new Book(111,111L,"bookTitle","author","pub","avai","image","summary",20);
		when(bookRepository.findById(111)).thenReturn(java.util.Optional.of(book));
		assertEquals(book,bookService.findBook(111));
	}
	@Test
	public void findBookByTitle(){
		Book book=new Book(111,111L,"bookTitle","author","pub","avai","image","summary",20);
		when(bookRepository.findByTitle("bookTitle")).thenReturn(book);
		assertEquals(book,bookService.findBookTitle("bookTitle"));
	}
	@Test
	public void delete(){
		Book book=new Book(111,111L,"bookTitle","author","pub","avai","image","summary",20);
		bookService.delete(book);
		verify(bookRepository,times(1)).delete(book);
	}
	@Test
	public void deleteById(){
		bookService.deleteBook(111);
		verify(bookRepository,times(1)).deleteById(111);
	}
	@Test
	public void get(){
		Book book=new Book(111,111L,"bookTitle","author","pub","avai","image","summary",20);
		Book book1=new Book(112,112L,"book2Title","author","pub","avai","image","summary",20);
		List<Integer> ids = new ArrayList<>();
		ids.add(111);ids.add(112);
		when(bookRepository.findAllById(ids)).thenReturn(Stream.of(book,book1).collect(Collectors.toList()));
		assertEquals(2,bookService.get(ids).size());
	}
	/*end*/

	/*Issued book service*/
	@Test
	public void getAllIsssuedBooks(){
		IssuedBook issuedBook=new IssuedBook();
		IssuedBook issuedBook1=new IssuedBook();
		when(issuedBookRepository.findAll()).thenReturn(Stream.of(issuedBook,issuedBook1).collect(Collectors.toList()));
		assertEquals(2,issuedBookService.getAll().size());
	}
	@Test
	public void findIssuedBookBYId(){
		IssuedBook issuedBook=new IssuedBook();
		when(issuedBookRepository.findById(111L)).thenReturn(java.util.Optional.of(issuedBook));
		assertEquals(issuedBook,issuedBookService.get(111L));
	}
	@Test
	public void addNewIssuedBook(){
		IssuedBook issuedBook=new IssuedBook();
		issuedBookService.save(issuedBook);
		verify(issuedBookRepository,times(1)).save(issuedBook);
	}
	/*end*/

	/*Issued movie service*/
	@Test
	public void getAllIsssuedMovies(){
		IssuedMovie issuedmovie=new IssuedMovie();
		IssuedMovie issuedmovie1=new IssuedMovie();
		when(issuedMovieRepository.findAll()).thenReturn(Stream.of(issuedmovie,issuedmovie1).collect(Collectors.toList()));
		assertEquals(2,issuedMovieService.getAll().size());
	}
	@Test
	public void findIssuedMvieBYId(){
		IssuedMovie issuedmovie=new IssuedMovie();
		when(issuedMovieRepository.findById(111L)).thenReturn(java.util.Optional.of(issuedmovie));
		assertEquals(issuedmovie,issuedMovieService.get(111L));
	}
	@Test
	public void addNewIssuedMovie(){
		IssuedMovie issuedmovie=new IssuedMovie();
		issuedMovieService.save(issuedmovie);
		verify(issuedMovieRepository,times(1)).save(issuedmovie);
	}
	/*end*/

	/*Issue service*/
	@Test
	public void getAllIssues(){
		Issue issue=new Issue();
		Issue issue1=new Issue();
		when(issueRepository.findAll()).thenReturn(Stream.of(issue,issue1).collect(Collectors.toList()));
		assertEquals(2,issueService.getAll().size());
	}
	@Test
	public void findIssueById(){
		Issue issue=new Issue();
		when(issueRepository.findById(111L)).thenReturn(java.util.Optional.of(issue));
		assertEquals(issue,issueService.get(111L));
	}
	@Test
	public void addnewisue(){
		Issue issue=new Issue();
		issueService.save(issue);
		verify(issueRepository,times(1)).save(issue);
	}
	/*end*/

	/*subscription service*/
	@Test
	public void getAllSubs(){
		Subscription subscription=new Subscription();
		Subscription subscription1=new Subscription();
		when(subscriptionRepository.findAll()).thenReturn(Stream.of(subscription,subscription1).collect(Collectors.toList()));
		assertEquals(2,subscriptionService.getAll().size());
	}
	@Test
	public void getSubsByType(){
		Subscription subscription=new Subscription();
		when(subscriptionRepository.findSubscriptionsByType("Free")).thenReturn(subscription);
		assertEquals(subscription,subscriptionService.get("Free"));
	}
	@Test
	public void getSubsById(){
		Subscription subscription=new Subscription();
		when(subscriptionRepository.findById(11L)).thenReturn(java.util.Optional.of(subscription));
		assertEquals(subscription,subscriptionService.getSubById(11L));
	}
	@Test
	public void addSubs(){
		Subscription subscription=new Subscription();
		subscriptionService.saveSubs(subscription);
		verify(subscriptionRepository,times(1)).save(subscription);
	}
}
