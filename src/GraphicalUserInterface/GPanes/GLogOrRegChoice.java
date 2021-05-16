package GraphicalUserInterface.GPanes;

import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.WindowPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class GLogOrRegChoice implements WindowPane {

    private String answer;
    private JPanel panel;
    private GInterface gInterface;
    private ResourceBundle resourceBundle;

    public GLogOrRegChoice(GInterface gInterface){
        this.gInterface = gInterface;
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    private JPanel createChoicePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        JButton log = new JButton(resourceBundle.getString("LOGIN"));
        log.setFont(log.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));

        JButton reg = new JButton(resourceBundle.getString("REGISTER"));
        reg.setFont(reg.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));

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
        return createChoicePanel();
    }
}
