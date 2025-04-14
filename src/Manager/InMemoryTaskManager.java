package Manager;

import resources.Epic;
import resources.Status;
import resources.Subtask;
import resources.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    // изначально присваевам id единицу
    private static int ID = 1;
    // создаем HashMap для тасков ,
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    // а здесь мы создаем мапу для всех сабтасков , я так понимаю
    private final Map<Integer, Subtask> subtasks = new HashMap<>();


    // данный метод присваивает новый айди с шагом +1.но в нем существует момент если задача в середине списка была удалена. то этот айди получается пропущен
    // в мейне создал пример такого момента
    public int getNextID() {
        return ID++;
    }

    // здесь мы создаем НОВЫЙ список со всем значениями хешмапы Тасков,
    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    // тоже для епиков
    @Override
    public List<Task> getAllEpic() {
        return new ArrayList<>(epics.values());
    }


    // тоже для саб тасков, тут как я понимаю именно весь список который создатся в этом классе
    @Override
    public List<Subtask> getAllSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    //а уже сабы выгружаем конкретного эпика
    @Override
    public List<Subtask> getEpicSubtask(Epic epic) {
        return epic.getSubtaskList();
    }


    //ну тут ясно удалили все из хешмапы
    @Override
    public void deleteAllTask() {
        for (Task task : tasks.values()){
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }


    //удаление всех эпиков добавленно очистка сабтасков для того
    // что бы не оставалась хешмапа сабтасков со ссылками на удаленный обьект
    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getId());
        }
        epics.clear();
        subtasks.clear();
    }

    //очень интересный метод для удаления сабтасков
    @Override
    public void deleteAllSubtask() {
        for (Subtask subtask : subtasks.values()){
            historyManager.remove(subtask.getId());
        }
        subtasks.clear();
        // по идеии этого достаточно что бы удалить все сабтаски
        // так как о чистит именно хэшмапу где хранятся ВСЕ саб таски
        // но в листах привязанных к конкретным эпикам останутся ссылки на удаленные обьекты
        // поэтому запускаем циклом чистку именно листов сабтасков
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();           // сам этот метод лежит в классе епик так как чистит именно лист сабасков
            epic.setStatus(Status.NEW);     // по заданию если эпик пустой у него должен изменится статус на нью
        }

    }


    // тут ясно по айди найти задачу
    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    // для эпиков
    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    //для сабтасков, по условию айди у нас присваивается любой задачи
    // хотя логику можно и докрутить , как мне кажется для саб тасков
    // не совсем верно присваивать айдишники по порядку вместе с эпиками и обычными тасками
    // впринципе логику можно доработать например сделать для сабтасков отдельный счетчик вида
    //айди эпика точка и уже айди субтаска 1.1 , 1.2 и тд и тп

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }


    // тут много вопросов , 1. по факту получается если мы создаем задачу по полному конструктору где руками пишем айди
    // то когда этот метод добавляеет ее в мапу с новым адйди следующим по порядку.
    // второе тут и проявляется методика добавления задачи, если д этого какойто айди был удален
    // он не заполнится, а будет присвоен следующий порядковый номер
    //да по условию метод должен возвращать этот добавленный обьект, но на данном этапе это бессмысленно
    @Override
    public Task addTask(Task task) {
        task.setId(getNextID());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);
        return epic;
    }

//    Этот метод отвечает за добавление подзадачи в общий менеджер задач.
//    Он устанавливает идентификатор подзадачи, добавляет ее в коллекцию подзадач и также обновляет статус соответствующего эпика,
//    к которому принадлежит подзадача.

    @Override
    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        subtasks.put(subtask.getId(), subtask); // тут тоже ошибка: было subtask.getEpicID()
        updateEpicStatus(epic);                 // так как мы добавили сабтаск с новым статусом мы говорим проверь статус эпика
        return subtask;

    }


    // здесь тоже возникает вопрос , по заданию параметр это новая версия обьекта,
    // получется странная логика, по факту этод метод не апдейта задачи
    // а помещение уже измененной задчи в хешмапу тасков.
    @Override
    public Task updateTask(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    //для эпиков
    @Override
    public Epic updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return epic;
    }

    //Опять же изза того что мы добавляем измененную задачу в хешмапу и еще в лист сабтасков в конкретный эпик

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        int subtaskid = subtask.getId();                        //берем из пришедшего сабтаска айди
        int epicid = subtask.getEpicID();                       // так как в саб таске у нас есть и айди еика получаем и его
        Subtask oldSubtask = subtasks.get(subtaskid);           // по айди ищем старый сабтаск
        subtasks.replace(subtaskid, subtask);                    //  метод реплейс принемает ключ и значение которое он заменит по этому ключу,
        // важно здесь мы меняем в ХЭШМАПЕ значение

        Epic epic = epics.get(epicid);                          // получаем эпик к которому привязан сабтаск
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();  // создаем сиписок в который копируем списо сабтасков нужного эпика
        subtaskList.remove(oldSubtask);                            // в нем удаляем старый сабтаск(тот который обновляем)
        subtaskList.add(subtask);                                  // добавляем новый, и получается как бы замена - читай обновление сабтаска внутри  ЛИСТА эпика
        epic.setSubtaskList(subtaskList);                           // теперь переписываем измененный Лист вместо того который хранится в эпике
        updateEpicStatus(epic);                                     // так как статус сабтаска мог изменится соответсвенно проверяем корректность статуса эпика
        return subtask;                                             // по условию метод должен вернуть обновленный сабтаск
    }


    // тут понятно удалил по айди
    @Override
    public Task deleteTaskByID(int id) {
        historyManager.remove(id);
        return tasks.remove(id);
    }

    @Override
    public Epic deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {                                     //тут правка
            for (Subtask subtaskId : epic.getSubtaskList()) {
                subtasks.remove(subtaskId.getId());
            }
        }
        historyManager.remove(id);
        for (Subtask subtaskId : epic.getSubtaskList()){
            subtasks.remove(subtaskId.getId());
            historyManager.remove(subtaskId.getId());
        }
        return epics.remove(id);
    }

