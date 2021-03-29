package User;

//import CommonClasses.DataBlock;

import java.util.Scanner;

public class CheckConnection extends Thread{

//    Boolean serverAnswer;
    User user;

    public CheckConnection(User user){
        this.user = user;
//        this.serverAnswer = serverAnswer;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(5000);
            if(user.serverAnswer.equals(false)){
                System.out.println("Сервер не отвечает...\nПытаюсь восстановить соединение.");
                while (true){

                    CreateConnection createConnection = new CreateConnection(user.transferCenter);
                    createConnection.start();
                    Thread.sleep(5000);
//                    System.out.println("ttt");
//                    System.out.println(createConnection.connectionReCreated);
                    if(createConnection.connectionReCreated == true){
                        User user1 = new User(createConnection.transferCenter);
                        user1.startCheckingCommands();
                    }
                    System.out.println("Введите 0, чтобы выйти или 1, чтобы продолжить попытки восстановить соединение.");
                    if((new Scanner(System.in)).nextInt() == 0){
                        System.out.println("Выхожу из программы...");
                        System.exit(0);
                    }
                }


            }
//            System.out.println("allIsRight");
//            else {
//                System.out.println("");
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
