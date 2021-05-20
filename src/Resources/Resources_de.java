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
                    {"Подключение", "Anschluß"},
                    {"Ввод данных", "Dateneingabe"},
                    {"Пользователь с таким логином уже существует.", "Ein Benutzer mit einem solchen Login existiert bereits."},
                    {"OK", "Gut"},
                    {"Пользователь успешно добавлен", "Benutzer erfolgreich hinzugefügt"},
                    {"В главное меню", "Im Hauptmenü"},
                    {"Пользователя с таким логином не существует!", "Ein Benutzer mit einem solchen Login existiert nicht!"},



                    {"Введите команду сюда", "Geben Sie den Befehl hier ein"},
                    {"Исполнить", "Erfüllen"},
                    {"Для просмотра списка команд необходимо ввести \"help\"", "Um die Liste der Befehle anzuzeigen, geben Sie \" help ein\""},
                    {"Введите фильтр", "Filter eingeben"},
                    {"Изменить", "Andern"},
                    {"Выберете строку!", "Wählen Sie eine Zeile!"},
                    {"Объект принадлежит другому пльзователю!", "Wählen Sie eine Zeile!"},
                    {"Удалить", "Entfernen"},
                    {"Необходимо выбрать строку!", "Sie müssen eine Zeile auswählen!"},
                    {"Сервер не отвечает!", "Der Server reagiert nicht!"},
                    {"Объект успешно удалён!", "Objekt erfolgreich gelöscht!"},
                    {"Имя", "Name"},
                    {"Координата x", "X-Koordinate"},
                    {"Координата y", "Y-Koordinate"},
                    {"Расположение", "Lage"},
                    {"Количество комнат", "Anzahl der Zimmer"},
                    {"Мебель", "Die Möbel"},
                    {"Вид", "Ausblick"},
                    {"Транспортные маршруты", "Transportwege"},
                    {"Имя дома", "Name des Hauses"},
                    {"Год пострйки дома", "Das Jahr der Reinigung des Hauses"},
                    {"Количество этажей в доме", "Anzahl der Stockwerke im Haus"},
                    {"Количество квартир на одном этаже", "Anzahl der Wohnungen pro Etage"},
                    {"Количество лифтов", "Anzahl der Aufzüge"},
                    {"Сохранить изменения", "Änderungen speichern"},
                    {"У квартиры обязательно должно быть имя", "УDie Wohnung muss unbedingt einen Namen haben"},
                    {"У квартиры обязательно должна быть координата по X", "Die Wohnung muss eine X-Koordinate haben"},
                    {"У квартиры обязательно должно быть координата по Y", "Die Wohnung muss eine Y-Koordinate haben"},
                    {"У квартиры обязательно должно быть расположение", "Die Wohnung muss unbedingt einen Standort haben"},
                    {"У квартиры обязательно должно быть количество комнат", "Die Wohnung muss die Anzahl der Zimmer haben"},
                    {"У квартиры обязательно должна быть мебель", "Die Wohnung muss Möbel haben"},
                    {"Все поля связанные с домом либо пустые, либо заполненные", "Alle Felder, die mit dem Haus verbunden sind, sind entweder leer oder ausgefüllt"},
                    {"Мебель задана некорректно!", "Die Möbel sind falsch!"},
                    {"Транспорт задан некорректно!", "Transport falsch eingestellt!"},
                    {"Вид задан некорректно!", "Die Ansicht ist falsch!"},
                    {"Потеряно соединение с сервером!", "Die Verbindung zum Server ist verloren!"},
                    {"Удаление прошло успешно.", "Die Deinstallation war erfolgreich."},
                    {"Потеряно соединение с сервером!", "Die Verbindung zum Server ist verloren!"},
                    {"Объект успешно обновлён", "Objekt erfolgreich aktualisiert"},
                    {"В этих координатах нет квартир!", "In diesen Koordinaten gibt es keine Wohnungen!"},
                    {"Объект принадлежит другому пользователю!", "Das Objekt gehört einem anderen Benutzer!"},
                    {"Имя пользователя", "Benutzername"},
                    {"id", "id"},
                    {"Дата создания объекта", "Erstellungsdatum des Objekts"},
                    {"Имя квартиры", "Name der Wohnung"},
                    {"Координата x", "X-Koordinate"},
                    {"Координата y", "Y-Koordinate"},
                    {"Расположение", "Lage"},
                    {"Количество комнат", "Anzahl der Zimmer"},
                    {"поле пустое", "feld leer"},
                    {"Транспортные маршруты", "Transportwege"},
                    {"Имя дома", "Name des Hauses"},
                    {"Год пострйки дома", "Das Jahr der Reinigung des Hauses"},
                    {"Количество этажей в доме", "Anzahl der Stockwerke im Haus"},
                    {"Количество квартир на одном этаже", "Anzahl der Wohnungen pro Etage"},
                    {"Количество лифтов", "Anzahl der Aufzüge"},
                    {"Имя дома", "Name des Hauses"},
                    {"Год пострйки дома", "Das Jahr der Reinigung des Hauses"},
                    {"Количество этажей в доме", "Anzahl der Stockwerke im Haus"},
                    {"Количество квартир на одном этаже", "Anzahl der Wohnungen pro Etage"},
                    {"Количество лифтов", "Anzahl der Aufzüge"},

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
