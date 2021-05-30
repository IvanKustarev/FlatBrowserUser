package GraphicalUserInterface.GPanes;

import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.WindowPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class GLogin implements WindowPane, Runnable{

    private String login;
    private String password;
    private Lock lock;
    private Condition condition;
    private GInterface gInterface;
    private ResourceBundle resourceBundle;

    public GLogin(Lock lock, Condition condition, GInterface gInterface){
        this.lock = lock;
        this.condition = condition;
        this.gInterface = gInterface;
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }


    private JPanel createLoginPanel(){

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));


        JPanel abstractLabelConnect = new JPanel();
        JLabel connectLabel = new JLabel(resourceBundle.getString("Ввод данных"));
        connectLabel.setFont(new Font("Dialog", Font.PLAIN, 25));
        abstractLabelConnect.add(connectLabel);
        mainPanel.add(abstractLabelConnect);


        JPanel abstractLogText = new JPanel();
        JTextField logText = new JTextField(15);
        logText.setForeground(Color.GRAY);
        logText.setFont(new Font("Dialog", Font.ITALIC, 20));
        logText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        logText.setText(resourceBundle.getString("Login"));
        logText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!logText.getText().equals(resourceBundle.getString("Login"))){
                }
                else {
                    logText.setText(null);
                    logText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(logText.getText().length() == 0){
                    logText.setText(resourceBundle.getString("Login"));
                    logText.setForeground(Color.GRAY);
                }
            }
        });
        abstractLogText.add(logText);
        mainPanel.add(abstractLogText);


        JPanel abstractPassText = new JPanel();
        JPasswordField passText = new JPasswordField(15);
        passText.setEchoChar((char) 0);
        passText.setForeground(Color.GRAY);
        passText.setFont(new Font("Dialog", Font.ITALIC, 20));
        passText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        passText.setText(resourceBundle.getString("Password:"));
        passText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!passText.getText().equals(resourceBundle.getString("Password:"))){
                    passText.setEchoChar('*');
                }
                else {
                    passText.setEchoChar('*');
                    passText.setText(null);
                    passText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(passText.getText().length() == 0){
                    passText.setEchoChar((char)0);
                    passText.setText(resourceBundle.getString("Password:"));
                    passText.setForeground(Color.GRAY);
                }
            }
        });
        abstractPassText.add(passText);
        mainPanel.add(abstractPassText);



        JPanel abstractLogButton = new JPanel();
        JButton logButton = new JButton(resourceBundle.getString("Login"));
        logButton.setFont(new Font("Dialog", Font.PLAIN, 25));
        logButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                login = logText.getText();
                password = passText.getText();
                lock.lock();
                condition.signalAll();
                lock.unlock();
            }
        });
        logButton.setBackground(new Color(0xFFD9ECEF, true));
        abstractLogButton.add(logButton);
        mainPanel.add(abstractLogButton);


        JPanel abstractMainPanel = new JPanel();
        abstractMainPanel.setPreferredSize(new Dimension(500, 500));
        abstractMainPanel.add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(350, 400));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));


        return abstractMainPanel;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public JPanel getPanel() {
        return createLoginPanel();
    }


    @Override
    public void run() {
        createLoginPanel();
    }
}
