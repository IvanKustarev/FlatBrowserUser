package Resources;

import java.util.ListResourceBundle;

public class Resources_ru extends ListResourceBundle implements Naming{

    private String name = "Русский";

    @Override
    public String getName() {
        return name;
    }

    private static final Object[][] contents =
            {
                    {"s1", "Привет"},
                    {"Консольная работа с командами", "Консольная работа с командами"},
                    {"Табличная работа с элементами", "Табличная работа с элементами"},
                    {"Область визуализации", "Область визуализации"}
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
