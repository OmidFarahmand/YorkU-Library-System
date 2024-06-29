package model;

public class VirtualItemSearchResult {

    public VirtualBook virtualBook;
    public VirtualBook getVirtualBook() {
		return virtualBook;
	}


	public void setVirtualBook(VirtualBook virtualBook) {
		this.virtualBook = virtualBook;
	}


	public UniProvidedNewsletter getUniNewsletter() {
		return uniNewsletter;
	}


	public void setUniNewsletter(UniProvidedNewsletter uniNewsletter) {
		this.uniNewsletter = uniNewsletter;
	}


	public PaidNewsletter getPaidNewsletter() {
		return paidNewsletter;
	}


	public void setPaidNewsletter(PaidNewsletter paidNewsletter) {
		this.paidNewsletter = paidNewsletter;
	}


	public UniProvidedNewsletter uniNewsletter;
    public PaidNewsletter paidNewsletter;

    
    public VirtualItemSearchResult(VirtualBook virtualBook, UniProvidedNewsletter uniNewsletter,
            PaidNewsletter paidNewsletter) {
        this.virtualBook = virtualBook;
        this.uniNewsletter = uniNewsletter;
        this.paidNewsletter = paidNewsletter;
    }



    
}
