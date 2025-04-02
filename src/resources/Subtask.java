package resources;

public class Subtask extends Task{
    private final int epicID;

    public Subtask(String taskName, String description, int id, int epicID) {
        super(taskName, description, id);
        this.epicID = epicID;
        this.status = Status.NEW;

    }

    public Subtask(int id, String name, String description, Status status, int epicID) {
        super(id, name, description, status);
        this.epicID = epicID;
    }

    public Subtask(String taskName, String description,int epicID) {
        super(taskName, description);
        this.epicID = epicID;
        this.status = Status.NEW;
    }

    public int getEpicID() {
        return epicID;
    }
}
