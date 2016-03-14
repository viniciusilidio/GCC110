
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Tela {

	private JFrame tela;
	private JPanel painelPrincipal;
	private JPanel painelContatos;
	private List<JButton> contatos;
	private JTextField chatInput;
	private JTextArea chatConversation;
	private final static int WIDTH = 800;
	private final static int HEIGHT = 500;

	private Cliente client;
	private String currentlyActivated;
	private Mensagens msg;

	public Tela(Cliente client, String nickname) {
		this.client = client;
		msg = new Mensagens();
		preparaTela(nickname);
		preparaPainelPrincipal();
		botaoCarregar();
		preparaAreasDeTexto();
		preparaContatos();
		mostraTela();
	}

	private void preparaTela(String nickname) {
		tela = new JFrame("Chat criptografado - GCC110 " + nickname);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setLayout(null);
	}

	private void preparaPainelPrincipal() {
		painelPrincipal = new JPanel();
		painelPrincipal.setSize(WIDTH, HEIGHT);
		painelPrincipal.setLayout(null);
	}

	private void preparaAreasDeTexto() {
		chatInput = new JTextField();
		chatInput.setBounds(10, 440, 620, 50);
		chatInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentlyActivated != null) {
					String texto = chatInput.getText();
					client.enviarMensagens(currentlyActivated, texto);
					chatConversation.setText(chatConversation.getText() + texto + "\n");
					chatInput.setText("");
				}
			}
		});

		painelPrincipal.add(chatInput);

		chatConversation = new JTextArea();
		chatConversation.setLayout(null);
		chatConversation.setBounds(10, 10, 620, 420);
		chatConversation.setEditable(false);
		chatConversation.setBorder(BorderFactory.createRaisedBevelBorder());
		painelPrincipal.add(chatConversation);
	}

	private void preparaContatos() {
		contatos = new ArrayList<JButton>();

		painelContatos = new JPanel();
		painelContatos.setBackground(Color.GRAY);
		painelContatos.setBorder(BorderFactory.createRaisedBevelBorder());
		painelContatos.setBounds(640, 10, 150, 420);
		painelContatos.setLayout(new GridLayout(0, 1));

		JButton addContatos = new JButton("Adcionar");
		addContatos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nome = JOptionPane.showInputDialog("Nome do contato");
				if (!nome.equals("")) {
					JButton contact = new JButton(nome);
					contact.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							currentlyActivated = contact.getText();
							chatConversation.setText(msg.carregarMensagens(client.getNickname(), currentlyActivated));
						}
					});
					contatos.add(contact);
					painelContatos.add(contact);
					painelContatos.revalidate();
				}
			}
		});

		JButton removeContatos = new JButton("Remover");
		removeContatos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nome = JOptionPane.showInputDialog("Nome do contato");

				if (nome.equals(currentlyActivated)) {
					currentlyActivated = null;
					chatConversation.setText("");
				}
				for (JButton j : contatos) {
					if (j.getText().equals(nome)) {
						painelContatos.remove(j);
						painelContatos.revalidate();
					}
				}

			}
		});

		painelContatos.add(removeContatos);
		painelContatos.add(addContatos);

		tela.add(painelContatos);
	}

	private void botaoCarregar() {
		JButton carregarArquivo = new JButton("Carregar IMG");

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
		carregarArquivo.setBounds(640, 440, 150, 50);
		painelPrincipal.add(carregarArquivo);
	}

	private void fillTextArea(String text) {
		chatConversation.setText(text);
	}

	private void showImage(BufferedImage image) {
		ImageIcon icon = new ImageIcon(image);

		JOptionPane.showMessageDialog(null, icon);
	}

	private void mostraTela() {
		tela.add(painelPrincipal);
		tela.setSize(WIDTH, HEIGHT);
		tela.setResizable(false);
		tela.setVisible(true);
	}
}
