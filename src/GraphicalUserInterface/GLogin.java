package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GLogin implements WindowPart, Runnable{

    private String login;
    private String password;
    private Lock lock;
    private Condition condition;
    private WorkingWithGInterface gInterface;

    public GLogin(Lock lock, Condition condition, WorkingWithGInterface gInterface){
        this.lock = lock;
        this.condition = condition;
        this.gInterface = gInterface;
    }


    private JPanel createLoginPanel(){

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));

        JPanel logPasPanel = new JPanel();
        logPasPanel.setLayout(new GridLayout(2, 2));

        JLabel label = new JLabel("Login: ");
        label.setFont(label.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));
        logPasPanel.add(label);

        JTextField logText = new JTextField(15);
        logPasPanel.add(logText);

        label = new JLabel("Password: ");
        label.setFont(label.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));
        logPasPanel.add(label);

        JPasswordField pasText = new JPasswordField(15);
        logPasPanel.add(pasText);
        mainPanel.add(logPasPanel);

//        label = new JLabel("Login");
        JButton logButton = new JButton("Login");
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
