
/**
 * Classe Cliente
 * Classe usada para representar o Cliente antes e depois de se conectar ao servidor
 */

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
	private Tela tela;
	private PrintStream output;
	private String nickname;

	/**
	 * Retorna o Socket cliente
	 * 
	 * @return cliente
	 */
	public Socket getCliente() {
		return cliente;
	}

	/**
	 * Retorn o nickname do cliente
	 * 
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Constroi a classe cliente, conetando o com o servidor atraves do host
	 * (ip) passado como parametro. Dispara uma thread para tratar as mensagens
	 * que chegam do servidor ao Client
	 * 
	 * @param host
	 * @param nickname
	 */
	public Cliente(String host, String nickname) {
		this.host = host;
		this.nickname = nickname;
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

	/**
	 * Envia uma mensagem para o usuario do servidor especificado no parametro
	 * nickname, caso o servidor não exista exibe uma mensagen de aviso
	 * 
	 * @param nickname
	 * @param msg
	 */
	public void enviarMensagens(String nickname, String msg) {
		if (output == null)
			tela.showWarning("Servidor desconectado");
		else
			output.println(nickname + ":" + criptografar(msg));
	}

	/**
	 * Thread responsável por receber as mensagens redirecionadas do servidor ao
	 * usuario
	 */
	@Override
	public void run() {
		try {
			Scanner s = new Scanner(cliente.getInputStream());

			while (s.hasNextLine()) {

				String[] message = s.nextLine().split(":");

				String newMessage = message[0] + ":" + descriptografar(message[1]);

				tela.changeTextAreat(newMessage);
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

	/**
	 * Cria uma nova classe Cliente e a instância preenchendo a com o nome do
	 * usuario, o ip do servidor e a chave de criptografia
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LoginInfo login = Tela.login();
		Cliente client = new Cliente(login.getHost(), login.getNickname());
		client.setChave(login.getChaveCript());
		Thread tc = new Thread(client);
		tc.start();
	}
}