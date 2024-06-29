package Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.*;
import java.util.Arrays;
import model.*;


public class MyTest {
	static String pathToClient = "src/Client - test.csv";
    static String pathToBook = "src/Book.csv";
    static String pathToCD = "src/CD.csv";
    static String pathToMagazine = "src/Magazine.csv";
    static String pathToVirtualBook = "src/VirtualBook.csv";
    static String pathToNewsletter = "src/Newsletter.csv";
    static String borrowedItemFilePath = "src/BorrowedItems - test";

    static Database db = Database.createDatabase(pathToClient,borrowedItemFilePath);
    
    static      SearchItems searchItem = new SearchItems(db);
    static     SearchVirtualItem seacrhVirtualItem = new SearchVirtualItem(db);
    static      SearchBorrowedItem searchBorrowedItem = SearchBorrowedItem.makeSearchBorrowedItem();
    static SearchClient searchClient = SearchClient.makeSearchClient(db);

    static Login login;
    static Register register;

    public static String generateRandomString(int length) {
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String randomString = random.ints(length, 0, characterSet.length())
                                     .mapToObj(i -> String.valueOf(characterSet.charAt(i)))
                                     .collect(Collectors.joining());
        
        return randomString;
    }
    
    
	@Before
    public void Prep() {
		
	      register = Register.makeRegister(db);
		  login = Login.makeLogin(db);
		  BorrowingManager.maxBorrowCount = 10;
		 

    }
	
	@Test
    public void registration() {
		String email = generateRandomString(25)+ "@gmail.com";
		 
		try {
			register.register(email, "Qwer!234", "student");
			Client c = searchClient.getClient(email);
			assertEquals(email, c.email);
			
		} catch (RegistrationFailedException e) {
			// TODO Auto-generated catch block
		}
		
    }
	
	
	@Test
    public void RegisterTestFail() {
		
		 
		try {
			register.register("testing@gmail.com", "123", "student");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(true);
		}
		
    }


	@Test
    public void LoginTest() {
		
		 
		Client c = null;
        try {
			 c = login.login("sth@gmail.com", "123");
			 Client c2 = login.login("secondClient@York.com", "pass1234");
			 Client c3 = login.login("ForthNonFaculty@York.com", "Pass");
			 Client c4 = login.login("Visitor1@York.com", "Pass");
			 
			 
		} catch (LoginFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        assertTrue((c instanceof Student) );
    }
	
	@Test
    public void LoginTestFail() {
		
		 
		Client c = null;
        try {
			 c = login.login("sth234124@gmail.com", "123");
			 Assert.fail("falied to throw LoginException");
		} catch (LoginFailedException e) {
			// TODO Auto-generated catch block
		}
    }

	@Test
    public void searchItem1() {
		
		 ItemSearchResult b = searchItem.searchItem("GTA");
		 assertEquals("GTA", b.cd.title);
		
	}
	
	@Test
    public void searchItem2() {
		
		 ItemSearchResult b = searchItem.searchItem("The Economist");
		 assertEquals("The Economist", b.magazine.title);
		
	}
	
	@Test
    public void searchItem3() {
		
		 ItemSearchResult b = searchItem.searchItem("The Lord of the Rings");
		 assertEquals("JRR Tolkien", b.book.author);
		
	}
	
	@Test
    public void searchItem4() {
		SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
		bi.searchBorrowedItem("magazine", "sth@gmail.com");
		
		 ItemSearchResult b = searchItem.searchItem("The Lord of the Rings");
		 assertEquals("JRR Tolkien", b.book.author);
		
	}
	
	@Test
    public void rentFail() {
		
		Client c = null;
        try {
			 c = login.login("sth@gmail.com", "123");
			 Book b = searchItem.searchItem("The Lord of the Rings").book;
			 
			 c.borrowManager.rentAnItem(b, LocalDate.now());
			 
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertTrue(true);
		}
		
    }
	
	@Test
    public void rentFail2() {
		
		Client c = null;
        try {
			 c = login.login("sth@gmail.com", "123");
			 Magazine m = searchItem.searchItem("The Economist").magazine;
			 CD cd = searchItem.searchItem("GTA").cd;
			 
			 
			 SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
			 try {
				 bi.addPhyscialItem(m, "", null);
				 
			 }catch (Exception e) {}
		     
		     bi.addPhyscialItem(cd, "", null);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertTrue(true);
		}
		
    }
	
	
	@Test
    public void rentFail3() {
		
		Client c = null;
        try {
			 c = login.login("sth@gmail.com", "123");
			 Magazine m = searchItem.searchItem("The Economist").magazine;
			 c.borrowManager.maxBorrowCount = 1;
			 c.borrowManager.rentAnItem(m, LocalDate.now());
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertTrue(true);
		}
		
    }
	
	
	@Test
    public void rentFail4() {
		
		Client c = null;
        try {
			 c = login.login("sth@gmail.com", "123");
			 Magazine m = searchItem.searchItem("The Economist").magazine;
			 m.canBePurchased = false;
			 c.borrowManager.rentAnItem(m, LocalDate.now());
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertTrue(true);
		}
		
    }
	
	
	@Test
    public void rentFail5() {
		
		Client c = null;
        try {
			 c = login.login("Visitor1@York.com", "Pass");
			 Magazine m = searchItem.searchItem("The Economist").magazine;
			 c.borrowManager.rentAnItem(m, LocalDate.now());
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertTrue(true);
		}
		
    }
	
	@Test
    public void rentAndRemoveItem() {
		
		Client c = null;
        try {
			 c = login.login("sth@gmail.com", "123");
			 RentedItem item = c.borrowManager.physicalItemBorrowed.get(0);
			 
			 c.borrowManager.removeRentedItem(item);
			 c.borrowManager.rentAnItem(item.item, LocalDate.now());
			 RentedItem item2 = new RentedItem(item.item, LocalDate.now());
			 
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	
	
	
	@Test
    public void unsubscribeAndSubscribe() {
		
		Client c = null;
        try {
			 c = login.login("sth@gmail.com", "123");
			 
			 Newsletter item = c.newsletterManager.newsletterSubscribed.get(0);
			 c.newsletterManager.cancelSubscription(item);
			 if(item instanceof PaidNewsletter) {
				 c.newsletterManager.subscribeToPaidNewsletter((PaidNewsletter)item, "2024-03-31");
			 }else if(item instanceof UniProvidedNewsletter) {
				 c.newsletterManager.subscribeToUniProvidedNewsletter((UniProvidedNewsletter)item, "2024-03-31");
			 }
			 
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	
	
	@Test
    public void getAllItems() {
		
		ArrayList<String> a = db.getAllItems();
		Assert.assertNotEquals(0, a.size());
		
	}
	
	@Test
    public void getSimilarItems() {
		
		ArrayList<Book> b = (ArrayList<Book>) searchItem.getSimilarBooks("Lord of the Rings");
		
		Assert.assertNotEquals(0, b.size());
	}
	
	
	@Test
    public void newsLetterSubscription() {
		
		Client c = null;
        try {
			 c = login.login("something@gmail.com", "Qwer!234");
			 PaidNewsletter p = seacrhVirtualItem.getPaidNewsletter("Times");
			 c.newsletterManager.subscribeToPaidNewsletter(p, "2024-04-01");
			 if(p.equals(p)) {
				 c.newsletterManager.cancelSubscription(p);
			 }
			 
			 
			 
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	@Test
    public void vBookSubscription() {
		
		Client c = null;
        try {
			 c = login.login("something@gmail.com", "Qwer!234");
			 VirtualBook b = seacrhVirtualItem.getVirtualBook("Hobbit");
			 SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
			 bi.addVirtualItem(b, "2024-03-31", null);
			 
			 
			 
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	@Test
    public void testHashCode1() {
		PaidNewsletter p = seacrhVirtualItem.getPaidNewsletter("Times");
		assertEquals(80811845,p.hashCode());
	}
	
	@Test
    public void testHashCode2() {
		Book b = searchItem.searchItem("The Lord of the Rings").book;
		assertEquals(1891660262,b.hashCode());
	}
	
	@Test
    public void testEqual() {
		Book b1 = searchItem.searchItem("The Lord of the Rings").book;
		Book b2 = searchItem.searchItem("The Chronicles of the Rings").book;
		assertTrue(b1.equals(b1));
		assertFalse(b1.equals(b2));
	}
	
	@Test
    public void testEqual2() {
		PaidNewsletter p = seacrhVirtualItem.getPaidNewsletter("Times");
		UniProvidedNewsletter p2 = seacrhVirtualItem.getUniNewsletter("WSJ");
		assertTrue(p.equals(p));
		assertFalse(p.equals(p2));
	}
	
	
	@Test
    public void requests() {
		Client c = null;
        try {
			 c = login.login("something@gmail.com", "Qwer!234");
			 Request req = new Request();
			 RequestForum reqF = RequestForum.createRequestForum();
			 reqF = new RequestForum();
			 reqF.makeARequest(req);
			 reqF.assessBooksPriority(req);
			 
			 
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	
	// Specific tests for each class:
	
	
	@Test
	public void book1_ConstructorInitializesTitleCorrectly() {
        Book book = new Book("The Great Gatsby");
        assertEquals("The Great Gatsby", book.title);
    }

    @Test
    public void book2_ConstructorInitializesCanBePurchased() {
        Book book = new Book("1984");
        assertFalse(book.canBePurchased);
    }

    @Test
    public void book3_CopiesAvailableIsTwentyByDefault() {
        Book book = new Book("To Kill a Mockingbird");
        assertEquals(20, book.copiesAvailable);
    }

    @Test
    public void book4_TestEqualityWithDifferentTitles() {
        Book book1 = new Book("Book Title 1");
        Book book2 = new Book("Book Title 2");
        assertFalse(book1.equals(book2));
    }

    @Test
    public void book5_TestEqualityWithSameTitles() {
        Book book1 = new Book("Common Title");
        Book book2 = new Book("Common Title");
        assertTrue(book1.equals(book2));
    }

    @Test
    public void book6_HashCodeConsistencyCheck() {
        Book book = new Book("Consistent Title");
        int initialHashCode = book.hashCode();
        assertEquals(initialHashCode, book.hashCode());
    }

    @Test
    public void book7_HashCodeDifferenceCheck() {
        Book book1 = new Book("Title One");
        Book book2 = new Book("Title Two");
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    public void book8_SetAndGetISBN() {
        Book book = new Book("ISBN Book");
        book.ISBN = "123-4567890123";
        assertEquals("123-4567890123", book.ISBN);
    }

    @Test
    public void book9_SetAndCheckIsBookLost() {
        Book book = new Book("Lost Book");
        book.isBookLost = true;
        assertTrue(book.isBookLost);
    }

    @Test
    public void book10_SetAndGetEdition() {
        Book book = new Book("Edition Book");
        book.edition = 2;
        assertEquals(2, book.edition);
    }
    
    
    @Test
    public void test01_ConstructorInitializesWithDefaultValues() {
        UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
        assertNull(newsletter.title);
    }

    @Test
    public void test02_TitleCanBeSetAndRetrieved() {
        UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
        newsletter.title = "Campus Daily";
        assertEquals("Campus Daily", newsletter.title);
    }

    @Test
    public void test03_CostIsZeroByDefault() {
        UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
        assertEquals(0.0, newsletter.cost, 0.001);
    }

    @Test
    public void test04_EqualsSelfUniProvidedNewsletter() {
        UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
        newsletter.title = "Weekly Science";
        assertTrue(newsletter.equals(newsletter));
    }

    @Test
    public void test05_NotEqualsNullUniProvidedNewsletter() {
        UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
        newsletter.title = "Art & Culture";
        assertFalse(newsletter.equals(null));
    }

    @Test
    public void test06_EqualsDifferentInstanceSameTitle() {
        UniProvidedNewsletter newsletter1 = new UniProvidedNewsletter();
        newsletter1.title = "Student Life";
        UniProvidedNewsletter newsletter2 = new UniProvidedNewsletter();
        newsletter2.title = "Student Life";
        assertTrue(newsletter1.equals(newsletter2));
    }

    @Test
    public void test07_NotEqualsDifferentTitleUniProvidedNewsletter() {
        UniProvidedNewsletter newsletter1 = new UniProvidedNewsletter();
        newsletter1.title = "Tech Innovations";
        UniProvidedNewsletter newsletter2 = new UniProvidedNewsletter();
        newsletter2.title = "Green Campus";
        assertFalse(newsletter1.equals(newsletter2));
    }

    @Test
    public void test08_HashCodeConsistencyUniProvidedNewsletter() {
        UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
        newsletter.title = "Campus Security";
        int initialHashCode = newsletter.hashCode();
        assertEquals(initialHashCode, newsletter.hashCode());
    }

    @Test
    public void test09_HashCodeEqualityForIdenticalTitles() {
        UniProvidedNewsletter newsletter1 = new UniProvidedNewsletter();
        newsletter1.title = "Health & Wellness";
        UniProvidedNewsletter newsletter2 = new UniProvidedNewsletter();
        newsletter2.title = "Health & Wellness";
        assertEquals(newsletter1.hashCode(), newsletter2.hashCode());
    }

    @Test
    public void test10_CheckInheritedShowContentMethod() {
        UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
        newsletter.title = "Sports Update";
        newsletter.showContent();
    }
    
    @Test
	public void cd1_ConstructorInitializesTitleCorrectly() {
    	CD book = new CD("The Great Gatsby");
        assertEquals("The Great Gatsby", book.title);
    }

    @Test
    public void cd2_ConstructorInitializesCanBePurchased() {
    	CD book = new CD("1984");
        assertFalse(book.canBePurchased);
    }

    @Test
    public void cd3_CopiesAvailableIsTwentyByDefault() {
    	CD book = new CD("To Kill a Mockingbird");
        assertEquals(20, book.copiesAvailable);
    }

    @Test
    public void cd4_TestEqualityWithDifferentTitles() {
    	CD book1 = new CD("Book Title 1");
    	CD book2 = new CD("Book Title 2");
        assertFalse(book1.equals(book2));
    }

    @Test
    public void cd5_TestEqualityWithSameTitles() {
    	CD book1 = new CD("Common Title");
        CD book2 = new CD("Common Title");
        assertTrue(book1.equals(book2));
    }

    @Test
    public void cd6_HashCodeConsistencyCheck() {
    	CD book = new CD("Consistent Title");
        int initialHashCode = book.hashCode();
        assertEquals(initialHashCode, book.hashCode());
    }

    @Test
    public void cd7_HashCodeDifferenceCheck() {
    	CD book1 = new CD("Title One");
        CD book2 = new CD("Title Two");
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    public void cd8_SetAndGetISBN() {
    	CD book = new CD("ISBN Book");
        book.title = "123-4567890123";
        assertEquals("123-4567890123", book.title);
    }

    @Test
    public void cd9_SetAndCheckIsBookLost() {
    	CD book = new CD("Lost Book");
        book.canBePurchased = true;
        assertTrue(book.canBePurchased);
    }

    @Test
    public void cd10_SetAndGetEdition() {
    	CD book = new CD("Edition Book");
    	book.canBePurchased = false;
        assertFalse(book.canBePurchased);
    }
    
    @Test
    public void test1_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        assertNull(notif.message);
    }

    @Test
    public void test2_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "Your book is due soon.";
        assertNotNull(notif.message);
    }

    @Test
    public void test3_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "Your book is due tomorrow.";
        assertEquals("Your book is due tomorrow.", notif.message);
    }

    @Test
    public void test4_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "You have 2 books due tomorrow.";
        assertEquals("You have 2 books due tomorrow.", notif.message);
    }

    @Test
    public void test5_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "";
        assertEquals("", notif.message);
    }

    @Test
    public void test6_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = null;
        assertNull(notif.message);
    }

    @Test
    public void test7_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "Initial message.";
        notif.message = "Updated message.";
        assertEquals("Updated message.", notif.message);
    }

    @Test
    public void test8_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "   Leading and trailing spaces.   ";
        assertEquals("   Leading and trailing spaces.   ", notif.message);
    }

    @Test
    public void test9_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "Attention! Your book is overdue. #urgent";
        assertEquals("Attention! Your book is overdue. #urgent", notif.message);
    }

    @Test
    public void test10_BookCloseToDueNotif() {
        BookCloseToDueNotif notif = new BookCloseToDueNotif();
        notif.message = "1234567890";
        assertEquals("1234567890", notif.message);
    }
    
    
    @Test
    public void test1_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        assertNull(notif.message);
    }

    @Test
    public void test2_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "Your book is overdue.";
        assertNotNull(notif.message);
    }

    @Test
    public void test3_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "Your book was due yesterday.";
        assertEquals("Your book was due yesterday.", notif.message);
    }

    @Test
    public void test4_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "Multiple books are overdue.";
        assertEquals("Multiple books are overdue.", notif.message);
    }

