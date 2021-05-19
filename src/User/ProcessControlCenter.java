package User;

import CommonClasses.CommandsData;
import CommonClasses.Flat;
import GraphicalUserInterface.GInterfaceConsolePrinter;
import GraphicalUserInterface.GPanes.GConsoleCommunicate;
import GraphicalUserInterface.GPanes.GReConnect;
import GraphicalUserInterface.GPanes.PlaneCreator;
import GraphicalUserInterface.GPanes.VisualSpace.*;
import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.WindowPane;
import HelpingModuls.ConnectionException;
import HelpingModuls.ConsoleScanner;
import Resources.ResourceControlCenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessControlCenter{

    private UserWork userWork;
    private TransferCenter transferCenter;
    private GInterface gInterface;
    private ResourceControlCenter resourceControlCenter;

    public ProcessControlCenter(TransferCenter transferCenter, UserWork userWork, GInterface gInterface, ResourceControlCenter resourceControlCenter){
        this.userWork = userWork;
        this.transferCenter = transferCenter;
        this.gInterface = gInterface;
        this.resourceControlCenter = resourceControlCenter;
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
            return;
        }
        toMainMenu();
    }

    private void toMainMenu(){
        createOnlyUserNameTopPart();
        createVariantsForMainMenu();
    }

    private void toVisualSpace(){
        VisualSpaceControlCenter visualSpaceControlCenter = new VisualSpaceControlCenter(userWork, transferCenter, this, gInterface, resourceControlCenter.getMainResourceBundle());
        createUserNameAndBackTopPartForVisualSpace(visualSpaceControlCenter);
        gInterface.setGPane(visualSpaceControlCenter);
    }

    private void createVariantsForMainMenu(){

        GVariantsForMainMenu gVariantsForMainMenu = new GVariantsForMainMenu();

        gInterface.setGPane(gVariantsForMainMenu);

    }

    private void toCommandCommunicateWithServer(){
        createUserNameAndBackTopPart();
        createSpaceForConsoleCommunicate();
    }

    private void toTableWork(){
        PlaneCreator.Table table = null;
        try {
            Flat[] flats = (userWork.new CommunicateWithServerByCommands().processCommand(CommandsData.SHOW, transferCenter)).getFlats();

            table = new PlaneCreator().new Table(flats, userWork.getMainUser(), transferCenter, gInterface, userWork, this);
        }catch (ConnectionException e){

            reConnect();
            return;
        }

        gInterface.setGPane(table);

        createUserNameAndBackTopPart();
    }

    private void createSpaceForConsoleCommunicate(){

        GConsoleCommunicate gConsoleCommunicate = new GConsoleCommunicate(this, userWork, transferCenter, gInterface);
        gInterface.setGPane(gConsoleCommunicate);
    }

    private void createOnlyUserNameTopPart(){

        gInterface.setSizeForLanguagePale(new Dimension(500, 40));
        OnlyUserNameCreator onlyUserNameCreator = new OnlyUserNameCreator();
        gInterface.setTopPartOfWindow(onlyUserNameCreator);
    }

    private void createUserNameAndBackTopPart(){
        gInterface.setSizeForLanguagePale(new Dimension(500, 40));
        UserNameAndBackCreator userNameAndBackCreator = new UserNameAndBackCreator();
        gInterface.setTopPartOfWindow(userNameAndBackCreator);
    }

    private void createUserNameAndBackTopPartForVisualSpace(VisualSpaceControlCenter visualSpaceControlCenter){

        UserBackAndBackToVisualSpaceCreator userBackAndBackToVisualSpaceCreator = new UserBackAndBackToVisualSpaceCreator(visualSpaceControlCenter);
        gInterface.setTopPartOfWindow(userBackAndBackToVisualSpaceCreator);

    }


    private void connect(){
        transferCenter.connect(gInterface);
    }

    private void login() throws ConnectionException {
        userWork.new Login().login(gInterface, transferCenter);
    }


    private class GVariantsForMainMenu implements WindowPane {

        private ResourceBundle resourceBundle;

        private JPanel createVariants() {
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(new Dimension(500, 400));
            JPanel abstractMainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(3, 1));

            JButton consoleWork = new JButton(resourceBundle.getString("Консольная работа с командами"));
            consoleWork.setFont(new Font("Dialog", Font.PLAIN, 25));
            consoleWork.setBackground(new Color(0xFFD9ECEF, true));
            consoleWork.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toCommandCommunicateWithServer();
                }
            });


            JButton tableWork = new JButton(resourceBundle.getString("Табличная работа с элементами"));
            tableWork.setFont(new Font("Dialog", Font.PLAIN, 25));
            tableWork.setBackground(new Color(0xFFD9ECEF, true));
            tableWork.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toTableWork();
                }
            });


            JButton visualWork = new JButton(resourceBundle.getString("Область визуализации"));
            visualWork.setFont(new Font("Dialog", Font.PLAIN, 25));
            visualWork.setBackground(new Color(0xFFD9ECEF, true));
            visualWork.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toVisualSpace();
                }
            });

            mainPanel.add(consoleWork);
            mainPanel.add(tableWork);
            mainPanel.add(visualWork);
            abstractMainPanel.add(mainPanel);

            return abstractMainPanel;
        }

        @Override
        public JPanel getPanel() {
            return createVariants();
        }

        @Override
        public void setLocale(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
        }
    }

    private class OnlyUserNameCreator implements WindowPane{

        private ResourceBundle resourceBundle;

        private JPanel createPane(){
            JPanel mainPain = new JPanel();
            mainPain.setPreferredSize(new Dimension(500, 40));
            mainPain.setLayout(new GridLayout(1,2));

            JLabel jLabel = new JLabel(resourceBundle.getString("User:") + userWork.getMainUser().getLogin());
            jLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
            jLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            JButton changeUser = new JButton(resourceBundle.getString("Сменить пользователя"));
            changeUser.setBackground(new Color(0xFFD9ECEF, true));
            changeUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    changeUser();
                }
            });
            changeUser.setFont(new Font("Dialog", Font.PLAIN, 20));

            mainPain.add(jLabel);
            mainPain.add(changeUser);

            return  mainPain;
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

    private class UserNameAndBackCreator implements WindowPane{

        private ResourceBundle resourceBundle;

        private JPanel createPanel(){
            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(new Dimension(500, 80));
            mainPanel.setLayout(new GridLayout(2, 1));

            JPanel abstractUserNamePane = new JPanel();
            abstractUserNamePane.setLayout(new GridLayout(1, 2));

            JLabel userName = new JLabel(resourceBundle.getString("User:") +userWork.getMainUser().getLogin());
            userName.setFont(new Font("Dialog", Font.PLAIN, 20));
            userName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            JButton mainMenuButton = new JButton(resourceBundle.getString("В главное меню"));
            mainMenuButton.setBackground(new Color(0xFFD9ECEF, true));
            mainMenuButton.setFont(new Font("Dialog", Font.PLAIN, 20));
            mainMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toMainMenu();
                }
            });

            JButton changeUser = new JButton(resourceBundle.getString("Сменить пользователя"));
            changeUser.setBackground(new Color(0xFFD9ECEF, true));
            changeUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gInterface.setSizeForLanguagePale(new Dimension(350, 40));
                    changeUser();
                }
            });
            changeUser.setFont(new Font("Dialog", Font.PLAIN, 20));

            abstractUserNamePane.add(userName);
            abstractUserNamePane.add(changeUser);

            mainPanel.add(abstractUserNamePane);
            mainPanel.add(mainMenuButton);

            return mainPanel;
        }

        @Override
        public JPanel getPanel() {
            return createPanel();
        }

        @Override
        public void setLocale(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
        }
    }

    private class UserBackAndBackToVisualSpaceCreator implements WindowPane{

        private ResourceBundle resourceBundle;
        private VisualSpaceControlCenter visualSpaceControlCenter;

        private UserBackAndBackToVisualSpaceCreator(VisualSpaceControlCenter visualSpaceControlCenter){
            this.visualSpaceControlCenter = visualSpaceControlCenter;
        }

        private JPanel createPane(){

            JPanel mainPanel = new JPanel();
            mainPanel.setPreferredSize(new Dimension(500, 80));
            mainPanel.setLayout(new GridLayout(2, 1));

            JPanel abstractUserNamePane = new JPanel();
            abstractUserNamePane.setLayout(new GridLayout(1, 2));

            JLabel userName = new JLabel(resourceBundle.getString("User:") +userWork.getMainUser().getLogin());
            userName.setFont(new Font("Dialog", Font.PLAIN, 20));
            userName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            JButton mainMenuButton = new JButton(resourceBundle.getString("В главное меню"));
            mainMenuButton.setBackground(new Color(0xFFD9ECEF, true));
            mainMenuButton.setFont(new Font("Dialog", Font.PLAIN, 20));
            mainMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    visualSpaceControlCenter.stopDifferenceHandler();
                    toMainMenu();
                }
            });

            JButton changeUser = new JButton(resourceBundle.getString("Сменить пользователя"));
            changeUser.setBackground(new Color(0xFFD9ECEF, true));
            changeUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    visualSpaceControlCenter.stopDifferenceHandler();
                    gInterface.setSizeForLanguagePale(new Dimension(350, 40));
                    changeUser();
                }
            });
            changeUser.setFont(new Font("Dialog", Font.PLAIN, 20));

            abstractUserNamePane.add(userName);
            abstractUserNamePane.add(changeUser);

            mainPanel.add(abstractUserNamePane);
            mainPanel.add(mainMenuButton);

            return mainPanel;

