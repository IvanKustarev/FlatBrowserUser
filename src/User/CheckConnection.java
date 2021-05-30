//package User;
//
////import CommonClasses.DataBlock;
//
//import GraphicalUserInterface.GInterfaceControlCenter;
//import GraphicalUserInterface.MainWindow;
//import GraphicalUserInterface.WorkingWithGInterface;
//
//import java.util.Scanner;
//
//public class CheckConnection extends Thread{
//
//    UserWork user;
//    WorkingWithGInterface gInterface;
//
//    //Надо переделывать
//    public CheckConnection(UserWork user, WorkingWithGInterface gInterface){
//        this.user = user;
//        this.gInterface = gInterface;
//    }
//
//    @Override
//    public void run(){
//        try {
//            Thread.sleep(5000);
//            if(user.serverAnswer.equals(false)){
//                Printer.println("Сервер не отвечает...\nПытаюсь восстановить соединение.");
//                while (true){
//
//                    CreateConnection createConnection = new CreateConnection(user.transferCenter);
//                    createConnection.start();
//                    Thread.sleep(5000);
//
//                    if(createConnection.connectionReCreated == true){
//
//                        UserWork user1 = new UserWork(createConnection.transferCenter);
//                        gInterface.restartWindow(user1, createConnection.transferCenter);
////                        user1.startCheckingCommands();
//                    }
//                    Printer.println("Введите 0, чтобы выйти или 1, чтобы продолжить попытки восстановить соединение.");
//                    if((new Scanner(System.in)).nextInt() == 0){
//                        Printer.println("Выхожу из программы...");
//                        System.exit(0);
//                    }
//                }
//
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
