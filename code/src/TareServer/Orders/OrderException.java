package TareServer.Orders;

public class OrderException extends Exception{

	String message;

	public OrderException(String message){
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}
	
}
