package Manager;

import resources.Task;
import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    List<Task> historyList = new LinkedList<>();

    @Override
    public void add(Task task){
        if (historyList.size() >= 10){
            historyList.removeFirst();
            historyList.add(task);
        }else {
            historyList.add(task);
        }
    }

    @Override
    public List<Task> getHistory(){
        return historyList;
        // а должно быть так:
        // return List.copyOf(historyList);
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "historyList=" + historyList +
                '}';
    }
}
