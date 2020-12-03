package ua.edu.ucu.immutable;

public class ImmutableArrayList implements ImmutableList {

    private int buffer;
    private int size;
    private Object[] array;

    public ImmutableArrayList(int buffer) {
        size = 0;
        this.buffer = buffer;
        array = new Object[buffer];
    }

    public ImmutableArrayList(Object[] values) {
        size = values.length;
        buffer = values.length * 2;
        array = new Object[buffer];

        for (int i = 0; i < values.length; i++) {
            array[i] = values[i];
        }
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private ImmutableArrayList copyList(int newBuffer) {
        ImmutableArrayList newList = new ImmutableArrayList(newBuffer);
        for (int i = 0; i < size; i++) {
            newList.array[i] = array[i];
        }
        newList.size = size;
        newList.buffer = newBuffer;
        return newList;
    }

    @Override
    public ImmutableArrayList add(Object e) {
        return addAll(size, new Object[]{e});
    }

    @Override
    public ImmutableArrayList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableArrayList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableArrayList addAll(int index, Object[] c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        int newBuffer = buffer;
        if (newBuffer < c.length + size) {
            newBuffer = 2 * (c.length + size);
        }
        ImmutableArrayList newList = copyList(newBuffer);

        for (int i = 0; (i < c.length && index + i < size); i++) {
            newList.array[i + index + c.length] = newList.array[index + i];
        }

        for (int i = 0; i < c.length; i++) {
            newList.array[index + i] = c[i];
        }

        newList.size += c.length;
        return newList;
    }

    @Override
    public Object get(int index) {
        indexCheck(index);
        return array[index];
    }

    @Override
    public ImmutableArrayList remove(int index) {
        indexCheck(index);
        ImmutableArrayList newList = copyList(buffer);
        for (int i = index; i < size; i++) {
            newList.array[i] = newList.array[i + 1];
        }
        newList.size--;
        return newList;
    }

    @Override
    public ImmutableArrayList set(int index, Object e) {
        indexCheck(index);

        ImmutableArrayList newList = copyList(buffer);
        newList.array[index] = e;

        return newList;
    }

    @Override
    public int indexOf(Object e) {
        for (int i = 0; i < size; i++) {
            if (array[i] == e) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableArrayList clear() {
        ImmutableArrayList newList = new ImmutableArrayList(1);
        newList.size = 0;
        return newList;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }
        return result;
    }
}
