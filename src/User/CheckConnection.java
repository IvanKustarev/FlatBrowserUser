package User;

//import CommonClasses.DataBlock;

import java.util.Scanner;

public class CheckConnection extends Thread{

    UserWork user;

    public CheckConnection(UserWork user){
        this.user = user;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(5000);
            if(user.serverAnswer.equals(false)){
                Printer.println("Сервер не отвечает...\nПытаюсь восстановить соединение.");
                while (true){

                    CreateConnection createConnection = new CreateConnection(user.transferCenter);
                    createConnection.start();
                    Thread.sleep(5000);

                    if(createConnection.connectionReCreated == true){
                        UserWork user1 = new UserWork(createConnection.transferCenter);
                        user1.startCheckingCommands();
                    }
                    Printer.println("Введите 0, чтобы выйти или 1, чтобы продолжить попытки восстановить соединение.");
                    if((new Scanner(System.in)).nextInt() == 0){
                        Printer.println("Выхожу из программы...");
                        System.exit(0);
                    }
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
