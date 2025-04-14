package test.java.Manager;

import Manager.Managers;
import Manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resources.Epic;
import resources.Status;
import resources.Subtask;
import resources.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask() {
        //проверяем, что InMemoryTaskManager добавляет задачи и может найти их по id;
        final Task task = taskManager.addTask(new Task("Test addNewTask", "Test addNewTask description"));
        final Task savedTask = taskManager.getTaskById(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        final List<Task> tasks = taskManager.getAllTask();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpicAndSubtasks() {
        //проверяем, что InMemoryTaskManager добавляет эпики и подзадачи и может найти их по id;
        final Epic flatRenovation = taskManager.addEpic(new Epic("Сделать ремонт", "Нужно успеть за отпуск",taskManager.getNextID()));
        final Subtask flatRenovationSubtask1 = taskManager.addSubtask(new Subtask("Поклеить обои", "Обязательно светлые!", taskManager.getNextID(),flatRenovation.getId()));
        final Subtask flatRenovationSubtask2 = taskManager.addSubtask(new Subtask("Установить новую технику", "Старую продать на Авито", taskManager.getNextID(),flatRenovation.getId()));
        final Subtask flatRenovationSubtask3 = taskManager.addSubtask(new Subtask("Заказать книжный шкаф", "Из темного дерева", taskManager.getNextID(),flatRenovation.getId()));
        final Epic savedEpic = taskManager.getEpicById(flatRenovation.getId());
        final Subtask savedSubtask1 = taskManager.getSubtaskById(flatRenovationSubtask1.getId());
        final Subtask savedSubtask2 = taskManager.getSubtaskById(flatRenovationSubtask2.getId());
        final Subtask savedSubtask3 = taskManager.getSubtaskById(flatRenovationSubtask3.getId());
        assertNotNull(savedEpic, "Эпик не найден.");
        assertNotNull(savedSubtask2, "Подзадача не найдена.");
        assertEquals(flatRenovation, savedEpic, "Эпики не совпадают.");
        assertEquals(flatRenovationSubtask1, savedSubtask1, "Подзадачи не совпадают.");
        assertEquals(flatRenovationSubtask3, savedSubtask3, "Подзадачи не совпадают.");
        final List<Task> epics = taskManager.getAllEpic();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(flatRenovation, epics.getFirst(), "Эпики не совпадают.");
        final List<Subtask> subtasks = taskManager.getAllSubtask();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(3, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(savedSubtask1, subtasks.getFirst(), "Подзадачи не совпадают.");
    }

    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        final Task expected = new Task("имя", "описание");
        taskManager.addTask(expected);
        final Task updatedTask = new Task( "новое имя", "новое описание", expected.getId());
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected, actual, "Вернулась задачи с другим id");
    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic("имя", "описание");
        taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic( "новое имя", "новое описание", expected.getId());
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected, actual, "Вернулся эпик с другим id");
    }

    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        final Epic epic = new Epic("имя", "описание");
        taskManager.addEpic(epic);
        final Subtask expected = new Subtask("имя", "описание", epic.getId());
        taskManager.addSubtask(expected);
        final Subtask updatedSubtask = new Subtask( "новое имя", "новое описание", expected.getId(), epic.getId());
        final Subtask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулась подзадача с другим id");
    }

    @Test
    public void deleteTasksShouldReturnEmptyList() {
        taskManager.addTask(new Task("Купить книги", "Список в заметках"));
        taskManager.addTask(new Task("Помыть полы", "С новым средством"));
        taskManager.deleteAllTask();
        List<Task> tasks = taskManager.getAllTask();
        assertTrue(tasks.isEmpty(), "После удаления задач список должен быть пуст.");
    }

    @Test
    public void deleteEpicsShouldReturnEmptyList() {
        taskManager.addEpic(new Epic("Сделать ремонт", "Нужно успеть за отпуск"));
        taskManager.deleteAllEpics();
        List<Task> epics = taskManager.getAllEpic();
        assertTrue(epics.isEmpty(), "После удаления эпиков список должен быть пуст.");
    }

    @Test
    public void deleteSubtasksShouldReturnEmptyList() {
        Epic flatRenovation = new Epic("Сделать ремонт", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        taskManager.addSubtask(new Subtask("Поклеить обои", "Обязательно светлые!", flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Установить новую технику", "Старую продать на Авито", flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Заказать книжный шкаф", "Из темного дерева", flatRenovation.getId()));
        taskManager.deleteAllSubtask();
        List<Subtask> subtasks = taskManager.getAllSubtask();
        assertTrue(subtasks.isEmpty(), "После удаления подзадач список должен быть пуст.");
    }

    @Test
    public void deleteTaskByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addTask(new Task("Купить книги", "Список в заметках",1));
        taskManager.addTask(new Task( "Помыть полы", "С новым средством", 2));
        assertNull(taskManager.deleteTaskByID(3));
    }

    @Test
    public void deleteEpicByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addEpic(new Epic("Сделать ремонт", "Нужно успеть за отпуск", 1));
        taskManager.deleteEpicById(1);
        assertNull(taskManager.deleteTaskByID(3));
    }

    @Test
    public void deleteSubtaskByIdShouldReturnNullIfKeyIsMissing() {
        Epic flatRenovation = new Epic("Сделать ремонт", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        taskManager.addSubtask(new Subtask("Поклеить обои", "Обязательно светлые!", flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Установить новую технику", "Старую продать на Авито", flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Заказать книжный шкаф", "Из темного дерева", flatRenovation.getId()));
        assertNull(taskManager.deleteTaskByID(4), "Метод должен вернуть null, если подзадача с указанным id не найдена.");




}
}