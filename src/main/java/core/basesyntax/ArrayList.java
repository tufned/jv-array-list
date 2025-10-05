package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROWTH_FACTOR_DENOMINATOR = 2;
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
        ensureCapacityAndGrowIfArrayFull();
        array[currentSize - 1] = value;
    }

    @Override
    public void add(T value, int index) {
        checkAddMethodIndex(index);
        ensureCapacityAndGrowIfArrayFull();
        System.arraycopy(array, index, array, index + 1, currentSize - index);
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
        System.arraycopy(array, index + 1, array, index, currentSize - index);
        return removedElem;
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < currentSize; i++) {
            if (array[i] == element || (array[i] != null && array[i].equals(element))) {
                return remove(i);
            }
        }
        throw new NoSuchElementException("Element " + element + " not found");
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    private void ensureCapacityAndGrowIfArrayFull() {
        if (currentSize + 1 == capacity) {
            extendCapacity();
        }
        currentSize++;
    }

    private void reduceCurrentSize() {
        currentSize--;
    }

    private void extendCapacity() {
        this.capacity += capacity / GROWTH_FACTOR_DENOMINATOR;
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, currentSize);
        array = newArray;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= currentSize) {
            throw new ArrayListIndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + currentSize);
        }
    }

    private void checkAddMethodIndex(int index) {
        if (index < 0 || index > currentSize) {
            throw new ArrayListIndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + currentSize);
        }
    }
}
