package test.java.resourse;

import org.junit.jupiter.api.Test;
import resources.Epic;
import resources.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    public void testEpicEqualityById() {
        Epic epic1 = new Epic("Epic 1", "Description 1", 1);
        Epic epic2 = new Epic("Epic 2", "Description 2", 1);
        Epic epic3 = new Epic("Epic 3", "Description 3", 2);

        assertEquals(epic1, epic2, "Эпики должны быть равны, так как их id равны.");
        assertNotEquals(epic1, epic3, "Эпики не должны быть равны, так как их id разные.");
    }

    @Test
    public void testAddSubtaskToEpic() {
        Epic epic = new Epic("Epic", "Description", 1);
        Subtask subtask = new Subtask("Subtask", "Description", 2, 1);

        epic.addSubtask(subtask);
        assertTrue(epic.getSubtaskList().contains(subtask), "Подзадача должна быть добавлена в список подзадач эпика.");
    }



}