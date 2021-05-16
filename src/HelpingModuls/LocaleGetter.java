package HelpingModuls;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleGetter {
    static public Locale getLocaleByBundle(ResourceBundle resourceBundle){

        String bundleName = resourceBundle.getBaseBundleName();

        if(bundleName.contains("Resources.Resources_ru")){
            return new Locale("ru", "RU");
        }
        if(bundleName.contains("Resources.Resources_de")){
            return new Locale("de", "GR");
        }
        return Locale.getDefault();
    }
}
