package Manager;

import resources.*;

import java.util.List;

public interface HistoryManager {

    public void add(Task task);

    public List<Task> getHistory();
}
