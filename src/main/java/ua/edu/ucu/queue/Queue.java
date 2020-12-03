package ua.edu.ucu.queue;

import ua.edu.ucu.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList queue;

    public Queue(ImmutableLinkedList queue) {
        this.queue = queue;
    }

    public Object peek() {
        return queue.getFirst();
    }

    public Object dequeue() {
        Object first = queue.getFirst();
        queue = queue.removeFirst();
        return first;
    }

    public void enqueue(Object obj) {
        queue = queue.addLast(obj);
    }

    public ImmutableLinkedList getQueue() {
        return queue;
    }


}