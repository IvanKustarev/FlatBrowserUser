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

public class GIpAndPortEntering implements WindowPane, Runnable {
    private String ip;
    private String port;
    private Lock lock;
    private Condition condition;
    private GInterface gInterface;
    private ResourceBundle resourceBundle;

    public GIpAndPortEntering(Lock lock, Condition condition, GInterface gInterface){
        this.lock = lock;
        this.condition = condition;
        this.gInterface = gInterface;
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    private JPanel enterDataToConnection() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        JLabel label = new JLabel(resourceBundle.getString("ip"));
        label.setFont(label.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));

        JPanel logPasPanel = new JPanel();
        logPasPanel.setLayout(new GridLayout(2, 2));
        logPasPanel.add(label);
        JTextField ipText = new JTextField(15);
        logPasPanel.add(ipText);

        label = new JLabel(resourceBundle.getString("port"));
        label.setFont(label.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));


        logPasPanel.add(label);
        JTextField portText = new JTextField(15);
        logPasPanel.add(portText);
        mainPanel.add(logPasPanel);

        JButton connectButton = new JButton(resourceBundle.getString("CONNECT"));
        connectButton.setFont(label.getFont().deriveFont((float)(gInterface.getMainWindowSize().height/11)));

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ip = ipText.getText();
                port = portText.getText();
                lock.lock();
                condition.signalAll();
                lock.unlock();
            }
        });
        mainPanel.add(connectButton);
        return mainPanel;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    @Override
    public JPanel getPanel() {
        return enterDataToConnection();
    }

    @Override
    public void run() {
        enterDataToConnection();
    }
}
