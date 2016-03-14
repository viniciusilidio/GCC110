
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
	private String path;
	private Mensagens msg;
	private List<User> contatos;
	
	
	public Socket getCliente() {
		return cliente;
	}
	
	public Cliente (String host, String nickname)  {
		super();
		this.host = host;	
		path = nickname + ".txt";
		msg = new Mensagens();
		contatos = new ArrayList<User>();
		
		try {
			
			cliente = new Socket(this.host, Servidor.PORTA);
			PrintStream output = new PrintStream(cliente.getOutputStream());
			output.println(nickname);
			Thread tc = new Thread(this);
			tc.start();
		
		} catch (UnknownHostException e) {
			System.out.println("Servidor não existe");
		} catch (IOException e) {
			// Não faz nada caso o servidor não exista
		}
		
	}

	public void executa() {
		
		try {
			Scanner teclado = new Scanner(System.in);
			PrintStream output = new PrintStream(cliente.getOutputStream());
			
		    while (teclado.hasNextLine()) {
		    	String[] message = teclado.nextLine().split(":");
		    	
		    	String newMessage = message[0] + ":" + criptografar(message[1]);
		    			    	
		    	output.println(newMessage);
			}
		    
			teclado.close();
		} catch (NullPointerException e) {
			// Não faz nada caso o servidor não exista
		} catch (IOException e) {
			// Não faz nada caso o servidor não exista
		}
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
		client.executa();
		Thread tc = new Thread(client);
		tc.start();
		s.close();
	}
}