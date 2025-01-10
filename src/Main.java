import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    static User admin = new User("admin", "adminPas", Role.ADMIN);
    static User moderator = new User("moderator", "modPas", Role.MODERATOR);
    static User user = new User("user", "userPas", Role.USER);
    static User[] users = {admin, moderator, user};

    static Notes[] notes = new Notes[5];
    static int countOfNotes = 0;

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в приложение Заметки!");
        mainMenu(new Scanner(System.in));

    }

    public static void mainMenu(Scanner sc) {
        System.out.println("""
                
                Выберите пункт меню (введите цифру):
                1 - Вход в приложение под именем пользователя
                0 - Выход из приложения
                
                """);
        int point = sc.nextInt();
        switch (point) {
            case 1 -> logInProgramm(new Scanner(System.in));
            case 0 -> {
                sc.close();
                exit();
            }
            default -> {
                System.out.println("\nВыбранный пункт отсутствует, попробуйте снова\n");
                mainMenu(new Scanner(System.in));
            }
        }
    }

    public static void logInProgramm(Scanner sc) {
        User currentUser = null;

        while (currentUser == null) {
            System.out.println("\nВведите логин: ");
            String login = sc.nextLine().trim();
            System.out.println("Введите пароль: ");
            String password = sc.nextLine().trim();

            try {
                for (User us : users) {
                    if (us.getLogin().equals(login) && us.getPassword().equals(password)) {
                        currentUser = new User(us);
                    }
                }
                if (currentUser == null) {
                    throw new NullPointerException("\nНеверное имя пользователя или пароль.");
                }
            } catch (NullPointerException npe) {
                System.out.println(npe.getMessage());
                System.out.println("Попробуйте снова.\n");
            }
        }

        menu(new Scanner(System.in), currentUser);
    }

    public static void exit() {
        System.exit(0);
    }

    public static void menu(Scanner sc, User currentUser) {
        System.out.println("""
                
                Что вы хотите сделать? (введите цифру)
                1 - Выполнить вход под другим пользователем
                2 - Создать новую заметку
                3 - Искать заметку по названию
                0 - Выйти из приложения
                
                """);
        int point = sc.nextInt();
        switch (point) {
            case 1 -> logInProgramm(new Scanner(System.in));
            case 2 -> {
                try {
                    if (currentUser.getRole().equals(Role.USER)) throw new PermissionExeption();
                    createNewNote(new Scanner(System.in), currentUser);
                } catch (PermissionExeption pe) {
                    System.out.println(pe.getMessage());
                    menu(new Scanner(System.in), currentUser);
                }
            }
            case 3 -> searchNote(new Scanner(System.in), currentUser);
            case 0 -> {
                sc.close();
                exit();
            }
            default -> {
                System.out.println("\nВыбранный пункт отсутствует, попробуйте снова\n");
                menu(new Scanner(System.in), currentUser);
            }
        }
        sc.close();
    }

    public static void createNewNote(Scanner sc, User author) {
        if (countOfNotes == 5) {
            System.out.println("Извините, места больше нет");
            menu(new Scanner(System.in), author);
        }

        String title = "";
        String bodyOfNote;

        while (title.isEmpty()) {
            try {
                System.out.println("\nВведите название заметки:\n");
                title = sc.nextLine().trim();
                if (title.isEmpty()) throw new NullPointerException();
            } catch (NullPointerException npe) {
                System.out.println("\nНазвание заметки не может быть пустым, попробуйте снова.\n");
            }
        }


        System.out.println("\nВведите содержимое заметки:\n");
        bodyOfNote = sc.nextLine();

        System.out.println("""
                
                Выберите тип заметки:
                1 - Фильмы
                2 - Рецепты
                3 - Покупки
                
                """);
        int point = sc.nextInt();
        switch (point) {
            case 1 -> {
                notes[countOfNotes] = new Movie(title, author, bodyOfNote);
                countOfNotes++;
            }
            case 2 -> {
                notes[countOfNotes] = new Recipe(title, author, bodyOfNote);
                countOfNotes++;
            }
            case 3 -> {
                notes[countOfNotes] = new Purchace(title, author, bodyOfNote);
                countOfNotes++;
            }
            default -> {
                System.out.println("\nВыбранный пункт отсутствует, попробуйте снова\n");
                createNewNote(new Scanner(System.in), author);
            }
        }
        menu(new Scanner(System.in), author);
    }

    public static void searchNote(Scanner sc, User currentUser) {
        System.out.println("\nВведите название заметки: \n");
        String title = sc.nextLine().trim();
        Notes requiredNote = null;
        int numberOfNote = 0;
        for (int i = 0; i < countOfNotes; i++) {
            if (notes[i].getTitle().equals(title)) {
                requiredNote = notes[i];
                numberOfNote = i;
            }
        } if (requiredNote == null) {
            System.out.println("\nЗаметка с таким названием не найдена.\n");
            menu(new Scanner(System.in), currentUser);
        }

        System.out.println("""
                
                Что вы хотите сделать? (введите цифру):
                1 - Вывести заметку в консоль
                2 - Изменить название заметки (доступно только для пользовтелей с ролью админ или модератор)
                3 - Заменить слово в заметке (доступно только для пользовтелей с ролью админ или модератор)
                4 - Заменить тело заметки (доступно только для пользовтелей с ролью админ или модератор)
                5 - Вывести автора заметки
                6 - Удалить заметку (доступно только для пользовтелей с ролью админ или модератор)
                
                """);

        int point = sc.nextInt();
        switch (point) {
            case 1 -> {
                System.out.println(requiredNote);
                menu(new Scanner(System.in), currentUser);
            }
            case 2 -> {
                try {
                    if (currentUser.getRole().equals(Role.USER)) throw new PermissionExeption();
                    String newTitle = "";
                    while (newTitle.isEmpty()) {
                        try {
                            System.out.println("\nВведите новое название заметки:\n");
                            newTitle = sc.nextLine().trim();
                            if (newTitle.isEmpty()) throw new NullPointerException();
                        } catch (NullPointerException npe) {
                            System.out.println("\nНазвание заметки не может быть пустым, попробуйте снова.\n");
                        }
                    }
                    requiredNote.setTitle(newTitle);
                    menu(new Scanner(System.in), currentUser);
                } catch (PermissionExeption pe) {
                    System.out.println(pe.getMessage());
                    menu(new Scanner(System.in), currentUser);
                }
            }
            case 3 -> {
                try {
                    if (currentUser.getRole().equals(Role.USER)) throw new PermissionExeption();
                    if (!(requiredNote instanceof Correctable)) throw new UnsupportedOperationException();
                    System.out.println("\nВведите слово, которое нужно заменить: \n");
                    sc.nextLine();
                    String curentWord = sc.nextLine().trim();
                    System.out.println("\nВведите новое слово: \n");
                    String newWord = sc.nextLine().trim();
                    ((Correctable) requiredNote).correct(curentWord, newWord);
                    menu(new Scanner(System.in), currentUser);
                } catch (PermissionExeption pe) {
                    System.out.println(pe.getMessage());
                    menu(new Scanner(System.in), currentUser);
                } catch (UnsupportedOperationException uoe) {
                    System.out.println("\nДля данного типа заметки не предусмотрено изменение слов в содержимом\n");
                    menu(new Scanner(System.in), currentUser);
                } catch (NoSuchElementException nee) {
                    System.out.println("Элемент не найден");
                    menu(new Scanner(System.in), currentUser);
                }
            }
            case 4 -> {
                try {
                    if (currentUser.getRole().equals(Role.USER)) throw new PermissionExeption();
                    System.out.println("\nВведите новое тело заметки: \n");
                    sc.nextLine();
                    requiredNote.setBodyOfNote(sc.nextLine().trim());
                    menu(new Scanner(System.in), currentUser);
                } catch (PermissionExeption pe) {
                    System.out.println(pe.getMessage());
                    menu(new Scanner(System.in), currentUser);
                }
            }
            case 5 -> {
                System.out.println(requiredNote.getAuthor());
                menu(new Scanner(System.in), currentUser);
            }
            case 6 -> {
                try {
                    if (currentUser.getRole().equals(Role.USER)) throw new PermissionExeption();
                    if (countOfNotes > numberOfNote + 1) {
                        for (int i = numberOfNote; i < countOfNotes - 1; i++) {
                            notes[i] = notes[i + 1].clone();
                        }
                        notes[countOfNotes - 1] = null;
                    } else notes[numberOfNote] = null;
                    countOfNotes--;
                    menu(new Scanner(System.in), currentUser);
                } catch (CloneNotSupportedException cnse) {
                    System.out.println(cnse.getMessage());
                } catch (PermissionExeption pe) {
                    System.out.println(pe.getMessage());
                    menu(new Scanner(System.in), currentUser);
                }
            }
            default -> {
                System.out.println("\nВыбранный пункт отсутствует, попробуйте снова\n");
                menu(new Scanner(System.in), currentUser);
            }
        }
    }
}