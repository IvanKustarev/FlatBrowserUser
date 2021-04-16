package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import GraphicalUserInterface.GInterfaceControlCenter;
import GraphicalUserInterface.Login;
import GraphicalUserInterface.MainWindow;
import GraphicalUserInterface.WorkingWithGInterface;

import java.io.*;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {

//        TransferCenter transferCenter = new TransferCenter();

//        TransferCenter transferCenter = new TransferCenter();


//        transferCenter.sendObjectToServer(new Flat());



//    Login login = new Login();


//        MainWindow mainWindow = new MainWindow();
        WorkingWithGInterface gInterface = new GInterfaceControlCenter();

        UserWork user = new UserWork(new TransferCenter(gInterface), gInterface);
//        user.startCheckingCommands();
    }
}
