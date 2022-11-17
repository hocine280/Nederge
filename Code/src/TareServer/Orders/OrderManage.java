package TareServer.Orders;

import java.util.Hashtable;

public class OrderManage {

	private Hashtable<Integer, Order> listOrder;

	public OrderManage(){
		this.listOrder = new Hashtable<Integer, Order>();
	}
	
	public void addOrder(Order order){
		int idOrder;

		do {
			idOrder = generateIdOrder();
		} while (this.listOrder.containsKey(idOrder));

		order.setId(idOrder);
		order.setLogin(generateLoginOrder());

		this.listOrder.put(idOrder, order);
	}

	public void removeOrder(int id, String login){
		if(this.listOrder.containsKey(id) && this.listOrder.get(id).verifOrder(id, login)){
			this.listOrder.remove(id);
		}
	}

	private int generateIdOrder(){
		return 12;
	}

	private String generateLoginOrder(){
		return "";
	}

}
