package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class IpAndPortEntering implements WindowPart, Runnable {
    private String ip;
    private String port;
    private Lock lock;
    private Condition condition;

    public IpAndPortEntering(Lock lock, Condition condition){
        this.lock = lock;
        this.condition = condition;
    }

    private JPanel enterDataToConnection() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        JPanel logPasPanel = new JPanel();
        logPasPanel.setLayout(new GridLayout(2, 2));
        logPasPanel.add(new JLabel("ip: "));
        JTextField ipText = new JTextField(15);
        logPasPanel.add(ipText);
        logPasPanel.add(new JLabel("port: "));
        JTextField portText = new JTextField(15);
        logPasPanel.add(portText);
        mainPanel.add(logPasPanel);

        JButton connectButton = new JButton("CONNECT");
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
