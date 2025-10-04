package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private int capacity;
    private T[] array;
    private int currentSize;

    public ArrayList() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
        this.currentSize = 0;
    }

    @Override
    public void add(T value) {
        addCurrentSize();
        array[currentSize - 1] = value;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > currentSize) {
            throw new ArrayListIndexOutOfBoundsException("Index must be less then list size");
        }
        addCurrentSize();
        for (int i = currentSize - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = value;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        array[index] = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        reduceCurrentSize();
        T removedElem = array[index];
        for (int i = index + 1; i <= currentSize; i++) {
            if (i > 0) {
                array[i - 1] = array[i];
            }
        }
        array[currentSize] = null;
        return removedElem;
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < currentSize; i++) {
            if (array[i] == element || (array[i] != null && array[i].equals(element))) {
                return remove(i);
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    private void addCurrentSize() {
        currentSize++;
        if (currentSize == capacity) {
            extendCapacity();
        }
    }

    private void reduceCurrentSize() {
        currentSize--;
    }

    private void extendCapacity() {
        this.capacity += capacity / 2;
        T[] newArray = (T[]) new Object[capacity];
        for (int i = 0; i < currentSize; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    private void checkIndex(int index) {
        if (index >= 0 && (index < currentSize || currentSize == 0)) {
            return;
        }
        throw new ArrayListIndexOutOfBoundsException("Index must be less then list size");
    }
}
