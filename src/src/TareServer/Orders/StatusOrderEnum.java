package TareServer.Orders;

public enum StatusOrderEnum {
	
	UNAVAILABLE("L'énergie n'est pas disponible"),
	WAITING_VALIDATION("Commande disponible en attente de validation"),
	PROCESS("Commande en cours de traitement"),
	DELIVERY("Commande en cours de livraison"),
	DELIVERED("Commande livré"),
	CANCEL("Commande annulé");

	private String descritption;

	StatusOrderEnum(String descritpion){
		this.descritption = descritpion;
	}

	@Override
	public String toString() {
		return this.descritption;
	}

}
