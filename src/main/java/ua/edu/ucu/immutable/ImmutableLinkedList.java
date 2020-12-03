package ua.edu.ucu.immutable;


public class ImmutableLinkedList implements ImmutableList {

    private static class Node {
        private Object val;
        private Node next;

        public Node(Object val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size;

    public ImmutableLinkedList() {
        this.size = 0;
    }

    public ImmutableLinkedList(Object[] values) {
        this.size = values.length;
        for (int i = 0; i < this.size; i++) {
            if (head == null) {
                head = new Node(values[i], null);
                tail = head;
            } else {
                tail.next = new Node(values[i], null);
                tail = tail.next;
            }
        }
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private ImmutableLinkedList createCopy() {
        ImmutableLinkedList newList = new ImmutableLinkedList();
        if (this.head == null) {
            return newList;
        }
        Node node = this.head;
        newList.head = new Node(this.head.val, this.head.next);

        while (node.next != null) {
            if (newList.tail == null) {
                newList.tail = new Node(node.val, null);
            }
            else {
                newList.tail.next = new Node(node.val, null);
            }
            node = node.next;
        }
        if (newList.tail == null) {
            newList.tail = new Node(node.val, null);
        }
        else {
            newList.tail.next = new Node(node.val, null);
        }

        newList.size = this.size;
        return newList;
    }

    private ImmutableLinkedList insert(int index, Object[] e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newList = this.createCopy();
        if (newList.head == null) {
            newList.head = new Node(e[0], null);
            newList.tail = newList.head;
            for (int i = 1; i < e.length; i++) {
                newList.tail.next = new Node(e[i], null);
                newList.tail = newList.tail.next;
            }
        } else if (index == 0) {
            newList.head = new Node(e[0], newList.head);
            Node node = newList.head;
            for (int i = 1; i < e.length; i++) {
                node.next = new Node(e[i], node.next);
                node = node.next;
            }
        } else {

            Node node = newList.head;
            for (int i = 0; i < index - 1; i++) {
                node = node.next;
            }

            for (int i = 0; i < e.length; i++) {
                node.next = new Node(e[i], node.next);
                node = node.next;
            }
        }
        newList.size += e.length;
        return newList;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return insert(size, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return insert(index, new Object[]{e});
    }

    public ImmutableLinkedList addFirst(Object e) {
        return insert(0, new Object[]{e});
    }


    public ImmutableLinkedList addLast(Object e) {
        return insert(size, new Object[]{e});
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size - 1);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }


    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return insert(size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        return insert(index, c);
    }

    @Override
    public Object get(int index) {
        indexCheck(index);
        Node node = this.head;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.val;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        indexCheck(index);
        ImmutableLinkedList newList = this.createCopy();

        if (size == 1) {
            newList.head = null;
            newList.tail = null;
        } else if (index == 0) {
            newList.head = newList.head.next;
        } else {
            Node node = newList.head;

            for (int i = 0; i < index - 1; i++) {
                node = node.next;
            }
            if (index != size - 1) {
                node.next = node.next.next;
            }
            else {
                node.next = null;
            }
        }
        newList.size--;
        return newList;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        indexCheck(index);
        ImmutableLinkedList newList = this.createCopy();
        Node node = newList.head;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.val = e;

        return newList;
    }

    @Override
    public int indexOf(Object e) {
        Node node = this.head;
        int i = 0;

        while (node.next != null) {
            if (node.val == e) {
                return i;
            }
            i++;
            node = node.next;
        }
        if (node.val == e) {
            return i;
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        Node node = this.head;

        for (int i = 0; i < this.size; i++) {
            array[i] = node.val;
            node = node.next;
        }
        return array;
    }
}
