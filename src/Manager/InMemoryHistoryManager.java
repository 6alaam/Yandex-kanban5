package Manager;

import resources.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    // заводим константу размера истории
    private static final int Max_History_Storage = 10;
    // делаем лист таксков. так как сабтаск и эпик наследники они также будут помещаться в этот лист
    public final List<Task> historyList = new ArrayList<>();

    //добавляем задачу в историю проверяя заполнилось ли хранилище
    // делаем через константу, а не число. так как магические числа плохо)

    @Override
    public void add(Task task) {
        if (task == null){
           return;}
        if (historyList.size() == Max_History_Storage){
            historyList.removeFirst();
        }
        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "historyList=" + historyList +
                '}';
    }
}
