package User;

import CommonClasses.Flat;
import GraphicalUserInterface.GReConnect;
import GraphicalUserInterface.PlaneCreator;
import GraphicalUserInterface.WorkingWithGInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessControlCenter {

    private UserWork userWork;
    private TransferCenter transferCenter;
    private WorkingWithGInterface gInterface;

    public ProcessControlCenter(TransferCenter transferCenter, UserWork userWork, WorkingWithGInterface gInterface){
        this.userWork = userWork;
        this.transferCenter = transferCenter;
        this.gInterface = gInterface;

        gInterface.creatingWindow();
    }

    public void start(){
        connect();
        working();
    }

    public void working(){
        try {
            login();
        } catch (ConnectionException connectionException) {
            reConnect();
            working();
            return;
        }
        toMainMenu();
    }

    private void toMainMenu(){
        createOnlyUserNameTopPart();
        createVariantsForMainMenu();
    }

    private void createVariantsForMainMenu(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2,1));

        JButton consoleWork = new JButton("Консольная работа с командами");
        consoleWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toCommandCommunicateWithServer();
            }
        });

        JButton tableWork = new JButton("Табличная работа с элементами");
        tableWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toTableWork();
            }
        });

        jPanel.add(consoleWork);
        jPanel.add(tableWork);


        gInterface.setSpaceForInteraction(jPanel);
    }

    private void toCommandCommunicateWithServer(){
        createUserNameAndBackTopPart();
        createSpaceForConsoleCommunicate();
    }

    private void toTableWork(){
        JPanel table = null;
        try {
            Flat[] flats = userWork.new CommunicateWithServerByCommands().getFlatArr(transferCenter);

            table = new PlaneCreator().new Table(flats).getPanel();
        }catch (ConnectionException e){
            reConnect();
            working();
            return;
        }
        gInterface.setSpaceForInteraction(table);

        createUserNameAndBackTopPart();
    }

    private void createSpaceForConsoleCommunicate(){
        JPanel jPanel = new JPanel();
        ConsoleScanner consoleScanner = new ConsoleScanner();
        ProcessControlCenter processControlCenter = this;

        JLabel command = new JLabel("Введите команду сюда: ");
        JTextField textField = new JTextField(15);
        JButton jButton = new JButton("Исполнить");
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/plain");
        editorPane.setText("Для просмотра списка команд необходимо ввести \"help\"");


        jButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(consoleScanner.isNeeded()){
                    consoleScanner.setNeeded(false);
                    consoleScanner.setStr(textField.getText());
                    textField.setText("");
                    synchronized (consoleScanner) {
                        consoleScanner.notify();
                    }
                }
                else {
                    //                        userWork.new CommunicateWithServerByCommands().startCheckingCommands(transferCenter, gInterface, textField.getText(), new GInterfaceConsolePrinter(editorPane), consoleScanner);
                    UserWork.CommunicateWithServerByCommands communicateWithServerByCommands = userWork.new CommunicateWithServerByCommands(transferCenter, gInterface, textField.getText(), new GInterfaceConsolePrinter(editorPane), consoleScanner, processControlCenter);
                    Thread thread = new Thread(communicateWithServerByCommands);
                    thread.start();
                    textField.setText("");
                }

            }
        });


        jPanel.add(command);
        jPanel.add(textField);
        jPanel.add(jButton);
//        jPanel.add(editorPane);
        jPanel.add(new JScrollPane(editorPane));

        gInterface.setSpaceForInteraction(jPanel);
    }

    private void createOnlyUserNameTopPart(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        JLabel jLabel = new JLabel("User: "+userWork.getMainUser().getLogin());

        jPanel.add(jLabel, constraints);

        gInterface.setTopPartOfWindow(jPanel);
    }

    private void createUserNameAndBackTopPart(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 0;
        constraints1.weighty = 0;
        constraints1.gridx = 0;
        constraints1.gridy = 0;
        constraints1.gridheight = 1;
        constraints1.gridwidth = 1;

        JLabel jLabel = new JLabel("User: "+userWork.getMainUser().getLogin());
        JButton jButton = new JButton("НАЗАД");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toMainMenu();
            }
        });

        jPanel.add(jLabel, constraints);
        jPanel.add(jButton, constraints1);

        gInterface.setTopPartOfWindow(jPanel);
    }

    public void reConnect(){
        boolean end = false;
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        GReConnect gReConnect = new GReConnect(lock, condition);
        gInterface.setSpaceForInteraction(gReConnect.getPanel());

        while (!end) {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
            }
            lock.unlock();

            boolean tryConnect = gReConnect.getTryConnect();

            if (tryConnect == false) {
                JOptionPane.showConfirmDialog(new JOptionPane(), "Завершаем работу!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                System.exit(0);
            } else {
                end = transferCenter.reConnect();
            }
        }
    }

    private void connect(){
        transferCenter.connect(gInterface);
    }

    private void login() throws ConnectionException {
        userWork.new Login().login(gInterface, transferCenter);
    }
}
