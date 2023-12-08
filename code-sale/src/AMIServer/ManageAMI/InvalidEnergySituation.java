package AMIServer.ManageAMI;

public enum InvalidEnergySituation {
	
	/**
		 * Si une énergie existe déjà
		 * @since 1.0
		 */
		AlreadyExist("L'énergie existe déjà"),
		/**
		 * Si une énergie est déjà vendu
		 * @since 1.0
		 */
		AlreadySold("L'énergie est déjà vendu"),
		/**
		 * Si une énergie n'existe pas
		 * @since 1.0
		 */
		NotExist("L'énergie n'existe pas"),
		/**
		 * Si le prix d'une énergie est trop haut
		 * @since 1.0
		 */
		PriceTooHigh("Le prix de l'énergie est trop haut"),
		/** 
		 * Si le prix d'une énergie est trop bas
		 * @since 1.0
		 */
		PriceTooLow("Le prix de l'énergie est trop faible"),
		/**
		 * Si le prix d'une énergie ne correspond pas
		 * @since 1.0
		 */
		PriceNotCorrespond("Le prix ne correspond pas"),
		/**
		 * Si le code de suivi TrackingCode est invalide
		 * @since 1.0
		 */
		TrackingCodeInvalid("Le code de suivi (TrackingCode) est invalide"),
		/**
		 * Si le code du producteur est incorrect
		 * @since 1.0
		 */
		ProducerInvalid("Le code du producteur ne correspond pas");

		/** Le message expliqant le problème qui est survenue */
		private String message;
		/**
		 * Constructeur par initialisation de l'énumération
		 * @param message Le message explicitant le cas d'erreur qui est survenue
		 * 
		 * @since 1.0
		 */
		InvalidEnergySituation(String message){
			this.message = message;
		}

		/**
		 * Surcharge de la méthode toString
		 * 
		 * @return La chaine de caractères représentant un des cas d'erreur
		 */
		@Override
		public String toString(){
			return this.message;
		}

}
