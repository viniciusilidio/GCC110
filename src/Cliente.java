
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente extends Criptografia implements Runnable {
	
	private Socket cliente;
	private String host;
	private List<User> contatos;
	private Tela tela;
	private PrintStream output;
	private String nickname;
	
	public Socket getCliente() {
		return cliente;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public Cliente (String host, String nickname)  {
		super();
		this.host = host;	
		this.nickname = nickname;
		contatos = new ArrayList<User>();
		tela = new Tela(this, nickname);
		
		try {
			cliente = new Socket(this.host, Servidor.PORTA);
			output = new PrintStream(cliente.getOutputStream());
			output.println(nickname);
			Thread tc = new Thread(this);
			tc.start();
		
		} catch (UnknownHostException e) {
			System.out.println("Servidor não existe");
		} catch (IOException e) {
			// Não faz nada caso o servidor não exista
		}
		
	}
	
	public void enviarMensagens (String nickname, String msg) {
		output.println(nickname + ":" + criptografar(msg));
	}
	
	@Override
	public void run() {
		try {
			Scanner s = new Scanner(cliente.getInputStream());
			
			while (s.hasNextLine()) {
				
				String[] message = s.nextLine().split(":");
								
				String newMessage = message[0] + ":" + descriptografar(message[1]);
				
				System.out.println(newMessage);
			}
			
			s.close();
			System.out.println("Servidor desconectou");
			Thread.currentThread().stop();
			
		} catch (NullPointerException e) {
			// Não faz nada caso o servidor não exista
		} catch (IOException e) {
			// Não faz nada caso o servidor não exista
		}
	}
	
	public static void main (String [] args) {
	
		System.out.println("Iniciando cliente ...");
		Scanner s = new Scanner(System.in);
		System.out.println("Digite o seu nickname");
		String nickname = s.nextLine();
		System.out.println("Digite o IP do computador que deseja conectar");
		String ip = s.nextLine();
		Cliente client = new Cliente(ip, nickname);
		Thread tc = new Thread(client);
		tc.start();
		s.close();
	}
}