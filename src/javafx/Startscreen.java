package javafx;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startscreen implements ActionListener{

    JFrame frame = new JFrame();
    JButton myButton = new JButton("Start");
    

    Startscreen(){
        myButton.setBounds(400,260,200,40);
        myButton.setFocusable(false);
        myButton.addActionListener(this);
        frame.add(myButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==myButton) {
            frame.dispose();
            new Main();
            
        }
        
    }

    
}
