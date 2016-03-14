/**
 * Classe contendo as informações de login do usuario
 */

public class LoginInfo {

	private String host;
	private String nickname;
	private int chaveCript;

	/**
	 * Constroi a classe com a ip do servidor, o apelido do usuario e a chave de
	 * criptografia
	 * @param host
	 * @param nickname
	 * @param chaveCript
	 */
	public LoginInfo(String host, String nickname, int chaveCript) {
		this.host = host;
		this.nickname = nickname;
		this.chaveCript = chaveCript;
	}

	/**
	 * Retorna o IP do host
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Retorno o apelido do usuario
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Retorna a chave de criptografia
	 * @return chaveCript
	 */
	public int getChaveCript() {
		return chaveCript;
	}
}
