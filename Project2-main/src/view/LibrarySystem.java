package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.*;
import view.LibrarySystem.MainFrame;
import view.LibrarySystem.OnlineResourcesPanel;
import view.LibrarySystem.OnlineResourcesWindow;
import view.LibrarySystem.SearchPanel;
import view.LibrarySystem.UserDashboardPanel;
import view.LibrarySystem.UserDashboardPanel.BooksBorrowedGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

public class LibrarySystem {

    static String pathToClient = "src/Client - Copy.csv";
    static String pathToBook = "src/Book.csv";
    static String pathToCD = "src/CD.csv";
    static String pathToMagazine = "src/Magazine.csv";
    static String pathToVirtualBook = "src/VirtualBook.csv";
    static String pathToNewsletter = "src/Newsletter.csv";
    static String borrowedItemFilePath = "src/BorrowedItems - Copy.csv";



    static Database db = Database.createDatabase(pathToClient,borrowedItemFilePath);
    public static SearchItems searchItem = new SearchItems(db);
    public static SearchVirtualItem seacrhVirtualItem = new SearchVirtualItem(db);
    public static SearchBorrowedItem searchBorrowedItem = SearchBorrowedItem.makeSearchBorrowedItem();

    static Register register = Register.makeRegister(db);
    static Login login = Login.makeLogin(db);

