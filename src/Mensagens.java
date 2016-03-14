import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Mensagens {

	public void carregarMensagens (String path, String nickname) {
		
		try {
			InputStream input = new FileInputStream(path);
		    Scanner entrada = new Scanner(input);
		    String condition = "/---" + nickname + "---/";
		    String line;
		    
		    while (entrada.hasNextLine()) {
		    			    	
		    	while (!entrada.nextLine().equals(condition));
		    	
		    	while (!(line = entrada.nextLine()).equals("/---END---/")) {
		    		System.out.println(line);
		    	}
		    }
		    
		    entrada.close();
		    
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo contendo as mensagens não foi encontrado");
		} catch (NoSuchElementException e) {
			System.out.printf("Conversas com o usuário %s não encontrada\n", nickname);
		}
	}
	
	public static void main (String[] args) {
		
		Mensagens msg = new Mensagens();
		msg.carregarMensagens("Vinicius.txt", "Gustavo");
	}
}