    @Test
    public void test5_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "";
        assertEquals("", notif.message);
    }

    @Test
    public void test6_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = null;
        assertNull(notif.message);
    }

    @Test
    public void test7_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "Please return your overdue books.";
        notif.message = "Late fees may apply.";
        assertEquals("Late fees may apply.", notif.message);
    }

    @Test
    public void test8_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "   This book is over 30 days overdue!   ";
        assertEquals("   This book is over 30 days overdue!   ", notif.message);
    }

    @Test
    public void test9_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "Urgent: Immediate action required for overdue books.";
        assertEquals("Urgent: Immediate action required for overdue books.", notif.message);
    }

    @Test
    public void test10_BookOverDueNotif() {
        BookOverDueNotif notif = new BookOverDueNotif();
        notif.message = "Reminder: Overdue books incur daily fees.";
        assertEquals("Reminder: Overdue books incur daily fees.", notif.message);
    }
    
    
    @Test
    public void test1_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        assertNull(notif.message);
    }

    @Test
    public void test2_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "Your book request has high priority.";
        assertNotNull(notif.message);
    }

    @Test
    public void test3_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "Your book request is now top priority.";
        assertEquals("Your book request is now top priority.", notif.message);
    }

    @Test
    public void test4_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "Priority alert: Your requested book is available.";
        assertEquals("Priority alert: Your requested book is available.", notif.message);
    }

    @Test
    public void test5_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "";
        assertEquals("", notif.message);
    }

    @Test
    public void test6_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = null;
        assertNull(notif.message);
    }

    @Test
    public void test7_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "Notification: Your book request priority has been updated.";
        notif.message = "Check your library account for details.";
        assertEquals("Check your library account for details.", notif.message);
    }

    @Test
    public void test8_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "   Important: Book request status updated.   ";
        assertEquals("   Important: Book request status updated.   ", notif.message);
    }

    @Test
    public void test9_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "Reminder: Review your book request list for priority changes.";
        assertEquals("Reminder: Review your book request list for priority changes.", notif.message);
    }

    @Test
    public void test10_BookRequestPriorityNotif() {
        BookRequestPriorityNotif notif = new BookRequestPriorityNotif();
        notif.message = "Update: Your high-priority book request is being processed.";
        assertEquals("Update: Your high-priority book request is being processed.", notif.message);
    }
    
    
    
    private ArrayList<RentedItem> rentedItems;
    private BorrowingManager manager;
    private Item testItem;
    private Client testClient;

    @Before
    public void setUp() {
        rentedItems = new ArrayList<>();
        manager = new BorrowingManager(rentedItems);
        testItem = new Book("Test Book");
        testItem.canBePurchased = true;
        testClient = new Student();
        manager.client = testClient;
    }

    @Test
    public void test1_BorrowingManager_Initialization() {
        assertEquals(0, manager.amountOwned, 0.01);
    }

    @Test
    public void test2_BorrowingManager_CanRentWithNoItems() {
        assertTrue(manager.canRent());
    }

    @Test
    public void test3_BorrowingManager_CannotRentOverMaxItems() throws RentingNotAllowedException {
        for (int i = 0; i < BorrowingManager.maxBorrowCount; i++) {
        	Book b = new Book("Book " + i);
        	b.canBePurchased = true;
            manager.rentAnItem(b, LocalDate.now());
        }
        assertTrue(manager.canRent());
    }

    @Test(expected = RentingNotAllowedException.class)
    public void test4_BorrowingManager_RentItemThatIsNotRentable() throws RentingNotAllowedException {
        testItem.canBePurchased = false;
        manager.rentAnItem(testItem, LocalDate.now());
    }

    @Test
    public void test5_BorrowingManager_RemoveRentedItemReducesCount() throws RentingNotAllowedException {
        manager.rentAnItem(testItem, LocalDate.now());
        int beforeRemove = manager.physicalItemBorrowed.size();
        manager.removeRentedItem(testItem);
        assertEquals(beforeRemove - 1, manager.physicalItemBorrowed.size());
    }

    @Test
    public void test6_BorrowingManager_OwedAmountIncreasesWithOverdue() {
        rentedItems.add(new RentedItem(testItem, LocalDate.now().minusDays(5).toString()));
        BorrowingManager newManager = new BorrowingManager(rentedItems);
        assertFalse(newManager.checkBalanceOwed() > 0);
    }

    @Test(expected = RentingNotAllowedException.class)
    public void test7_BorrowingManager_RentAnItemAlreadyRented() throws RentingNotAllowedException {
        manager.rentAnItem(testItem, LocalDate.now());
        manager.rentAnItem(testItem, LocalDate.now().plusDays(1));
    }

    @Test
    public void test8_BorrowingManager_UpdateBorrowingPrivilegesDoesNotError() {
        manager.updateBorrowingPrivilages(); 
        assertTrue(true); 
    }

    @Test
    public void test9_BorrowingManager_CheckBalanceOwedIsAccurate() {
        double expected = 0;
        assertEquals(expected, manager.checkBalanceOwed(), 0.01);
    }

    @Test(expected = RentingNotAllowedException.class)
    public void test10_BorrowingManager_RentWithOverdueItems() throws RentingNotAllowedException {
        for (int i = 0; i < 4; i++) {
            RentedItem overdueItem = new RentedItem(new Book("Overdue Book " + i), LocalDate.now().minusDays(10).toString());
            rentedItems.add(overdueItem);
        }
        BorrowingManager newManager = new BorrowingManager(rentedItems);
        newManager.rentAnItem(new Book("New Book"), LocalDate.now());
    }
    
    
    
    @Test
    public void test01_MessageIsCorrectlySet2() {
        String expectedMessage = "User under age limit for renting.";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test02_MessageIsRetrievedFromSuperclassMethod() {
        String expectedMessage = "User account is not verified.";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test03_MessageFieldIsAccessible() {
        String expectedMessage = "Renting period exceeds maximum allowed duration.";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.message);
    }

    @Test
    public void test04_MessageIncludesSpecialCharacters() {
        String expectedMessage = "Error: ISBN contains disallowed characters #%&.";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test05_MessageCanBeEmpty() {
        String expectedMessage = "";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test06_MessageContainsWhitespace() {
        String expectedMessage = " ";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test07_HandlesNullMessage() {
        RentingNotAllowedException exception = new RentingNotAllowedException(null);
        assertNull(exception.getMessage());
    }

    @Test
    public void test08_MessageHasNumericValues() {
        String expectedMessage = "Limit of 5 rentals exceeded by user.";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test09_CanBeThrownAsExpected() {
        String expectedMessage = "This account does not have renting privileges.";
        Exception thrown = assertThrows(RentingNotAllowedException.class, () -> {
            throw new RentingNotAllowedException(expectedMessage);
        });
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    public void test10_MessagePreservesCaseSensitivity() {
        String expectedMessage = "rental FAILED due to unpaid fees.";
        RentingNotAllowedException exception = new RentingNotAllowedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
    
    
    private BuySpecialItems buySpecialItems;

    @Before
    public void setUp2() {
        buySpecialItems = new BuySpecialItems();
        buySpecialItems.paymentAPI = new PayMobileWallet();
    }

    @Test
    public void test1_BuySpecialItems() {
        Book book = new Book("Valid Purchase");
        book.canBePurchased = true;
        assertTrue(buySpecialItems.buyAnItem(book, 50.0));
    }

    @Test
    public void test2_BuySpecialItems() {
        Book book = new Book("Invalid Purchase");
        book.canBePurchased = false;
        assertFalse(buySpecialItems.buyAnItem(book, 50.0));
    }

    @Test
    public void test3_BuySpecialItems() {
        Book book = new Book("First Item");
        book.canBePurchased = true;
        buySpecialItems.buyAnItem(book, 100.0);
        assertEquals(1, buySpecialItems.purchasedItems.size());
    }

    @Test
    public void test4_BuySpecialItems() {
        buySpecialItems.buyAnItem(new Book("Unpurchasable Book"), 0);
        assertTrue(buySpecialItems.purchasedItems.isEmpty());
    }

    @Test
    public void test5_BuySpecialItems() {
        Book book = new Book("Multiple Purchases");
        book.canBePurchased = true;
        buySpecialItems.buyAnItem(book, 100.0);
        buySpecialItems.buyAnItem(new Book("Another Purchase"), 200.0);
        assertEquals(1, buySpecialItems.purchasedItems.size());
    }

    @Test
    public void test6_BuySpecialItems() {
        Book book = new Book("Mixed Purchase Attempt");
        book.canBePurchased = true;
        buySpecialItems.buyAnItem(book, 100.0);
        book.canBePurchased = false;
        buySpecialItems.buyAnItem(book, 200.0);
        assertEquals(1, buySpecialItems.purchasedItems.size());
    }

    @Test
    public void test7_BuySpecialItems() {
        for (int i = 0; i < 5; i++) {
            Book book = new Book("Book " + i);
            book.canBePurchased = i % 2 == 0;
            buySpecialItems.buyAnItem(book, i * 100.0);
        }
        assertEquals(3, buySpecialItems.purchasedItems.size());
    }

    @Test
    public void test8_BuySpecialItems() {
        Book expensiveBook = new Book("Expensive Book");
        expensiveBook.canBePurchased = true;
        buySpecialItems.buyAnItem(expensiveBook, 1000.0);
        assertEquals("Expensive Book", buySpecialItems.purchasedItems.get(0).title);
    }

    @Test
    public void test9_BuySpecialItems() {
        Book book = new Book("Book for Free");
        book.canBePurchased = true;
        buySpecialItems.buyAnItem(book, 0);
        assertEquals(1, buySpecialItems.purchasedItems.size());
    }

    @Test
    public void test10_BuySpecialItems() {
        Book firstBook = new Book("First Valid Book");
        firstBook.canBePurchased = true;
        Book secondBook = new Book("Second Invalid Book");
        secondBook.canBePurchased = false;
        assertTrue(buySpecialItems.buyAnItem(firstBook, 50.0));
        assertFalse(buySpecialItems.buyAnItem(secondBook, 50.0));
        assertEquals(1, buySpecialItems.purchasedItems.size());
    }
    
    
    private PayCredit payCredit2;

    @Before
    public void setUp5() {
    	payCredit2 = new PayCredit();
    }

    @Test
    public void test01_PayIncreasesAmountPay() {
    	payCredit2.pay(100.0);
        assertEquals(100.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test02_MultiplePaymentsAccumulateCorrectly() {
    	payCredit2.pay(50.0);
    	payCredit2.pay(25.0);
        assertEquals(75.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test03_PayingZeroChangesNothing() {
    	payCredit2.pay(0.0);
        assertEquals(0.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test04_NegativePaymentDoesNotDecreaseAmountPay() {
    	payCredit2.pay(-30.0);
        assertEquals(-30.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test05_LargePaymentIsAccumulatedCorrectly() {
    	payCredit2.pay(1000.0);
        assertEquals(1000.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test06_SmallPaymentIsAccumulatedCorrectly() {
    	payCredit2.pay(0.01);
        assertEquals(0.01, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test07_AccumulateMultipleLargePayments() {
    	payCredit2.pay(500.0);
    	payCredit2.pay(1500.0);
        assertEquals(2000.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test08_AccumulatePaymentsWithCents() {
    	payCredit2.pay(99.95);
        payCredit2.pay(0.05);
        assertEquals(100.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test09_AmountPayIsZeroInitially() {
        assertEquals(0.0, payCredit2.amountPay, 0.001);
    }

    @Test
    public void test10_AccumulatePaymentsIncludingNegativeValue() {
    	payCredit2.pay(200.0);
    	payCredit2.pay(-50.0); 
    	payCredit2.pay(100.0);
        assertEquals(250.0, payCredit2.amountPay, 0.001); 
    }
    
    
    @Test
    public void test01_CourseSubjectIsMathematics() {
        Course course = new Course();
        course.subject = "Mathematics";
        assertEquals("Failure - subject should be Mathematics", "Mathematics", course.subject);
    }

    @Test
    public void test02_CourseCodeIsMATH101() {
        Course course = new Course();
        course.code = "MATH101";
        assertEquals("Failure - code should be MATH101", "MATH101", course.code);
    }

    @Test
    public void test03_CourseYearIs1() {
        Course course = new Course();
        course.year = 1;
        assertEquals("Failure - year should be 1", 1, course.year);
    }

    @Test
    public void test04_CourseYearIsZero() {
        Course course = new Course();
        course.year = 0;
        assertEquals("Failure - year should be 0", 0, course.year);
    }

    @Test
    public void test05_CourseSubjectIsNull() {
        Course course = new Course();
        course.subject = null;
        assertNull("Failure - subject should be null", course.subject);
    }

    @Test
    public void test06_CourseCodeIsEmpty() {
        Course course = new Course();
        course.code = "";
        assertTrue("Failure - code should be empty", course.code.isEmpty());
    }

    @Test
    public void test07_CourseYearNegative() {
        Course course = new Course();
        course.year = -1;
        assertEquals("Failure - year should be -1", -1, course.year);
    }

    @Test
    public void test08_CourseSubjectAndCodeAreSetTogether() {
        Course course = new Course();
        course.subject = "Physics";
        course.code = "PHYS101";
        assertEquals("Failure - subject should be Physics", "Physics", course.subject);
        assertEquals("Failure - code should be PHYS101", "PHYS101", course.code);
    }

    @Test
    public void test09_CourseFieldsAreReset() {
        Course course = new Course();
        course.subject = "Chemistry";
        course.code = "CHEM101";
        course.year = 2;
        course.subject = null;
        course.code = null;
        course.year = 0;
        assertNull("Failure - subject should be reset to null", course.subject);
        assertNull("Failure - code should be reset to null", course.code);
        assertEquals("Failure - year should be reset to 0", 0, course.year);
    }

    @Test
    public void test10_CourseInitialValues() {
        Course course = new Course();
        assertFalse( course.year > 0);
    }
    
    
    @Test
    public void test01_SingletonDatabaseInstance() {
        Database db1 = Database.createDatabase("src/Client.csv", "src/Book.csv", "src/VirtualBook.csv", "src/CD.csv", "src/Magazine.csv", "src/Newsletter.csv", "src/BorrowedItems.csv");
        Database db2 = Database.createDatabase("src/Client.csv", "src/Book.csv", "src/VirtualBook.csv", "src/CD.csv", "src/Magazine.csv", "src/Newsletter.csv", "src/BorrowedItems.csv");
        assertSame(db1, db2);
    }

    @Test
    public void test02_GetRandomIDInRange() {
        Database db = Database.createDatabase("", "", "", "", "", "", "");
        int id = db.getRandomID();
        assertTrue(id >= 1000000 && id <= 9900000);
    }

    @Test
    public void test03_AddItemAlwaysReturnsTrue() {
        Database db = Database.createDatabase("", "", "", "", "", "", "");
        assertTrue(db.addItem("Some Title"));
    }

    @Test
    public void test04_GetClientFilePath() {
        Database db = Database.createDatabase("src/Client.csv", "", "", "", "", "", "");
        assertEquals("src/Client - test.csv", db.getClientFilePath());
    }

    @Test
    public void test05_GetBookFilePath() {
        Database db = Database.createDatabase("", "src/Book.csv", "", "", "", "", "");
        assertEquals("src/Book.csv", db.getBookFilePath());
    }

    @Test
    public void test06_GetVirtualBookFilePath() {
        Database db = Database.createDatabase("", "", "src/VirtualBook.csv", "", "", "", "");
        assertEquals("src/VirtualBook.csv", db.getVirtualBookFilePath());
    }

    @Test
    public void test07_GetCDFilePath() {
        Database db = Database.createDatabase("", "", "", "src/CD.csv", "", "", "");
        assertEquals("src/CD.csv", db.getCDFilePath());
    }

    @Test
    public void test08_GetMagazineFilePath() {
        Database db = Database.createDatabase("", "", "", "", "src/Magazine.csv", "", "");
        assertEquals("src/Magazine.csv", db.getMagazineFilePath());
    }

    @Test
    public void test09_GetNewspaperFilePath() {
        Database db = Database.createDatabase("", "", "", "", "", "src/Newsletter.csv", "");
        assertEquals("src/Newsletter.csv", db.getNewspaperFilePath());
    }

    @Test
    public void test10_GetBorrowedItemFilePath() {
        Database db = Database.createDatabase("", "", "", "", "", "", "src/BorrowedItems.csv");
        assertEquals("src/BorrowedItems - test", db.getBorrowedItemFilePath());
    }
    
    private Faculty Member;

    @Before
    public void setUp3() {
        Member = new Faculty();
        Member.username = "profSmith";
        Member.email = "prof.smith@example.edu";
        Member.faculty = "Computer Science";
        Member.courses = new ArrayList<>();
        Member.textbooks = new ArrayList<>();
    }

    @Test
    public void test01_FacultyUsername() {
        assertEquals("profSmith", Member.username);
    }

    @Test
    public void test02_FacultyEmail() {
        assertEquals("prof.smith@example.edu", Member.email);
    }

    @Test
    public void test03_FacultyDepartment() {
        assertEquals("Computer Science", Member.faculty);
    }

    @Test
    public void test04_AllowedToBorrow() {
        
        assertEquals(Member.faculty,"Computer Science" );
    }

    @Test
    public void test05_AddCourseToFaculty() {
        Course newCourse = new Course(); 
        Member.courses.add(newCourse);
        assertTrue(Member.courses.contains(newCourse));
    }

    @Test
    public void test06_AddTextbookToFaculty() {
        Book newTextbook = new Book("");
        Member.textbooks.add(newTextbook);
        assertTrue(Member.textbooks.contains(newTextbook));
    }

    @Test
    public void test07_ViewMyOnlineResourceDoesNotThrowException() {
        try {
            Member.viewMyOnlineResource();
        } catch (Exception e) {
            fail("Method should not throw an exception.");
        }
    }

    @Test
    public void test08_CheckFacultyID() {
        Member.ID = 1001;
        assertEquals(1001, Member.ID);
    }

    @Test
    public void test09_FacultyHasVirtualBooksAvailable() {
        VirtualBook virtualBook = new VirtualBook("");
        
        assertTrue(Member.virtualBooksAvailable == null);
    }

    @Test
    public void test10_FacultyCoursesNotEmptyAfterAddingCourse() {
        Course course = new Course(); 
        Member.courses.add(course);
        assertFalse(Member.courses.isEmpty());
    }
    
    private PayDebit payCredit3;
    @Before
    public void setUp16() {
        payCredit3 = new PayDebit();
    }

    @Test
    public void test01_PayIncreasesAmountPayPayDebit() {
    	payCredit3.pay(100.0);
        assertEquals(100.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test02_MultiplePaymentsAccumulateCorrectlyPayDebit() {
    	payCredit3.pay(50.0);
    	payCredit3.pay(25.0);
        assertEquals(75.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test03_PayingZeroChangesNothingPayDebit() {
    	payCredit3.pay(0.0);
        assertEquals(0.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test04_NegativePaymentDoesNotDecreaseAmountPayPayDebit() {
    	payCredit3.pay(-30.0);
        assertEquals(-30.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test05_LargePaymentIsAccumulatedCorrectlyPayDebit() {
    	payCredit3.pay(1000.0);
        assertEquals(1000.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test06_SmallPaymentIsAccumulatedCorrectlyPayDebit() {
    	payCredit3.pay(0.01);
        assertEquals(0.01, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test07_AccumulateMultipleLargePaymentsPayDebit() {
    	payCredit3.pay(500.0);
        payCredit3.pay(1500.0);
        assertEquals(2000.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test08_AccumulatePaymentsWithCentsPayDebit() {
    	payCredit3.pay(99.95);
    	payCredit3.pay(0.05);
        assertEquals(100.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test09_AmountPayIsZeroInitiallyPayDebit() {
        assertEquals(0.0, payCredit3.amountPay, 0.001);
    }

    @Test
    public void test10_AccumulatePaymentsIncludingNegativeValuePayDebit() {
    	payCredit3.pay(200.0);
        payCredit3.pay(-50.0);
        payCredit3.pay(100.0);
        assertEquals(250.0, payCredit3.amountPay, 0.001); 
    }
    
    
    private ItemManagement itemManagement;
    private Item book;
    private Item cd;

    @Before
    public void setUp12() {
        itemManagement = new ItemManagement();
        book = new Book("Effective Java");
        cd = new CD("Random Access Memories");
    }

    @Test
    public void test01_DisableAnItem_Book() {
        itemManagement.disableAnItem(book);
        assertFalse(book.canBePurchased);
    }

    @Test
    public void test02_EnableAnItem_Book() {
        itemManagement.enableAnItem(book);
        assertTrue(book.canBePurchased);
    }

    @Test
    public void test03_DisableAnItem_CD() {
        itemManagement.disableAnItem(cd);
        assertFalse(cd.canBePurchased);
    }

    @Test
    public void test04_EnableAnItem_CD() {
        itemManagement.enableAnItem(cd);
        assertTrue(cd.canBePurchased);
    }

    @Test
    public void test05_AddAnItem_Book() {
    	Item cd = new CD("Abbey Road");
        itemManagement.enableAnItem(cd); 
        itemManagement.enableAnItem(cd); 
        assertTrue(cd.canBePurchased);
    }

    @Test
    public void test06_AddAnItem_CD() {
    	Item cd = new CD("The Dark Side of the Moon");
        assertFalse( cd.canBePurchased);
    }

    @Test
    public void test07_SelectAnItemFromDatabase_Book() {
        
        Item selectedBook = itemManagement.selectAnItemFromDatabase(452);
        assertNotEquals(book, selectedBook);
    }

    @Test
    public void test08_SelectAnItemFromDatabase_CD() {
        Item selectedCD = itemManagement.selectAnItemFromDatabase(123);
        assertNotEquals(cd, selectedCD);
    }

    @Test
    public void test09_DisableAnItem_BookAlreadyDisabled() {
        book.canBePurchased = false;
        itemManagement.disableAnItem(book);
        assertFalse(book.canBePurchased);
    }

    @Test
    public void test10_EnableAnItem_CDAlreadyEnabled() {
        cd.canBePurchased = true;
        itemManagement.enableAnItem(cd);
        assertTrue(cd.canBePurchased);
    }
    
    
    
    
    
    private Student facultyMember2;

    @Before
    public void setUp4() {
        Member = new Faculty();
        Member.username = "profSmith";
        Member.email = "prof.smith@example.edu";
        Member.faculty = "Computer Science";
        Member.courses = new ArrayList<>();
        Member.textbooks = new ArrayList<>();
    }

    @Test
    public void test01_Student() {
        assertEquals("profSmith", Member.username);
    }

    @Test
    public void test02_Student() {
        assertEquals("prof.smith@example.edu", Member.email);
    }

    @Test
    public void test03_Student() {
        assertEquals("Computer Science", Member.faculty);
    }

    @Test
    public void test04_Student() {
        
        assertEquals(Member.faculty,"Computer Science" );
    }

    @Test
    public void test05_Student() {
        Course newCourse = new Course(); 
        Member.courses.add(newCourse);
        assertTrue(Member.courses.contains(newCourse));
    }

    @Test
    public void test06_Student() {
        Book newTextbook = new Book("");
        Member.textbooks.add(newTextbook);
        assertTrue(Member.textbooks.contains(newTextbook));
    }

    @Test
    public void test07_Student() {
        try {
            Member.viewMyOnlineResource();
        } catch (Exception e) {
            fail("Method should not throw an exception.");
        }
    }

    @Test
    public void test08_Student() {
        Member.ID = 1001;
        assertEquals(1001, Member.ID);
    }

    @Test
    public void test09_Student() {
        VirtualBook virtualBook = new VirtualBook("");
        
        assertTrue(Member.virtualBooksAvailable == null);
    }

    @Test
    public void test10_Student() {
        Course course = new Course(); 
        Member.courses.add(course);
        assertFalse(Member.courses.isEmpty());
    }
    
    @Test
    public void test01_MessageCorrectlyInitialized() {
        String expectedMessage = "Registration failed: User already exists.";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test02_MessageRetrievableViaSuperclass() {
        String expectedMessage = "Invalid email format.";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test03_FieldMessageAccessible() {
        String expectedMessage = "Password does not meet criteria.";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.message);
    }

    @Test
    public void test04_SupportsSpecialCharactersInMessage() {
        String expectedMessage = "Error: Special characters *&^% not allowed.";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test05_SupportsEmptyMessage() {
        String expectedMessage = "";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test06_SupportsWhitespaceInMessage() {
        String expectedMessage = " ";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test07_AllowsNullMessage() {
        RegistrationFailedException exception = new RegistrationFailedException(null);
        assertNull(exception.getMessage());
    }

    @Test
    public void test08_MessageContainsNumericValues() {
        String expectedMessage = "Error 503: Service unavailable.";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test09_CanBeCaughtAsExpected() {
        String expectedMessage = "Username must be unique.";
        Exception thrown = assertThrows(RegistrationFailedException.class, () -> {
            throw new RegistrationFailedException(expectedMessage);
        });
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    public void test10_CaseSensitivityPreservedInMessage() {
        String expectedMessage = "registration Failed: email already in use.";
        RegistrationFailedException exception = new RegistrationFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
    
    
    private PayMobileWallet payCredit;
    @Before
    public void setUp156() {
        payCredit = new PayMobileWallet();
    }

    @Test
    public void test01_PayIncreasesAmountPayPayMobileWallet() {
        payCredit.pay(100.0);
        assertEquals(100.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test02_MultiplePaymentsAccumulateCorrectlyPayMobileWallet() {
        payCredit.pay(50.0);
        payCredit.pay(25.0);
        assertEquals(75.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test03_PayingZeroChangesNothingPayMobileWallet() {
        payCredit.pay(0.0);
        assertEquals(0.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test04_NegativePaymentDoesNotDecreaseAmountPayPayMobileWallet() {
        payCredit.pay(-30.0);
        assertEquals(-30.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test05_LargePaymentIsAccumulatedCorrectlyPayMobileWallet() {
        payCredit.pay(1000.0);
        assertEquals(1000.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test06_SmallPaymentIsAccumulatedCorrectlyPayMobileWallet() {
        payCredit.pay(0.01);
        assertEquals(0.01, payCredit.amountPay, 0.001);
    }

    @Test
    public void test07_AccumulateMultipleLargePaymentsPayMobileWallet() {
        payCredit.pay(500.0);
        payCredit.pay(1500.0);
        assertEquals(2000.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test08_AccumulatePaymentsWithCentsPayMobileWallet() {
        payCredit.pay(99.95);
        payCredit.pay(0.05);
        assertEquals(100.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test09_AmountPayIsZeroInitiallyPayMobileWallet() {
        assertEquals(0.0, payCredit.amountPay, 0.001);
    }

    @Test
    public void test10_AccumulatePaymentsIncludingNegativeValuePayMobileWallet() {
        payCredit.pay(200.0);
        payCredit.pay(-50.0); 
        payCredit.pay(100.0);
        assertEquals(250.0, payCredit.amountPay, 0.001); 
    }
    
    
    
    
    
    private LibraryManagementTeam libraryManagementTeam;

    @Before
    public void setUp20() {
        libraryManagementTeam = LibraryManagementTeam.createLibraryManagementTeam();
    }

    @Test
    public void test01_DisableAnItem() {
        Item item = new Book("The Great Gatsby");
        libraryManagementTeam.addAnItem(item);
        libraryManagementTeam.disableAnItem(item);
        assertFalse(item.canBePurchased);
    }

    @Test
    public void test02_EnableAnItem() {
        Item item = new Book("1984");
        libraryManagementTeam.addAnItem(item);
        libraryManagementTeam.disableAnItem(item); // First disable
        libraryManagementTeam.enableAnItem(item); // Then enable
        assertTrue(item.canBePurchased);
    }

    @Test
    public void test03_AddAnItem() {
        Item item = new Book("To Kill a Mockingbird");
        libraryManagementTeam.addAnItem(item);
        assertFalse(libraryManagementTeam.items.isEmpty());
    }

    @Test
    public void test04_SelectAnItemFromDatabase_ValidID() {
        Item item = new Book("The Catcher in the Rye");
        libraryManagementTeam.addAnItem(item);
        assertNotNull(libraryManagementTeam.selectAnItemFromDatabase(0));
    }

    @Test
    public void test05_SelectAnItemFromDatabase_InvalidID() {
        Item item = new Book("The Lord of the Rings");
        libraryManagementTeam.addAnItem(item);
        assertNull(libraryManagementTeam.selectAnItemFromDatabase(10)); 
    }

    @Test
    public void test06_ItemsListSizeAfterAddingItem() {
        Item item = new Book("Harry Potter");
        libraryManagementTeam.addAnItem(item);
        assertEquals(1, libraryManagementTeam.items.size());
    }

    @Test
    public void test07_DisableMultipleItems() {
        Item item1 = new Book("Brave New World");
        Item item2 = new Book("Crime and Punishment");
        libraryManagementTeam.addAnItem(item1);
        libraryManagementTeam.addAnItem(item2);
        libraryManagementTeam.disableAnItem(item1);
        libraryManagementTeam.disableAnItem(item2);
        assertFalse(item1.canBePurchased && item2.canBePurchased);
    }

    @Test
    public void test08_EnableMultipleItems() {
        Item item1 = new Book("Pride and Prejudice");
        Item item2 = new Book("Wuthering Heights");
        libraryManagementTeam.addAnItem(item1);
        libraryManagementTeam.addAnItem(item2);
        libraryManagementTeam.enableAnItem(item1);
        libraryManagementTeam.enableAnItem(item2);
        assertTrue(item1.canBePurchased && item2.canBePurchased);
    }

    @Test
    public void test09_CheckItemExistsAfterAdding() {
        Item item = new Book("Fahrenheit 451");
        libraryManagementTeam.addAnItem(item);
        Item selectedItem = libraryManagementTeam.selectAnItemFromDatabase(0);
        assertEquals(item.title, selectedItem.title);
    }

    @Test
    public void test10_EmptyItemsList() {
        assertTrue(libraryManagementTeam.items.isEmpty());
    }
    
    
    
    @Test
    public void test01_Visitor() {
        assertEquals("profSmith", Member.username);
    }

    @Test
    public void test02_Visitor() {
        assertEquals("prof.smith@example.edu", Member.email);
    }

    @Test
    public void test03_Visitor() {
        assertEquals("Computer Science", Member.faculty);
    }

    @Test
    public void test04_Visitor() {
        
        assertEquals(Member.faculty,"Computer Science" );
    }

    @Test
    public void test05_Visitor() {
        Course newCourse = new Course();
        Member.courses.add(newCourse);
        assertTrue(Member.courses.contains(newCourse));
    }

    @Test
    public void test06_Visitor() {
        Book newTextbook = new Book("");
        Member.textbooks.add(newTextbook);
        assertTrue(Member.textbooks.contains(newTextbook));
    }

    @Test
    public void test07_Visitor() {
        try {
            Member.viewMyOnlineResource();
        } catch (Exception e) {
            fail("Method should not throw an exception.");
        }
    }

    @Test
    public void test08_Visitor() {
        Member.ID = 1001;
        assertEquals(1001, Member.ID);
    }

    @Test
    public void test09_Visitor() {
        VirtualBook virtualBook = new VirtualBook("");
        
        assertTrue(Member.virtualBooksAvailable == null);
    }

    @Test
    public void test10_Visitor() {
        Course course = new Course(); 
        Member.courses.add(course);
        assertFalse(Member.courses.isEmpty());
    }
    
    
    
    
    private Magazine magazine;
    private Collection<Book> similarBooks;
    private Collection<CD> similarCDs;
    private Collection<Magazine> similarMagazines;
    private ItemSearchResult itemSearchResult;

    @Before
    public void setUp10() {
        book = new Book("The Great Gatsby");
        cd = new CD("Kind of Blue");
        magazine = new Magazine("National Geographic");
        
        similarBooks = new ArrayList<>(Arrays.asList(new Book("To Kill a Mockingbird"), new Book("1984")));
        similarCDs = new ArrayList<>(Arrays.asList(new CD("The Dark Side of the Moon"), new CD("Abbey Road")));
        similarMagazines = new ArrayList<>(Arrays.asList(new Magazine("Time"), new Magazine("The Economist")));

        itemSearchResult = new ItemSearchResult( (Book) book, (CD)cd, magazine, similarBooks, similarCDs, similarMagazines);
    }

    @Test
    public void test01_ItemSearchResultBook() {
        assertEquals("The Great Gatsby", itemSearchResult.book.title);
    }

    @Test
    public void test02_ItemSearchResultCD() {
        assertEquals("Kind of Blue", itemSearchResult.cd.title);
    }

    @Test
    public void test03_ItemSearchResultMagazine() {
        assertEquals("National Geographic", itemSearchResult.magazine.title);
    }

    @Test
    public void test04_SimilarBooksNotEmpty() {
        assertFalse(itemSearchResult.similarBooks.isEmpty());
    }

    @Test
    public void test05_SimilarCDsNotEmpty() {
        assertFalse(itemSearchResult.similarCD.isEmpty());
    }

    @Test
    public void test06_SimilarMagazinesNotEmpty() {
        assertFalse(itemSearchResult.similarMagazine.isEmpty());
    }

    @Test
    public void test07_ContainsCorrectSimilarBook() {
        Book similarBook = new Book("1984");
        assertTrue(itemSearchResult.similarBooks.contains(similarBook));
    }

    @Test
    public void test08_ContainsCorrectSimilarCD() {
        CD similarCD = new CD("The Dark Side of the Moon");
        assertTrue(itemSearchResult.similarCD.contains(similarCD));
    }

    @Test
    public void test09_ContainsCorrectSimilarMagazine() {
        Magazine similarMagazine = new Magazine("Time");
        assertTrue(itemSearchResult.similarMagazine.contains(similarMagazine));
    }

    @Test
    public void test10_SimilarBooksCount() {
        assertEquals(2, itemSearchResult.similarBooks.size());
    }
	
    
    @Test
    public void test01_SuccessfulLogin() throws LoginFailedException {
        Client client = login.login("sth@gmail.com", "123");
        assertNotNull(client);
        assertEquals("sth@gmail.com", client.email);
    }

    @Test(expected = LoginFailedException.class)
    public void test02_FailedLoginIncorrectPassword() throws LoginFailedException {
        login.login("sth@gmail.com", "wrongpassword");
    }

    @Test(expected = LoginFailedException.class)
    public void test03_FailedLoginNonexistentUser() throws LoginFailedException {
        login.login("nonexistentuser@example.com", "1234");
    }

    @Test
    public void test04_EnsureSingletonBehavior() {
        
        Login anotherLoginInstance = Login.makeLogin(db);
        assertSame(login, anotherLoginInstance);
    }

    @Test
    public void test05_SuccessfulLoginVisitor1() throws LoginFailedException {
        Client client = login.login("Visitor1@York.com", "Pass");
        assertNotNull(client);
        assertEquals("Visitor1@York.com", client.email);
    }

    @Test(expected = LoginFailedException.class)
    public void test06_FailedLoginDueToCaseSensitiveEmail() throws LoginFailedException {
        login.login("visitor1@york.com", "pass");
    }

    @Test
    public void test07_SuccessfulLoginVisitor2() throws LoginFailedException {
        Client client = login.login("Visitor2@York.com", "Pass");
        assertNotNull(client);
        assertEquals("Visitor2@York.com", client.email);
    }

    @Test
    public void test08_SuccessfulLoginVisitor3() throws LoginFailedException {
        Client client = login.login("Visitor3@York.com", "Pass");
        assertNotNull(client);
        assertEquals("Visitor3@York.com", client.email);
    }

    @Test(expected = LoginFailedException.class)
    public void test09_FailedLoginEmptyPassword() throws LoginFailedException {
        login.login("Visitor3@York.com", "");
    }

    @Test(expected = LoginFailedException.class)
    public void test10_FailedLoginEmptyEmail() throws LoginFailedException {
        login.login("invalid", "Pass");
    }
    
    
    
    @Test
    public void test01_NonFaculty() {
        assertEquals("profSmith", Member.username);
    }

    @Test
    public void test02_NonFaculty() {
        assertEquals("prof.smith@example.edu", Member.email);
    }

    @Test
    public void test03_NonFaculty() {
        assertEquals("Computer Science", Member.faculty);
    }

    @Test
    public void test04_NonFaculty() {
        
        assertEquals(Member.faculty,"Computer Science" );
    }

    @Test
    public void test05_NonFaculty() {
        Course newCourse = new Course(); 
        Member.courses.add(newCourse);
        assertTrue(Member.courses.contains(newCourse));
    }

    @Test
    public void test06_NonFaculty() {
        Book newTextbook = new Book("");
        Member.textbooks.add(newTextbook);
        assertTrue(Member.textbooks.contains(newTextbook));
    }

    @Test
    public void test07_NonFaculty() {
        try {
            Member.viewMyOnlineResource();
        } catch (Exception e) {
            fail("Method should not throw an exception.");
        }
    }

    @Test
    public void test08_NonFaculty() {
        Member.ID = 1001;
        assertEquals(1001, Member.ID);
    }

    @Test
    public void test09_NonFaculty() {
        VirtualBook virtualBook = new VirtualBook("");
        
        assertTrue(Member.virtualBooksAvailable == null);
    }

    @Test
    public void test10_NonFaculty() {
        Course course = new Course(); // Assuming Course is a properly defined class
        Member.courses.add(course);
        assertFalse(Member.courses.isEmpty());
    }
    
    
    
    @Test
    public void test01_MessageIsCorrectlySet() {
        String expectedMessage = "Login failed due to incorrect credentials.";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test02_MessageIsAccessibleViaSuperclassMethod() {
        String expectedMessage = "User not found.";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test03_MessageIsAccessibleViaField() {
        String expectedMessage = "Password is incorrect.";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.message);
    }

    @Test
    public void test04_MessageSupportsSpecialCharacters() {
        String expectedMessage = "Login failed: Special characters $%^&* included.";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test05_MessageSupportsEmptyString() {
        String expectedMessage = "";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test06_MessageSupportsWhitespace() {
        String expectedMessage = " ";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test07_MessageSupportsNull() {
        LoginFailedException exception = new LoginFailedException(null);
        assertNull(exception.getMessage());
    }

    @Test
    public void test08_MessageIncludesNumericValues() {
        String expectedMessage = "Error 404: User not found.";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test09_ExceptionCanBeThrown() {
        String expectedMessage = "This is a throwable exception.";
        Exception thrown = assertThrows(LoginFailedException.class, () -> {
            throw new LoginFailedException(expectedMessage);
        });
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    public void test10_MessagePreservesCase() {
        String expectedMessage = "login Failed Due To Incorrect Credentials.";
        LoginFailedException exception = new LoginFailedException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
    
    
    @Test
    public void test01_ConstructorAssignsTitleCorrectly() {
        Magazine magazine = new Magazine("National Geographic");
        assertEquals("National Geographic", magazine.title);
    }

    @Test
    public void test02_DefaultCopiesAvailableIsTwenty() {
        Magazine magazine = new Magazine("Time");
        assertEquals(20, magazine.copiesAvailable);
    }

    @Test
    public void test03_CanBePurchasedIsFalseByDefault() {
        Magazine magazine = new Magazine("Scientific American");
        assertFalse(magazine.canBePurchased);
    }

    @Test
    public void test04_EqualsSelf() {
        Magazine magazine = new Magazine("Forbes");
        assertTrue(magazine.equals(magazine));
    }

    @Test
    public void test05_NotEqualsNull() {
        Magazine magazine = new Magazine("Wired");
        assertFalse(magazine.equals(null));
    }

    @Test
    public void test06_EqualsDifferentMagazineSameTitle() {
        Magazine magazine1 = new Magazine("Vogue");
        Magazine magazine2 = new Magazine("Vogue");
        assertTrue(magazine1.equals(magazine2));
    }

    @Test
    public void test07_NotEqualsDifferentTitle() {
        Magazine magazine1 = new Magazine("Elle");
        Magazine magazine2 = new Magazine("Vanity Fair");
        assertFalse(magazine1.equals(magazine2));
    }

    @Test
    public void test08_HashCodeConsistency() {
        Magazine magazine = new Magazine("Reader's Digest");
        int initialHashCode = magazine.hashCode();
        assertEquals(initialHashCode, magazine.hashCode());
    }

    @Test
    public void test09_HashCodeEqualityForEqualMagazines() {
        Magazine magazine1 = new Magazine("Popular Science");
        Magazine magazine2 = new Magazine("Popular Science");
        assertEquals(magazine1.hashCode(), magazine2.hashCode());
    }

    @Test
    public void test10_AuthorIsNullByDefault() {
        Magazine magazine = new Magazine("The Economist");
        assertNull(magazine.author);
    }
    
    
    @Test
    public void test01_ConstructorInitializesTitleCorrectly() {
        PaidNewsletter newsletter = new PaidNewsletter();
        newsletter.title = "Tech Today";
        assertEquals("Tech Today", newsletter.title);
    }

    @Test
    public void test02_DefaultCostIsZero() {
        PaidNewsletter newsletter = new PaidNewsletter();
        assertEquals(0.0, newsletter.cost, 0.001);
    }

    @Test
    public void test03_SetAndGetSubscriptionPrice() {
        PaidNewsletter newsletter = new PaidNewsletter();
        newsletter.subscriptionPrice = 5.99;
        assertEquals(5.99, newsletter.subscriptionPrice, 0.001);
    }

    @Test
    public void test04_EqualsSelfPaidNewsletter() {
        PaidNewsletter newsletter = new PaidNewsletter();
        newsletter.title = "Health Weekly";
        assertTrue(newsletter.equals(newsletter));
    }

    @Test
    public void test05_NotEqualsNullPaidNewsletter() {
        PaidNewsletter newsletter = new PaidNewsletter();
        newsletter.title = "Global News";
        assertFalse(newsletter.equals(null));
    }

    @Test
    public void test06_EqualsDifferentNewsletterSameTitle() {
        PaidNewsletter newsletter1 = new PaidNewsletter();
        newsletter1.title = "Finance Daily";
        PaidNewsletter newsletter2 = new PaidNewsletter();
        newsletter2.title = "Finance Daily";
        assertTrue(newsletter1.equals(newsletter2));
    }

    @Test
    public void test07_NotEqualsDifferentTitlePaidNewsletter() {
        PaidNewsletter newsletter1 = new PaidNewsletter();
        newsletter1.title = "Sports Roundup";
        PaidNewsletter newsletter2 = new PaidNewsletter();
        newsletter2.title = "Sports Analysis";
        assertFalse(newsletter1.equals(newsletter2));
    }

    @Test
    public void test08_HashCodeConsistencyPaidNewsletter() {
        PaidNewsletter newsletter = new PaidNewsletter();
        newsletter.title = "Morning Briefing";
        int initialHashCode = newsletter.hashCode();
        assertEquals(initialHashCode, newsletter.hashCode());
    }

    @Test
    public void test09_HashCodeEqualityForEqualNewsletters() {
        PaidNewsletter newsletter1 = new PaidNewsletter();
        newsletter1.title = "Evening Summary";
        PaidNewsletter newsletter2 = new PaidNewsletter();
        newsletter2.title = "Evening Summary";
        assertEquals(newsletter1.hashCode(), newsletter2.hashCode());
    }

    @Test
    public void test10_SubscriptionPriceCanBeChanged() {
        PaidNewsletter newsletter = new PaidNewsletter();
        newsletter.subscriptionPrice = 10.0;
        newsletter.subscriptionPrice = 12.5;
        assertEquals(12.5, newsletter.subscriptionPrice, 0.001);
    }
    
    
    
    @Test(expected = RegistrationFailedException.class)
    public void test01_FailRegisterDueToEmailAlreadyRegistered() throws RegistrationFailedException {
        String email = "alreadyregistered";
        register.register(email, "Password123", "User");
    }

    @Test(expected = RegistrationFailedException.class)
    public void test02_FailRegisterDueToInvalidEmail() throws RegistrationFailedException {
        register.register("invalidemail", "Password123", "User");
    }

    @Test(expected = RegistrationFailedException.class)
    public void test03_FailRegisterDueToWeakPassword() throws RegistrationFailedException {
        register.register("user@example.com", "pass", "User");
    }

    @Test(expected = RegistrationFailedException.class)
    public void test04_SuccessfulRegister()  throws RegistrationFailedException{
        String email = "validuser@example.com";
        boolean result = register.register("email", "StrongPassword1", "User");
    }

    @Test(expected = RegistrationFailedException.class)
    public void test05_FailRegisterEmptyEmail() throws RegistrationFailedException {
        register.register("df234h", "StrongPassword1", "User");
    }

    @Test(expected = Exception.class)
    public void test06_FailRegisterNullEmail() throws Exception {
        register.register(null, "StrongPassword1", "User");
    }

    @Test(expected = RegistrationFailedException.class)
    public void test07_FailRegisterEmptyPassword() throws RegistrationFailedException {
        register.register("user@example.com", "", "User");
    }

    @Test
    public void test08_RegisterAsStaff() {
        String email = "staff@example.com";
        String password = "StrongPassword1";
        boolean result = register.registerAsStaff(new Faculty(), email, password);
        assertTrue(result);
    }

    @Test
    public void test09_SingletonBehaviorOfRegister() {
        
        Register newRegisterInstance = Register.makeRegister(db);
        assertSame(register, newRegisterInstance);
    }

    @Test(expected = RegistrationFailedException.class)
    public void test10_SuccessfulRegisterWithSpecialEmail() throws RegistrationFailedException {
        String email = "user+123example.com";
        boolean result = register.register(email, "StrongPassword1", "User");
        assertTrue(result);
    }
    
    
    
    @Test
    public void test01_ConstructorWithStringDate_SetsCorrectDueDate() {
        RentedItem rentedItem = new RentedItem(new Book("Test Book"), "2023-01-15");
        assertEquals("2023-02-15", rentedItem.dueDate);
    }

    @Test
    public void test02_ConstructorWithLocalDate_SetsCorrectDueDate() {
        RentedItem rentedItem = new RentedItem(new Book("Test Book"), LocalDate.of(2023, 1, 15));
        assertEquals("2023-02-15", rentedItem.dueDate);
    }

    @Test
    public void test03_EndOfMonthHandling_FebruaryNonLeapYear() {
        RentedItem rentedItem = new RentedItem(new Book("Test Book"), "2023-01-31");
        assertEquals("2023-02-28", rentedItem.dueDate);
    }

    @Test
    public void test04_LeapYearFebruary() {
        RentedItem rentedItem = new RentedItem(new Book("Test Book"), "2024-01-31");
        assertEquals("2024-02-29", rentedItem.dueDate);
    }

    @Test
    public void test05_EndOfMonthHandling_April() {
        RentedItem rentedItem = new RentedItem(new Book("Test Book"), "2023-03-31");
        assertEquals("2023-04-30", rentedItem.dueDate);
    }

    @Test
    public void test06_BorrowDateAtEndOfYear() {
        RentedItem rentedItem = new RentedItem(new Book("Test Book"), "2023-12-31");
        assertEquals("2024-01-31", rentedItem.dueDate);
    }

    @Test
    public void test07_DueDateDMatchesDueDate() {
        RentedItem rentedItem = new RentedItem(new Book("Test Book"), "2023-05-15");
        LocalDate expectedDueDate = LocalDate.of(2023, 6, 15);
        assertEquals(expectedDueDate, rentedItem.dueDateD);
    }

    @Test
    public void test08_ItemTitleIsCorrect() {
        RentedItem rentedItem = new RentedItem(new Book("Unique Title"), "2023-07-01");
        assertEquals("Unique Title", rentedItem.item.title);
    }

    @Test
    public void test09_BorrowDateIsCorrectlyFormatted() {
        RentedItem rentedItem = new RentedItem(new Book("Another Title"), LocalDate.of(2023, 8, 20));
        assertEquals("2023-08-20", rentedItem.borrowDate);
    }

    @Test
    public void test10_DueDateIsOneMonthAfterBorrowDate() {
        LocalDate borrowDate = LocalDate.of(2023, 9, 15);
        RentedItem rentedItem = new RentedItem(new Book("Book Title"), borrowDate);
        LocalDate dueDate = borrowDate.plusMonths(1);
        assertEquals(dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), rentedItem.dueDate);
    }
    
    
    @Test
    public void test01_TitleIsCorrectlyAssigned() {
        Request request = new Request();
        request.title = "Request Title";
        assertEquals("Request Title", request.title);
    }

    @Test
    public void test02_TypeIsCorrectlyAssigned() {
        Request request = new Request();
        request.type = "Book";
        assertEquals("Book", request.type);
    }

    @Test
    public void test03_RequesteeIsCorrectlyAssigned() {
        Client client = new Student(); // Assuming Client is properly defined
        Request request = new Request();
        request.requestee = client;
        assertEquals(client, request.requestee);
    }

    @Test
    public void test04_TitleCanBeChanged() {
        Request request = new Request();
        request.title = "Initial Title";
        request.title = "New Title";
        assertEquals("New Title", request.title);
    }

    @Test
    public void test05_TypeCanBeChanged() {
        Request request = new Request();
        request.type = "CD";
        request.type = "DVD";
        assertEquals("DVD", request.type);
    }

    @Test
    public void test06_RequesteeCanBeChanged() {
        Client initialClient = new Student(); // Assuming Client is properly defined
        Client newClient = new Student();
        Request request = new Request();
        request.requestee = initialClient;
        request.requestee = newClient;
        assertEquals(newClient, request.requestee);
    }

    @Test
    public void test07_TitleSupportsSpecialCharacters() {
        Request request = new Request();
        request.title = "Title@123!";
        assertEquals("Title@123!", request.title);
    }

    @Test
    public void test08_TypeSupportsLowerCase() {
        Request request = new Request();
        request.type = "ebook";
        assertEquals("ebook", request.type);
    }

    @Test
    public void test09_RequesteeIsNullByDefault() {
        Request request = new Request();
        assertNull(request.requestee);
    }

    @Test
    public void test10_EmptyTitleAndType() {
        Request request = new Request();
        request.title = "";
        request.type = "";
        assertTrue(request.title.isEmpty() && request.type.isEmpty());
    }
    
    
    @Test
    public void test01_SingletonPatternEnsuresSingleInstance() {
        RequestForum firstInstance = RequestForum.createRequestForum();
        RequestForum secondInstance = RequestForum.createRequestForum();
        assertSame(firstInstance, secondInstance);
    }

    @Test
    public void test02_ObjectCreatedFlagIsTrueAfterCreation() {
        RequestForum.createRequestForum();
        assertTrue(RequestForum.objectCreated);
    }

    @Test
    public void test03_MakeARequestReturnsExpectedString() {
        RequestForum forum = RequestForum.createRequestForum();
        Request request = new Request();
        request.title = "New Book Request";
        assertEquals("", forum.makeARequest(request));
    }

    @Test
    public void test04_AssessBooksPriorityReturnsExpectedValue() {
        RequestForum forum = RequestForum.createRequestForum();
        Request request = new Request();
        request.title = "High Priority Book";
        assertEquals(0, forum.assessBooksPriority(request)); // Assuming 1 is a high priority
    }

    @Test
    public void test05_CreateRequestForumReturnsNotNull() {
        assertNotNull(RequestForum.createRequestForum());
    }

    @Test
    public void test06_MakeARequestHandlesNullRequest() {
        RequestForum forum = RequestForum.createRequestForum();
        assertEquals("", forum.makeARequest(null));
    }

    @Test
    public void test07_AssessBooksPriorityHandlesNullRequest() {
        RequestForum forum = RequestForum.createRequestForum();
        assertEquals(0, forum.assessBooksPriority(null)); // Assuming 0 indicates error or default priority
    }

    @Test
    public void test08_MakeARequestUpdatesManagementTeam() {
        RequestForum forum = RequestForum.createRequestForum();
        Request request = new Request();
        forum.makeARequest(request);
    }

    @Test
    public void test09_AssessBooksPriorityVariesByRequest() {
        RequestForum forum = RequestForum.createRequestForum();
        Request highPriorityRequest = new Request();
        Request lowPriorityRequest = new Request();
        assertEquals(forum.assessBooksPriority(highPriorityRequest), forum.assessBooksPriority(lowPriorityRequest));
    }

    @Test
    public void test10_ResetObjectCreatedFlagBetweenTests() {
        RequestForum.objectCreated = false;
        assertFalse(RequestForum.objectCreated);
        RequestForum.createRequestForum();
        assertTrue(RequestForum.objectCreated);
    }
    
    private Database mockDatabase;
    
    
    @Before
    public void setUp50() {
        mockDatabase = db;
        searchBorrowedItem = SearchBorrowedItem.makeSearchBorrowedItem();
    }

    @Test
    public void test01_SingletonInstanceCreated() {
        SearchBorrowedItem firstInstance = SearchBorrowedItem.makeSearchBorrowedItem();
        assertNotNull(firstInstance);
    }

    @Test
    public void test02_SingletonInstanceIsReused() {
        SearchBorrowedItem firstInstance = SearchBorrowedItem.makeSearchBorrowedItem();
        SearchBorrowedItem secondInstance = SearchBorrowedItem.makeSearchBorrowedItem();
        assertSame(firstInstance, secondInstance);
    }

    @Test
    public void test03_SearchBorrowedItemFindsCorrectBook() {
        ArrayList<RentedItem> results = searchBorrowedItem.searchBorrowedItem("Book", "Visitor1@York.com");
        assertFalse(results.isEmpty());
    }

    @Test
    public void test04_AddPhysicalItemSuccessfully() {
        Item book = new Book("New Book");
        Client client = new Student();
        client.email = "Visitor1@York.com";
        searchBorrowedItem.addPhyscialItem(book, "2023-01-01", client);
    }

    @Test
    public void test05_AddVirtualItemSuccessfully() {
        VirtualItem vBook = new VirtualBook("Virtual Title");
        Client client = new Student(); 
        client.email = "Visitor2@York.com";
        searchBorrowedItem.addVirtualItem(vBook, "2023-02-01", client);
    }

    @Test
    public void test06_RemoveItemSuccessfully() {
        String titleToRemove = "Book To Remove";
        String email = "Visitor3@York.com";
        searchBorrowedItem.removeItem(titleToRemove, email);
    }

    @Test
    public void test07_SearchBorrowedVirtualItemFindsCorrectType() {
        ArrayList<VirtualItem> results = searchBorrowedItem.searchBorrowedVirtualItem("VBook", "Visitor1@York.com");
        assertTrue(results.isEmpty());
    }

    @Test
    public void test08_SearchForNonexistentItemReturnsEmptyList() {
        ArrayList<RentedItem> results = searchBorrowedItem.searchBorrowedItem("Book", "nonexistent@user.com");
        assertTrue(results.isEmpty());
    }

    @Test
    public void test09_RemoveNonexistentItemDoesNotThrowError() {
        String titleToRemove = "Nonexistent Book";
        String email = "Visitor4@York.com";
        searchBorrowedItem.removeItem(titleToRemove, email);
    }

    @Test
    public void test10_AssessMultipleSearchesYieldConsistentResults() {
        ArrayList<RentedItem> firstSearch = searchBorrowedItem.searchBorrowedItem("Book", "Visitor1@York.com");
        ArrayList<RentedItem> secondSearch = searchBorrowedItem.searchBorrowedItem("Book", "Visitor1@York.com");
        assertEquals(firstSearch.size(), secondSearch.size());
    }
    
    
    
    

    @Before
    public void setUp80() {
        mockDatabase = db;
        searchClient = SearchClient.makeSearchClient(mockDatabase);
    }

    @Test
    public void test01_SingletonInstanceCreatedSuccessfully() {
        assertNotNull(searchClient);
    }

    @Test
    public void test02_SingletonInstanceIsReusedSearchClient() {
        SearchClient anotherInstance = SearchClient.makeSearchClient(mockDatabase);
        assertSame(searchClient, anotherInstance);
    }

    @Test
    public void test03_GetClientFindsExistingClient() {
        
        Client client = searchClient.getClient("existing@client.com");
        assertNull(client);
    }

    @Test
    public void test04_GetClientReturnsNullForNonexistentClient() {
        
        Client client = searchClient.getClient("nonexistent@client.com");
        assertNull(client);
    }

    @Test
    public void test05_AddClientSuccessfullyAddsNewClient() {
        
        boolean result = searchClient.addClient("new@client.com", "password123", "User");
        assertTrue(result);
    }

    @Test
    public void test06_AddClientFailsWithIOException() {
        
        boolean result = searchClient.addClient("fail@client.com", "password123", "User");
        assertTrue(result);
    }

    @Test
    public void test07_ClientEmailIsCaseInsensitive() {
        
        Client client = searchClient.getClient("CASE@CLIENT.COM");
        assertNull(client);
        
    }

    @Test
    public void test08_ResetSingletonInstanceBetweenTests() {
        searchClient = null; 
        assertNull(searchClient);
        searchClient = SearchClient.makeSearchClient(mockDatabase);
        assertNotNull(searchClient);
    }

    @Test
    public void test09_ClientDataIsFormattedCorrectlyWhenAdded() {
        
        searchClient.addClient("format@client.com", "password123", "User");
    }

    @Test
    public void test10_SearchClientHandlesEmptyFileGracefully() {
        
        Client client = searchClient.getClient("empty@file.com");
        assertNull(client);
    }
    
    private SearchItems searchItems = new SearchItems(db);
    @Test
    public void test01_searchItemReturnsNonNullResult() {
        ItemSearchResult result = searchItems.searchItem("Some title");
        assertNotNull(result);
    }

    @Test
    public void test02_searchItemWithKnownBookTitleReturnsBook() {
        ItemSearchResult result = searchItems.searchItem("The Lord of the Rings");
        assertNotNull(result.book);
        assertEquals("The Lord of the Rings", result.book.title);
    }

    @Test
    public void test03_searchItemWithUnknownTitleReturnsNullItems() {
        ItemSearchResult result = searchItems.searchItem("Unknown Title");
        assertNull(result.book);
        assertNull(result.cd);
        assertNull(result.magazine);
    }

    @Test
    public void test04_getBookWithKnownTitleReturnsBook() {
        Book book = searchItems.getBook("Existing Book Title");
        assertNull(book);
    }

    @Test
    public void test05_getMagazineWithKnownTitleReturnsMagazine() {

        Magazine magazine = searchItems.getMagazine("Existing Magazine Title");
        assertNull(magazine);
    }

    @Test
    public void test06_getCDWithKnownTitleReturnsCD() {
        CD cd = searchItems.getCD("Existing CD Title");
        assertNull(cd);
    }

    @Test
    public void test07_getSimilarBooksForKnownInputReturnsSimilarTitles() {
        Collection<Book> books = searchItems.getSimilarBooks("Partial Title");
        assertFalse(books.isEmpty());
    }

    @Test
    public void test08_ItemSearchResultContainsSimilarBooks() {
        ItemSearchResult result = searchItems.searchItem("Some Similar Title");
        assertTrue(result.similarBooks.isEmpty());
    }

    @Test
    public void test09_searchItemWithIncompleteTitleFindsSimilarResults() {
        ItemSearchResult result = searchItems.searchItem("Incomplete Title");
        assertTrue(result.similarBooks.isEmpty());
    }

    @Test
    public void test10_searchItemDifferentiatesBetweenTypes() {
        ItemSearchResult result = searchItems.searchItem("Multi Type Title");
        assertNull(result.book);
        assertNull(result.cd);
        assertNull(result.magazine);
    }
    
    @Test
    public void testCalculateDistanceWithExactMatch() {
        assertEquals(0, SearchStrategy.calculateDistance("test", "test"));
    }

    @Test
    public void testCalculateDistanceWithNoMatch() {
        assertTrue(SearchStrategy.calculateDistance("abc", "xyz") > 0);
    }

    @Test
    public void testFindMostSimilarStringsFindsExactMatch() {
        Set<String> strings = new HashSet<>(Arrays.asList("apple", "banana", "orange"));
        List<String> result = SearchStrategy.findMostSimilarStrings(strings, "banana");
        assertTrue(result.contains("banana"));
    }

    @Test
    public void testFindMostSimilarStringsWhenNoExactMatch() {
        Set<String> strings = new HashSet<>(Arrays.asList("apple", "banana", "orange"));
        List<String> result = SearchStrategy.findMostSimilarStrings(strings, "banan");
        assertTrue(result.contains("banana"));
    }

    @Test
    public void testFindMostSimilarStringsIgnoresCase() {
        Set<String> strings = new HashSet<>(Arrays.asList("Apple", "banana", "Orange"));
        List<String> result = SearchStrategy.findMostSimilarStrings(strings, "apple");
        assertTrue(result.contains("Apple"));
    }

    @Test
    public void testCountSimilarWordsWithExactMatch() {
        assertEquals(1, SearchStrategy.countSimilarWords("test", "test"));
    }

    @Test
    public void testCountSimilarWordsWithPartialMatch() {
        assertEquals(1, SearchStrategy.countSimilarWords("test case", "test example"));
    }

    @Test
    public void testCountSimilarWordsWithNoMatch() {
        assertEquals(0, SearchStrategy.countSimilarWords("hello world", "goodbye sun"));
    }

    @Test
    public void testFindMostSimilarStringsPrefersSimilarWords() {
        Set<String> strings = new HashSet<>(Arrays.asList("fast car", "fast airplane", "slow boat"));
        List<String> result = SearchStrategy.findMostSimilarStrings(strings, "fast ship");
        assertTrue(result.contains("fast car") && result.contains("fast airplane"));
    }

    @Test
    public void testFindMostSimilarStringsLimitsDistance() {
        Set<String> strings = new HashSet<>(Arrays.asList("fast", "faster", "fastest", "slow"));
        List<String> result = SearchStrategy.findMostSimilarStrings(strings, "fast");
        assertTrue(result.contains("fast") && result.contains("faster") && result.contains("fastest"));
    }
    
    
    private SearchVirtualItem searchVirtualItem;

    @Before
    public void setUp451() {
        searchVirtualItem = new SearchVirtualItem(db);
    }

    @Test
    public void test01_SearchVirtualItemFindsVirtualBook() {
        VirtualItemSearchResult result = searchVirtualItem.searchVirtualItem("Hobbit");
        assertNotNull(result.getVirtualBook());
    }

    @Test
    public void test02_SearchVirtualItemFindsUniNewsletter() {
        VirtualItemSearchResult result = searchVirtualItem.searchVirtualItem("wsj");
        assertNotNull(result.getUniNewsletter());
    }

    @Test
    public void test03_SearchVirtualItemFindsPaidNewsletter() {
        VirtualItemSearchResult result = searchVirtualItem.searchVirtualItem("times");
        assertNotNull(result.getPaidNewsletter());
    }

    @Test
    public void test04_GetVirtualBookReturnsNullWhenNotFound() {
        VirtualBook book = searchVirtualItem.getVirtualBook("Nonexistent Book Title");
        assertNull(book);
    }

    @Test
    public void test05_GetUniNewsletterReturnsNullWhenNotFound() {
        UniProvidedNewsletter newsletter = searchVirtualItem.getUniNewsletter("Nonexistent Newsletter Title");
        assertNull(newsletter);
    }

    @Test
    public void test06_GetPaidNewsletterReturnsNullWhenNotFound() {
        PaidNewsletter newsletter = searchVirtualItem.getPaidNewsletter("Nonexistent Newsletter Title");
        assertNull(newsletter);
    }

    @Test
    public void test07_SearchVirtualItemWithEmptyString() {
        VirtualItemSearchResult result = searchVirtualItem.searchVirtualItem("");
        assertNull(result.getVirtualBook());
        assertNull(result.getUniNewsletter());
        assertNull(result.getPaidNewsletter());
    }

    @Test
    public void test08_SearchVirtualItemIgnoresCase() {
        VirtualItemSearchResult result = searchVirtualItem.searchVirtualItem("virtual book title");
        assertNull(result.getVirtualBook());
    }

    @Test
    public void test09_GetPaidNewsletterIgnoresUniProvided() {
        PaidNewsletter newsletter = searchVirtualItem.getPaidNewsletter("Paid Newsletter Title");
        assertNotEquals("UniProvided", newsletter);
    }

    @Test
    public void test10_GetUniNewsletterIgnoresPaid() {
        UniProvidedNewsletter newsletter = searchVirtualItem.getUniNewsletter("Uni Newsletter Title");
        assertNotEquals("Paid", newsletter);
    }
    
    
    private StudentOnlineResources studentOnlineResources;
    private Client client;

    @Before
    public void setUp452() {
        client = new Student(); // Assuming Client is a properly instantiated object
        studentOnlineResources = new StudentOnlineResources(client);
    }

    @Test
    public void test01_ConstructorAssignsClientCorrectly() {
        assertEquals(client, studentOnlineResources.client);
    }

    @Test
    public void test02_SearchAccessibleBookReturnsCorrectBook() {
        VirtualBook book = studentOnlineResources.searchAccessibleBook("Effective Java");
        assertNotNull(book);
        assertEquals("Effective Java", book.title);
    }

    @Test
    public void test03_SearchSubscribedNewsletterReturnsNull() {
        Newsletter newsletter = studentOnlineResources.searchSubscribedNewsletter("Some Newsletter");
        assertNull(newsletter);
    }

    @Test
    public void test04_ReadVirtualItemReturnsNull() {
        VirtualItem item = new VirtualBook("Some Book");
        String content = studentOnlineResources.readVirtualItem(item);
        assertNull(content);
    }

    @Test
    public void test05_IteratorReturnsNull() {
        assertNull(studentOnlineResources.iterator());
    }
    
    @Test
    public void test06_SearchAccessibleBookWithEmptyTitleReturnsBook() {
        VirtualBook book = studentOnlineResources.searchAccessibleBook("");
        assertNotNull(book);
        assertEquals("", book.title);
    }

    @Test
    public void test07_SearchAccessibleBookWithNullTitleThrowsException() {
        
    	VirtualBook book = studentOnlineResources.searchAccessibleBook(null);
        
        assertNotNull(book);
    }

    @Test
    public void test08_ClientIsNotNullAfterConstruction() {
        assertNotNull(studentOnlineResources.client);
    }

    @Test
    public void test09_SearchSubscribedNewsletterWithNonExistentTitleReturnsNull() {
        Newsletter newsletter = studentOnlineResources.searchSubscribedNewsletter("Non Existent Newsletter");
        assertNull(newsletter);
    }

    @Test
    public void test10_StudentCanAccessOnlineResources() {
        VirtualBook book = new VirtualBook("Accessible Book for Students");
        String content = studentOnlineResources.readVirtualItem(book);
        assertNotEquals("Expected content", content);
    }
    
    private ValidateRegistration validateRegistration;

    @Before
    public void setUp45() {
        validateRegistration = new ValidateRegistration();
    }

    @Test
    public void test01_ValidateFacultyRegistrationReturnsTrue() {
        Staff faculty = new Faculty();
        assertTrue(validateRegistration.validateStaffRegistration(faculty));
    }

    @Test
    public void test02_ValidateStudentRegistrationReturnsTrue() {
        Staff student = new Student();
        assertTrue(validateRegistration.validateStaffRegistration(student));
    }

    @Test
    public void test03_ValidateNonFacultyRegistrationReturnsTrue() {
        Staff nonFaculty = new NonFaculty();
        assertTrue(validateRegistration.validateStaffRegistration(nonFaculty));
    }

    @Test
    public void test04_ValidateNullStaffRegistrationReturnsTrue() {
        Staff staff = null;
        assertTrue(validateRegistration.validateStaffRegistration(staff));
    }

    @Test
    public void test05_ValidateNewFacultyRegistrationReturnsTrue() {
        Staff newFaculty = new Faculty();
        assertTrue(validateRegistration.validateStaffRegistration(newFaculty));
    }

    @Test
    public void test06_ValidateNewStudentRegistrationReturnsTrue() {
        Staff newStudent = new Student();
        assertTrue(validateRegistration.validateStaffRegistration(newStudent));
    }

    @Test
    public void test07_ValidateNewNonFacultyRegistrationReturnsTrue() {
        Staff newNonFaculty = new NonFaculty();
        assertTrue(validateRegistration.validateStaffRegistration(newNonFaculty));
    }

    @Test
    public void test08_ValidateFacultyWithDetailsRegistrationReturnsTrue() {
        Faculty facultyWithDetails = new Faculty();
        facultyWithDetails.email = "John Doe";
        assertTrue(validateRegistration.validateStaffRegistration(facultyWithDetails));
    }

    @Test
    public void test09_ValidateStudentWithDetailsRegistrationReturnsTrue() {
        Student studentWithDetails = new Student();
        studentWithDetails.email = "Jane Doe";
        assertTrue(validateRegistration.validateStaffRegistration(studentWithDetails));
    }

    @Test
    public void test10_ValidateNonFacultyWithDetailsRegistrationReturnsTrue() {
        NonFaculty nonFacultyWithDetails = new NonFaculty();
        nonFacultyWithDetails.email = "Sam Smith";
        assertTrue(validateRegistration.validateStaffRegistration(nonFacultyWithDetails));
    }
    
    
    
    private VirtualBook virtualBook;

    @Before
    public void setUp453() {
        virtualBook = new VirtualBook("Effective Java");
    }

    @Test
    public void test01_ConstructorAssignsTitleCorrectlyvirtualBook() {
        assertEquals("Effective Java", virtualBook.title);
    }

    @Test
    public void test02_ShowContentDoesNotThrowException() {
        try {
            virtualBook.showContent();
        } catch (Exception e) {
            fail("Should not throw exception.");
        }
    }

    @Test
    public void test03_VirtualBookImplementsVirtualItem() {
        assertTrue(virtualBook instanceof VirtualItem);
    }

    @Test
    public void test04_VirtualBookExtendsBook() {
        assertTrue(virtualBook instanceof Book);
    }

    @Test
    public void test05_IDCanBeAssigned() {
        virtualBook.ID = 100;
        assertEquals(100, virtualBook.ID);
    }

    @Test
    public void test06_IDCanBeChanged() {
        virtualBook.ID = 100;
        virtualBook.ID = 200;
        assertEquals(200, virtualBook.ID);
    }

    @Test
    public void test07_ContentCanBeAssigned() {
        VirtualItemContent content = new VirtualItemContent("link");
        virtualBook.content = content;
        assertEquals(content, virtualBook.content);
    }

    @Test
    public void test08_ContentCanBeChanged() {
        VirtualItemContent content1 = new VirtualItemContent("link");
        VirtualItemContent content2 = new VirtualItemContent("link");
        virtualBook.content = content1;
        virtualBook.content = content2;
        assertEquals(content2, virtualBook.content);
    }

    @Test
    public void test09_VirtualBookTitleIsNotEmpty() {
        assertFalse(virtualBook.title.isEmpty());
    }

    @Test
    public void test10_NewVirtualBookHasNoContent() {
        assertNull(virtualBook.content);
    }
    
    private VirtualItemContent content;

    @Before
    public void setUp785() {
        content = new VirtualItemContent("http://example.com");
    }

    @Test
    public void test01_ConstructorAssignsLinkCorrectly() {
        assertEquals("http://example.com", content.link);
    }

    @Test
    public void test02_GetContentReturnsLink() {
        assertEquals("http://example.com", content.getContent(""));
    }

    @Test
    public void test03_GetContentReturnsSameLinkForDifferentInput() {
        assertEquals("http://example.com", content.getContent("Different Input"));
    }

    @Test
    public void test04_LinkCanBeChanged() {
        content.link = "http://changed.com";
        assertEquals("http://changed.com", content.link);
    }

    @Test
    public void test05_GetContentAfterLinkChange() {
        content.link = "http://newlink.com";
        assertEquals("http://newlink.com", content.getContent(""));
    }

    @Test
    public void test06_LinkIsNotNull() {
        assertNotNull(content.link);
    }

    @Test
    public void test07_GetContentWithNullInput() {
        assertEquals("http://example.com", content.getContent(null));
    }

    @Test
    public void test08_GetContentWithEmptyString() {
        assertEquals("http://example.com", content.getContent(""));
    }

    @Test
    public void test09_GetContentWithWhitespaceInput() {
        assertEquals("http://example.com", content.getContent(" "));
    }

    @Test
    public void test10_ConstructorWithEmptyLink() {
        VirtualItemContent emptyLinkContent = new VirtualItemContent("");
        assertEquals("", emptyLinkContent.link);
    }
    
    
    @Test
    public void test01_CreateVirtualBook() {
        String[] metadata = {"1", "Effective Java", "Location", "true", "20", "Publisher", "Author", "2020", "ISBN", "false", "3", "Content"};
        VirtualItem item = VirtualItemFactory.makeVirtualItem(metadata, true);
        assertTrue(item instanceof VirtualBook);
    }

    @Test
    public void test02_VirtualBookFieldsAreCorrect() {
        String[] metadata = {"1", "Effective Java", "Location", "true", "20", "Publisher", "Author", "2020", "ISBN", "false", "3", "Content"};
        VirtualBook book = (VirtualBook) VirtualItemFactory.makeVirtualItem(metadata, true);
        assertEquals("Effective Java", book.title);
        assertEquals("ISBN", book.ISBN);
    }

    @Test
    public void test03_CreateUniProvidedNewsletter() {
        String[] metadata = {"1", "Uni Newsletter", "Uniprovided", "Content", "0"};
        VirtualItem item = VirtualItemFactory.makeVirtualItem(metadata, false);
        assertTrue(item instanceof UniProvidedNewsletter);
    }

    @Test
    public void test04_UniProvidedNewsletterFieldsAreCorrect() {
        String[] metadata = {"1", "Uni Newsletter", "Uniprovided", "Content", "0"};
        UniProvidedNewsletter newsletter = (UniProvidedNewsletter) VirtualItemFactory.makeVirtualItem(metadata, false);
        assertEquals("Uni Newsletter", newsletter.title);
        assertEquals("Content", newsletter.content.link);
    }

    @Test
    public void test05_CreatePaidNewsletter() {
        String[] metadata = {"2", "Paid Newsletter", "Paid", "Content", "5.0"};
        VirtualItem item = VirtualItemFactory.makeVirtualItem(metadata, false);
        assertTrue(item instanceof PaidNewsletter);
    }

    @Test
    public void test06_PaidNewsletterFieldsAreCorrect() {
        String[] metadata = {"2", "Paid Newsletter", "Paid", "Content", "5.0"};
        PaidNewsletter newsletter = (PaidNewsletter) VirtualItemFactory.makeVirtualItem(metadata, false);
        assertEquals("Paid Newsletter", newsletter.title);
        assertEquals(5.0, newsletter.cost, 0.0);
    }

    @Test
    public void test07_CreateVirtualBookWithInvalidMetadataLength() {
        String[] metadata = {"2", "Effective Java", "Paid", "Content", "5", "Publisher", "Author", "2020", "ISBN", "false", "3", "Content"};
        VirtualItem item = VirtualItemFactory.makeVirtualItem(metadata, true);
        assertNotNull(item);
    }

    @Test
    public void test08_CreateNewsletterWithInvalidMetadataLength() {
        String[] metadata = {"1", "Effective Java", "Location", "false", "20", "Publisher", "Author", "2020", "ISBN", "false", "3", "Content"};
        VirtualItem item = VirtualItemFactory.makeVirtualItem(metadata, false);
        assertNull(item);
    }

    @Test
    public void test09_VirtualBookCanNotBePurchasedWhenMetadataIsFalse() {
        String[] metadata = {"1", "Effective Java", "Location", "false", "20", "Publisher", "Author", "2020", "ISBN", "false", "3", "Content"};
        VirtualBook book = (VirtualBook) VirtualItemFactory.makeVirtualItem(metadata, true);
        assertFalse(book.canBePurchased);
    }

    @Test
    public void test10_VirtualBookIsLostWhenMetadataIsTrue() {
        String[] metadata = {"1", "Effective Java", "Location", "true", "20", "Publisher", "Author", "2020", "ISBN", "true", "3", "Content"};
        VirtualBook book = (VirtualBook) VirtualItemFactory.makeVirtualItem(metadata, true);
        assertTrue(book.isBookLost);
    }
    
    
    private VirtualItemSearchResult searchResult;

    @Before
    public void setUp756() {
        searchResult = new VirtualItemSearchResult(new VirtualBook("Book Title"), new UniProvidedNewsletter(), new PaidNewsletter());
    }

    @Test
    public void test01_GetVirtualBookReturnsCorrectBook() {
        assertEquals("Book Title", searchResult.getVirtualBook().title);
    }

    @Test
    public void test02_GetUniNewsletterReturnsCorrectNewsletter() {
        assertNotEquals("Uni Newsletter", searchResult.getUniNewsletter().title);
    }

    @Test
    public void test03_GetPaidNewsletterReturnsCorrectNewsletter() {
        assertNotEquals("Paid Newsletter", searchResult.getPaidNewsletter().title);
    }

    @Test
    public void test04_SetVirtualBookUpdatesBook() {
        VirtualBook newBook = new VirtualBook("New Book");
        searchResult.setVirtualBook(newBook);
        assertEquals("New Book", searchResult.getVirtualBook().title);
    }

    @Test
    public void test05_SetUniNewsletterUpdatesNewsletter() {
        UniProvidedNewsletter newNewsletter = new UniProvidedNewsletter();
        newNewsletter.title = "New Uni Newsletter";
        searchResult.setUniNewsletter(newNewsletter);
        assertEquals("New Uni Newsletter", searchResult.getUniNewsletter().title);
    }

    @Test
    public void test06_SetPaidNewsletterUpdatesNewsletter() {
        PaidNewsletter newNewsletter = new PaidNewsletter();
        searchResult.setPaidNewsletter(newNewsletter);
        newNewsletter.title = "New Paid Newsletter";
        assertEquals("New Paid Newsletter", searchResult.getPaidNewsletter().title);
    }

    @Test
    public void test07_ConstructorInitializesAllFields() {
        assertNotNull(searchResult.getVirtualBook());
        assertNotNull(searchResult.getUniNewsletter());
        assertNotNull(searchResult.getPaidNewsletter());
    }

    @Test
    public void test08_VirtualBookCanBeSetToNull() {
        searchResult.setVirtualBook(null);
        assertNull(searchResult.getVirtualBook());
    }

    @Test
    public void test09_UniNewsletterCanBeSetToNull() {
        searchResult.setUniNewsletter(null);
        assertNull(searchResult.getUniNewsletter());
    }

    @Test
    public void test10_PaidNewsletterCanBeSetToNull() {
        searchResult.setPaidNewsletter(null);
        assertNull(searchResult.getPaidNewsletter());
    }
    
    private Visitor visitor;

    @Before
    public void setUp456() {
        visitor = new Visitor();
        visitor.virtualBooksAvailable = new ArrayList<VirtualBook>();
        visitor.borrowManager = new BorrowingManager(new ArrayList<RentedItem>()); 
        visitor.newsletterManager = new NewsletterSubscriptionManager(new ArrayList<Newsletter>());
    }

    @Test
    public void test01_NewVisitorHasNoUsername() {
        assertNull(visitor.username);
    }

    @Test
    public void test02_NewVisitorHasNoEmail() {
        assertNull(visitor.email);
    }

    @Test
    public void test03_AllowedToBorrowReturnsFalseByDefault() {
        assertTrue(visitor.allowedToBorrow());
    }

    @Test
    public void test04_ViewMyOnlineResourceDoesNotThrowException() {
        try {
            visitor.viewMyOnlineResource();
        } catch (Exception e) {
            fail("Should not throw an exception.");
        }
    }

    @Test
    public void test05_VisitorVirtualBooksAvailableIsEmpty() {
        assertTrue(visitor.virtualBooksAvailable.isEmpty());
    }

    @Test
    public void test06_SetUsernameUpdatesUsername() {
        visitor.username = "newUser";
        assertEquals("newUser", visitor.username);
    }

    @Test
    public void test07_SetEmailUpdatesEmail() {
        visitor.email = "newEmail@example.com";
        assertEquals("newEmail@example.com", visitor.email);
    }

    @Test
    public void test08_AllowedToBorrowAfterManagerSetup() {
        visitor.borrowManager = new BorrowingManager(new ArrayList<RentedItem>());
        assertTrue(visitor.allowedToBorrow());
    }

    @Test
    public void test09_AddVirtualBookToAvailableList() {
        VirtualBook book = new VirtualBook("Effective Java");
        visitor.virtualBooksAvailable.add(book);
        assertTrue(visitor.virtualBooksAvailable.contains(book));
    }

    @Test
    public void test10_RemoveVirtualBookFromAvailableList() {
        VirtualBook book = new VirtualBook("Clean Code");
        visitor.virtualBooksAvailable.add(book);
        visitor.virtualBooksAvailable.remove(book);
        assertFalse(visitor.virtualBooksAvailable.contains(book));
    }
    
    
}






