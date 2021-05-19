package GraphicalUserInterface.GPanes;

import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.GInterfaceConsolePrinter;
import GraphicalUserInterface.WindowPane;
import HelpingModuls.ConsoleScanner;
import User.ProcessControlCenter;
import User.TransferCenter;
import User.UserWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class GConsoleCommunicate implements WindowPane {

    private ProcessControlCenter processControlCenter;
    private UserWork userWork;
    private TransferCenter transferCenter;
    private GInterface gInterface;
    private ResourceBundle resourceBundle;

    public GConsoleCommunicate(ProcessControlCenter processControlCenter, UserWork userWork, TransferCenter transferCenter, GInterface gInterface){
        this.processControlCenter = processControlCenter;
        this.userWork = userWork;
        this.transferCenter = transferCenter;
        this.gInterface = gInterface;
    }

    private JPanel createPane(){
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(700, 700));
        ConsoleScanner consoleScanner = new ConsoleScanner();
//        ProcessControlCenter processControlCenter = this;

        JPanel commandEnterSpace = new JPanel();
        commandEnterSpace.setPreferredSize(new Dimension(500, 40));
        commandEnterSpace.setMinimumSize(new Dimension(400, 40));

        JTextField textField = new JTextField(15);
        textField.setText("Введите команду сюда");
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("Dialog", Font.PLAIN, 20));
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!textField.getText().equals("Введите команду сюда")){
                }
                else {
                    textField.setText(null);
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(textField.getText().length() == 0){
                    textField.setText("Введите команду сюда");
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        JButton execute = new JButton("Исполнить");
        execute.setBackground(new Color(0xFFD9ECEF, true));
        execute.setFont(new Font("Dialog", Font.PLAIN, 20));

        commandEnterSpace.add(textField);
        commandEnterSpace.add(execute);


        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/plain");
        editorPane.setText("Для просмотра списка команд необходимо ввести \"help\"");
        editorPane.setFont(new Font("Dialog", Font.PLAIN, 20));

        execute.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField.getText().equals("Введите команду сюда")){
                    textField.setText("");
                }
                if(consoleScanner.isNeeded()){
                    consoleScanner.setNeeded(false);
                    consoleScanner.setStr(textField.getText());
                    textField.setText("Введите команду сюда");
                    textField.setForeground(Color.GRAY);
                    synchronized (consoleScanner) {
                        consoleScanner.notify();
                    }
                }
                else {
                    UserWork.CommunicateWithServerByCommands communicateWithServerByCommands =
                            userWork.new CommunicateWithServerByCommands(transferCenter, gInterface, textField.getText(),
                                    new GInterfaceConsolePrinter(editorPane), consoleScanner, processControlCenter);
                    Thread thread = new Thread(communicateWithServerByCommands);
                    thread.start();
                    textField.setText("Введите команду сюда");
                    textField.setForeground(Color.GRAY);
                }

            }
        });

        mainPanel.setLayout(new GridBagLayout());

//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.weightx = 0;
//        constraints.weighty = 0;
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.gridheight = 1;
//        constraints.gridwidth = 1;
//        GridBagConstraints constraints1 = new GridBagConstraints();
//        constraints1.weightx = 0;
//        constraints1.weighty = 0;
//        constraints1.gridx = 1;
//        constraints1.gridy = 0;
//        constraints1.gridheight = 1;
//        constraints1.gridwidth = 1;
        GridBagConstraints constraints0 = new GridBagConstraints();
        constraints0.weightx = 0;
        constraints0.weighty = 0;
        constraints0.gridx = 0;
        constraints0.gridy = 0;
        constraints0.gridheight = 1;
        constraints0.gridwidth = 1;
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 0;
        constraints1.weighty = 0;
        constraints1.gridx = 0;
        constraints1.gridy = 1;
        constraints1.gridheight = 5;
        constraints1.gridwidth = 1;

        mainPanel.add(commandEnterSpace, constraints0);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        editorPane.setSize(new Dimension(500, 500));
        scrollPane.setSize(new Dimension(500, 500));
        scrollPane.setMinimumSize(new Dimension(500, 400));
        scrollPane.setPreferredSize(new Dimension(700, 700));
        mainPanel.add(scrollPane, constraints1);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        return mainPanel;
    }

    @Override
    public JPanel getPanel() {
        return createPane();
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
