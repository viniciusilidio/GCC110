/**
 * Classe responsável por criptografar e descriptografar as mensagens enviadas
 * pelos usuarios
 */

public class Criptografia {

	private int chave = 0;

	/**
	 * Criptografa a string passado como parametro usando a chave continda na
	 * instancia da classe, caso a chave seja 0 o retorno da criptografia sera a
	 * mensagem original sem modificações
	 * 
	 * @param message
	 * @return criptedString
	 */
	public String criptografar(String message) {

		char charMessage[] = message.toCharArray();
		char criptMessage[] = new char[message.length()];

		for (int i = 0; i < message.length(); i++) {
			char criptedChar = (char) (((int) charMessage[i]) + chave * (i + 1));
			criptMessage[i] = criptedChar;
		}

		String criptedString = String.valueOf(criptMessage);

		return criptedString;
	}

	/**
	 * Descriptografa a string passado como parametro usando a chave continda na
	 * instancia da classe
	 * 
	 * @param message
	 * @return decryptedString
	 */
	public String descriptografar(String message) {
		char charMessage[] = message.toCharArray();
		char decryptedMessage[] = new char[message.length()];

		for (int i = 0; i < message.length(); i++) {
			char criptedChar = (char) (((int) charMessage[i]) - chave * (i + 1));
			decryptedMessage[i] = criptedChar;
		}

		String decryptedString = String.valueOf(decryptedMessage);

		return decryptedString;
	}

	/**
	 * retorn a chave de criptografia
	 * @return chave
	 */
	public int getChave() {
		return chave;
	}

	/**
	 * Define a chave de criptografia
	 * @param chave
	 */
	public void setChave(int chave) {
		this.chave = chave;
	}
}
