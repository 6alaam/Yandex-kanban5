package Manager;

import resources.Epic;
import resources.Subtask;
import resources.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTask();

    List<Task> getAllEpic();

    List<Subtask> getAllSubtask();

    List<Subtask> getEpicSubtask(Epic epic);

    void deleteAllTask();

    void deleteAllEpics();

    void deleteAllSubtask();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    Task addTask(Task task);

    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    Task deleteTaskByID(int id);

    Epic deleteEpicById(int id);

    Subtask deleteSubtaskById(int id);

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();

}