// во всех методах связанных с сабтаском мы сначаала взаимодействуем с ХЕШМАПОЙ сабтасков (здесь удаляем)
    // а после работаем с листом привязанным к конкретному эпику

    @Override
    public Subtask deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);        // получили сабтаск по айди (а если точнее присвоили обьекту subtask ссылку на сабтаск из хешмапы)
        historyManager.remove(id);
        if (subtask == null) {                     //тут правка
            return null;
        }
        int epicId = subtask.getEpicID();           // получили айди связанного епика
        Subtask deletedSubtask = subtasks.remove(id);                        // удалили из ХЕШМАПЫ сабтаск
        Epic epic = epics.get(epicId);              // получили епик по айди(смотри выше как проходит процесс)
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();  // получили листсабтасков конкретного эпика
        subtaskList.remove(subtask);                            // удалили из листа
        epic.setSubtaskList(subtaskList);                       // полученный лист поместили в эпик заменив
        updateEpicStatus(epic);                                   // так как статус эпика мог поменятся проверили и поменяли статус
        return deletedSubtask;
    }

    // апдейт статуса еика, по условию статус епика равен нью или доне
    // если все сабтаски внутри имеют этот статус
    @Override
    public void updateEpicStatus(Epic epic) {
        int allIsDone = 0;                      // мы заводим счетчики этих статусов и присваиваем им значение 0
        int allIsNew = 0;
        ArrayList<Subtask> listSubtask = epic.getSubtaskList(); // создаем лист сабтасков данного епика, епик унас приходит как аргумент
        for (Subtask subtask : listSubtask) {                    // далее пройдя по всем элементам списка мы считаем количество статусов нью / доне
            if (subtask.getStatus() == Status.DONE) {
                allIsDone++;
            }
            if (subtask.getStatus() == Status.NEW) {
                allIsNew++;
            }
        }                                                      // после подсчета количества статусов саб тасков мы делаем ветвление
        if (allIsDone == listSubtask.size()) {                   // если счетчик равен размеру листа значит все задачи имеют этот статус
            epic.setStatus(Status.DONE);                        // а значит по условию эпик имеет тот же статус нью/доне
        }
        if (allIsNew == listSubtask.size()) {
            epic.setStatus(Status.NEW);
        } else {                                                  // если же есть разные статусы подзадач то епик получает статус в процессе
            epic.setStatus(Status.IN_PROGRESS);
        }


    }

    @Override                                // еще метод
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }



    // этот метод сделал исключительно для тестов в мейне . делал через неqросетку
    public void printTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Нет задач для отображения.");
            return;
        }
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            Integer key = entry.getKey();
            Task task = entry.getValue();
            System.out.println("ID: " + key + ", " + task);
        }
        System.out.println(historyManager.getHistory());
    }


    // метод для печати эпиков с принадлежащим им сабтаскам
    public void printEpicWithSubtasks(int epicId) {
        Epic epic = getEpicById(epicId);
        if (epic == null) {
            System.out.println("Эпик с ID " + epicId + " не найден.");
            return;
        }
        System.out.println("Эпик: " + epic);
        System.out.println(historyManager.getHistory());
        List<Subtask> subtaskList = epic.getSubtaskList();
        if (subtaskList.isEmpty()) {
            System.out.println("У этого эпика нет сабтасков.");
        } else {
            System.out.println("Сабтаски:");
            for (Subtask subtask : subtaskList) {
                System.out.println(" - " + subtask);
            }
            System.out.println(historyManager.getHistory());
        }
    }


}





