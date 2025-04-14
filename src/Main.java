import Manager.Managers;
import Manager.TaskManager;
import resources.Epic;
import resources.Subtask;

public class Main {
    public static void main(String[] args) {


//        TaskManger taskManger = new InMemoryTaskManager();
//        // вот тут интересно, создали обьект типа таск , и дали обьекту имя таск1, и взаимодействие привычное с обьектом,
//        // тоесть по имени обьекта мы и распечатать можем и добавить и тд и тп.
//        Task task1 = new Task("test","tester");
//        System.out.println(task1);
//        taskManger.addTask(task1);
//        System.out.println(task1);
//
//        // а вот здесь мы создаем задачу как положенно через метод аддТаск.
//        // и многое становится непревычно как например поменять описание(description)
//        // до этого все было понятно имя обьекта точка и вызываем что надо, а как быть если обьект то без имени
//        taskManger.addTask(new Task("test2","tester2"));
//        taskManger.addTask(new Task("test3","tester3"));
//        System.out.println();
//        taskManger.printTasks();
//        task1.setDescription("обновлено"); // так мы делаем когда имя обьекта понятно
//        taskManger.updateTask(task1);   // ну и обновили в хешмапе
//        // а вот тут мы берем метод получения задачи по айди, и через точку вызываем следующий метод
//        // если по подробнее то тут написано у обьекта который ты получишь по айди (2) поменяй описание на охереть
//        taskManger.getTaskById(2).setDescription("ОХЕРЕТЬ");
//
//
//        // тут порядок немного другой , но смысл тот же
//        //  в хешмапе тасков измени (впиши изменнную) под вторым айдишником
//        taskManger.updateTask(taskManger.getTaskById(2));
//        taskManger.printTasks();
//
//        System.out.println();
//        taskManger.deleteTaskByID(2);
//        taskManger.printTasks();
//        System.out.println();
//        taskManger.addTask(new Task("test4","tester4"));
//        taskManger.addTask(new Task("test5","tester5"));
//        Task task4test = new Task("test4test","а что будет если принудительно создать задачу с существующим айди",4);
//
//        taskManger.addTask(task4test);
//        taskManger.addTask(new Task("test6","tester6"));
//        taskManger.printTasks();
//        System.out.println();
//        System.out.println(taskManger.getAllTask());
//        System.out.println();
//
//
//        Epic epic1 = new Epic("EPICTest1","TESTEPIC!");
//        taskManger.addEpic(epic1);
//        System.out.println(taskManger.getAllEpic());
//        System.out.println();
//        taskManger.addEpic(new Epic("EPICTest2","TESTEPIC"));
//        System.out.println(taskManger.getEpicById(2));
//        System.out.println(taskManger.getEpicById(9));
//        Subtask subtask1 = new Subtask("subtask11","TestSubtak11",8);
//        Subtask subtask2 = new Subtask("Subtask12","TestSubtask12",8);
//        taskManger.addSubtask(subtask1);
//        taskManger.addSubtask(subtask2);
//        System.out.println(subtask1);
//        System.out.println();
//        System.out.println(epic1);
//        taskManger.printEpicWithSubtasks(8);
//        System.out.println();
//        System.out.println(taskManger.addSubtask(new Subtask("субтаск добавленный ","добавли через метод", epic1.getId())));
//        taskManger.printEpicWithSubtasks(8);
//        System.out.println();
//
//        //taskManger.updateSubtask(taskManger.getSubtaskById(11).setDescription("офигенно"));
//        // этод метод не работает потому что сет дискрипшен ничего не возвращает,
//        taskManger.printEpicWithSubtasks(8);


        TaskManager taskManager = Managers.getDefault();

        // обьект инмемори здесь создавать не нужно, так он создается в инмемори таск менеджер,
        //    InMemoryHistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

//        for (int i = 1; i < 11; i++) {
//            taskManager.addTask(new Task(("task " + i), ("test " + i)));
//        }
//
//        taskManager.printTasks();
//        for (int i = 1; i < 11; i++) {
//            taskManager.getTaskById(i);
//        }
//
//    //    System.out.println(taskManager.getHistory());
//        System.out.println();
//
//        taskManager.printHistory();

        for (int i = 1; i <= 1; i++) {
            // Создаем новый эпик
            taskManager.addEpic(new Epic("Epic " + i, "Description for Epic " + i));


            // Создаем 3 подзадачи для каждого эпика
            for (int j = 1; j <= 5; j++) {
                taskManager.addSubtask(new Subtask("Subtask " + j + " of Epic " + i, "Description for Subtask " + j, i));
                taskManager.printEpicWithSubtasks(i);

            }
        }

        System.out.println();

        for (int i = 1; i <= 1; i++) {
            taskManager.getEpicById(i);
            for (int j = 2; j <= 5; j++) {
                taskManager.getSubtaskById(i);
            }
        }
        System.out.println("__________________________");
        taskManager.printHistory();

    }

}