package Manager;

import resources.*;

import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {

    private Node head;
    private Node tail;
    private final Map<Integer, Node> historyList = new HashMap();

    @Override
    public void add(Task task) {
        final int id = task.getId(); // тут понятно
        removeNode(historyList.get(id)); // если такая задача уже есть, удаляем ее
        linkLast(task);                      // делаем новый запрос хвостом
        historyList.put(task.getId(), tail); // суем в мапу под своим id
    }

    @Override
    public List<Task> getHistory() {
        return getTask();
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = historyList.remove(id); // тут понятно
        if (nodeToRemove != null) {   // если ссылка не пуста и такой айдишник есть
            removeNode(nodeToRemove); // говорим что конкретно мы удалили и обновляем положение головы/хвоста
        }
    }

    public void linkLast(Task task) {
        Node newNode = new Node(task);
        if (tail == null){  //если список пуст, узел становится головой и хвостом
            head = newNode;
            tail = newNode;
        }else {
            tail.setNext(newNode); // если не пустой, меняем узел на следующий для текущего хвоста
            newNode.setPrev(tail); // тут мы указываем на то, что объект, который сейчас лежит в хвосте, становится prev
            tail = newNode; // и обновляем объект, на который ссылается хвост
        }
    }

    private List<Task> getTask() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;              //суем в переменную голову списка
        while (current != null){
            tasks.add(current.getTask()); // добавляем в лист
            current = current.getNext();  // меняем ссылку переменной на следующий узел
        }
        return List.copyOf(tasks);
    }

    public void removeNode(Node node) {
        if (node == null){
            return;
        }
        if (node.getPrev() != null){  //если у удаляемого узла предыдущий узел есть, мы устанавливаем его ссылку
            node.getPrev().setNext(node.getNext()); //на следующий узел, чтобы занять пустое место после удаления
        }else {
            head = node.getNext(); // если предыдущего нет - значит мы удаляем голову,соответственно мы обновляем голову, чтобы она указывала на следующий
        }
        if (node.getNext() != null){
            node.getNext().setPrev(node.getPrev()); // тут уже понятно
        }else {
            tail = node.getPrev();
        }
    }
}
