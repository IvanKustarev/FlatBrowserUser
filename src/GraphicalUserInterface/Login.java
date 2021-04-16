package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Login implements WindowPart, Runnable{

    private String login;
    private String password;
    private Lock lock;
    private Condition condition;

    public Login(Lock lock, Condition condition){
        this.lock = lock;
        this.condition = condition;
    }


    private JPanel createLoginPanel(){

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));

        JPanel logPasPanel = new JPanel();
        logPasPanel.setLayout(new GridLayout(2, 2));
        logPasPanel.add(new JLabel("Login: "));
        JTextField logText = new JTextField(15);
        logPasPanel.add(logText);
        logPasPanel.add(new JLabel("Password: "));
        JTextField pasText = new JTextField(15);
        logPasPanel.add(pasText);
        mainPanel.add(logPasPanel);

        JButton logButton = new JButton("Login");
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
