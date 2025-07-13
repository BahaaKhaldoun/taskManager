import javax.swing.text.DateFormatter;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Main {
//Add delayed after the title in the same line
    static String addres = "C:\\Users\\User\\OneDrive\\Desktop\\taskManager\\output";
    static ArrayList<String[]> list = new ArrayList<>();
    static ArrayList<String> list2 = new ArrayList<>();
    static LinkedList<String> l = new LinkedList<>();
    static LocalDate date=LocalDate.now();

    public static void main(String[] args) throws IOException, ParseException {
        reader();
        System.out.println("welcome to task manager!");
        System.out.println("1.add task");
        System.out.println("2.view all tasks");
        System.out.println("3.update a task");
        System.out.println("4.delete task");
        System.out.println("5.Exit");
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        while (choose != 5) {
            switch (choose) {
                case 1: {
                    addTask();
                }
                break;
                case 2:
                    viewAllTasks();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4: {
                    deleteTask();
                }

            }
            System.out.println("welcome to task manager!");
            System.out.println("1.add task");
            System.out.println("2.view all tasks");
            System.out.println("3.update a task");
            System.out.println("4.delete task");
            System.out.println("5.Exit");
            choose = scanner.nextInt();

        }
    }

    public static void reader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(addres));
        while (reader.ready()) {
            l.add(reader.readLine());
        }
        reader.close();
    }

    public static void addTask() throws IOException {
        int id;
        id= (l.size()/6)+1;
        System.out.println("enter task title,description,data,status and recurrence");
        Scanner scanner = new Scanner(System.in);
        File file = new File(addres);
        try {
            if (!file.exists())
                file.createNewFile();

            PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            l.add("id: " + id);
            printWriter.append(l.getLast() + "\n");
            l.add("title: " + scanner.nextLine());
            printWriter.append(l.getLast() + "\n");
            l.add("description: " + scanner.nextLine());
            printWriter.append(l.getLast() + "\n");
            l.add("date: " + scanner.nextLine());
            String r1 = l.getLast().substring(6);
            LocalDate localDate2 = LocalDate.parse(r1, dateTimeFormatter);
            printWriter.append(l.getLast() + "\n");
            l.add("status: " + scanner.nextLine());
            if (date.isAfter(localDate2)) {
                printWriter.append(l.getLast() + " DELAYED! " + "\n");
            }
            else{
                printWriter.append(l.getLast() + "\n");
            }
                l.add("recurrence: " + scanner.nextLine());
                printWriter.append(l.getLast() + "\n");

            printWriter.close();

        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }

        System.out.println("task added ");
    }
    public static void viewAllTasks() throws IOException {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(addres));

            while (bufferedReader.ready()) {
                System.out.println((bufferedReader.readLine()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTask() throws IOException, ParseException {
        System.out.println("enter the id of the task that you want to change");
        Scanner scanner = new Scanner(System.in);
        int numberOftask = scanner.nextInt();


        System.out.println("what do you want to update?");
        System.out.println("1.title");
        System.out.println("2.description");
        System.out.println("3.date");
        System.out.println("4.status");
        System.out.println("5.recurrence");
        int theChanger = scanner.nextInt();
        int toChange = 0;
        for (int i = 1; i < numberOftask; i++) {
            toChange +=6;
        }

        switch (theChanger) {
            case 1: {
                System.out.println("enter the new title");
                String s = scanner.next();
                l.remove(toChange + 1);
                l.add(toChange + 1, "title: " + s);
                break;
            }
            case 2: {
                System.out.println("enter the new description");
                String s = scanner.next();
                l.remove(toChange + 2);
                l.add(toChange + 2, "description: " + s);
                break;
            }
            case 3: {
                System.out.println("enter the new date");
                String s = scanner.next();
                l.remove(toChange + 3);
                l.add(toChange + 3, "date: " + s);
                break;
            }
            case 4: {
                System.out.println("enter the new status");
                String s = scanner.next();
                l.remove(toChange + 4);
                l.add(toChange + 4, "status: " + s);
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String r = l.get(toChange + 3).substring(6, 16);
                LocalDate localDate = LocalDate.parse(r, dateTimeFormatter);
                if (s.equals("done")) {

                    switch (l.get(toChange + 5).substring(11)) {
                        case " daily": {
                            localDate = localDate.plusDays(1);
                            break;
                        }
                        case " weekly": {
                            localDate = localDate.plusWeeks(1);
                            break;
                        }
                        case " monthly":
                            localDate = localDate.plusMonths(1);
                            break;
                    }
                }
                int id = (l.size() / 6) + 1;
                File file = new File(addres);
                if (s.equals("done")) {
                    try {
                        if (!file.exists())
                            file.createNewFile();
                        PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
                        l.add("id: " + id);
                        printWriter.append(l.getLast() + "\n");
                        l.add(l.get(toChange + 1));
                        printWriter.append("title: " + l.getLast() + "\n");
                        l.add(l.get(toChange + 2));
                        printWriter.append("description: " + l.getLast() + "\n");
                        l.add("date: " + localDate.format(dateTimeFormatter));
                        printWriter.append("date: " + localDate.format(dateTimeFormatter) + "\n");

                        if (date.isAfter(localDate)){
                            l.add("status: "+"pending"+"  (DELAYED!)");
                        printWriter.append("status: " + "pending"+"  (DELAYED!)" + "\n");
                        }

                        printWriter.append("status: " + "pending" + "\n");
                        l.add(l.get(toChange + 5));
                        printWriter.append("recurrence: " + l.get(toChange + 5) + "\n");
                        printWriter.close();
                    } catch (IOException e) {
                        System.out.println("COULD NOT LOG!!");
                    }

                    System.out.println("task added ");
                }
                break;
            }
            case 5: {
                System.out.println("enter the new recurrence");
                String s = scanner.next();
                l.remove(toChange + 5);
                l.add(toChange + 5, "recurrence: " + s);
                break;
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(addres));
            for (String c : l)
                bufferedWriter.write(c + "\n");
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("Not found");
        }
        System.out.println("task updated ");
    }

    public static void deleteTask() throws IOException {
        System.out.println("enter the id of the task that you want to be deleted");
        Scanner scanner = new Scanner(System.in);
        String del = "id: " + scanner.next();
        int a = l.indexOf(del);
        System.out.println(a);
        for (int i = 0; i < 6; i++) {
            l.remove(a);
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(addres));
            for (String c : l)
                bufferedWriter.write(c + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Not found");
        }
        System.out.println("task deleted ");
    }

}