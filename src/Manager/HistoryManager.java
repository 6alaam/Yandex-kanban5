package Manager;

import resources.Task;

import java.util.List;

public interface HistoryManager {

    // это интерфейс заводим мы его заранее так как после понадобится еще одна реализация истории
    // соотвтсвенно заводим методы без реализации, пока нам нужно добавить таск и вернуть историю

    void add(Task task);

    List<Task>getHistory();

}
