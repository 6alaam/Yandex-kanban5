package Manager;

public class Managers {

    // метод гет дефаулт нужен на будущее, если будут созданы классы имплементирующие таск менеджер
    // то в реализацию мы поставим вилку типа если условие такое создать одного класса , а если друое то другой класс создаем
    //важный момент это будет работать именнно для классов имплементирующих таск менеджер
    public static TaskManager getDefault() {
         return new InMemoryTaskManager();
    }

    public static InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
