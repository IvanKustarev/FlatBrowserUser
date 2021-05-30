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
        JPanel mainPanel = new JPanel();
        JPanel abstractMainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(350, 400));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        mainPanel.setLayout(new GridLayout(2,1));

        JButton log = new JButton(resourceBundle.getString("LOGIN"));
        log.setFont(new Font("Dialog", Font.PLAIN, 25));
        log.setBackground(new Color(0xFFD9ECEF, true));

        JButton reg = new JButton(resourceBundle.getString("REGISTER"));
        reg.setFont(new Font("Dialog", Font.PLAIN, 25));
        reg.setBackground(new Color(0xFFD9ECEF, true));


        mainPanel.add(log);
        mainPanel.add(reg);
        abstractMainPanel.add(mainPanel);

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

        return abstractMainPanel;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public JPanel getPanel() {
        return createChoicePanel();
    }
}
