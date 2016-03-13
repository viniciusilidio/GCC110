
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente extends Criptografia implements Runnable {
	
	private Socket cliente;
	private String host;
	private String nickname;
	
	public Socket getCliente() {
		return cliente;
	}
	
	public Cliente (String host, String nickname) throws UnknownHostException, IOException, ConnectException {		
		super(1);
		
		this.nickname = nickname;
		this.host = host;	
		
		try {
			
			cliente = new Socket(this.host, Servidor.PORTA);
			PrintStream output = new PrintStream(cliente.getOutputStream());
			output.println(nickname);
			Thread tc = new Thread(this);
			tc.start();
			
		} catch (ConnectException e) {
			System.out.println("Servidor não existe");
		}
	}
	
	public void executa() throws IOException {
		Scanner teclado = new Scanner(System.in);
		PrintStream output = new PrintStream(cliente.getOutputStream());
		
	    while (teclado.hasNextLine()) {
	    	output.println(teclado.nextLine());
		}
	    
		teclado.close();
	}
	
	@Override
	public void run() {
		try {
			Scanner s = new Scanner(cliente.getInputStream());
			
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String [] args) throws ConnectException, UnknownHostException, IOException {
	
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
		
	}
}