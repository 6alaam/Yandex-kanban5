package test.java.resourse;

import org.junit.jupiter.api.Test;
import resources.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test

    public void SubtasksWithEqualIdShouldBeEqual() {

        Subtask subtask1 = new Subtask("Купить хлеб", "В Дикси у дома",10,5);
        Subtask subtask2 = new Subtask("Купить молоко", "В Пятерочке",10,5);
        assertEquals(subtask1, subtask2, "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}