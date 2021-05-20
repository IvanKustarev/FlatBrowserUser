package User;

//import CommonClasses.AbstractDataBlock;
//import CommonClasses.DataBlock;

import GraphicalUserInterface.InterfaceControlCenter;
import GraphicalUserInterface.GInterface;
import HelpingModuls.LocaleGetter;
import Resources.ResourceControlCenter;
//import javafx.scene.input.DataFormat;

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
        ResourceBundle bundleLV = ResourceBundle.getBundle("Resources.Resources_lv", new Locale("lv", "LV"));
        ResourceBundle bundleES = ResourceBundle.getBundle("Resources.Resources_es", new Locale("es", "ES"));

        ResourceBundle[] resourceBundles = new ResourceBundle[]{bundleRu, bundleDE, bundleLV, bundleES};

        ResourceControlCenter resourceControlCenter = new ResourceControlCenter(bundleRu, resourceBundles);

        TransferCenter transferCenter = new TransferCenter();
        UserWork user = new UserWork(resourceControlCenter);
        GInterface gInterface = new InterfaceControlCenter(resourceControlCenter);


        ProcessControlCenter processControlCenter = new ProcessControlCenter(transferCenter, user, gInterface, resourceControlCenter);
        processControlCenter.start();
    }

    public static Locale getLocaleByResourceName(String resourceName){
        if(resourceName != null) {
            if (resourceName.equals("Resources.Resources_ru")) {
                return new Locale("ru", "Ru");
            }
            if (resourceName.equals("Resources.Resources_de")) {
                return new Locale("de", "GR");
            }
            if (resourceName.equals("Resources.Resources_lv")) {
                return new Locale("lv", "LV");
            }
            if (resourceName.equals("Resources.Resources_es")) {
                return new Locale("es", "ES");
            }
        }
        return new Locale("ru", "Ru");
    }
}
