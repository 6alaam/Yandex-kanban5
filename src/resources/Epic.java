package resources;

import java.util.ArrayList;

public class Epic extends Task {

    //здесь нам нужен лист что бы привязать сабтаски к конкретному эпику
    private ArrayList<Subtask> subtaskList = new ArrayList<>();

    public Epic(int id, String name, String description, Status status) {

        super(id, name, description, status);

    }



    public Epic(String taskName, String description, int id) {
        super(taskName, description, id);
        this.status = Status.NEW;

    }


    public Epic(String taskName, String description) {
        super(taskName, description);
        this.status = Status.NEW;

    }


//    Метод addSubtask(Subtask subtask) в классе Epic:
//    Этот метод добавляет подзадачу в список подзадач конкретного эпика. Он управляет внутренним состоянием объекта Epic
//    и позволяет добавлять подзадачи к этому эпику.

    public void addSubtask(Subtask subtask) {
        subtaskList.add(subtask);
    }

    // методы эти в этом классе расположенны для логики и обращаются к листам конкретных эпиков
    //поидеи метод этот можно не дубировать и написать только в менеджере
    // но исользование его тогда будет не удобным.
    public void clearSubtasks(){
        subtaskList.clear();
    }

    //обычный геттер
    public ArrayList<Subtask> getSubtaskList(){
        return subtaskList;
    }

    // сеттер
    public void setSubtaskList(ArrayList<Subtask> SubtaskList){
        this.subtaskList = subtaskList;
    }


}
