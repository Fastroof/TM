package ua.edu.sumdu.j2se.lytvynenko.tasks;

public class LinkedTaskList {

    private Node head;
    private Node tail;
    private int size = 0;

    /**
     * Add the specified task to the list.
     * @param task specified task
     */
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.task = task;
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    /**
     * Remove a task from the list and returns the truth if such a task was in the list.
     * If there are several such tasks in the list, the method deletes the first one.
     */
    public boolean remove(Task task) {
        boolean result = false;

        if ((size == 1) && (head.task.equals(task))) {
            head = null;
            tail = null;
            result = true;
        } else if (size > 1) {
            if (head.task.equals(task)) {
                head = head.next;
                head.prev = null;
                result = true;
            } else if (tail.task.equals(task)) {
                tail = tail.prev;
                tail.next = null;
                result = true;
            } else {
                Node temp = head;
                for (int i = 0; i < size - 2; i++) {
                    if (temp.next.task.equals(task)) {
                        temp.next = temp.next.next;
                        temp.next.prev = temp.next.prev.prev;
                        result = true;
                        break;
                    }
                    temp = temp.next;
                }
            }
        }

        if (result) {
            size--;
        }
        return result;
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node temp = head;
        if (index + 1 <= size/2) {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp.task;
    }

    /**
     * Return a list of tasks that are scheduled to run
     * at least once after time from and no later than to.
     */
    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList result = new LinkedTaskList();
        Node temp = head;
        for (int i = 0; i < size; i++) {
            if ((temp.task.nextTimeAfter(from) >= from) && (temp.task.nextTimeAfter(from) <= to)) {
                result.add(temp.task);
            }
            temp = temp.next;
        }
        return result;
    }
}