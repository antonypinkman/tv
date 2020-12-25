package timetable;

import timetable.domain.Channel;
import timetable.domain.Genre;
import timetable.domain.TVProgram;
import timetable.mysql.MySqlDaoFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final MySqlDaoFactory factory = MySqlDaoFactory.instance;

    private static String readData(String name) {
        System.out.printf("%s: ", name);
        return new Scanner(System.in).nextLine();
    }
    private static Date getDate() {
        try {
            var string = readData("Введите дату");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d M yyyy");
            LocalDate date = LocalDate.parse(string, formatter);
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception ex) {
            System.out.println("Неверный формат. Правильный: 25 12 2020");
            return null;
        }
    }

    private static void selectChannels() {
        for (Channel channel : factory.getDao(factory.getContext(), Channel.class).selectAll()) {
            System.out.printf("id=%d|title=%s\n", channel.getId(), channel.getTitle());
        }
    }
    private static void selectGenres() {
        for (Genre genre : factory.getDao(factory.getContext(), Genre.class).selectAll()) {
            System.out.printf("id=%d|title=%s\n", genre.getId(), genre.getGenreName());
        }
    }
    private static void selectPrograms() {
        for (TVProgram program : factory.getDao(factory.getContext(), TVProgram.class).selectAll()) {
            System.out.printf("id=%d|channel_id=%d|title=%s|time=%s|genre_id=%d\n",
                    program.getId(),
                    program.getChannel().getId(),
                    program.getTitle(),
                    program.getBeginTime().toString(),
                    program.getGenre().getId());
        }
    }
    private static void insertChannel() {
        var channel = new Channel();
        channel.setTitle(readData("Введите название канала"));
        factory.getDao(factory.getContext(), Channel.class).insert(channel);
    }
    private static void insertGenre() {
        var genre = new Genre();
        genre.setGenreName(readData("Введите название жанра"));
        factory.getDao(factory.getContext(), Genre.class).insert(genre);
    }
    private static void insertProgram() {
        var program = new TVProgram();
        program.setTitle(readData("Введите название передачи"));
        var channelId = Integer.parseInt(readData("Введите id канала"));
        var channel = factory.getDao(factory.getContext(), Channel.class).getObjectById(channelId);
        if (channel == null) {
            System.out.println("Неизвестный канал");
            return;
        }
        program.setChannel(channel);
        var genreId = Integer.parseInt(readData("Введите id жанра"));
        var genre = factory.getDao(factory.getContext(), Genre.class).getObjectById(genreId);
        if (genre == null) {
            System.out.println("Неизвестный жанр");
            return;
        }
        program.setGenre(genre);
        var date = getDate();
        if (date == null) {
            return;
        }
        program.setBeginTime(date);
        factory.getDao(factory.getContext(), TVProgram.class).insert(program);
    }
    private static void updateChannel() {
        var channel = factory.getDao(factory.getContext(), Channel.class).getObjectById(Integer.parseInt(readData("Введите id изменяемого объекта")));
        if (channel == null) {
            System.out.println("Неизвестный канал");
            return;
        }
        channel.setTitle(readData("Введите название канала"));
        factory.getDao(factory.getContext(), Channel.class).update(channel);
    }
    private static void updateGenre() {
        var genre = factory.getDao(factory.getContext(), Genre.class).getObjectById(Integer.parseInt(readData("Введите id изменяемого объекта")));
        if (genre == null) {
            System.out.println("Неизвестный жанр");
            return;
        }
        genre.setGenreName(readData("Введите название жанра"));
        factory.getDao(factory.getContext(), Genre.class).update(genre);
    }
    private static void updateProgram() {
        var program = factory.getDao(factory.getContext(), TVProgram.class).getObjectById(Integer.parseInt(readData("Введите id изменяемого объекта")));
        if (program == null) {
            System.out.println("Неизвестная программа");
            return;
        }
        program.setTitle(readData("Введите название передачи"));
        var channelId = Integer.parseInt(readData("Введите id канала"));
        var channel = factory.getDao(factory.getContext(), Channel.class).getObjectById(channelId);
        if (channel == null) {
            System.out.println("Неизвестный канал");
            return;
        }
        program.setChannel(channel);
        var genreId = Integer.parseInt(readData("Введите id жанра"));
        var genre = factory.getDao(factory.getContext(), Genre.class).getObjectById(genreId);
        if (genre == null) {
            System.out.println("Неизвестный жанр");
            return;
        }
        program.setGenre(genre);
        var date = getDate();
        if (date == null) {
            return;
        }
        program.setBeginTime(date);
        factory.getDao(factory.getContext(), TVProgram.class).update(program);
    }
    private static void deleteChannels() {
        var channel = factory.getDao(factory.getContext(), Channel.class).getObjectById(Integer.parseInt(readData("Введите id удаляемого объекта")));
        if (channel == null) {
            System.out.println("Неизвестный канал");
            return;
        }
        factory.getDao(factory.getContext(), Channel.class).delete(channel);
    }
    private static void deleteGenres() {
        var genre = factory.getDao(factory.getContext(), Genre.class).getObjectById(Integer.parseInt(readData("Введите id удаляемого объекта")));
        if (genre == null) {
            System.out.println("Неизвестный жанр");
            return;
        }
        factory.getDao(factory.getContext(), Genre.class).delete(genre);
    }
    private static void deletePrograms() {
        var program = factory.getDao(factory.getContext(), TVProgram.class).getObjectById(Integer.parseInt(readData("Введите id удаляемого объекта")));
        if (program == null) {
            System.out.println("Неизвестная программа");
            return;
        }
        factory.getDao(factory.getContext(), TVProgram.class).delete(program);
    }

    public static HashMap<Integer, String> helpers = new HashMap<>() {{
        put(1, "1 = select channels");
        put(2, "2 = select genres");
        put(3, "3 = select tv programs");
        put(4, "4 = insert channel");
        put(5, "5 = insert genre");
        put(6, "6 = insert tv program");
        put(7, "7 = update channel");
        put(8, "8 = update genre");
        put(9, "9 = update tv program");
        put(10, "10 = delete channel");
        put(11, "11 = delete genre");
        put(12, "12 = delete tv program");
    }};
    public static HashMap<Integer, Runnable> actions = new HashMap<>() {{
        put(1, Main::selectChannels);
        put(2, Main::selectGenres);
        put(3, Main::selectPrograms);
        put(4, Main::insertChannel);
        put(5, Main::insertGenre);
        put(6, Main::insertProgram);
        put(7, Main::updateChannel);
        put(8, Main::updateGenre);
        put(9, Main::updateProgram);
        put(10, Main::deleteChannels);
        put(11, Main::deleteGenres);
        put(12, Main::deletePrograms);
    }};

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        helpers.values().forEach(System.out::println);
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                var a = scanner.nextInt();
                actions.entrySet().stream().filter(x -> x.getKey()==a).findFirst().ifPresentOrElse(
                        x -> x.getValue().run(),
                        () -> System.out.println("Неизвестное действие")
                );
            } catch (Exception ex) {
                System.out.println("Неизвестное действие");
            }
            helpers.values().forEach(System.out::println);
        }
    }
}
