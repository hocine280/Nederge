package AMIServer.ManageAMI;

import java.util.HashMap;
import java.util.Iterator;

public class ProducerManage {
	
	private HashMap<Integer, String> listProducer;

	public ProducerManage(){
		this.listProducer = new HashMap<Integer, String>();
	};

	public int addProducer(String nameProducer){
		if(this.containsProducer(nameProducer)){
			return this.getCodeProducer(nameProducer);
		}

		int code;

		do {
			code = (int) (Math.random() * Integer.MAX_VALUE);
		} while (this.listProducer.containsKey(code));

		this.listProducer.put(code, nameProducer);

		return code;
	}

	public String getNameProducer(int code){
		return this.listProducer.get(code);
	}

	public boolean containsProducer(int codeProducer){
		return this.listProducer.containsKey(codeProducer);
	}

	public boolean containsProducer(String nameProducer){
		return this.listProducer.containsValue(nameProducer);
	}

	public int getCodeProducer(String nameProducer){
		Iterator<Integer> allCode = this.listProducer.keySet().iterator();
		int code = (int) allCode.next();
		
		while (allCode.hasNext() && !this.listProducer.get(code).equals(nameProducer)) {
			code = (int) allCode.next();
		}

		return code;
	}

	public void removeProducer(int code, String nameProducer){
		this.listProducer.remove(code, nameProducer);
	}

}
