package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import java.io.*;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {

//        TransferCenter transferCenter = new TransferCenter();

//        TransferCenter transferCenter = new TransferCenter();


//        transferCenter.sendObjectToServer(new Flat());


        User user = new User(new TransferCenter());
        user.startCheckingCommands();
    }
}
