package TareServer.Orders;

import java.util.Hashtable;
import java.util.Random;

import org.json.JSONArray;

public class OrderManage {

	private Hashtable<Integer, Order> listOrder;

	public OrderManage(){
		this.listOrder = new Hashtable<Integer, Order>();
	}
	
	public void addOrder(int idOrder, Order order) throws OrderException{
		if(!this.listOrder.containsKey(idOrder)){
			this.listOrder.put(idOrder, order);
		}
	}

	public void removeOrder(int id, String login) throws OrderException{
		if(!this.listOrder.containsKey(id) || !this.listOrder.get(id).verifOrder(id, login)){
			throw new OrderException("Le champ idOrderForm ou loginOrder est incorrect.");
		}

		this.listOrder.remove(id);
	}

	public int generateIdOrder(){
		int idOrder;
		
		do {
			idOrder = (int) (Math.random() * (Integer.MAX_VALUE));
		} while (this.listOrder.containsKey(idOrder));

		return idOrder;
	}

	public String generateLoginOrder(){
		String login = "";
		Random rand = new Random();

		for (int i = 0; i < 6; i++) {
			login += (char) (rand.nextInt(26) + 97);
		}

		return login;
	}

	public Order getOrder(int idOrder){
		return this.listOrder.get(idOrder);
	}

	public JSONArray listOrder(){
		JSONArray array = new JSONArray();

		this.listOrder.forEach((id, order)->{
			array.put(order.toJson());
		});

		return array;
	}

}
