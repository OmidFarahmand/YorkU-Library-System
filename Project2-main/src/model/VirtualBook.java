package model;

public class VirtualBook extends Book implements VirtualItem {

    public int ID;
    public VirtualItemContent content;

    public VirtualBook(String title) {
        super(title);
    }

    @Override
    public void showContent() {
        // Handled by the GUI, redundant 
    }
}
