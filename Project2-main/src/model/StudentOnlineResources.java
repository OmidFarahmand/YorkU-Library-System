package model;

public class StudentOnlineResources extends OnlineResources {

    public VirtualBook searchAccessibleBook(String input) {
        return new VirtualBook(input); // TO BE CHANGED LATER
    }

    public StudentOnlineResources(Client client) {
        super(client);
    }

}
