package GraphicalUserInterface.VisualSpace;

import CommonClasses.CommandsData;
import CommonClasses.Flat;
import GraphicalUserInterface.WindowPart;
import GraphicalUserInterface.WorkingWithGInterface;
import HelpingModuls.ConnectionException;
import User.ProcessControlCenter;
import User.TransferCenter;
import User.UserWork;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisualSpaceControlCenter implements  WindowPart{

    private UserWork userWork;
    private TransferCenter transferCenter;
    private ProcessControlCenter processControlCenter;
    private GVisualSpace gVisualSpace;
    private String[][] usersColour;
    private JPanel visualSpace;
    private Flat[] flats;
    private String[][] userColourVariations;
    private WorkingWithGInterface gInterface;
    private DifferenceHandler differenceHandler;

    public VisualSpaceControlCenter(UserWork userWork, TransferCenter transferCenter, ProcessControlCenter processControlCenter, WorkingWithGInterface gInterface){
        this.processControlCenter = processControlCenter;
        this.transferCenter = transferCenter;
        this.userWork = userWork;
        this.gInterface = gInterface;
    }

    private void createVisualSpace(){
        try {
            flats = userWork.new CommunicateWithServerByCommands().processCommand(CommandsData.SHOW, transferCenter).getFlats();
        } catch (ConnectionException e) {
            JOptionPane.showConfirmDialog(new JOptionPane(), "Сервер не отвечает!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
            stopDifferenceHandler();
            processControlCenter.reConnect();
            processControlCenter.working();
            return;
        }

        GVisualSpace gVisualSpace = new GVisualSpace(flats, processControlCenter, userColourVariations, gInterface, userWork, this, transferCenter);
        visualSpace = gVisualSpace.getPanel();
    }

    private void startWork(){

        ExecutorService service = Executors.newSingleThreadExecutor();
        differenceHandler = new DifferenceHandler(flats, gVisualSpace, processControlCenter, userWork, transferCenter, userColourVariations);
        service.execute(differenceHandler);
    }

    public void stopDifferenceHandler(){
        differenceHandler.setStop(true);
    }

    @Override
    public JPanel getPanel() {
        createVisualSpace();
        startWork();
        return visualSpace;
    }
}
