package model;

public class RequestForum {
    public LibraryManagementTeam managementTeam;
    public static boolean objectCreated = false;
    private static RequestForum r;
    
    public static RequestForum createRequestForum() {
    	if(objectCreated == false) {
    		r = new RequestForum();
    		objectCreated = true;
    	}
    	
    	
        
        return r;
    }

    public String makeARequest(Request request) {
        return "";
    }

    public int assessBooksPriority(Request request) {
        return 0;
    }

}
