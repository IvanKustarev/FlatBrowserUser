package GraphicalUserInterface.GPanes.VisualSpace;

import CommonClasses.CommandsData;
import CommonClasses.Flat;
import GraphicalUserInterface.WindowPane;
import GraphicalUserInterface.GInterface;
import HelpingModuls.ConnectionException;
import User.ProcessControlCenter;
import User.TransferCenter;
import User.UserWork;
import GraphicalUserInterface.*;

import javax.swing.*;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisualSpaceControlCenter implements WindowPane {

    private UserWork userWork;
    private TransferCenter transferCenter;
    private ProcessControlCenter processControlCenter;
    private GVisualSpace gVisualSpace;
    private String[][] usersColour;
    private JPanel visualSpace;
    private Flat[] flats;
    private String[][] userColourVariations;
    private GInterface gInterface;
    private DifferenceHandler differenceHandler;
    private ResourceBundle resourceBundle;

    public VisualSpaceControlCenter(UserWork userWork, TransferCenter transferCenter, ProcessControlCenter processControlCenter, GInterface gInterface, ResourceBundle resourceBundle){
        this.processControlCenter = processControlCenter;
        this.transferCenter = transferCenter;
        this.userWork = userWork;
        this.gInterface = gInterface;
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    private void createVisualSpace(){
        try {
            flats = userWork.new CommunicateWithServerByCommands().processCommand(CommandsData.SHOW, transferCenter).getFlats();
        } catch (ConnectionException e) {
            JOptionPane.showConfirmDialog(new JOptionPane(), "Сервер не отвечает!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
            stopDifferenceHandler();
            processControlCenter.reConnect();
//            processControlCenter.working();
            return;
        }

        GVisualSpace gVisualSpace = new GVisualSpace(flats, processControlCenter, userColourVariations, gInterface, userWork, this, transferCenter, resourceBundle);
        visualSpace = gVisualSpace.getPanel();
    }

    private void startWork(){

        ExecutorService service = Executors.newSingleThreadExecutor();
        differenceHandler = new DifferenceHandler(flats, gVisualSpace, processControlCenter, userWork, transferCenter, userColourVariations, gInterface);
        service.execute(differenceHandler);
    }

    public void stopDifferenceHandler(){
        if(differenceHandler != null){
            differenceHandler.setStop(true);
        }
    }

    @Override
    public JPanel getPanel() {
        createVisualSpace();
        startWork();
        return visualSpace;
    }
}
