import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tela {

	private JFrame tela;
	private JPanel painelPrincipal;
	
	public Tela () {
		preparaTela();
		preparaPainelPrincipal();
		botaoCarregar();
		mostraTela();
	}
	
	private void preparaTela () {
		tela = new JFrame("Chat criptografado - GCC110");
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void preparaPainelPrincipal () {
		painelPrincipal = new JPanel();
		tela.add(painelPrincipal);
	}
	
	private void botaoCarregar () {
		JButton carregarArquivo = new JButton("Carregar imagem");
		
		carregarArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int op = chooser.showOpenDialog(null);
				
				if (op == JFileChooser.APPROVE_OPTION) {
					File getArquivo;
					try {
						getArquivo = chooser.getSelectedFile();
						BufferedImage image = ImageIO.read(getArquivo);
						
						showImage(image);
						
					} catch (IOException fnf) {
						System.out.println("Arquivo não encontrado");
					}
				}
			}
		});
		
		carregarArquivo.setLayout(null);
		carregarArquivo.setBounds(690, 440, 100, 50);
		painelPrincipal.add(carregarArquivo);
	}
	
	private void showImage (BufferedImage image) {
		ImageIcon icon = new ImageIcon(image);
		
		JOptionPane.showMessageDialog(null, icon);
	}
	
	
	private void mostraTela () {
		tela.repaint();
		tela.setSize(800, 500);
		tela.setResizable(false);
		tela.setVisible(true);
	}
	
	public static void main (String[] args) {
		Tela frame = new Tela();
	}
}
