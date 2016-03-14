import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Mensagens {

	public String carregarMensagens (String usuario, String nickname) {
		
		StringBuilder stb = new StringBuilder();
		
		try {
			String arquivo = usuario + ";" + nickname + ".txt";
						
			InputStream input = new FileInputStream(arquivo);
			
			System.out.println(input);
			
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
	
	public void gravarMensagens (String usuario, String nickname) {
		
		try {
			String arquivo = usuario + ";" + nickname + ".txt";
			
			File f = new File(arquivo);
			
			if (f.exists()) {
				
				
				
				
			} else {
				f.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("Arquivo ja existe");
		}
		
	}
}
