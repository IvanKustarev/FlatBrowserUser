package Resources;

import java.util.ListResourceBundle;

public class Resources_de extends ListResourceBundle implements Naming{

    private String name = "Deutsch";

    private static final Object[][] contents =
            {
                    {"s1", "hello"},
                    {"Консольная работа с командами", "Konsolenarbeit mit Befehlen"},
                    {"Табличная работа с элементами", "Tabellarische Arbeit mit Elementen"},
                    {"Область визуализации", "Visualisierungsbereich"}
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    @Override
    public String getName() {
        return name;
    }
}
