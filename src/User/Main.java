package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import GraphicalUserInterface.GInterfaceControlCenter;
import GraphicalUserInterface.WorkingWithGInterface;

import java.io.*;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {


        TransferCenter transferCenter = new TransferCenter();
        UserWork user = new UserWork();
        WorkingWithGInterface gInterface = new GInterfaceControlCenter();

        ProcessControlCenter processControlCenter = new ProcessControlCenter(transferCenter, user, gInterface);
        processControlCenter.start();
    }
}
