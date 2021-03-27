package L6User;

//import CommonClasses.AbstractDataBlock;
import CommonClasses.CommandsData;
//import CommonClasses.DataBlock;
import CommonClasses.Flat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {

//        TransferCenter transferCenter = new TransferCenter();

//        TransferCenter transferCenter = new TransferCenter();


//        transferCenter.sendObjectToServer(new Flat());


        User user = new User(new TransferCenter());
        user.startCheckingCommands();
    }
}
