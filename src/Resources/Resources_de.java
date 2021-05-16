package Resources;

import java.util.ListResourceBundle;

public class Resources_de extends ListResourceBundle implements Naming{

    private String name = "Deutsch";

    private static final Object[][] contents =
            {
                    {"s1", "hello"},
                    {"Консольная работа с командами", "Konsolenarbeit mit Befehlen"},
                    {"Табличная работа с элементами", "Tabellarische Arbeit mit Elementen"},
                    {"Область визуализации", "Visualisierungsbereich"},
                    {"User:", "Benutzer: "},
                    {"НАЗАД", "ZURÜCK"},
                    {"ip", "ip-adresse"},
                    {"Введены некорректные данные!", "Falsche Daten eingegeben!"},
                    {"port", "hafen"},
                    {"Ошибка подключения", "Verbindungsfehlers"},
                    {"CONNECT", "Mitmachen"},
                    {"LOGIN", "Anmeldung"},
                    {"REGISTER", "Eintragung"},

                    {"Попробуем снова...", "Versuchen wir es nochmal..."},
                    {"Проблема с созданием порта!", "Problem mit der Port-Erstellung!"},
                    {"Проблема с подключением к серверу. Пробуем всё заново!", "Problem mit der Verbindung zum Server. Wir versuchen es noch einmal!"},
                    {"Это что-то страшное... Попробуем ещё раз!", "Das ist etwas Schreckliches... Versuchen wir es noch einmal!"},
                    {"Нужно выбрать один из вариантов!", "Sie müssen eine der Optionen wählen!"},
                    {"Да", "Ja"},
                    {"Нет", "Nein"},
                    {"Продолжаем попытки подключения к серверу?", "Versuchen Sie weiterhin, eine Verbindung zum Server herzustellen?"},
                    {"Завершаем работу!", "Wir beenden die Arbeit!"},
                    {"Уведомление", "Benachrichtigung"},

                    {"Login:", "Benutzername"},
                    {"Password:", "Passwort"},
                    {"Login", "Eingang"},

                    {"Неверный пароль!", "НFalsches Passwort!"},
                    {"Сменить пользователя", "Benutzer ändern"},


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
