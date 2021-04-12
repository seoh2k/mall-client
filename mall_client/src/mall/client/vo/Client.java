package mall.client.vo;

public class Client {
	private int clientNo;
	private String clienMail;
	private String clientPw;
	private String clientDate;
	private String clientMail;
	
	public int getClientNo() {
		return clientNo;
	}
	public void setClientNo(int clientNo) {
		this.clientNo = clientNo;
	}
	public String getClienMail() {
		return clienMail;
	}
	public void setClienMail(String clienMail) {
		this.clienMail = clienMail;
	}
	public String getClientPw() {
		return clientPw;
	}
	public void setClientPw(String clientPw) {
		this.clientPw = clientPw;
	}
	public String getClientDate() {
		return clientDate;
	}
	public void setClientDate(String clientDate) {
		this.clientDate = clientDate;
	}
	public String getClientMail() {
		return clientMail;
	}
	public void setClientMail(String clientMail) {
		this.clientMail = clientMail;
	}
	
	@Override
	public String toString() {
		return "Client [clientNo=" + clientNo + ", clienMail=" + clienMail + ", clientPw=" + clientPw + ", clientDate="
				+ clientDate + ", clientMail=" + clientMail + "]";
	}
	
	
}