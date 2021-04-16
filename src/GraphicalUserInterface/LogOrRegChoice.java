package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogOrRegChoice implements WindowPart{

    private String answer;
    private JPanel panel;

    public LogOrRegChoice(){
        panel = createChoicePanel();
    }

    private JPanel createChoicePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        JButton log = new JButton("LOGIN");
        JButton reg = new JButton("REGISTER");
        panel.add(log);
        panel.add(reg);

        log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer = "0";
            }
        });
        reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer = "1";
            }
        });

        return panel;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
