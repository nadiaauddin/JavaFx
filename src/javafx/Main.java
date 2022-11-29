package javafx;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStreamReader;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;



public class Main extends JFrame {
    public static void main(String[] args) {
        new Startscreen();
		
    }
	private static JPanel hangmanPanel, inputPanel;
    private static JTextField wTextField = new JTextField(20);
    private static BufferedImage hangman = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
    private static int WrongLetters = 0;
	private static JLabel Label = new JLabel("Pick a word"), rightletterLabel, wrong = new JLabel();
	private static String Guess = null;
    private static String wrongLString = "Wrong letters: ", rightletter = "";
	

    public Main() {
		setTitle("Hangman");
		setLayout(new BorderLayout());
        drawsStand(hangman);

        inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.gridy = 1;
        inputPanel.add(Label, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        inputPanel.add(wTextField, gridBagConstraints);

        add(inputPanel);
	

        wTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Guess == null) {
                    Guess = wTextField.getText().toLowerCase();
                    if (Guess.length() <= 0) {
                        try {
                            String str;
                            List<String> dictionary = new ArrayList();
							URL url = new URL("https://www.dictionary.com/.txt");
                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
							while ((str = in.readLine()) != null) {
                                dictionary.add(str);
                            }
                            in.close();
                            Guess = dictionary.get( (int) (dictionary.size()*Math.random()) );
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    wTextField.setText("");
                    Label.setText("Guess a letter ");
                    String displayWord = "";
                    for (int i = 0; i < Guess.length(); i++) {
                        rightletter += "_";
                        displayWord += " _ ";
                    }
                    rightletterLabel = new JLabel(displayWord);
                    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
                    gridBagConstraints1.gridx = 1;
                    gridBagConstraints1.gridy = 2;
                    gridBagConstraints1.gridwidth = 2;
                    inputPanel.add(rightletterLabel, gridBagConstraints1);
                    gridBagConstraints1 = new GridBagConstraints();
                    gridBagConstraints1.gridx = 1;
                    gridBagConstraints1.gridy = 3;
                    gridBagConstraints1.gridwidth = 2;
                    inputPanel.add(wrong, gridBagConstraints1);

                    return;
                }

                if (Guess.indexOf(wTextField.getText()) >= 0) {
                    Right();
                } else {
                    WrongGuess();
                    wTextField.setText("");
                }


            }
        });

        setSize(1000, 600);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Hangman();
        revalidate();

    }



		private void Right() {
			String guess = wTextField.getText().toLowerCase();
			addtheGuess(guess);
			String displayString = "";
			for (int i = 0; i < rightletter.length(); i++) {
				displayString += rightletter.substring(i, i + 1) + " ";
			}
			rightletterLabel.setText(displayString);

			if (rightletter.indexOf("_") < 0) {
				JOptionPane.showMessageDialog(this, "You Won!");
			}
			wTextField.setText("");
		}


    private void addtheGuess(String guess) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int index = Guess.indexOf(guess);
             index >= 0;
             index = Guess.indexOf(guess, index + 1)) {
            indexes.add(index);
        }
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.get(i);
            StringBuilder stringBuilder = new StringBuilder(rightletter);
            stringBuilder.replace(index, index + guess.length(), guess);
    		rightletter = stringBuilder.toString();
        }
    }

	private static void drawsStand(BufferedImage image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.red);
		g.drawLine(100, 100, 250, 100);
        g.drawLine(100, 475, 100, 100);
        g.drawLine(250, 100, 250, 200);
		g.drawLine(10, 475, 250, 475);
		g.dispose();
    }

    private void WrongGuess() {
        WrongLetters++;

        wrongLString += wTextField.getText() + ", ";
        wrong.setText(wrongLString);
        Graphics2D g = (Graphics2D) hangman.getGraphics();
        int x = 250, y = 200;
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.red);

        switch (WrongLetters) {
            case 1: 
				g.drawOval(-20 + x, y, 40, 40);
				break;
            case 2: 
				g.drawLine(x, y + 40, x, y + 40 + 80);
				break;
            case 3: 
                g.drawLine(x, y + 40 + 20, x + 20, y + 40 + 60);
                break;
            case 4: 
                g.drawLine(x, y + 40 + 20, x - 20, y + 40 + 60);
                break;
            case 5: 
                g.drawLine(x, y + 40 + 80, x + 20, y + 40 + 80 + 40);
                break;
            case 6: 
                g.drawLine(x, y + 40 + 80, x - 20, y + 40 + 80 + 40);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Game Over :(");
                break;
        }
        g.dispose();
        Hangman();
        revalidate();
    }

    private void Hangman() {
        if (hangmanPanel != null)
            remove(hangmanPanel);

        hangmanPanel = new JPanel();
        hangmanPanel.add(new JLabel(new ImageIcon(hangman)));
        add(hangmanPanel, BorderLayout.WEST);
        revalidate();

    }

  
}