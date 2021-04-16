package mall.client.vo;

public class Cart {
	private int cartNo;
	private String clientMail;
	private int ebookNo;
	private String cartDate;
	
	public int getCartNo() {
		return cartNo;
	}
	public void setCartNo(int cartNo) {
		this.cartNo = cartNo;
	}
	public String getClientMail() {
		return clientMail;
	}
	public void setClientMail(String clientMail) {
		this.clientMail = clientMail;
	}
	public int getEbookNo() {
		return ebookNo;
	}
	public void setEbookNo(int ebookNo) {
		this.ebookNo = ebookNo;
	}
	public String getCartDate() {
		return cartDate;
	}
	public void setCartDate(String cartDate) {
		this.cartDate = cartDate;
	}
	@Override
	public String toString() {
		return "Cart [cartNo=" + cartNo + ", clientMail=" + clientMail + ", ebookNo=" + ebookNo + ", cartDate=" + cartDate
				+ "]";
	}

	
	
}