    public static Client client;
    private static BooksBorrowedGUI tableViewPanel;
    public static MainFrame frame;
    public static ArrayList<String> allItems = db.getAllItems();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    class NewsletterPanel extends JPanel {
        private JEditorPane editorPane;
        private JButton cancelButton;

        public NewsletterPanel() {
            setLayout(new BorderLayout());

            editorPane = new JEditorPane();
            editorPane.setEditable(false);
            try {
                // Example: Loading a web page
                editorPane.setPage(new URL("http://www.example.com/"));
            } catch (IOException e) {
                editorPane.setContentType("text/html");
                editorPane.setText("<html>Page not found.</html>");
            }

            JScrollPane scrollPane = new JScrollPane(editorPane);
            add(scrollPane, BorderLayout.CENTER);

            cancelButton = new JButton("Cancel Subscription");
            add(cancelButton, BorderLayout.SOUTH);

            // Action listener for the cancel button
            cancelButton.addActionListener(e -> {
                // Placeholder for cancellation logic
                JOptionPane.showMessageDialog(this, "Subscription cancelled.", "Cancellation",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    static class RegisterPanel extends JPanel {
        private JTextField emailField = new JTextField(20);
        private JPasswordField passwordField = new JPasswordField("123", 20);
        private JComboBox<String> userTypeCombo = new JComboBox<>(
                new String[] { "Student", "Faculty Member", "Non-Faculty Staff", "Visitor" });
        private JButton registerButton = new JButton("Register");
        private JButton cancelButton = new JButton("Back");

        public RegisterPanel(MainFrame mainFrame) {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            add(new JLabel("Email:"), gbc);
            add(emailField, gbc);
            add(new JLabel("Password:"), gbc);
            add(passwordField, gbc);
            add(new JLabel("User Type:"), gbc);
            add(userTypeCombo, gbc);
            add(registerButton, gbc);
            add(cancelButton, gbc);

            registerButton.addActionListener(e -> registerUser(mainFrame));
            cancelButton.addActionListener(e -> mainFrame.showPanel("Login")); // This requires a "Login" panel to be
                                                                               // implemented
        }

        private void registerUser(MainFrame mainFrame) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String type = userTypeCombo.getSelectedItem().toString();

            try {
                register.register(email, password, type);


                if(type != "Visitor"){
                    JOptionPane.showMessageDialog(mainFrame, "Registration successful!\nYour Registration Requires Further Validation From the Library Management Team.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

                }else{
                        JOptionPane.showMessageDialog(mainFrame, "Registration successful!", "Success",
                                                JOptionPane.INFORMATION_MESSAGE);

                }
                
            } catch (RegistrationFailedException e) {

                JOptionPane.showMessageDialog(mainFrame, e.message, "Registration Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    static class LoginPanel extends JPanel {
        private JTextField emailField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private JButton newUserButton;
        public static LoginPanel LOGIN = null;

   


        public LoginPanel(MainFrame mainFrame) {
            emailField = new JTextField("sth@gmail.com", 20);
            passwordField = new JPasswordField(20);
            loginButton = new JButton("Login");
            newUserButton = new JButton("New User? Register Here.");

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            add(new JLabel("Email:"), gbc);
            add(emailField, gbc);
            add(new JLabel("Password:"), gbc);
            add(passwordField, gbc);
            add(loginButton, gbc);
            add(newUserButton, gbc);

            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());

                    if(email.equals("admin")){

                        AdminFrame frame = new AdminFrame();
                        frame.setVisible(true);

                    }else{
                        try {
                            client = login.login(email, password);
                            // Assuming UserDashboardPanel is ready and can be shown
                            mainFrame.initOtherPanels();
                            mainFrame.getContentPane().removeAll(); // Remove previous panel/views
                            mainFrame.getContentPane().add(new UserDashboardPanel(mainFrame), BorderLayout.CENTER);
                            // panel
                            mainFrame.getContentPane().revalidate(); // Revalidate the content pane
                            mainFrame.getContentPane().repaint(); // Repaint to display the new panel


                            


                        } catch (LoginFailedException le) {
                            JOptionPane.showMessageDialog(mainFrame, le.message, "Error", JOptionPane.ERROR_MESSAGE);
                        }


                    }

                    

                }
            });

            newUserButton.addActionListener(e -> mainFrame.showPanel("Register"));
        }

    }


    public static class AdminFrame extends JFrame{

        public AdminFrame() {
            setTitle("Admin Panel");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
    
            JPanel topPanel = new JPanel();
            JButton addItemButton = new JButton("Add Item");
            JButton manageItemButton = new JButton("Manage Item");
    
            addItemButton.addActionListener(this::openAddItemWindow);
            manageItemButton.addActionListener(this::openManageItemWindow);
    
            topPanel.add(addItemButton);
            topPanel.add(manageItemButton);
    
            add(topPanel, BorderLayout.NORTH);
        }
    
        private void openAddItemWindow(ActionEvent e) {
            JDialog addItemDialog = new JDialog(this, "Add Item", true);
            addItemDialog.setLayout(new FlowLayout());
            addItemDialog.setSize(300, 200);
    
            JTextField itemNameField = new JTextField(20);
            JTextField authorField = new JTextField(20);
            JButton submitButton = new JButton("Submit");
    
            submitButton.addActionListener(event -> {
                String itemName = itemNameField.getText();
                String author = authorField.getText();
                // Implement your submit logic here
                // For demonstration, just print the input
                System.out.println("Item Name: " + itemName + ", Author: " + author);
    
                addItemDialog.dispose(); // Close the dialog
            });
    
            addItemDialog.add(new JLabel("Item Name:"));
            addItemDialog.add(itemNameField);
            addItemDialog.add(new JLabel("Author:"));
            addItemDialog.add(authorField);
            addItemDialog.add(submitButton);
    
            addItemDialog.setVisible(true);
        }
    
        private void openManageItemWindow(ActionEvent e) {
            JDialog manageItemDialog = new JDialog(this, "Manage Item", true);
            manageItemDialog.setLayout(new BorderLayout());
            manageItemDialog.setSize(400, 300);
    
            // Example list of items
            DefaultListModel<String> model = new DefaultListModel<>();

            for (String item : allItems) {
                model.addElement(item);
            }


            JList<String> itemList = new JList<>(model);
    
            itemList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        String selectedItem = itemList.getSelectedValue();
                        // Open enable/disable option dialog
                        openEnableDisableDialog(selectedItem);
                    }
                }
            });
    
            manageItemDialog.add(new JScrollPane(itemList), BorderLayout.CENTER);
            manageItemDialog.setVisible(true);
        }
    
        private void openEnableDisableDialog(String item) {
            Object[] options = {"Enable", "Disable"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Do you want to enable or disable this item: " + item + "?",
                    "Enable/Disable Item",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (choice == JOptionPane.YES_OPTION) {
                // Enable logic here

                for (int i = 0; i < allItems.size(); i++) {
                    if (allItems.get(i).equals(item)) {
                        allItems.remove(i);
                        allItems.add(i, replaceEnding(item, "TRUE"));
                        break; // Exit the loop once the target is found and replaced
                    }
                }

                System.out.println(item + " enabled.");
            } else {

                for (int i = 0; i < allItems.size(); i++) {
                    if (allItems.get(i).equals(item)) {
                        allItems.remove(i);
                        allItems.add(i, replaceEnding(item, "FALSE"));
                        break; // Exit the loop once the target is found and replaced
                    }
                }
                // Disable logic here
                System.out.println(item + " disabled.");
            }
        }


        public static String replaceEnding(String originalString, String newValue) {
            if (originalString.endsWith("TRUE")) {
                // Remove "TRUE" and append the new value
                return originalString.substring(0, originalString.length() - "TRUE".length()) + newValue;
            } else if (originalString.endsWith("FALSE")) {
                // Remove "FALSE" and append the new value
                return originalString.substring(0, originalString.length() - "FALSE".length()) + newValue;
            }
            // Return the original string if it does not end with "TRUE" or "FALSE"
            return originalString;
        }



    }



    public static class BookSearchPanel extends JPanel {
        private JTextField searchField;
        private JButton searchButton;
        private JTextArea resultsTextArea;
        private JTextArea recommendationsTextArea;

        public BookSearchPanel() {
            setLayout(new BorderLayout(5, 5));

            // Search panel at the top
            JPanel searchPanel = new JPanel();
            searchField = new JTextField(20);
            searchButton = new JButton("Search");
            searchPanel.add(new JLabel("Search for a Book:"));
            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            add(searchPanel, BorderLayout.NORTH);

            // Results panel in the center
            JPanel resultsPanel = new JPanel(new GridLayout(2, 1));
            resultsTextArea = new JTextArea();
            resultsTextArea.setEditable(false);
            resultsTextArea.setBorder(BorderFactory.createTitledBorder("Search Results"));

            recommendationsTextArea = new JTextArea();
            recommendationsTextArea.setEditable(false);
            recommendationsTextArea.setBorder(BorderFactory.createTitledBorder("Recommendations"));

            resultsPanel.add(new JScrollPane(resultsTextArea));
            resultsPanel.add(new JScrollPane(recommendationsTextArea));
            add(resultsPanel, BorderLayout.CENTER);

            // Implement the search functionality
            searchButton.addActionListener(e -> searchBooks());
        }

        private void searchBooks() {
            String searchText = searchField.getText();
            // Placeholder: Implement your search logic here. Update resultsTextArea with
            // search results
            resultsTextArea.setText("Searching for: " + searchText + "\nResult 1\nResult 2");

            // Placeholder: Implement your recommendations logic here. This could be based
            // on text similarity.
            recommendationsTextArea.setText("Recommendations:\nSimilar Book 1\nSimilar Book 2");
        }
    }

    static class MainFrame extends JFrame {
        private CardLayout cardLayout;
        private JPanel cardPanel;
        

        

        public MainFrame() {
            

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);


            setTitle("YorkU Library Management");
            setSize(1400, 600); // Ensure this size is sufficient
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // CardLayout to switch between different panels
            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);

            // Adding panels to the CardLayout
            cardPanel.add(new SearchPanel(), "Search");
            cardPanel.add(new LoginPanel(this), "Login");
            cardPanel.add(new RegisterPanel(this), "Register");
            cardPanel.add(new BookSearchPanel(), "BookSearch"); // Added BookSearchPanel to the card layout

            // Set the main layout of the frame and add the card panel
            // initPanels();
            setLayout(new BorderLayout()); // This layout supports flexibility in size
            add(createTitlePanel(), BorderLayout.NORTH); // Create and add the title panel
            add(cardPanel, BorderLayout.CENTER); // Add the card panel to the center
            add(cardPanel, BorderLayout.CENTER); // Ensure cardPanel is added correctly
            setVisible(true);

            // Show the Login panel by default
            showPanel("Login");
            frame = this;

        }

