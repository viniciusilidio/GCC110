
/**
 * Classe responsável por ler e armazenar as mensagens enviadas e recebidas pelos usuarios
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Mensagens {

	/**
	 * Carrega as mensagens antigas entre dois usuarios
	 * 
	 * @param usuario
	 * @param nickname
	 * @return String contendo todas as mensagens do arquivo
	 */
	public String carregarMensagens(String usuario, String nickname) {

		String arquivo = usuario + ";" + nickname + ".txt";
		try {
			File f = new File(arquivo);

			if (!f.exists())
				f.createNewFile();

		} catch (IOException e) {
			System.out.println("Arquivo ja existe");
		}

		StringBuilder stb = new StringBuilder();

		try {

			InputStream input = new FileInputStream(arquivo);

			Scanner entrada = new Scanner(input);
			String line;

			while (entrada.hasNextLine()) {
				line = entrada.nextLine();
				stb.append(line);
				stb.append("\n");
			}

			entrada.close();
			return stb.toString();

		} catch (FileNotFoundException e) {
			return "Arquivo contendo as mensagens não foi encontrado";
		} catch (NoSuchElementException e) {
			return "Conversas com o usuário " + nickname + " não encontrada\n";
		}
	}

	/**
	 * Grava as mensagens entre dois usuarios em um arquivo de texto
	 * 
	 * @param usuario
	 * @param nickname
	 * @param message
	 */
	public void gravarMensagens(String usuario, String nickname, String message) {
		String arquivo = usuario + ";" + nickname + ".txt";
		FileWriter out;

		try {
			out = new FileWriter(arquivo, true);
			out.write(message + "\n");
			out.close();
		} catch (IOException e) {
			System.out.println("Arquivo não encontrado");
		}
	}
}
