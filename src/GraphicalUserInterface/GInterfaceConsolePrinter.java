package GraphicalUserInterface;

import HelpingModuls.Printer;

import javax.swing.*;

public class GInterfaceConsolePrinter implements Printer {
    JEditorPane jEditorPane;

    public GInterfaceConsolePrinter(JEditorPane jEditorPane){
        this.jEditorPane = jEditorPane;
    }

    @Override
    public <T> void print(T phrase) {
        jEditorPane.setText(jEditorPane.getText() + String.valueOf(phrase));
    }

    @Override
    public <T> void println(T phrase) {
        jEditorPane.setText(jEditorPane.getText() + String.valueOf(phrase) + "\n");
    }
}
