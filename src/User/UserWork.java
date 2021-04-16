package User;


import CommonClasses.*;
import GraphicalUserInterface.LogOrRegChoice;
import GraphicalUserInterface.Login;
import GraphicalUserInterface.MainWindow;
import GraphicalUserInterface.WorkingWithGInterface;
//import CommonClasses.DataBlock;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserWork {

    TransferCenter transferCenter;
    User mainUser;
    WorkingWithGInterface gInterface;


    public UserWork(TransferCenter transferCenter, WorkingWithGInterface gInterface){
        this.transferCenter = transferCenter;
        this.gInterface = gInterface;
        login();

    }

    private void login(){
//      Обновляем окно на всякий случай
//        mainWindow.getContentPane().removeAll();
//        mainWindow.repaint();

        LogOrRegChoice logOrRegChoice = new LogOrRegChoice();
        gInterface.setSpaceForInteraction(logOrRegChoice.getPanel());

        while (logOrRegChoice.getAnswer() == null){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String answer = logOrRegChoice.getAnswer();


//        Printer.println("Необходимо авторизироваться.");
//        Printer.println("Войти - 0, зарегестрироваться - 1.");
        Scanner scanner = new Scanner(System.in);
        int scan;
        try {
            scan = Integer.valueOf(answer);
        }catch (Exception e){
//            Printer.println("Это что-то страшное... Попробуем ещё раз!");
            JOptionPane.showConfirmDialog(new JOptionPane(), "Это что-то страшное... Попробуем ещё раз!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
            login();
            return;
        }
        if(scan == 0){
            entering(scanner);
        }
        else {
            if(scan == 1){
                registering(scanner);
            }
            else {
//                Printer.println("Нужно выбрать один из вариантов!");
                JOptionPane.showConfirmDialog(new JOptionPane(), "Нужно выбрать один из вариантов!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                login();
                return;
            }
        }
    }

    private void entering(Scanner scanner){
        CheckConnection checkConnection = new CheckConnection(this, gInterface);
        boolean end = false;
        while (!end){
            try {
//                Printer.println("Введите имя пользователя:");
//                String name = scanner.nextLine();
//                Printer.println("Введите пароль:");
//                String password = scanner.nextLine();

//                mainWindow.getContentPane().removeAll();
//                mainWindow.repaint();

                Lock lock = new ReentrantLock();
                lock.lock();
                Condition condition = lock.newCondition();
                Login login = new Login(lock, condition);
                gInterface.setSpaceForInteraction(login.getPanel());
//                mainWindow.add(login.getPanel());
//                mainWindow.setVisible(true);
                condition.await();
                lock.unlock();

                String name = login.getLogin();
                String password = login.getPassword();
//                mainWindow.remove(login.getPanel());


                User user = new User(false, name, password);

                DataBlock dataBlock = new DataBlock();
                dataBlock.setUser(user);
                dataBlock.setCommandsData(CommandsData.CHECKUSER);

                checkConnection.start();
                serverAnswer = false;
                transferCenter.sendObjectToServer(dataBlock);
                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                serverAnswer = true;

                if(dataBlock.getParameter().equals("true")){
                    Printer.println(dataBlock.getPhrase());
                    mainUser = user;
                    end = true;
                }
                else {
//                    Printer.println(dataBlock.getPhrase());
                    JOptionPane.showConfirmDialog(new JOptionPane(), dataBlock.getPhrase() + "\nПробуем занова!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
//                    Printer.println("Пробуем занова!");
                    entering(scanner);
                    return;
                }

            }catch (Exception e){ }
        }
    }

    private void registering(Scanner scanner){
        CheckConnection checkConnection = new CheckConnection(this, gInterface);
        boolean end = false;
        while (!end){
            try {
//                Printer.println("Введите имя пользователя:");
//                String name = scanner.nextLine();
//                Printer.println("Введите пароль:");
//                String password = scanner.nextLine();

//                mainWindow.getContentPane().removeAll();
//                mainWindow.repaint();

                Lock lock = new ReentrantLock();
                lock.lock();
                Condition condition = lock.newCondition();
                Login login = new Login(lock, condition);
                gInterface.setSpaceForInteraction(login.getPanel());
//                mainWindow.add(login.getPanel());
//                mainWindow.setVisible(true);
                condition.await();
                lock.unlock();

//                Login login = new Login();
//                mainWindow.add(login.getPanel());
//                mainWindow.setVisible(true);
//                while (login.getLogin() == null | login.getPassword() == null){
//                    Thread.sleep(50);
//                }
                String name = login.getLogin();
                String password = login.getPassword();



                User user = new User(true, name, password);

                DataBlock dataBlock = new DataBlock();
                dataBlock.setUser(user);
                dataBlock.setCommandsData(CommandsData.CHECKUSER);

                checkConnection.start();
                serverAnswer = false;
                transferCenter.sendObjectToServer(dataBlock);
                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                serverAnswer = true;

                if(dataBlock.getParameter().equals("true")){
//                    Printer.println(dataBlock.getPhrase());
                    JOptionPane.showConfirmDialog(new JOptionPane(), dataBlock.getPhrase(), "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                    mainUser = user;
                    end = true;
                }
                else {
//                    Printer.println(dataBlock.getPhrase());
//                    Printer.println("Пробуем занова!");
                    JOptionPane.showConfirmDialog(new JOptionPane(), dataBlock.getPhrase() + "\nПробуем занова!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                    registering(scanner);
                    return;
                }

            }catch (Exception e){ }
        }
    }

    public void startCheckingCommands(){
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        CommandCenter cc = new CommandCenter();
        String command = new String();
        CommandsData commandsDataForSendToServer = null;

        Printer.println("Для просмотра списка команд необходимо ввести \"help\"");

        while (!exit){
            Printer.println("Введите команду:");
            command = scanner.nextLine();
            if(command.equals("")){
                Printer.println("Необходимо ввести команду!");
            }
            else {
                commandsDataForSendToServer = cc.whatTheCommand(command);
                if(commandsDataForSendToServer == null){
                    Printer.println("Такой команды не существует!");
                }
                else {
                    commandsDataForSendToServer.setCreator(null);
                    if(commandsDataForSendToServer.equals(CommandsData.EXIT)){

                        exit = true;
                    }
                    else {

                        cc.packingCommandInCommandsObject(command, commandsDataForSendToServer);

                        if(commandsDataForSendToServer.isCommandWithElementParameter()){
                            commandsDataForSendToServer.setFlat(Flat.createFlat(null));
                        }

                        communicateWithServerAboutCommand(commandsDataForSendToServer);
                    }
                }
            }
            commandsDataForSendToServer = null;
        }
        Printer.println("Выход из программы...");
    }



    private void copyFieldsFromTo(CommandsData commandsData, DataBlock dataBlock){
        dataBlock.setCommandWithElementParameter(commandsData.isCommandWithElementParameter());
        dataBlock.setCreator(commandsData.getCreator());
        dataBlock.setParameter(commandsData.getParameter());
        dataBlock.setFlat(commandsData.getFlat());
        dataBlock.setBufferedReader(commandsData.getBufferedReader());
        dataBlock.setOpeningFiles(commandsData.getOpeningFiles());
        dataBlock.setCommandEnded(commandsData.isCommandEnded());
        dataBlock.setPhrase(commandsData.getPhrase());
        dataBlock.setServerNeedStringParameter(commandsData.isServerNeedStringParameter);
        dataBlock.setServerNeedElementParameter(commandsData.isServerNeedElementParameter);
        dataBlock.setUserNeedToShowFlatArr(commandsData.isUserNeedToShowFlatArr);
        dataBlock.setFlats(commandsData.getFlats());
    }

    private void copyFieldsFromTo(DataBlock dataBlock, CommandsData commandsData){
        commandsData.setCommandWithElementParameter(dataBlock.isCommandWithElementParameter());
        commandsData.setCreator(dataBlock.getCreator());
        commandsData.setParameter(dataBlock.getParameter());
        commandsData.setFlat(dataBlock.getFlat());
        commandsData.setFlats(dataBlock.getFlats());
        commandsData.setBufferedReader(dataBlock.getBufferedReader());
        commandsData.setOpeningFiles(dataBlock.getOpeningFiles());
        commandsData.setCommandEnded(dataBlock.isCommandEnded());
        commandsData.setPhrase(dataBlock.getPhrase());
        commandsData.setServerNeedStringParameter(dataBlock.isServerNeedStringParameter);
        commandsData.setServerNeedElementParameter(dataBlock.isServerNeedElementParameter);
        commandsData.setUserNeedToShowFlatArr(dataBlock.isUserNeedToShowFlatArr);
    }

    public Boolean serverAnswer = true;

    private void communicateWithServerAboutCommand(Object obj){
        CommandsData commandsData = (CommandsData) obj;
        boolean end = false;

        while (!end){
//            Нужно оптимизировать так, чтобы находился вне цикла и не возникала ошибка при запуске execute_script
            CheckConnection checkConnection = new CheckConnection(this, gInterface);

            if(commandsData.getCreator() != null){
                if(commandsData.getCreator().equals(Creator.SCRIPT)){

                    DataBlock dataBlock;
                    serverAnswer = false;
                    checkConnection.start();
                    dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                    serverAnswer = true;
                    commandsData = dataBlock.getCommandsData();
                    copyFieldsFromTo(dataBlock, commandsData);

                }
                else {

                    DataBlock dataBlock = new DataBlock();
                    copyFieldsFromTo(commandsData, dataBlock);
                    dataBlock.setCommandsData(commandsData);
                    dataBlock.setUser(mainUser);

                    transferCenter.sendObjectToServer(dataBlock);
                    serverAnswer = false;
                    checkConnection.start();
                    dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                    serverAnswer = true;
                    commandsData = dataBlock.getCommandsData();
                    copyFieldsFromTo(dataBlock, commandsData);

                }
            }
            else {

                DataBlock dataBlock = new DataBlock();
                copyFieldsFromTo(commandsData, dataBlock);
                dataBlock.setCommandsData(commandsData);
                dataBlock.setUser(mainUser);
                transferCenter.sendObjectToServer(dataBlock);
                serverAnswer = false;
                checkConnection.start();
                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                serverAnswer = true;
                commandsData = dataBlock.getCommandsData();
                copyFieldsFromTo(dataBlock, commandsData);

            }



            end = commandsData.isCommandEnded();
            Printer.println(commandsData.getPhrase());

            if(commandsData.isServerNeedElementParameter){
                Printer.println("Необходимо задать квартиру.");
                commandsData.setFlat(Flat.createFlat(null));
            }

            if(commandsData.isServerNeedStringParameter){
                commandsData.setParameter((new Scanner(System.in)).nextLine());
            }

            if(commandsData.isUserNeedToShowFlatArr()){
                for(int i =0;i<commandsData.getFlats().length;i++){
                    commandsData.getFlats()[i].show();
                    Printer.println("");
                }
            }
        }

    }
}