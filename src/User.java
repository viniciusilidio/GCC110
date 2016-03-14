
/**
 * Classe usada para representar o usuario apos se conectar ao servidor. 
 * Contem as informações para o servidor redirecionar as mensagens para
 * o User correto
 */
import java.net.Socket;

public class User {

	private String nickname;
	private Socket socket;

	/**
	 * Constroi a classe User
	 * 
	 * @param nickname
	 * @param socket
	 */
	public User(String nickname, Socket socket) {
		this.nickname = nickname;
		this.socket = socket;
	}

	/**
	 * Retorna o apelido do usuario
	 * 
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Retorna a conexão do servidor para o usuario
	 * @return socket
	 */
	public Socket getSocket() {
		return socket;
	}
}
