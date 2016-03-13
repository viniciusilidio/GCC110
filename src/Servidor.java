
import java.io.IOException;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor implements Runnable {
	
	final static int PORTA = 12345;
	private static List<User> clientes;
	private static ServerSocket servidor;
	private static User justConnected;
	
	public Servidor (int porta) {
		System.out.println("Iniciando servidor ...");
		clientes = new ArrayList<User>();
	}
	
	public void executa() throws IOException {
		Scanner s;
		
		try {
			servidor = new ServerSocket(PORTA);
			System.out.printf("Porta %d aberta\n", PORTA);
			while (true) {
				/* Espera um cliente se conectar ao servidor, cria um objeto para este cliente
				 * e lança uma thread para tratar as mensagens enviadas por ele */
				
				Socket c = servidor.accept();
				s = new Scanner(c.getInputStream());
				User user = new User(s.nextLine(), c);
				justConnected = user;
				
				System.out.println("Nova conexão com o cliente " + user.getNickname());
			
				Thread t = new Thread(this);
				t.start();
				
				Servidor.clientes.add(user);
			}
		} catch (BindException e) {
			System.out.printf("Porta %d ocupada\n", PORTA);
		}
	}

	private void redirecionarMensagens () throws IOException {
		
		User client = justConnected;
		Scanner s = new Scanner(client.getSocket().getInputStream());
				
		while (s.hasNextLine()) {
						
			String messageReceived = s.nextLine();
			String[] message = messageReceived.split(";");
			
			for (User u : clientes) {
								
				if (u.getNickname().equals(message[0])) {
					
					PrintStream output = new PrintStream(u.getSocket().getOutputStream());
					
					if (u.getNickname().equals(client.getNickname())) {
						output.println("Servidor: Você não pode enviar mensagens para si mesmo");
						break;
					}
					
					String newMessage = client.getNickname() + ": " + message[1];
					
					output.println(newMessage);
					
					break;
				}
			}
		}
	}
	
	
	@Override
	public void run () {
		try {
			redirecionarMensagens();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) throws IOException {
		Servidor server = new Servidor(PORTA);
		server.executa();
	}
}