        private JPanel createTitlePanel() {
            // Create a title panel with a label and add it to the top
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JTextArea titleLabel = new JTextArea("YorkU Library Management");// To
                                                                                                                     // be
                                                                                                                     // removed
                                                                                                                     // later
            titleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Customize the font as needed
            titlePanel.add(titleLabel);
            return titlePanel;
        }

        private void initOtherPanels() {
            cardPanel.add(new UserDashboardPanel(this), "UserDashboard");
            cardPanel.add(new OnlineResourcesPanel(this), "OnlineResources");
        }

        // Method to switch between panels
        public void showPanel(String panelName) {
            cardLayout.show(cardPanel, panelName);
        }


    }

    static class RequestWindow extends JFrame{


        public RequestWindow() {
            // Set the title of the JFrame
            setTitle("Request Form");
            // Set the size of the JFrame
            setSize(300, 200);
            // Specify an action for the close button
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // Center the window
            setLocationRelativeTo(null);
    
            // Create a JPanel with a GridLayout
            JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
    
            // Create the textbox for the book title
            JTextField bookTitleField = new JTextField();
            // Create the dropdown box with options
            JComboBox<String> bookTypeBox = new JComboBox<>(new String[]{"textbook", "self-improvement"});
    
            // Create the submit button
            JButton submitButton = new JButton("Submit");
            // Add action listener to the submit button
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String bookTitle = bookTitleField.getText();
                    String bookType = (String) bookTypeBox.getSelectedItem();
                    String priority = "Low";
                    if(bookType.equalsIgnoreCase("textbook")){
                        priority = "High";
                    }
                    // Show a dialog message when the submit button is pressed
                    JOptionPane.showMessageDialog(RequestWindow.this, "Request for the book \"" + bookTitle + "\" is submitted. Priority: "+ priority, "Request Submitted", JOptionPane.INFORMATION_MESSAGE);
                }
            });
    
            // Add components to the panel
            panel.add(new JLabel("Book Title:"));
            panel.add(bookTitleField);
            panel.add(new JLabel("Book Type:"));
            panel.add(bookTypeBox);
            panel.add(submitButton);
    
            // Add the panel to the frame
            add(panel);
    
            // Make the window visible
            setVisible(true);
        }

        
    }

    public static class FacultyDashboard extends JFrame {
        private JList<String> textbookList;
        private DefaultListModel<String> listModel;
    
        public FacultyDashboard() {
            setTitle("Faculty Dashboard");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
    
            JPanel mainPanel = new JPanel(new BorderLayout());
            JLabel titleLabel = new JLabel("Textbooks Available", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
            mainPanel.add(titleLabel, BorderLayout.NORTH);
    
            listModel = new DefaultListModel<>();
            textbookList = new JList<>(listModel);
            updateTextbookList(getMockTextbooks()); // Populate the list with mock data
    
            JScrollPane scrollPane = new JScrollPane(textbookList);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
    
            JButton notifyButton = new JButton("Notify Library for a Textbook");
            notifyButton.addActionListener(e -> openNotifyDialog());
            mainPanel.add(notifyButton, BorderLayout.SOUTH);
    
            add(mainPanel);
        }
    
        private void openNotifyDialog() {
            JDialog notifyDialog = new JDialog(this, "Notify Library", true);
            notifyDialog.setLayout(new FlowLayout());
            notifyDialog.setSize(300, 150);
    
            JTextField textbookNameField = new JTextField(20);
            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> {
                String textbookName = textbookNameField.getText().trim();
                if (textbookName.isEmpty()) {
                    JOptionPane.showMessageDialog(notifyDialog, "The textbook name field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    notifyLibraryForProcurement(textbookName);
                    notifyDialog.dispose();
                }
            });
    
            notifyDialog.add(new JLabel("Textbook Name:"));
            notifyDialog.add(textbookNameField);
            notifyDialog.add(submitButton);
    
            notifyDialog.setLocationRelativeTo(this);
            notifyDialog.setVisible(true);
        }
    
        private void notifyLibraryForProcurement(String textbookName) {
            // Placeholder for notifying the library management with the textbook name
            JOptionPane.showMessageDialog(this, "Library management has been notified for: " + textbookName, "Notification Sent", JOptionPane.INFORMATION_MESSAGE);
        }
    
        private void updateTextbookList(ArrayList<Textbook> textbooks) {
            listModel.clear();
            for (Textbook book : textbooks) {
                String listItem = book.title + (book.newEditionAvailable ? " (New Edition Available!)" : "");
                listModel.addElement(listItem);
            }
        }
    
        private ArrayList<Textbook> getMockTextbooks() {
            ArrayList<Textbook> textbooks = new ArrayList<>();
            textbooks.add(new Textbook("Introduction to Algorithms", true));
            textbooks.add(new Textbook("Principles of Compiler Design", false));
            textbooks.add(new Textbook("Artificial Intelligence: A Modern Approach", false));
            return textbooks;
        }
    
        static class Textbook {
            String title;
            boolean newEditionAvailable;
    
            public Textbook(String title, boolean newEditionAvailable) {
                this.title = title;
                this.newEditionAvailable = newEditionAvailable;
            }
        }
    }


    static class PurchaseWindow extends JFrame{


        public PurchaseWindow() {
            // Set the title of the JFrame
            setTitle("Purchase Discounted Item");
            // Set the size of the JFrame
            setSize(300, 200);
            // Specify an action for the close button
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // Center the window
            setLocationRelativeTo(null);
    
            // Create a JPanel with a GridLayout
            JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
    
            // Create the dropdown box with options
            JComboBox<String> title = new JComboBox<>(new String[]{"Intro to Python: Textbook", "Psychology: DVD"});
    
            // Create the submit button
            JButton submitButton = new JButton("Submit");
            // Add action listener to the submit button
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String bookTitle = title.getSelectedItem().toString();
                    PaymentWindow pw = new PaymentWindow();
                    boolean pay = pw.pay(bookTitle);

                }
            });
    
            // Add components to the panel
            panel.add(new JLabel("Select an Item:"));
            panel.add(title);
            panel.add(submitButton);
    
            // Add the panel to the frame
            add(panel);
    
            // Make the window visible
            setVisible(true);
        }

        
    }

    static class UserDashboardPanel extends JPanel {

        public UserDashboardPanel(MainFrame mainFrame) {

            setLayout(new BorderLayout());

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JButton myOnlineResourcesButton = new JButton("My Online Resources");
            myOnlineResourcesButton.addActionListener(e -> {
                EventQueue.invokeLater(() -> {
                    JFrame onlineResourcesWindow = new OnlineResourcesWindow();
                    onlineResourcesWindow.setVisible(true);
                });
            });
            topPanel.add(myOnlineResourcesButton);

            // Ensure searchButton is defined before adding it
            JButton searchButton = new JButton("Search");
            searchButton.addActionListener(e -> {
                EventQueue.invokeLater(() -> {
                    JFrame searchWindow = new SearchWindow();
                    searchWindow.setVisible(true);
                });
            });
            topPanel.add(searchButton);

            // Ensure searchButton is defined before adding it
            JButton requestButton = new JButton("Request an Item");
            requestButton.addActionListener(e -> {
                EventQueue.invokeLater(() -> {
                    JFrame searchWindow = new RequestWindow();
                    searchWindow.setVisible(true);
                });
            });
            topPanel.add(requestButton);


            // Ensure searchButton is defined before adding it
            JButton perchaseButton = new JButton("Purchase Discounted Items");
            perchaseButton.addActionListener(e -> {
                EventQueue.invokeLater(() -> {
                    JFrame purchaseWindow = new PurchaseWindow();
                    purchaseWindow.setVisible(true);
                });
            });
            topPanel.add(perchaseButton);

            if(client instanceof Faculty){
                JButton facultyButton = new JButton("Faculty Textbooks");
                facultyButton.addActionListener(e -> {
                EventQueue.invokeLater(() -> {
                    JFrame facultyWindow = new FacultyDashboard();
                    facultyWindow.setVisible(true);
                });
            });
                topPanel.add(facultyButton);
            }

            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                    EventQueue.invokeLater(() -> {
                        client = null;
                        frame.dispose();
                        // logout
                        SwingUtilities.invokeLater(() -> {
                            frame = new MainFrame();
                            frame.setVisible(true);
                        });
                        
                        
                });
            });
            topPanel.add(logoutButton);

            add(topPanel, BorderLayout.NORTH);

            

            add(topPanel, BorderLayout.NORTH);

            tableViewPanel = new BooksBorrowedGUI();
            add(tableViewPanel, BorderLayout.CENTER);

        }

        public static class BooksBorrowedGUI extends JPanel {

            private JTable bookTable;
            private DefaultTableModel model;

            public BooksBorrowedGUI() {

                ArrayList<RentedItem> itemsBorrowed = client.borrowManager.physicalItemBorrowed;
                String[][] bookData = new String[itemsBorrowed.size()][2];

                for (int i = 0; i < bookData.length; i++) {
                    bookData[i][0] = "\"" + itemsBorrowed.get(i).item.title + "\" by "+ itemsBorrowed.get(i).item.publisher + ": " + itemsBorrowed.get(i).item.location;
                    bookData[i][1] = itemsBorrowed.get(i).dueDate;
                }

                setLayout(new BorderLayout());

                // Header Label for rented books
                JLabel headerLabel = new JLabel("Your Rented Items", SwingConstants.CENTER);
                headerLabel.setFont(new Font("Serif", Font.BOLD, 
                18));
                add(headerLabel, BorderLayout.NORTH);

                // Table for listing rented books and due dates
                String[] columnNames = { "Item Title", "Due Date" };
                model = new DefaultTableModel(bookData, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        // This makes the table cells not editable
                        return false;
                    }
                };

                bookTable = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(bookTable);
                add(scrollPane, BorderLayout.CENTER);

                // Customize renderer for due dates
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                    //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH);

                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                                column);
                        // Assuming the second column contains the due date
                        // You would replace this with your actual logic to check if the book is due

                        if (column == 1) {
                            try {
                                LocalDate dueDate = LocalDate.parse(value.toString(), dateFormat);
                                LocalDate currentDate = LocalDate.now();
                                // Highlight the row in red if the current date is after the due date
                                if (currentDate.isAfter(dueDate)) {
                                    comp.setBackground(Color.RED);
                                    comp.setForeground(Color.WHITE); // Set text color to white for better readability

                                } else {
                                    comp.setBackground(Color.WHITE);
                                    comp.setForeground(Color.BLACK);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle parse exception if date format doesn't match
                            }
                        } else {
                            // Reset to default styling when not rendering the due date column
                            comp.setBackground(Color.WHITE);
                            comp.setForeground(Color.BLACK);
                        }
                        return comp;
                    }
                };
                bookTable.getColumnModel().getColumn(1).setCellRenderer(renderer);

                // Returning an Item

                bookTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2 && !e.isConsumed()) {
                            e.consume(); // Mark the click as consumed
                            int row = bookTable.rowAtPoint(e.getPoint()); // Get the clicked row
                            String itemTitle = (String) bookTable.getValueAt(row, 0); // Assuming column 0 has the item
                            String[] parts = itemTitle.split("\"");
                            itemTitle = parts[1];


                            // Show a confirmation dialog
                            int response = JOptionPane.showConfirmDialog(null,
                                    "Do you want to return the item: " + itemTitle + "?", "Return Item",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (response == JOptionPane.YES_OPTION) {
                                // Return an item
                                Item item = null;
                                if (searchItem.getBook(itemTitle) != null) {
                                    item = searchItem.getBook(itemTitle);

                                } else if (searchItem.getCD(itemTitle) != null) {

                                    item = searchItem.getCD(itemTitle);
                                } else {
                                    item = searchItem.getMagazine(itemTitle);
                                }

                                client.borrowManager.removeRentedItem(item);
                                tableViewPanel.refreshTableData();

                            } else if (response == JOptionPane.NO_OPTION) {
                                // User clicked NO. Simply close the dialog, no further action needed
                                System.out.println("Return cancelled for item: " + itemTitle);

                            }
                        }
                    }
                });

                // Label for displaying the number of items due
                JLabel itemsDueLabel = new JLabel(
                        "Remember to Return the Rented Items in a Timely Manner. Balance Owned: $"
                                + client.borrowManager.checkBalanceOwed());
                add(itemsDueLabel, BorderLayout.SOUTH);

                setVisible(true);
            }

            public void refreshTableData() {
                // Fetch the latest list of items borrowed
                ArrayList<RentedItem> itemsBorrowed = client.borrowManager.physicalItemBorrowed;
                String[][] bookData = new String[itemsBorrowed.size()][2];

                for (int i = 0; i < bookData.length; i++) {
                    bookData[i][0] = "\"" + itemsBorrowed.get(i).item.title + "\" by "+ itemsBorrowed.get(i).item.publisher + ": " + itemsBorrowed.get(i).item.location;
                    bookData[i][1] = itemsBorrowed.get(i).dueDate;
                }

                // Remove all existing rows
                model.setRowCount(0);

                // Add new rows from the latest data
                for (String[] rowData : bookData) {
                    model.addRow(rowData);
                }

                //System.out.println("Table is Refreshed");
            }
        }

    }

    // onlineResourcesPanel
    public static class OnlineResourcesPanel extends JPanel {
        private MainFrame mainFrame;

        public OnlineResourcesPanel(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
            // Initialization code...
        }
    }

    public static class SearchPanel extends JPanel {

        public SearchPanel() {
            setLayout(new BorderLayout());
            JPanel searchInputPanel = new JPanel();
            JTextField searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            JTextArea searchResultsArea = new JTextArea();
            searchResultsArea.setEditable(false);

            searchButton.addActionListener(e -> {
                // Implement search functionality here
                // For example, update searchResultsArea with search results
                searchResultsArea.setText("Search results for: \"" + searchField.getText() + "\"");
            });

            searchInputPanel.add(new JLabel("Enter search query:"));
            searchInputPanel.add(searchField);
            searchInputPanel.add(searchButton);
            add(searchInputPanel, BorderLayout.NORTH);
            add(new JScrollPane(searchResultsArea), BorderLayout.CENTER);
        }
    }

    // opens new window for search panel
    public static class SearchWindow extends JFrame {
        Book book;
        CD cd;
        Magazine mg;
        ArrayList<Book> similarBooks;

        VirtualBook vBook;
        UniProvidedNewsletter uNews;
        PaidNewsletter pNews;

        private JTabbedPane tabbedPane;

        public SearchWindow() {
            setTitle("Search");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            searchAnItem();
            tabbedPane = new JTabbedPane();
            addCategoriesTabbedPane();
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
        }

        private void searchAnItem() {
            // Create a panel to hold the search components
            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new BorderLayout(5, 0)); // A little space between components

            // Create the search text field
            JTextField searchTerm = new JTextField();
            searchTerm.setColumns(20); // Set the width of the text field

            // Make the text field nicer
            searchTerm.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Change font settings as needed

            // Create the search button
            JButton searchButton = new JButton("Search");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    book = searchItem.getBook(searchTerm.getText());
                    cd = searchItem.getCD(searchTerm.getText());
                    mg = searchItem.getMagazine(searchTerm.getText());
                    similarBooks = (ArrayList<Book>) searchItem.getSimilarBooks(searchTerm.getText());

                    vBook = seacrhVirtualItem.getVirtualBook(searchTerm.getText());
                    pNews = seacrhVirtualItem.getPaidNewsletter(searchTerm.getText());
                    uNews = seacrhVirtualItem.getUniNewsletter(searchTerm.getText());

                    // Update the tabbed pane with new search results
                    tabbedPane.removeAll(); // Remove all existing tabs
                    addCategoriesTabbedPane(); // Re-add the tabs based on new search results

                    // Refresh the UI to display the updated tabbed pane
                    tabbedPane.revalidate();
                    tabbedPane.repaint();

                }
            });

            // Add components to the search panel
            searchPanel.add(searchTerm, BorderLayout.CENTER);
            searchPanel.add(searchButton, BorderLayout.EAST);

            // Optionally, make the panel and its components look nicer
            searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add some padding around the panel
            searchButton.setFont(new Font("SansSerif", Font.BOLD, 12)); // Set the font for the button

            // Add the search panel to the frame's content pane
            getContentPane().add(searchPanel, BorderLayout.NORTH);
        }

        private void addCategoriesTabbedPane() {

            // Books Tab
            JPanel booksPanel = createInteractiveListPanel("Books", book, similarBooks);
            tabbedPane.addTab("Books", booksPanel);

            // CDs Tab
            JPanel cdsPanel = createInteractiveListPanelCD("CDs", cd);
            tabbedPane.addTab("CDs", cdsPanel);

            // Magazines Tab
            JPanel magazinesPanel = createInteractiveListPanelM("Magazines", mg);
            tabbedPane.addTab("Magazines", magazinesPanel);

            // Uni Newspapers Tab
            JPanel uniNewspapersPanel = createInteractiveListPanelUNews("Uni Newspapers", uNews);
            tabbedPane.addTab("Uni Newspapers", uniNewspapersPanel);

            // Paid Newspapers Tab
            JPanel paidNewspapersPanel = createInteractiveListPanelPNews("Paid Newspapers", pNews);
            tabbedPane.addTab("Paid Newspapers", paidNewspapersPanel);

            // Virtual Books Tab
            JPanel virtualBooksPanel = createInteractiveListPanelVNews("Virtual Books", vBook);
            tabbedPane.addTab("Virtual Books", virtualBooksPanel);

        }

        private JPanel createInteractiveListPanelM(String category, Magazine mg2) {
            JPanel panel = new JPanel(new BorderLayout());
            DefaultListModel<String> model = new DefaultListModel<>();
            if (mg2 != null) {
                model.addElement(mg2.title);
            }

            JList<String> list = new JList<>(model);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int index = list.locationToIndex(e.getPoint());
                        String selectedItem = list.getModel().getElementAt(index);
                        // Show confirmation dialog to rent the item
                        int response = JOptionPane.showConfirmDialog(SearchWindow.this,
                                "Do you want to rent " + selectedItem + "?",
                                "Rent " + category,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            // Handle the renting logic here
                            try {
                                client.borrowManager.rentAnItem(searchItem.getMagazine(selectedItem), LocalDate.now());
                                tableViewPanel.refreshTableData(); // Call the refresh method on tableViewPanel
                                JOptionPane.showMessageDialog(SearchWindow.this,
                                        selectedItem + " has been rented.",
                                        "Item Rented",
                                        JOptionPane.INFORMATION_MESSAGE);

                            } catch (RentingNotAllowedException e1) {
                                // TODO Auto-generated catch block
                                JOptionPane.showMessageDialog(SearchWindow.this, e1.message, "Renting Failed",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(list);
            panel.add(scrollPane, BorderLayout.CENTER);
            return panel;
        }

        private JPanel createInteractiveListPanelCD(String category, CD cd2) {
            JPanel panel = new JPanel(new BorderLayout());
            DefaultListModel<String> model = new DefaultListModel<>();
            if (cd2 != null) {
                model.addElement(cd2.title);
            }

            JList<String> list = new JList<>(model);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int index = list.locationToIndex(e.getPoint());
                        String selectedItem = list.getModel().getElementAt(index);
                        //System.out.println("item: " + selectedItem);
                        // Show confirmation dialog to rent the item
                        int response = JOptionPane.showConfirmDialog(SearchWindow.this,
                                "Do you want to rent " + selectedItem + "?",
                                "Rent " + category,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            // Handle the renting logic here
                            try {
                                client.borrowManager.rentAnItem(searchItem.getCD(selectedItem), LocalDate.now());
                                tableViewPanel.refreshTableData(); // Call the refresh method on tableViewPanel
                                JOptionPane.showMessageDialog(SearchWindow.this,
                                        selectedItem + " has been rented.",
                                        "Item Rented",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (RentingNotAllowedException e1) {
                                // TODO Auto-generated catch block
                                JOptionPane.showMessageDialog(SearchWindow.this, e1.message, "Renting Failed",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(list);
            panel.add(scrollPane, BorderLayout.CENTER);
            return panel;
        }

        // Book
        private JPanel createInteractiveListPanel(String category, Book book, ArrayList<Book> similarBooks) {
            JPanel panel = new JPanel(new BorderLayout());
            DefaultListModel<String> model = new DefaultListModel<>();
            if (book != null) {
                model.addElement(book.title);
            }
            if (similarBooks != null) {
                for (Book item : similarBooks) {
                    model.addElement(item.title);
                }
            }

            JList<String> list = new JList<>(model);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int index = list.locationToIndex(e.getPoint());
                        String selectedItem = list.getModel().getElementAt(index);
                        //System.out.println("item: " + selectedItem);
                        // Show confirmation dialog to rent the item
                        int response = JOptionPane.showConfirmDialog(SearchWindow.this,
                                "Do you want to rent " + selectedItem + "?",
                                "Rent " + category,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            // Handle the renting logic here
                            try {
                                client.borrowManager.rentAnItem(searchItem.getBook(selectedItem), LocalDate.now());
                                tableViewPanel.refreshTableData(); // Call the refresh method on tableViewPanel
                                JOptionPane.showMessageDialog(SearchWindow.this,
                                        selectedItem + " has been rented.",
                                        "Item Rented",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (RentingNotAllowedException e1) {
                                // TODO Auto-generated catch block
                                JOptionPane.showMessageDialog(SearchWindow.this, e1.message, "Renting Failed",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(list);
            panel.add(scrollPane, BorderLayout.CENTER);
            return panel;
        }

        // Virtual Books Tab
        private JPanel createInteractiveListPanelUNews(String category, UniProvidedNewsletter news) {
            JPanel panel = new JPanel(new BorderLayout());
            DefaultListModel<String> model = new DefaultListModel<>();
            if (news != null) {
                model.addElement(news.title);
            }

            JList<String> list = new JList<>(model);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int index = list.locationToIndex(e.getPoint());
                        String selectedItem = list.getModel().getElementAt(index);

                        // Show confirmation dialog to rent the item
                        int response = JOptionPane.showConfirmDialog(SearchWindow.this,
                                "Do you want to subscribe to " + selectedItem + "?",
                                "Rent " + category,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            // Handle the renting logic here

                            client.newsletterManager.subscribeToUniProvidedNewsletter(news, LocalDate.now().toString());
                            JOptionPane.showMessageDialog(SearchWindow.this,
                                    selectedItem + " has been subscribed.",
                                    "Subscribed Item",
                                    JOptionPane.INFORMATION_MESSAGE);

                        }
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(list);
            panel.add(scrollPane, BorderLayout.CENTER);
            return panel;
        }

        // Paid Newspapers Tab
        private JPanel createInteractiveListPanelPNews(String category, PaidNewsletter news) {
            JPanel panel = new JPanel(new BorderLayout());
            DefaultListModel<String> model = new DefaultListModel<>();

            if (news != null) {
                model.addElement(news.title);
            }

            JList<String> list = new JList<>(model);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int index = list.locationToIndex(e.getPoint());
                        String selectedItem = list.getModel().getElementAt(index);
                        // Prompt for payment and subscription
                        int response = JOptionPane.showConfirmDialog(SearchWindow.this,
                                "This is a paid newspaper. Do you wish to proceed with payment and subscribe to "
                                        + selectedItem + "?",
                                "Subscribe and Pay for " + category,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            // Payment
                            PaymentWindow pw = new PaymentWindow();
                            boolean pay = pw.pay(selectedItem);
                            client.newsletterManager.subscribeToPaidNewsletter(news, LocalDate.now().toString());

                        }
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(list);
            panel.add(scrollPane, BorderLayout.CENTER);
            return panel;
        }

        // Paid Newspapers Tab
        private JPanel createInteractiveListPanelVNews(String category, VirtualBook book) {
            JPanel panel = new JPanel(new BorderLayout());
            DefaultListModel<String> model = new DefaultListModel<>();
            if (book != null) {
                model.addElement(book.title);
            }

            JList<String> list = new JList<>(model);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int index = list.locationToIndex(e.getPoint());
                        String selectedItem = list.getModel().getElementAt(index);

                        // Show confirmation dialog to rent the item
                        int response = JOptionPane.showConfirmDialog(SearchWindow.this,
                                "Do you want to add " + selectedItem + " to the MyOnlineResources?",
                                "Rent " + category,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            // Handle the renting logic here
                            
                            
                            searchBorrowedItem.addVirtualItem(book, LocalDate.now().toString(), client);
                            client.virtualBooksAvailable.add(book);

                            

                            JOptionPane.showMessageDialog(SearchWindow.this,
                                    selectedItem + " has been added to the MyOnlineResources.",
                                    "Subscribed Virtual Book",
                                    JOptionPane.INFORMATION_MESSAGE);


                        }
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(list);
            panel.add(scrollPane, BorderLayout.CENTER);
            return panel;
        }
    }
    
    public static class PaymentWindow extends JFrame {
            public boolean pay(String selectedItem) {
                setTitle("Process Payment");
                setSize(300, 200); // Set the size of the window
                setLocationRelativeTo(null); // Center the window
                setLayout(new GridLayout(5, 2, 5, 5)); // Set layout

                // Create and add the form components
                add(new JLabel("Card Number:"));
                JTextField cardNumberField = new JTextField();
                add(cardNumberField);

                add(new JLabel("Expiration Date (MM/YY):"));
                JTextField expirationDateField = new JTextField();
                add(expirationDateField);

                add(new JLabel("CVV:"));
                JTextField cvvField = new JTextField();
                add(cvvField);

                JButton submitButton = new JButton("Submit Payment");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Simulate payment acceptance
                        JOptionPane.showMessageDialog(PaymentWindow.this,
                                "Your payment has been processed. You have purchased/subscribed to " + selectedItem + ".",
                                "Subscription and Payment Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                });

                add(submitButton);

                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the window without exiting the application
                setVisible(true); // Make the window visible
                return true;
            }
        }
    


    // opens new window for my online resource panel
    public static class OnlineResourcesWindow extends JFrame {

        public OnlineResourcesWindow() {
            setTitle("Online Resources");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            // Create a tabbed pane to hold the tabs for News Articles and Virtual Books
            JTabbedPane tabbedPane = new JTabbedPane();

            // NewsArticles Tab
            ArrayList<Newsletter> newsletters = client.newsletterManager.newsletterSubscribed;
            JList<String> newsList = new JList<>();
            DefaultListModel<String> newsModel = new DefaultListModel<>();
            for (Newsletter newsletter : newsletters) {
                if(!newsModel.contains(newsletter.title))
                    newsModel.addElement(newsletter.title);
            }
            newsList.setModel(newsModel);
            newsList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                        // Right-click
                        int index = newsList.locationToIndex(e.getPoint());
                        if (index != -1) { // Check if an actual item is clicked
                            newsList.setSelectedIndex(index); // Select the item at the clicked position
                            Newsletter selectedNewsletter = newsletters.get(index);
                            // Ask if they want to cancel the subscription
                            int confirm = JOptionPane.showConfirmDialog(newsList, "Do you want to cancel the subscription to \"" + selectedNewsletter.title + "\"?", "Cancel Subscription", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                // Implement cancellation logic here
                                System.out.println("Subscription cancelled for: " + selectedNewsletter.title);
                                client.newsletterManager.cancelSubscription(selectedNewsletter);
                            }
                        }
                    } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                        // Double left-click
                        int index = newsList.locationToIndex(e.getPoint());
                        Newsletter selectedNewsletter = newsletters.get(index);
                        // Open a webpage with the content of the selected newsletter
                        try {
                            Desktop.getDesktop().browse(new URL(selectedNewsletter.content.link).toURI());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            JScrollPane newsScrollPane = new JScrollPane(newsList);
            tabbedPane.addTab("NewsArticles", newsScrollPane);

            // VirtualBooks Tab - Only available if the user is a Student
            
            ArrayList<VirtualBook> vBooks = client.virtualBooksAvailable;
            JList<String> vBooksList = new JList<>();
            DefaultListModel<String> vBooksModel = new DefaultListModel<>();
            for (VirtualBook vBook : vBooks) {
                if(!vBooksModel.contains(vBook.title))
                    vBooksModel.addElement(vBook.title);
            }
            vBooksList.setModel(vBooksModel);
            vBooksList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int index = vBooksList.locationToIndex(e.getPoint());
                        VirtualBook selectedVBook = vBooks.get(index);
                        // Open a webpage with the content of the selected virtual book
                        
                        try {
                            //Desktop.getDesktop().browse(new URL(selectedVBook.content.link).toURI());
                            Desktop.getDesktop().browse(new URL(selectedVBook.content.link).toURI());

                            // SimpleWebBrowser wb = new SimpleWebBrowser(selectedVBook.content.link);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            JScrollPane vBooksScrollPane = new JScrollPane(vBooksList);
            tabbedPane.addTab("VirtualBooks", vBooksScrollPane);
            

            // Add the tabbed pane to the main content pane of the window
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
    

            

        }
    }


    public static class SimpleWebBrowser extends JFrame {
        public SimpleWebBrowser(String url) {
            setTitle("Web Content Loader");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
    
            JEditorPane webContent = new JEditorPane();
            webContent.setEditable(false);
    
            try {
                webContent.setPage(url);
            } catch (IOException e) {
                webContent.setContentType("text/html");
                webContent.setText("<html>Page not found.</html>");
                e.printStackTrace();
            }
    
            JScrollPane scrollPane = new JScrollPane(webContent);
            add(scrollPane);
    
            setVisible(true);
        }
    
    }


    class DashboardFrame extends JFrame {
        public DashboardFrame() {
            setTitle("User Dashboard");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null); // Center on screen

            // Add content to your dashboard window here
            JLabel welcomeLabel = new JLabel("Welcome to the Dashboard", SwingConstants.CENTER);
            add(welcomeLabel);
        }
    }

    // req6
    class FacultyDashboardPanel extends JPanel {
        // Example constructor parameters might include user details if needed
        public FacultyDashboardPanel() {
            setLayout(new BorderLayout());
            add(createCoursesPanel(), BorderLayout.CENTER);
            add(createNotificationsPanel(), BorderLayout.SOUTH);
        }

        private JPanel createCoursesPanel() {
            // Simulate a panel that lists courses and textbooks
            JPanel coursesPanel = new JPanel();
            coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));

            // Placeholder for course and textbook listings
            JLabel courseLabel = new JLabel("Course: Introduction to Programming");
            JLabel textbookLabel = new JLabel("Textbook: Introduction to Java Programming, 11th Edition");

            coursesPanel.add(courseLabel);
            coursesPanel.add(textbookLabel);
            // Repeat for each course and textbook as needed

            return coursesPanel;
        }

        private JPanel createNotificationsPanel() {
            // Simulate a notifications area for textbook updates or needs
            JPanel notificationsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel notificationLabel = new JLabel(
                    "Notification: A new edition of 'Introduction to Java Programming' is available.");
            notificationsPanel.add(notificationLabel);
            // Include additional notifications as needed

            return notificationsPanel;
        }
    }


}
