package GraphicalUserInterface;

import javax.swing.*;
import java.util.ResourceBundle;

public interface WindowPane {
    public JPanel getPanel();
    public void setLocale(ResourceBundle resourceBundle);
}
