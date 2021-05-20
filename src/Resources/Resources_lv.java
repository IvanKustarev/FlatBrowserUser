package Resources;

import java.util.ListResourceBundle;

public class Resources_lv extends ListResourceBundle implements Naming {

    private String name = "lietuviškas";

    @Override
    public String getName() {
        return name;
    }

    private static final Object[][] contents =
            {
                    {"s1", "Sveikinimas"},
                    {"Консольная работа с командами", "Konsolės darbas su komandomis"},
                    {"Табличная работа с элементами", "Табличная работа с элементами"},
                    {"Область визуализации", "Табличная darbas su elementais"},
                    {"User:", "Naudotojas:"},
                    {"НАЗАД", "Atgal"},
                    {"ip", "ip adresas"},
                    {"Введены некорректные данные!", "Įvestas некорректные duomenų!"},
                    {"port", "uostas"},
                    {"Ошибка подключения", "Klaida prisijungti"},
                    {"CONNECT", "Prisijungti"},
                    {"LOGIN", "Įėjimas"},
                    {"REGISTER", "Registracija"},

                    {"Попробуем снова...", "Pabandykime dar kartą..."},
                    {"Проблема с созданием порта!", "Problema kuriant uoste!"},
                    {"Проблема с подключением к серверу. Пробуем всё заново!", "Problema jungiantis prie serverio. Пробуем vis iš naujo!"},
                    {"Это что-то страшное... Попробуем ещё раз!", "Tai yra baisi... Pabandykime dar kartą!"},
                    {"Нужно выбрать один из вариантов!", "Reikia pasirinkti vieną iš variantų!"},
                    {"Да", "Taip"},
                    {"Нет", "Nėra"},
                    {"Продолжаем попытки подключения к серверу?", "Ir toliau bando prisijungti prie serverio?"},
                    {"Завершаем работу!", "Завершаем darbą!"},
                    {"Уведомление", "Pranešimas"},

                    {"Login:", "Vartotojo vardas"},
                    {"Password:", "Slaptažodis"},
                    {"Login", "Įėjimas"},

                    {"Неверный пароль!", "Slaptažodį!"},
                    {"Сменить пользователя", "Zrm"},
                    {"Подключение", "Prisijungti"},
                    {"Ввод данных", "Duomenų įvedimas"},
                    {"Пользователь с таким логином уже существует.", "Vartotojas su tokiu логином jau egzistuoja."},
                    {"OK", "Gerai"},
                    {"Пользователь успешно добавлен", "Vartotojas sėkmingai pridėtas"},
                    {"В главное меню", "Į pagrindinį meniu"},
                    {"Пользователя с таким логином не существует!", "Vartotojo su tokiu логином nėra!"},


                    {"Введите команду сюда", "Įrašykite čia"},
                    {"Исполнить", "Įvykdyti"},
                    {"Для просмотра списка команд необходимо ввести \"help\"", "Norėdami peržiūrėti sąrašą komandų, norite įvesti \"help\""},
                    {"Введите фильтр", "Įveskite filtras"},
                    {"Изменить", "Išduoti"},
                    {"Выберете строку!", "Pasirinkite eilutę!"},
                    {"Объект принадлежит другому пльзователю!", "Objektas priklauso kitam vartotojui!"},
                    {"Удалить", "Pašalinti"},
                    {"Необходимо выбрать строку!", "Reikia pasirinkti eilutę!"},
                    {"Сервер не отвечает!", "Serveris neatsako!"},
                    {"Объект успешно удалён!", "Objektas sėkmingai ištrintas!"},
                    {"Имя", "Vardas"},
                    {"Координата x", "Koordinatė x"},
                    {"Координата y", "Koordinatė y"},
                    {"Расположение", "Išsidėstymas"},
                    {"Количество комнат", "Kambarių skaičius"},
                    {"Мебель", "Baldai"},
                    {"Вид", "Rūšis"},
                    {"Транспортные маршруты", "Transporto maršrutai"},
                    {"Имя дома", "Vardas namuose"},
                    {"Год пострйки дома", "Metais pastatytas namuose"},
                    {"Количество этажей в доме", "Aukštų skaičius namuose"},
                    {"Количество квартир на одном этаже", "Skaičius butų, viename aukšte"},
                    {"Количество лифтов", "Skaičius liftai"},
                    {"Сохранить изменения", "Išsaugoti pakeitimus"},
                    {"У квартиры обязательно должно быть имя", "Jūs butas būtinai turi būti vardą"},
                    {"У квартиры обязательно должна быть координата по X", "Jūs butas būtinai turi būti koordinatė X"},
                    {"У квартиры обязательно должно быть координата по Y", "Jūs butas būtinai turi būti koordinatė Y"},
                    {"У квартиры обязательно должно быть расположение", "Jūs butas būtinai turi būti vieta"},
                    {"У квартиры обязательно должно быть количество комнат", "Jūs butas būtinai turi būti kambarių skaičius"},
                    {"У квартиры обязательно должна быть мебель", "Jūs butas būtinai turi būti baldai"},
                    {"Все поля связанные с домом либо пустые, либо заполненные", "Visi laukai susijusių su namų arba tušti, arba užpildytas"},
                    {"Мебель задана некорректно!", "Baldai šablono neveikti!"},
                    {"Транспорт задан некорректно!", "Transportas nurodyta neteisingai!"},
                    {"Вид задан некорректно!", "Vaizdas nurodyta neteisingai!"},
                    {"Потеряно соединение с сервером!", "Prarastas ryšys su serveriu!"},
                    {"Удаление прошло успешно.", "Trinti praėjo sėkmingai."},
                    {"Потеряно соединение с сервером!", "Prarastas ryšys su serveriu!"},
                    {"Объект успешно обновлён", "Objektas sėkmingai atnaujintas"},
                    {"В этих координатах нет квартир!", "Šių koordinatės nėra butų!"},
                    {"Объект принадлежит другому пользователю!", "Objektas priklauso kitam vartotojui!"},
                    {"Имя пользователя", "Vartotojo vardas"},
                    {"id", "id"},
                    {"Дата создания объекта", "Sukūrimo data objektas"},
                    {"Имя квартиры", "Vardas butai"},
                    {"Координата x", "Koordinatė x"},
                    {"Координата y", "Koordinatė y"},
                    {"Расположение", "Išsidėstymas"},
                    {"Количество комнат", "Kambarių skaičius"},
                    {"Мебель", "Baldai"},
                    {"Вид", "Rūšis"},
                    {"поле пустое", "laukas tuščias"},
                    {"Транспортные маршруты", "Transporto maršrutai"},
                    {"поле пустое", "laukas tuščias"},
                    {"Имя дома", "Vardas namuose"},
                    {"поле пустое", "laukas tuščias"},
                    {"Год пострйки дома", "Metais pastatytas namuose"},
                    {"поле пустое", "laukas tuščias"},
                    {"Количество этажей в доме", "Aukštų skaičius namuose"},
                    {"поле пустое", "laukas tuščias"},
                    {"Количество квартир на одном этаже", "Skaičius butų, viename aukšte"},
                    {"поле пустое", "laukas tuščias"},
                    {"Количество лифтов", "Skaičius liftai"},
                    {"поле пустое", "laukas tuščias"},
                    {"Имя дома", "Vardas namuose"},
                    {"Год пострйки дома", "Metais pastatytas namuose"},
                    {"Количество этажей в доме", "Aukštų skaičius namuose"},
                    {"Количество квартир на одном этаже", "Skaičius butų, viename aukšte"},
                    {"Количество лифтов", "Skaičius liftai"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
