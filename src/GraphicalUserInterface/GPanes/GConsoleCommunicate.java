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
        JPanel jPanel = new JPanel();
        ConsoleScanner consoleScanner = new ConsoleScanner();
//        ProcessControlCenter processControlCenter = this;

        JLabel commandLabel = new JLabel("Введите команду сюда: ");
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
                    UserWork.CommunicateWithServerByCommands communicateWithServerByCommands =
                            userWork.new CommunicateWithServerByCommands(transferCenter, gInterface, textField.getText(),
                                    new GInterfaceConsolePrinter(editorPane), consoleScanner, processControlCenter);
                    Thread thread = new Thread(communicateWithServerByCommands);
                    thread.start();
                    textField.setText("");
                }

            }
        });

        jPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 0;
        constraints1.weighty = 0;
        constraints1.gridx = 1;
        constraints1.gridy = 0;
        constraints1.gridheight = 1;
        constraints1.gridwidth = 1;
        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.weightx = 0;
        constraints2.weighty = 0;
        constraints2.gridx = 2;
        constraints2.gridy = 0;
        constraints2.gridheight = 1;
        constraints2.gridwidth = 1;
        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.weightx = 0;
        constraints3.weighty = 0;
        constraints3.gridx = 0;
        constraints3.gridy = 1;
        constraints3.gridheight = 7;
        constraints3.gridwidth = 3;

        jPanel.add(commandLabel, constraints);
        jPanel.add(textField, constraints1);
        jPanel.add(jButton, constraints2);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        editorPane.setSize(new Dimension(500, 500));
        scrollPane.setSize(new Dimension(500, 500));
        scrollPane.setMinimumSize(new Dimension(500, 500));
        scrollPane.setPreferredSize(new Dimension(500, 500));
        jPanel.add(scrollPane, constraints3);
        jPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        return jPanel;
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
