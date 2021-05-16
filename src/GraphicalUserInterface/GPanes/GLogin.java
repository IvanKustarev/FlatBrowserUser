package GraphicalUserInterface.GPanes;

import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.WindowPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        mainPanel.setLayout(new GridLayout(2,1));

        JPanel logPasPanel = new JPanel();
        logPasPanel.setLayout(new GridLayout(2, 2));

        JLabel label = new JLabel(resourceBundle.getString("Login:"));
        label.setFont(label.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));
        logPasPanel.add(label);

        JTextField logText = new JTextField(15);
        logPasPanel.add(logText);

        label = new JLabel(resourceBundle.getString("Password:"));
        label.setFont(label.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));
        logPasPanel.add(label);

        JPasswordField pasText = new JPasswordField(15);
        logPasPanel.add(pasText);
        mainPanel.add(logPasPanel);

//        label = new JLabel("Login");
        JButton logButton = new JButton(resourceBundle.getString("Login"));
        logButton.setFont(logButton.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));

        logButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                login = logText.getText();
                password = pasText.getText();

//                Lock lock = new ReentrantLock();
                lock.lock();
                condition.signalAll();
                lock.unlock();
            }
        });
        mainPanel.add(logButton);
        return mainPanel;
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
