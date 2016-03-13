import java.util.InputMismatchException;
import java.util.Scanner;

public class Criptografia {
	
	private int chave = 0;
	
	public Criptografia (int op) {
		if (op != 0) {
		
			Scanner s = new Scanner(System.in);
			
			System.out.println("Digite a chave de criptografia (número inteiro):");
			System.out.println("Case a chave de criptografia esteja incorreta o texto ficara incompreensível");
			
			fillCript(s);
		}
	}
	
	
	private void fillCript (Scanner s) {
		int criptChave;
		
		try {
			criptChave = s.nextInt();
			chave = criptChave;
		} catch (InputMismatchException e) {
			s.nextLine();
			System.out.println("Digite um número inteiro:");
			fillCript(s);
		}
	}
	
	public String criptografar (String message) {
	
		char charMessage[] = message.toCharArray();
		char criptMessage[] = new char[message.length()];
		
		for (int i = 0; i<message.length(); i++) {
			char criptedChar = (char) (((int)charMessage[i]) + chave * (i + 1));
			criptMessage[i] = criptedChar;
		}
		
		String criptedString = String.valueOf(criptMessage);
		
		return criptedString;
	}
	
	public String descriptografar (String message, int chave) {
		char charMessage[] = message.toCharArray();
		char decryptedMessage[] = new char[message.length()];
		
		for (int i = 0; i<message.length(); i++) {
			char criptedChar = (char) (((int)charMessage[i]) - chave * (i + 1));
			decryptedMessage[i] = criptedChar;
		}
		
		String decryptedString = String.valueOf(decryptedMessage);
		
		return decryptedString;
	}
	
	public int getChave () {
		return chave;
	}
	
	public void setChave(int chave) {
		this.chave = chave;
	}
}
