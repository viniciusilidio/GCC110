
/**
 * Classe usada para representar o servidor
 */
import java.io.IOException;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor implements Runnable {

	public static int PORTA = 12345;
	private static List<User> clientes;
	private static ServerSocket servidor;
	private static User justConnected;

	/**
	 * Constro o servidor usando a constante PORTA como a porta de conexão do
	 * socket
	 * 
	 * @param porta
	 */
	public Servidor() {
		System.out.println("Iniciando servidor ...");
		clientes = new ArrayList<User>();
	}

	/**
	 * Executa o servidor e aceita atraves de um loop infinito a conexão de
	 * vários usuarios, disparando uma thread para tratar as mensagens por eles
	 * enviadas individualmente
	 */
	public void executa() {
		Scanner s;

		try {
			servidor = new ServerSocket(PORTA);
			System.out.printf("Porta %d aberta\n", PORTA);
			while (true) {

				/*
				 * Espera um cliente se conectar ao servidor, cria um objeto
				 * para este cliente e lança uma thread para tratar as mensagens
				 * enviadas por ele
				 */

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
		} catch (IOException e) {
			// Não faz nada caso a porta esteja sendo usada
		}
	}

	/**
	 * Redireciona as mensagens enviadas pelos usuarios as destinatarios
	 * corretos
	 */
	private void redirecionarMensagens() {

		try {
			User client = justConnected;
			Scanner s = new Scanner(client.getSocket().getInputStream());

			while (s.hasNextLine()) {

				String messageReceived = s.nextLine();
				String[] message = messageReceived.split(":");

				for (User u : clientes) {

					if (u.getNickname().equals(message[0])) {

						PrintStream output = new PrintStream(u.getSocket().getOutputStream());

						if (u.getNickname().equals(client.getNickname())) {
							output.println("Servidor: Você não pode enviar mensagens para si mesmo");
							break;
						}

						String newMessage = client.getNickname() + ":" + message[1];

						System.out.println(newMessage);

						output.println(newMessage);

						break;
					}
				}
			}

			System.out.printf("O cliente %s desconectou\n", client.getNickname());
			clientes.remove(client);
			s.close();

		} catch (IOException e) {
			//
		}
	}

	/**
	 * Dispara uma threada para redirecionar as mensagens de cada usuario que se
	 * conecta
	 */
	@Override
	public void run() {
		redirecionarMensagens();
	}

	/**
	 * Cria e executa um servidor
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		Servidor server = new Servidor();
		server.executa();
	}
}