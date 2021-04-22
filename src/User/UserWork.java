package User;


import CommonClasses.*;
import GraphicalUserInterface.GLogOrRegChoice;
import GraphicalUserInterface.GLogin;
import GraphicalUserInterface.WorkingWithGInterface;
import HelpingModuls.*;
//import CommonClasses.DataBlock;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserWork{

//    TransferCenter transferCenter;
    private User mainUser;
//    WorkingWithGInterface gInterface;


    public UserWork(/*TransferCenter transferCenter*/){
//        this.transferCenter = transferCenter;
//        this.gInterface = gInterface;
//        login();

    }

    public User getMainUser() {
        return mainUser;
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

//    private Boolean serverAnswer = true;



    public class CommunicateWithServerByCommands implements Runnable{

        TransferCenter transferCenter;
        WorkingWithGInterface gInterface;
        String command;
        Printer printer;
        ConsoleScanner consoleScanner;
        ProcessControlCenter processControlCenter;

        public CommunicateWithServerByCommands(TransferCenter transferCenter, WorkingWithGInterface gInterface, String command, Printer printer, ConsoleScanner consoleScanner, ProcessControlCenter processControlCenter){
            this.transferCenter = transferCenter;
            this.gInterface = gInterface;
            this.command = command;
            this.printer = printer;
            this.consoleScanner = consoleScanner;
            this.processControlCenter = processControlCenter;

            printer.println("\n");
        }

        public CommunicateWithServerByCommands(){}

        @Override
        public void run() {
            try {
                startCheckingCommands(transferCenter, gInterface, command, printer, consoleScanner);
            } catch (ConnectionException connectionException) {
                processControlCenter.reConnect();
                processControlCenter.working();
                return;
            }
        }

        public void startCheckingCommands(TransferCenter transferCenter, WorkingWithGInterface gInterface, String command, Printer printer, ConsoleScanner consoleScanner) throws  ConnectionException{

            CommandCenter cc = new CommandCenter();
            CommandsData commandsDataForSendToServer = null;

                if(command.equals("")){
                    printer.println("Необходимо ввести команду!");
                    return;
                }
                else {
                    commandsDataForSendToServer = cc.whatTheCommand(command);
                    if(commandsDataForSendToServer == null){
                        printer.println("Такой команды не существует!");
                        return;
                    }
                    else {
                        commandsDataForSendToServer.setCreator(null);
                        if(commandsDataForSendToServer.equals(CommandsData.EXIT)){
                            JOptionPane.showConfirmDialog(new JOptionPane(), "Введена команда exit. Завершаю работу...", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                            System.exit(0);
                        }
                        else {

                            cc.packingCommandInCommandsObject(command, commandsDataForSendToServer);

                            if(commandsDataForSendToServer.isCommandWithElementParameter()){
                                commandsDataForSendToServer.setFlat(Flat.createFlat(null, new FlatCreator(printer, consoleScanner)));
                            }

                            new CommunicateWithServerByCommands().communicateWithServerAboutCommand(commandsDataForSendToServer, transferCenter, gInterface, printer, consoleScanner);
                        }
                    }
                }
        }


        DataBlock dataBlock0;
        DataBlock dataBlock1;
        DataBlock dataBlock2;

        public void communicateWithServerAboutCommand(Object obj,  TransferCenter transferCenter, WorkingWithGInterface gInterface, Printer printer, ConsoleScanner consoleScanner) throws ConnectionException{
            CommandsData commandsData = (CommandsData) obj;
            boolean end = false;

            while (!end){
//            Нужно оптимизировать так, чтобы находился вне цикла и не возникала ошибка при запуске execute_script
//            CheckConnection checkConnection = new CheckConnection(this, gInterface);
                dataBlock1 = new DataBlock();

                if(commandsData.getCreator() != null){
                    if(commandsData.getCreator().equals(Creator.SCRIPT)){


                        new TimeLimitedCode(5, TimeUnit.SECONDS){

                            @Override
                            public void codeBlock() {
                                dataBlock1 = (DataBlock) transferCenter.receiveObjectFromServer();
                            }
                        }.start();

                        commandsData = dataBlock1.getCommandsData();
                        copyFieldsFromTo(dataBlock1, commandsData);

                    }
                    else {
                        copyFieldsFromTo(commandsData, dataBlock1);
                        dataBlock1.setCommandsData(commandsData);
                        dataBlock1.setUser(mainUser);

                        transferCenter.sendObjectToServer(dataBlock1);

                        new TimeLimitedCode(5, TimeUnit.SECONDS){

                            @Override
                            public void codeBlock() {
                                dataBlock1 = (DataBlock) transferCenter.receiveObjectFromServer();
                            }
                        }.start();

                        commandsData = dataBlock1.getCommandsData();
                        copyFieldsFromTo(dataBlock1, commandsData);

                    }
                }
                else {
                    copyFieldsFromTo(commandsData, dataBlock1);
                    dataBlock1.setCommandsData(commandsData);
                    dataBlock1.setUser(mainUser);
                    transferCenter.sendObjectToServer(dataBlock1);

                    new TimeLimitedCode(5, TimeUnit.SECONDS){

                        @Override
                        public void codeBlock() {
                            dataBlock1 = (DataBlock) transferCenter.receiveObjectFromServer();
                        }
                    }.start();

                    commandsData = dataBlock1.getCommandsData();
                    copyFieldsFromTo(dataBlock1, commandsData);

                }

                if(commandsData.equals(CommandsData.EXIT)){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Найдена команда exit. Завершаю работу...", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                }

                end = commandsData.isCommandEnded();
                printer.println(commandsData.getPhrase());

                if(commandsData.isServerNeedElementParameter){
                    printer.println("Необходимо задать квартиру.");
                    commandsData.setFlat(Flat.createFlat(null, new FlatCreator(printer, consoleScanner)));
                }

                if(commandsData.isServerNeedStringParameter){
                    consoleScanner.setNeeded(true);
                    try {
                        synchronized (consoleScanner){
                            consoleScanner.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    commandsData.setParameter(consoleScanner.getStr());
                }

                if(commandsData.isUserNeedToShowFlatArr()){
                    for(int i =0;i<commandsData.getFlats().length;i++){
                        String str = commandsData.getFlats()[i].show() + "\n";
                        printer.println(str);
                    }
                }
            }

        }

//        Для получения массива элементов
//        public Flat[] getFlatArr(TransferCenter transferCenter) throws ConnectionException {
//            dataBlock0 = new DataBlock();
//            dataBlock0.setCommandsData(CommandsData.SHOW);
//            dataBlock0.setUser(mainUser);
//            transferCenter.sendObjectToServer(dataBlock0);
//            new TimeLimitedCode(5, TimeUnit.SECONDS){
//
//                @Override
//                public void codeBlock() {
//                    dataBlock0 = (DataBlock) transferCenter.receiveObjectFromServer();
//                }
//            }.start();
//            return dataBlock0.getFlats();
//        }

        public DataBlock processCommand(CommandsData commandsData, TransferCenter transferCenter) throws ConnectionException{
            dataBlock2 = new DataBlock();
            dataBlock2.setUser(mainUser);
            dataBlock2.setCommandsData(commandsData);
            copyFieldsFromTo(commandsData, dataBlock2);
            transferCenter.sendObjectToServer(dataBlock2);
            new TimeLimitedCode(5, TimeUnit.SECONDS){

                @Override
                public void codeBlock() {
                    dataBlock2 = (DataBlock) transferCenter.receiveObjectFromServer();
                }
            }.start();
            return dataBlock2;
        }
    }


    public class Login{

//        Обязательно всегда создавать новый. Вынесен отдельно, чтобы работала проверка подключения в другом потоке.
        private DataBlock dataBlock;

        public void login(WorkingWithGInterface gInterface, TransferCenter transferCenter) throws ConnectionException{
            login(gInterface, new DataBlock(), transferCenter);
        }

        private void login(WorkingWithGInterface gInterface, DataBlock newDataBlock, TransferCenter transferCenter) throws ConnectionException{

            GLogOrRegChoice gLogOrRegChoice = new GLogOrRegChoice(gInterface);
            gInterface.setSpaceForInteraction(gLogOrRegChoice.getPanel());

            while (gLogOrRegChoice.getAnswer() == null){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String answer = gLogOrRegChoice.getAnswer();


//        Printer.println("Необходимо авторизироваться.");
//        Printer.println("Войти - 0, зарегестрироваться - 1.");
//            Scanner scanner = new Scanner(System.in);
            int scan;
            try {
                scan = Integer.valueOf(answer);
            }catch (Exception e){
//            Printer.println("Это что-то страшное... Попробуем ещё раз!");
                JOptionPane.showConfirmDialog(new JOptionPane(), "Это что-то страшное... Попробуем ещё раз!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                login(gInterface, new DataBlock(), transferCenter);
                return;
            }
            if(scan == 0){
                entering(transferCenter, gInterface);
            }
            else {
                if(scan == 1){
                    registering(new DataBlock(), transferCenter, gInterface);
                }
                else {
//                Printer.println("Нужно выбрать один из вариантов!");
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Нужно выбрать один из вариантов!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                    login(gInterface, transferCenter);
                    return;
                }
            }
        }

        public void entering(TransferCenter transferCenter, WorkingWithGInterface gInterface) throws ConnectionException{
            entering(new DataBlock(), transferCenter, gInterface);
        }

        private void entering(DataBlock newDataBlock, TransferCenter transferCenter, WorkingWithGInterface gInterface) throws ConnectionException{
//        CheckConnection checkConnection = new CheckConnection(this, gInterface);
            boolean end = false;
            while (!end){
                Lock lock = new ReentrantLock();
                 lock.lock();
                 Condition condition = lock.newCondition();
                 GLogin gLogin = new GLogin(lock, condition, gInterface);
                 gInterface.setSpaceForInteraction(gLogin.getPanel());
                 try {
                     condition.await();
                 } catch (InterruptedException e) { }
                 lock.unlock();
                 String name = gLogin.getLogin();
                 String password = gLogin.getPassword();



                 User user = new User(false, name, password);

                 dataBlock = newDataBlock;
                 dataBlock.setUser(user);
                 dataBlock.setCommandsData(CommandsData.CHECKUSER);


                 new TimeLimitedCode(5, TimeUnit.SECONDS) {

                     @Override
                     public void codeBlock() {
                         transferCenter.sendObjectToServer(dataBlock);
                         dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                     }
                 }.start();


                 if(dataBlock.getParameter().equals("true")){
                     ConsolePrinter.println(dataBlock.getPhrase());
                     mainUser = user;
                     end = true;
                 }
                 else {
                     JOptionPane.showConfirmDialog(new JOptionPane(), dataBlock.getPhrase() + "\nПробуем занова!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                     entering(transferCenter, gInterface);
                     return;
                 }


            }
        }

        public void registering(TransferCenter transferCenter, WorkingWithGInterface gInterface) throws ConnectionException{
            registering(new DataBlock(), transferCenter, gInterface);
        }

        private void registering(DataBlock newDataBlock, TransferCenter transferCenter, WorkingWithGInterface gInterface) throws ConnectionException{
//        CheckConnection checkConnection = new CheckConnection(this, gInterface);
            boolean end = false;
            while (!end){

//                Printer.println("Введите имя пользователя:");
//                String name = scanner.nextLine();
//                Printer.println("Введите пароль:");
//                String password = scanner.nextLine();

//                mainWindow.getContentPane().removeAll();
//                mainWindow.repaint();

                    Lock lock = new ReentrantLock();
                    lock.lock();
                    Condition condition = lock.newCondition();
                    GLogin GLogin = new GLogin(lock, condition, gInterface);
                    gInterface.setSpaceForInteraction(GLogin.getPanel());
//                mainWindow.add(login.getPanel());
//                mainWindow.setVisible(true);
                try {
                    condition.await();
                } catch (InterruptedException e) { }
                lock.unlock();

//                Login login = new Login();
//                mainWindow.add(login.getPanel());
//                mainWindow.setVisible(true);
//                while (login.getLogin() == null | login.getPassword() == null){
//                    Thread.sleep(50);
//                }
                    String name = GLogin.getLogin();
                    String password = GLogin.getPassword();



                    User user = new User(true, name, password);

//                DataBlock dataBlock = new DataBlock();
                    dataBlock = newDataBlock;
                    dataBlock.setUser(user);
                    dataBlock.setCommandsData(CommandsData.CHECKUSER);


                    new TimeLimitedCode(5, TimeUnit.SECONDS){

                        @Override
                        public void codeBlock() {
                            transferCenter.sendObjectToServer(dataBlock);
                            dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                        }
                    }.start();

//                checkConnection.start();
//                serverAnswer = false;
//                transferCenter.sendObjectToServer(dataBlock);
//                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
//                serverAnswer = true;

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
                        registering(new DataBlock(), transferCenter, gInterface);
                        return;
                    }

            }
        }
    }
}