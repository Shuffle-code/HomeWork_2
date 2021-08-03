package HW_7.Server.server;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthService { /*Класс для проверки логина и пароля*/
    private static final List<Entry> entries; /*Коллекция List пользователей*/

    static {/*Почти "база данных"*/
        entries = List.of(/* Установил JDK16, не работал метод: Возвращающий неизменяемый список, содержащий три элемента.*/
                new Entry("name1", "nick1", "pass1"),
                new Entry("name2", "nick2", "pass2"),
                new Entry("name3", "nick3", "pass3")
        );
    }
    /* Создали метод "поиска пользователя по имени и паролю" метод возращает первый Optional, при совпадении логина и пароля */
    public Optional<Entry> findUserByLoginAndPassword(String login, String password) {
        /**
         for (AuthService.Entry entry : entries) {
         if (entry.login.equals(login) && entry.password.equals(password)) {
         return Optional.of(entry);
         }
         }

         */
        /* Понятная форма записи, через цикл for*/

        return entries.stream()
                .filter(entry -> entry.login.equals(login) && entry.password.equals(password))
                .findFirst(); /*Метод возращающий Optional, первый объект*/
        /* Сложная форма записи, через LMD-выражение ф.интерфейса Predicate(утверждение истина или ложь)*/
    }

    static class Entry {/*Класс пользователей со свойствами*/
        String name;
        String login;
        String password;

        Entry(String name, String login, String password) { /*Конструктор*/
            this.name = name;
            this.login = login;
            this.password = password;
        }

        String getName() {
            return name;
        } /*Геты для обращения к свойствам класса Entry вне класа и пакета*/

        String getLogin() {
            return login;
        }

        String getPassword() {
            return password;
        }

        @Override /*переопределение метода для корректного сравнения (В стандартном исполнении сравнивает ссылки и значения, нам надо только значения)*/
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(name, entry.name) && Objects.equals(login, entry.login) && Objects.equals(password, entry.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, login, password);
        }
    }
}
