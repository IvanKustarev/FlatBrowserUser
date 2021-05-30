package GraphicalUserInterface.GPanes;

import GraphicalUserInterface.WindowPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class GReConnect implements WindowPane {
    
    private boolean tryConnect = false;
    private Lock lock;
    private Condition condition;
    private ResourceBundle resourceBundle;


    public boolean getTryConnect(){
        return tryConnect;
    }

    public GReConnect(Lock lock, Condition condition){
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }


    public JPanel createReConnectPanel(){

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 0;
        constraints1.weighty = 0;
        constraints1.gridx = 0;
        constraints1.gridy = 0;
        constraints1.gridheight = 1;
        constraints1.gridwidth = 2;

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.weightx = 0;
        constraints2.weighty = 0;
        constraints2.gridx = 0;
        constraints2.gridy = 2;
        constraints2.gridheight = 1;
        constraints2.gridwidth = 1;

        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.weightx = 0;
        constraints3.weighty = 0;
        constraints3.gridx = 1;
        constraints3.gridy = 2;
        constraints3.gridheight = 1;
        constraints3.gridwidth = 1;

        JButton yes = new JButton(resourceBundle.getString("Да"));
        JButton no = new JButton(resourceBundle.getString("Нет"));

        jPanel.add(new JLabel(resourceBundle.getString("Продолжаем попытки подключения к серверу?")), constraints1);
        jPanel.add(yes, constraints2);
        jPanel.add(no, constraints3);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryConnect = true;
                lock.lock();
                condition.signalAll();
                lock.unlock();
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryConnect = false;
                lock.lock();
                condition.signalAll();
                lock.unlock();
            }
        });

        return jPanel;
    }

    @Override
    public JPanel getPanel() {
        return createReConnectPanel();
    }
}
