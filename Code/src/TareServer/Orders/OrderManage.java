package TareServer.Orders;

import java.util.Hashtable;

public class OrderManage {

	private Hashtable<Integer, Order> listOrder;

	public OrderManage(){
		this.listOrder = new Hashtable<Integer, Order>();
	}
	
	public void addOrder(int idOrder, Order order){

		this.listOrder.put(idOrder, order);
	}

	public void removeOrder(int id, String login){
		if(this.listOrder.containsKey(id) && this.listOrder.get(id).verifOrder(id, login)){
			this.listOrder.remove(id);
		}
	}

	public int generateIdOrder(){
		int idOrder;
		
		do {
			idOrder = (int) (this.listOrder.size() + Math.random() * (Integer.MAX_VALUE - this.listOrder.size()));
		} while (this.listOrder.containsKey(idOrder));

		return idOrder;
	}

	public String generateLoginOrder(){
		return "test";
	}

	public Order getOrder(int idOrder){
		return this.listOrder.get(idOrder);
	}

}