//            JPanel jPanel = new JPanel();
//            jPanel.setLayout(new GridBagLayout());
//            GridBagConstraints constraints = new GridBagConstraints();
//            constraints.weightx = 0;
//            constraints.weighty = 0;
//            constraints.gridx = 1;
//            constraints.gridy = 0;
//            constraints.gridheight = 1;
//            constraints.gridwidth = 1;
//
//            GridBagConstraints constraints1 = new GridBagConstraints();
//            constraints1.weightx = 0;
//            constraints1.weighty = 0;
//            constraints1.gridx = 0;
//            constraints1.gridy = 0;
//            constraints1.gridheight = 1;
//            constraints1.gridwidth = 1;
//
//            GridBagConstraints constraints2 = new GridBagConstraints();
//            constraints1.weightx = 0;
//            constraints1.weighty = 0;
//            constraints1.gridx = 2;
//            constraints1.gridy = 0;
//            constraints1.gridheight = 1;
//            constraints1.gridwidth = 1;
//
//
//            JButton changeUser = new JButton(resourceBundle.getString("Сменить пользователя"));
//            changeUser.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    changeUser();
//                }
//            });
//
//            JLabel jLabel = new JLabel(resourceBundle.getString("User:")+userWork.getMainUser().getLogin());
//            JButton jButton = new JButton(resourceBundle.getString("НАЗАД"));
//            jButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    visualSpaceControlCenter.stopDifferenceHandler();
//                    toMainMenu();
//                }
//            });
//
//            jPanel.add(jLabel, constraints);
//            jPanel.add(jButton, constraints1);
//            jPanel.add(changeUser, constraints2);
//
//            return jPanel;
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

    public void reConnect(){
        new Thread(new ReConnecter()).start();
    }

    public class ReConnecter implements Runnable{


        @Override
        public void run() {
            reConnect();
        }

        public void reConnect(){
            boolean end = false;
            Lock lock = new ReentrantLock();
            Condition condition = lock.newCondition();
            GReConnect gReConnect = new GReConnect(lock, condition);

            gInterface.setGPane(gReConnect);

            while (!end) {
                lock.lock();
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
                lock.unlock();

                boolean tryConnect = gReConnect.getTryConnect();


                if (tryConnect == false) {
                    gInterface.sendNotification("Завершаем работу!", "Уведомление");
                    System.exit(0);
                } else {
                    end = transferCenter.reConnect();
                }
            }
            working();
        }
    }

    public void changeUser(){
        new Thread(new UserChanger()).start();
    }

    public class UserChanger implements Runnable{

        @Override
        public void run() {
            working();
        }
    }
}
