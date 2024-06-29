package randoop;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ErrorTest0 {

    public static boolean debug = false;

    @Test
    public void test01() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test01");
        model.SearchBorrowedItem searchBorrowedItem0 = model.SearchBorrowedItem.makeSearchBorrowedItem();
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        searchBorrowedItem0.removeItem("", "model.RegistrationFailedException: ");
    }

    @Test
    public void test02() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test02");
        model.SearchBorrowedItem searchBorrowedItem0 = model.SearchBorrowedItem.makeSearchBorrowedItem();
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        searchBorrowedItem0.removeItem("model.RegistrationFailedException: ", "hi!");
    }

    @Test
    public void test03() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test03");
        model.Request request0 = new model.Request();
        java.lang.String str1 = request0.type;
        model.ValidateRegistration validateRegistration2 = new model.ValidateRegistration();
        model.Student student3 = new model.Student();
        student3.viewMyOnlineResource();
        boolean boolean5 = validateRegistration2.validateStaffRegistration((model.Staff) student3);
        request0.requestee = student3;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean7 = student3.allowedToBorrow();
    }

    @Test
    public void test04() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test04");
        model.Student student0 = new model.Student();
        int int1 = student0.ID;
        model.StudentOnlineResources studentOnlineResources2 = new model.StudentOnlineResources((model.Client) student0);
        student0.ID = '#';
        java.lang.String str5 = student0.email;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean6 = student0.allowedToBorrow();
    }

    @Test
    public void test05() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test05");
        java.lang.String[] strArray4 = new java.lang.String[] { "src/Magazine.csv", "src/Book.csv", "hi!", "src/VirtualBook.csv" };
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client5 = model.ClientFactory.makeClient(strArray4);
    }

    @Test
    public void test06() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test06");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean12 = searchClient8.addClient("src/Magazine.csv", "", "src/VirtualBook.csv");
    }

    @Test
    public void test07() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test07");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client11 = searchClient9.getClient("src/Magazine.csv");
    }

    @Test
    public void test08() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test08");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client11 = searchClient9.getClient("src/VirtualBook.csv");
    }

    @Test
    public void test09() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test09");
        model.Database database0 = model.Database.database;
        model.Student student1 = new model.Student();
        int int2 = student1.ID;
        model.StudentOnlineResources studentOnlineResources3 = new model.StudentOnlineResources((model.Client) student1);
        student1.ID = '#';
        database0.addClient((model.Client) student1);
        java.util.ArrayList<model.VirtualBook> virtualBookList7 = student1.virtualBooksAvailable;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean8 = student1.allowedToBorrow();
    }

    @Test
    public void test10() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test10");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean13 = searchClient9.addClient("hi!", "src/BorrowedItems.csv", "src/Book.csv");
    }

    @Test
    public void test11() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test11");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client11 = searchClient9.getClient("src/Book.csv");
    }

    @Test
    public void test12() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test12");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.Database.database = database7;
        model.SearchVirtualItem searchVirtualItem9 = new model.SearchVirtualItem(database7);
        model.SearchClient searchClient10 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client12 = searchClient10.getClient("src/Magazine.csv");
    }

    @Test
    public void test13() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test13");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean13 = searchClient9.addClient("src/Newsletter.csv", "src/BorrowedItems.csv", "");
    }

    @Test
    public void test14() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test14");
        model.Visitor visitor0 = new model.Visitor();
        java.util.ArrayList<model.VirtualBook> virtualBookList1 = visitor0.virtualBooksAvailable;
        model.StudentOnlineResources studentOnlineResources2 = new model.StudentOnlineResources((model.Client) visitor0);
        model.RentedItem[] rentedItemArray3 = new model.RentedItem[] {};
        java.util.ArrayList<model.RentedItem> rentedItemList4 = new java.util.ArrayList<model.RentedItem>();
        boolean boolean5 = java.util.Collections.addAll((java.util.Collection<model.RentedItem>) rentedItemList4, rentedItemArray3);
        model.BorrowingManager borrowingManager6 = new model.BorrowingManager(rentedItemList4);
        model.Visitor visitor7 = new model.Visitor();
        visitor7.email = "hi!";
        borrowingManager6.client = visitor7;
        studentOnlineResources2.client = visitor7;
        model.NonFaculty nonFaculty12 = new model.NonFaculty();
        model.Visitor visitor13 = new model.Visitor();
        model.VirtualBook[] virtualBookArray14 = new model.VirtualBook[] {};
        java.util.ArrayList<model.VirtualBook> virtualBookList15 = new java.util.ArrayList<model.VirtualBook>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<model.VirtualBook>) virtualBookList15, virtualBookArray14);
        visitor13.virtualBooksAvailable = virtualBookList15;
        visitor13.username = "";
        model.Visitor visitor20 = new model.Visitor();
        model.VirtualBook[] virtualBookArray21 = new model.VirtualBook[] {};
        java.util.ArrayList<model.VirtualBook> virtualBookList22 = new java.util.ArrayList<model.VirtualBook>();
        boolean boolean23 = java.util.Collections.addAll((java.util.Collection<model.VirtualBook>) virtualBookList22, virtualBookArray21);
        visitor20.virtualBooksAvailable = virtualBookList22;
        visitor13.virtualBooksAvailable = virtualBookList22;
        nonFaculty12.virtualBooksAvailable = virtualBookList22;
        visitor7.virtualBooksAvailable = virtualBookList22;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean28 = visitor7.allowedToBorrow();
    }

    @Test
    public void test15() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test15");
        model.Database database0 = model.Database.database;
        java.util.ArrayList<java.lang.String> strList1 = database0.getAllItems();
        model.Client client3 = database0.getClient("src/Magazine.csv");
        model.Register register4 = model.Register.makeRegister(database0);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean8 = register4.register("model.LoginFailedException: hi!", "hi!", "src/VirtualBook.csv");
    }

    @Test
    public void test16() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test16");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client10 = searchClient8.getClient("model.RegistrationFailedException: ");
    }

    @Test
    public void test17() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test17");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean12 = searchClient8.addClient("hi!", "src/Newsletter.csv", "src/BorrowedItems.csv");
    }

    @Test
    public void test18() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test18");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        database7.itemPath = "";
        model.SearchClient searchClient12 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client14 = searchClient12.getClient("");
    }

    @Test
    public void test19() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test19");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.Database.database = database7;
        model.SearchVirtualItem searchVirtualItem9 = new model.SearchVirtualItem(database7);
        model.SearchClient searchClient10 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client12 = searchClient10.getClient("hi!");
    }

    @Test
    public void test20() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test20");
        java.lang.String[] strArray6 = new java.lang.String[] { "hi!", "src/Client.csv", "src/BorrowedItems.csv", "src/BorrowedItems.csv", "src/Client.csv" };
        model.Item item7 = model.ItemFactory.makeItem("hi!", strArray6);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client8 = model.ClientFactory.makeClient(strArray6);
    }

    @Test
    public void test21() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test21");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client11 = searchClient9.getClient("src/CD.csv");
    }

    @Test
    public void test22() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test22");
        model.Faculty faculty0 = new model.Faculty();
        model.BorrowingManager borrowingManager1 = faculty0.borrowManager;
        faculty0.faculty = "model.RegistrationFailedException: src/Magazine.csv";
        model.Faculty faculty4 = new model.Faculty();
        faculty4.username = "hi!";
        java.lang.String str7 = faculty4.username;
        model.Course course8 = new model.Course();
        java.lang.String str9 = course8.subject;
        course8.code = "";
        course8.subject = "src/Magazine.csv";
        model.Course course14 = new model.Course();
        java.lang.String str15 = course14.code;
        model.Course course16 = new model.Course();
        java.lang.String str17 = course16.subject;
        java.lang.String str18 = course16.subject;
        java.lang.String str19 = course16.code;
        java.lang.String str20 = course16.code;
        model.Course course21 = new model.Course();
        java.lang.String str22 = course21.code;
        model.Course course23 = new model.Course();
        java.lang.String str24 = course23.subject;
        java.lang.String str25 = course23.subject;
        java.lang.String str26 = course23.code;
        course23.code = "src/VirtualBook.csv";
        model.Course course29 = new model.Course();
        model.Course course30 = new model.Course();
        java.lang.String str31 = course30.subject;
        course30.code = "";
        course30.subject = "src/Magazine.csv";
        int int36 = course30.year;
        model.Course course37 = new model.Course();
        java.lang.String str38 = course37.code;
        java.lang.String str39 = course37.subject;
        course37.subject = "model.RegistrationFailedException: ";
        course37.subject = "src/Magazine.csv";
        model.Course[] courseArray44 = new model.Course[] { course8, course14, course16, course21, course23, course29, course30, course37 };
        java.util.ArrayList<model.Course> courseList45 = new java.util.ArrayList<model.Course>();
        boolean boolean46 = java.util.Collections.addAll((java.util.Collection<model.Course>) courseList45, courseArray44);
        faculty4.courses = courseList45;
        faculty0.courses = courseList45;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean49 = faculty0.allowedToBorrow();
    }

    @Test
    public void test23() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test23");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean12 = searchClient8.addClient("model.RegistrationFailedException: src/Book.csv", "src/VirtualBook.csv", "model.RegistrationFailedException: src/Book.csv");
    }

    @Test
    public void test24() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test24");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client10 = searchClient8.getClient("src/Book.csv");
    }

    @Test
    public void test25() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test25");
        model.NonFaculty nonFaculty0 = new model.NonFaculty();
        model.Visitor visitor1 = new model.Visitor();
        model.VirtualBook[] virtualBookArray2 = new model.VirtualBook[] {};
        java.util.ArrayList<model.VirtualBook> virtualBookList3 = new java.util.ArrayList<model.VirtualBook>();
        boolean boolean4 = java.util.Collections.addAll((java.util.Collection<model.VirtualBook>) virtualBookList3, virtualBookArray2);
        visitor1.virtualBooksAvailable = virtualBookList3;
        visitor1.username = "";
        model.Visitor visitor8 = new model.Visitor();
        model.VirtualBook[] virtualBookArray9 = new model.VirtualBook[] {};
        java.util.ArrayList<model.VirtualBook> virtualBookList10 = new java.util.ArrayList<model.VirtualBook>();
        boolean boolean11 = java.util.Collections.addAll((java.util.Collection<model.VirtualBook>) virtualBookList10, virtualBookArray9);
        visitor8.virtualBooksAvailable = virtualBookList10;
        visitor1.virtualBooksAvailable = virtualBookList10;
        nonFaculty0.virtualBooksAvailable = virtualBookList10;
        model.StudentOnlineResources studentOnlineResources15 = new model.StudentOnlineResources((model.Client) nonFaculty0);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean16 = nonFaculty0.allowedToBorrow();
    }

    @Test
    public void test26() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test26");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.Database.database = database7;
        model.SearchVirtualItem searchVirtualItem9 = new model.SearchVirtualItem(database7);
        model.Register register10 = model.Register.makeRegister(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean14 = register10.register("src/CD.csv", "src/VirtualBook.csv", "src/Magazine.csv");
    }

    @Test
    public void test27() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test27");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client10 = searchClient8.getClient("");
    }

    @Test
    public void test28() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test28");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client10 = searchClient8.getClient("model.LoginFailedException: hi!");
    }

    @Test
    public void test29() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test29");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean13 = searchClient9.addClient("model.RentingNotAllowedException: ", "model.RegistrationFailedException: src/Book.csv", "src/CD.csv");
    }

    @Test
    public void test30() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test30");
        model.Faculty faculty0 = new model.Faculty();
        faculty0.username = "hi!";
        java.util.Collection<model.Course> courseCollection3 = faculty0.courses;
        java.util.Collection<model.Book> bookCollection4 = faculty0.textbooks;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean5 = faculty0.allowedToBorrow();
    }

    @Test
    public void test31() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test31");
        model.Newsletter[] newsletterArray0 = new model.Newsletter[] {};
        java.util.ArrayList<model.Newsletter> newsletterList1 = new java.util.ArrayList<model.Newsletter>();
        boolean boolean2 = java.util.Collections.addAll((java.util.Collection<model.Newsletter>) newsletterList1, newsletterArray0);
        model.NewsletterSubscriptionManager newsletterSubscriptionManager3 = new model.NewsletterSubscriptionManager(newsletterList1);
        model.Client client4 = newsletterSubscriptionManager3.client;
        model.Student student5 = new model.Student();
        int int6 = student5.ID;
        model.StudentOnlineResources studentOnlineResources7 = new model.StudentOnlineResources((model.Client) student5);
        student5.ID = '#';
        java.lang.String str10 = student5.email;
        newsletterSubscriptionManager3.client = student5;
        model.Newsletter[] newsletterArray12 = new model.Newsletter[] {};
        java.util.ArrayList<model.Newsletter> newsletterList13 = new java.util.ArrayList<model.Newsletter>();
        boolean boolean14 = java.util.Collections.addAll((java.util.Collection<model.Newsletter>) newsletterList13, newsletterArray12);
        model.NewsletterSubscriptionManager newsletterSubscriptionManager15 = new model.NewsletterSubscriptionManager(newsletterList13);
        newsletterSubscriptionManager3.newsletterSubscribed = newsletterList13;
        model.NewsletterSubscriptionManager newsletterSubscriptionManager17 = new model.NewsletterSubscriptionManager(newsletterList13);
        model.Visitor visitor18 = new model.Visitor();
        model.VirtualBook[] virtualBookArray19 = new model.VirtualBook[] {};
        java.util.ArrayList<model.VirtualBook> virtualBookList20 = new java.util.ArrayList<model.VirtualBook>();
        boolean boolean21 = java.util.Collections.addAll((java.util.Collection<model.VirtualBook>) virtualBookList20, virtualBookArray19);
        visitor18.virtualBooksAvailable = virtualBookList20;
        visitor18.viewMyOnlineResource();
        newsletterSubscriptionManager17.client = visitor18;
        model.Student student25 = new model.Student();
        student25.viewMyOnlineResource();
        student25.viewMyOnlineResource();
        newsletterSubscriptionManager17.client = student25;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean29 = student25.allowedToBorrow();
    }

    @Test
    public void test32() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test32");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        java.lang.String str10 = database7.virtualItemFilePath;
        java.lang.String str11 = database7.getBookFilePath();
        database7.itemPath = "src/Magazine.csv";
        model.Login login14 = model.Login.makeLogin(database7);
        java.lang.String str15 = database7.getNewspaperFilePath();
        java.lang.String str16 = database7.getMagazineFilePath();
        model.SearchClient searchClient17 = model.SearchClient.makeSearchClient(database7);
        java.lang.String str18 = database7.getNewspaperFilePath();
        model.SearchClient searchClient19 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean23 = searchClient19.addClient("src/Client.csv", "src/VirtualBook.csv", "");
    }

    @Test
    public void test33() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test33");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean13 = searchClient9.addClient("src/Newsletter.csv", "src/Client.csv", "src/CD.csv");
    }

    @Test
    public void test34() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test34");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        java.lang.String str10 = database7.virtualItemFilePath;
        java.lang.String str11 = database7.getBookFilePath();
        database7.itemPath = "src/Magazine.csv";
        model.Login login14 = model.Login.makeLogin(database7);
        java.lang.String str15 = database7.getNewspaperFilePath();
        java.lang.String str16 = database7.getMagazineFilePath();
        model.SearchClient searchClient17 = model.SearchClient.makeSearchClient(database7);
        java.lang.String str18 = database7.getNewspaperFilePath();
        model.SearchClient searchClient19 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean23 = searchClient19.addClient("src/Newsletter.csv", "model.RentingNotAllowedException: hi!", "model.RentingNotAllowedException: hi!");
    }

    @Test
    public void test35() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test35");
        java.lang.String[] strArray7 = new java.lang.String[] { "src/Magazine.csv", "hi!", "src/Newsletter.csv", "src/VirtualBook.csv", "src/CD.csv", "src/Newsletter.csv" };
        model.Item item8 = model.ItemFactory.makeItem("hi!", strArray7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client9 = model.ClientFactory.makeClient(strArray7);
    }

    @Test
    public void test36() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test36");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        java.lang.String str10 = database7.virtualItemFilePath;
        java.lang.String str11 = database7.getBookFilePath();
        database7.itemPath = "src/Magazine.csv";
        model.Login login14 = model.Login.makeLogin(database7);
        java.lang.String str15 = database7.getNewspaperFilePath();
        java.lang.String str16 = database7.getMagazineFilePath();
        model.SearchClient searchClient17 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean21 = searchClient17.addClient("src/Magazine.csv", "src/Magazine.csv", "src/CD.csv");
    }

    @Test
    public void test37() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test37");
        model.Newsletter[] newsletterArray0 = new model.Newsletter[] {};
        java.util.ArrayList<model.Newsletter> newsletterList1 = new java.util.ArrayList<model.Newsletter>();
        boolean boolean2 = java.util.Collections.addAll((java.util.Collection<model.Newsletter>) newsletterList1, newsletterArray0);
        model.NewsletterSubscriptionManager newsletterSubscriptionManager3 = new model.NewsletterSubscriptionManager(newsletterList1);
        model.Client client4 = newsletterSubscriptionManager3.client;
        model.UniProvidedNewsletter uniProvidedNewsletter5 = new model.UniProvidedNewsletter();
        uniProvidedNewsletter5.title = "";
        java.lang.String str8 = uniProvidedNewsletter5.title;
        newsletterSubscriptionManager3.subscribeToUniProvidedNewsletter(uniProvidedNewsletter5, "");
        java.util.ArrayList<model.Newsletter> newsletterList11 = newsletterSubscriptionManager3.newsletterSubscribed;
        model.Visitor visitor12 = new model.Visitor();
        visitor12.email = "hi!";
        newsletterSubscriptionManager3.client = visitor12;
        model.StudentOnlineResources studentOnlineResources16 = new model.StudentOnlineResources((model.Client) visitor12);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean17 = visitor12.allowedToBorrow();
    }

    @Test
    public void test38() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test38");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.Database.database = database7;
        model.SearchVirtualItem searchVirtualItem9 = new model.SearchVirtualItem(database7);
        model.SearchClient searchClient10 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean14 = searchClient10.addClient("model.RegistrationFailedException: src/Magazine.csv", "src/Newsletter.csv", "model.RegistrationFailedException: src/Magazine.csv");
    }

    @Test
    public void test39() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test39");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        java.lang.String str10 = database7.virtualItemFilePath;
        java.lang.String str11 = database7.getBookFilePath();
        database7.itemPath = "src/Magazine.csv";
        model.Login login14 = model.Login.makeLogin(database7);
        java.lang.String str15 = database7.getNewspaperFilePath();
        java.lang.String str16 = database7.getBorrowedItemFilePath();
        boolean boolean18 = database7.addItem("src/Book.csv");
        model.Client client20 = database7.getClient("src/Book.csv");
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean21 = client20.allowedToBorrow();
    }

    @Test
    public void test40() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test40");
        java.lang.String[] strArray6 = new java.lang.String[] { "model.RegistrationFailedException: src/Book.csv", "model.RegistrationFailedException: src/Book.csv", "model.RegistrationFailedException: src/Magazine.csv", "src/Client.csv", "src/VirtualBook.csv", "model.RentingNotAllowedException: src/Magazine.csv" };
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client7 = model.ClientFactory.makeClient(strArray6);
    }

    @Test
    public void test41() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test41");
        java.lang.String[] strArray7 = new java.lang.String[] { "src/Client.csv", "src/Book.csv", "src/CD.csv", "src/Book.csv", "src/Newsletter.csv", "model.RegistrationFailedException: src/Magazine.csv" };
        model.Item item8 = model.ItemFactory.makeItem("hi!", strArray7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client9 = model.ClientFactory.makeClient(strArray7);
    }

    @Test
    public void test42() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test42");
        model.Database database0 = model.Database.database;
        java.lang.String str1 = database0.getMagazineFilePath();
        model.Login login2 = new model.Login(database0);
        java.lang.String str3 = database0.getBorrowedItemFilePath();
        model.Register register4 = model.Register.makeRegister(database0);
        model.Student student5 = new model.Student();
        int int6 = student5.ID;
        model.StudentOnlineResources studentOnlineResources7 = new model.StudentOnlineResources((model.Client) student5);
        student5.ID = 29;
        database0.addClient((model.Client) student5);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean11 = student5.allowedToBorrow();
    }

    @Test
    public void test43() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test43");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        java.lang.String str10 = database7.virtualItemFilePath;
        model.Login login11 = new model.Login(database7);
        model.Database database12 = login11.db;
        model.SearchClient searchClient13 = model.SearchClient.makeSearchClient(database12);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean17 = searchClient13.addClient("", "", "");
    }

    @Test
    public void test44() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test44");
        model.Database database0 = model.Database.database;
        java.util.ArrayList<java.lang.String> strList1 = database0.getAllItems();
        model.SearchClient searchClient2 = model.SearchClient.makeSearchClient(database0);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client4 = searchClient2.getClient("src/BorrowedItems.csv");
    }

    @Test
    public void test45() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test45");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.Database.database = database7;
        model.SearchVirtualItem searchVirtualItem9 = new model.SearchVirtualItem(database7);
        model.SearchClient searchClient10 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean14 = searchClient10.addClient("hi!", "src/CD.csv", "model.LoginFailedException: hi!");
    }

    @Test
    public void test46() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test46");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        java.lang.String str10 = database7.virtualItemFilePath;
        java.lang.String str11 = database7.getBookFilePath();
        database7.itemPath = "src/Magazine.csv";
        model.Login login14 = model.Login.makeLogin(database7);
        java.lang.String str15 = database7.getNewspaperFilePath();
        java.lang.String str16 = database7.getMagazineFilePath();
        model.SearchClient searchClient17 = model.SearchClient.makeSearchClient(database7);
        java.lang.String str18 = database7.getNewspaperFilePath();
        model.SearchClient searchClient19 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client21 = searchClient19.getClient("src/VirtualBook.csv");
    }

    @Test
    public void test47() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test47");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client10 = searchClient8.getClient("model.RentingNotAllowedException: ");
    }

    @Test
    public void test48() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test48");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean12 = searchClient8.addClient("src/Magazine.csv", "src/CD.csv", "model.RentingNotAllowedException: hi!");
    }

    @Test
    public void test49() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test49");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean12 = searchClient8.addClient("model.RegistrationFailedException: model.RentingNotAllowedException: hi!", "model.RegistrationFailedException: src/Magazine.csv", "");
    }

    @Test
    public void test50() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test50");
        model.Database database0 = model.Database.database;
        java.lang.String str1 = database0.getMagazineFilePath();
        model.Login login2 = new model.Login(database0);
        model.SearchClient searchClient3 = model.SearchClient.makeSearchClient(database0);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean7 = searchClient3.addClient("", "", "model.RegistrationFailedException: src/Book.csv");
    }

    @Test
    public void test51() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test51");
        model.Faculty faculty0 = new model.Faculty();
        faculty0.username = "hi!";
        java.lang.String str3 = faculty0.username;
        model.Course course4 = new model.Course();
        java.lang.String str5 = course4.subject;
        course4.code = "";
        course4.subject = "src/Magazine.csv";
        model.Course course10 = new model.Course();
        java.lang.String str11 = course10.code;
        model.Course course12 = new model.Course();
        java.lang.String str13 = course12.subject;
        java.lang.String str14 = course12.subject;
        java.lang.String str15 = course12.code;
        java.lang.String str16 = course12.code;
        model.Course course17 = new model.Course();
        java.lang.String str18 = course17.code;
        model.Course course19 = new model.Course();
        java.lang.String str20 = course19.subject;
        java.lang.String str21 = course19.subject;
        java.lang.String str22 = course19.code;
        course19.code = "src/VirtualBook.csv";
        model.Course course25 = new model.Course();
        model.Course course26 = new model.Course();
        java.lang.String str27 = course26.subject;
        course26.code = "";
        course26.subject = "src/Magazine.csv";
        int int32 = course26.year;
        model.Course course33 = new model.Course();
        java.lang.String str34 = course33.code;
        java.lang.String str35 = course33.subject;
        course33.subject = "model.RegistrationFailedException: ";
        course33.subject = "src/Magazine.csv";
        model.Course[] courseArray40 = new model.Course[] { course4, course10, course12, course17, course19, course25, course26, course33 };
        java.util.ArrayList<model.Course> courseList41 = new java.util.ArrayList<model.Course>();
        boolean boolean42 = java.util.Collections.addAll((java.util.Collection<model.Course>) courseList41, courseArray40);
        faculty0.courses = courseList41;
        model.NewsletterSubscriptionManager newsletterSubscriptionManager44 = faculty0.newsletterManager;
        model.BorrowingManager borrowingManager45 = faculty0.borrowManager;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean46 = faculty0.allowedToBorrow();
    }

    @Test
    public void test52() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test52");
        model.Database database7 = model.Database.createDatabase("hi!", "model.RegistrationFailedException: src/Magazine.csv", "src/CD.csv", "src/Client.csv", "model.LoginFailedException: hi!", "model.RegistrationFailedException: src/Magazine.csv", "model.LoginFailedException: hi!");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client10 = searchClient8.getClient("model.RegistrationFailedException: model.RentingNotAllowedException: hi!");
    }

    @Test
    public void test53() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test53");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.SearchClient searchClient8 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        model.Client client10 = searchClient8.getClient("model.LoginFailedException: hi!");
    }

    @Test
    public void test54() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test54");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean13 = searchClient9.addClient("model.RegistrationFailedException: ", "src/BorrowedItems.csv", "model.RegistrationFailedException: ");
    }

    @Test
    public void test55() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test55");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.Database.database = database7;
        java.lang.String str10 = database7.getCDFilePath();
        java.lang.String str11 = database7.itemPath;
        database7.virtualItemFilePath = "model.RegistrationFailedException: ";
        model.SearchClient searchClient14 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean18 = searchClient14.addClient("model.RegistrationFailedException: model.RentingNotAllowedException: hi!", "model.RegistrationFailedException: src/Book.csv", "");
    }

    @Test
    public void test56() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test56");
        model.Database database7 = model.Database.createDatabase("hi!", "hi!", "", "model.RegistrationFailedException: ", "", "", "");
        model.Database.database = database7;
        model.SearchVirtualItem searchVirtualItem9 = new model.SearchVirtualItem(database7);
        model.Login login10 = new model.Login(database7);
        model.SearchClient searchClient11 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean15 = searchClient11.addClient("src/CD.csv", "src/VirtualBook.csv", "src/Newsletter.csv");
    }

    @Test
    public void test57() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test57");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean13 = searchClient9.addClient("model.LoginFailedException: hi!", "src/Newsletter.csv", "model.RegistrationFailedException: model.RentingNotAllowedException: hi!");
    }

    @Test
    public void test58() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test58");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getVirtualBookFilePath();
        model.SearchClient searchClient9 = model.SearchClient.makeSearchClient(database7);
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean13 = searchClient9.addClient("src/CD.csv", "", "model.RegistrationFailedException: src/Book.csv");
    }

    @Test
    public void test59() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "ErrorTest0.test59");
        model.Database database7 = model.Database.createDatabase("model.RegistrationFailedException: ", "hi!", "model.RegistrationFailedException: ", "", "", "model.RegistrationFailedException: ", "model.RegistrationFailedException: ");
        java.lang.String str8 = database7.getBookFilePath();
        database7.itemPath = "hi!";
        model.Database.database = database7;
        model.Register register12 = model.Register.makeRegister(database7);
        model.Database database13 = register12.database;
        // during test generation this statement threw an exception of type java.lang.NullPointerException in error
        boolean boolean17 = register12.register("model.LoginFailedException: hi!", "model.RegistrationFailedException: model.RentingNotAllowedException: hi!", "model.RegistrationFailedException: model.RentingNotAllowedException: hi!");
    }
}

