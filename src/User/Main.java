package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import GraphicalUserInterface.InterfaceControlCenter;
import GraphicalUserInterface.GInterface;
import HelpingModuls.LocaleGetter;
import Resources.ResourceControlCenter;
import javafx.scene.input.DataFormat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {

        ResourceBundle bundleRu = ResourceBundle.getBundle("Resources.Resources_ru", new Locale("ru", "RU"));
        ResourceBundle bundleDE = ResourceBundle.getBundle("Resources.Resources_de", new Locale("de", "GR"));

        ResourceBundle[] resourceBundles = new ResourceBundle[]{bundleRu, bundleDE};

        ResourceControlCenter resourceControlCenter = new ResourceControlCenter(bundleRu, resourceBundles);

        TransferCenter transferCenter = new TransferCenter();
        UserWork user = new UserWork(resourceControlCenter);
        GInterface gInterface = new InterfaceControlCenter(resourceControlCenter);

        ProcessControlCenter processControlCenter = new ProcessControlCenter(transferCenter, user, gInterface, resourceControlCenter);
        processControlCenter.start();
    }
}
