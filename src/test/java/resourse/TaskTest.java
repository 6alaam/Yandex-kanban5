package test.java.resourse;

import org.junit.jupiter.api.Test;
import resources.Status;
import resources.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        Task task1 = new Task("Task 1", "Description 1", 1);
        Task task2 = new Task("Task 2", "Description 2", 1);
        Task task3 = new Task("Task 3", "Description 3", 2);

        assertEquals(task1, task2, "Задачи должны быть равны, так как их id равны.");
        assertNotEquals(task1, task3, "Задачи не должны быть равны, так как их id разные.");
    }
    }



