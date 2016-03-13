import java.net.Socket;

public class User {

	private String nickname;
	private Socket socket;
	
	public User (String nickname, Socket socket) {
		this.nickname = nickname;
		this.socket = socket;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
