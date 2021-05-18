package GraphicalUserInterface.GPanes;

import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.WindowPane;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
        mainPanel.setLayout(new GridLayout(4, 1));


        JPanel abstractLabelConnect = new JPanel();
        JLabel connectLabel = new JLabel(resourceBundle.getString("Подключение"));
        connectLabel.setFont(new Font("Dialog", Font.PLAIN, 25));
        abstractLabelConnect.add(connectLabel);
        mainPanel.add(abstractLabelConnect);


        JPanel abstractIpText = new JPanel();
        JTextField ipText = new JTextField(15);
        ipText.setForeground(Color.GRAY);
        ipText.setFont(new Font("Dialog", Font.ITALIC, 20));
        ipText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        ipText.setText(resourceBundle.getString("ip"));
        ipText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!ipText.getText().equals(resourceBundle.getString("ip"))){
                }
                else {
                    ipText.setText(null);
                    ipText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(ipText.getText().length() == 0){
                    ipText.setText(resourceBundle.getString("ip"));
                    ipText.setForeground(Color.GRAY);
                }
            }
        });
        abstractIpText.add(ipText);
        mainPanel.add(abstractIpText);


        JPanel abstractPortText = new JPanel();
        JTextField portText = new JTextField(15);
        portText.setForeground(Color.GRAY);
        portText.setFont(new Font("Dialog", Font.ITALIC, 20));
        portText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        portText.setText(resourceBundle.getString("port"));
        portText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!portText.getText().equals(resourceBundle.getString("port"))){
                }
                else {
                    portText.setText(null);
                    portText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(portText.getText().length() == 0){
                    portText.setText(resourceBundle.getString("port"));
                    portText.setForeground(Color.GRAY);
                }
            }
        });
        abstractPortText.add(portText);
        mainPanel.add(abstractPortText);



        JPanel abstractConnectButton = new JPanel();
        JButton connectButton = new JButton(resourceBundle.getString("CONNECT"));
        connectButton.setFont(new Font("Dialog", Font.PLAIN, 25));
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
        connectButton.setBackground(new Color(0xFFD9ECEF, true));
        abstractConnectButton.add(connectButton);
        mainPanel.add(abstractConnectButton);


        JPanel abstractMainPanel = new JPanel();
        abstractMainPanel.setPreferredSize(new Dimension(500, 500));
        abstractMainPanel.add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(350, 400));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return abstractMainPanel;
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
