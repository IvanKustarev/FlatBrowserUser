package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import GraphicalUserInterface.GInterfaceControlCenter;
import GraphicalUserInterface.VisualSpace.VisualSpaceControlCenter;
import GraphicalUserInterface.WorkingWithGInterface;
import Resources.ResourceControlCenter;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {


        ResourceBundle bundleRu = ResourceBundle.getBundle("Resources.Resources_ru", new Locale("ru", "RU"));
        ResourceBundle bundleDE = ResourceBundle.getBundle("Resources.Resources_de", new Locale("de", "DE"));


        ResourceBundle[] resourceBundles = new ResourceBundle[]{bundleRu, bundleDE};

        ResourceControlCenter resourceControlCenter = new ResourceControlCenter(bundleRu, resourceBundles);



        TransferCenter transferCenter = new TransferCenter();
        UserWork user = new UserWork();
        WorkingWithGInterface gInterface = new GInterfaceControlCenter(resourceControlCenter);

        ProcessControlCenter processControlCenter = new ProcessControlCenter(transferCenter, user, gInterface, resourceControlCenter);
        processControlCenter.start();
    }
}
