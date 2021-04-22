package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;

public interface WorkingWithGInterface {
    public void setSpaceForInteraction(JPanel spaceForInteraction);

    public void removeWindow();

    public void restartWindow();

    public void creatingWindow();

    public void setTopPartOfWindow(JPanel topPartOfWindow);

    public void repaint();

    public Dimension getMainWindowSize();
}
