public class MyLinkedList <T extends Comparable<T>> implements MyList<T> {
    private int size = 0;
    private MyListNode<T> head;
    private MyListNode<T> tail;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void addFirst(T elem) throws IllegalArgumentException {
        if (elem == null) {
            throw new IllegalArgumentException("adding element failed, no data available");
        }
        MyListNode<T> add = new MyListNode<>(elem);
        add.setNext(head);      // link with first elem in list - null node if list empty
        head = add;             // new node is head

        if (tail == null) {    // tail also null - first element in list now head & tail new node
            tail = add;
        }
        size++;
    }

    @Override
    public void addLast(T elem) throws IllegalArgumentException {
        if (elem == null) {
            throw new IllegalArgumentException("adding element failed, no data available");
        }
        if (head == null) {
            addFirst(elem);     // methodcall
            return;
        }
        MyListNode<T> add = new MyListNode<>(elem);
        tail.setNext(add);      // link to current tail
        tail = add;             // set tail to new node
        size++;
    }


    @Override
    public void addSorted(T val) throws IllegalArgumentException {
        if (val == null) {
            throw new IllegalArgumentException("adding element failed, no data available");
        }

        if (head == null || val.compareTo(head.getElement()) <= 0) {        // add as first element
            addFirst(val);
            return;
        }

        if (tail == null || val.compareTo(tail.getElement()) >= 0) {        // add as last element
            addLast(val);
            return;
        }

        MyListNode<T> curr = head;                                         // set pointer to iterate over list
        while (curr.getNext().getElement().compareTo(val) < 0) {
            curr = curr.getNext();
        }

        MyListNode<T> add = new MyListNode<T>(val);
        add.setNext(curr.getNext());
        curr.setNext(add);
        size++;
    }

    @Override
    public void sortASC() {
        if (head == null) {
            throw new IllegalArgumentException("empty list");
        }

        boolean swapped;
        do {
            swapped = false;
            MyListNode<T> curr = head;

            while (curr.getNext() != null) {
                MyListNode<T> next = curr.getNext();

                // compare every single pair of elements
                if (curr.getElement().compareTo(next.getElement()) > 0) {
                    T tmp = curr.getElement();              // safe elem to swap
                    curr.setElement(next.getElement());
                    next.setElement(tmp);
                    swapped = true;
                }
                curr = next;
            }
        } while (swapped);

    }

    @Override
    public void sortDES() {
        if (head == null) {
            throw new IllegalArgumentException("empty list");
        }

        boolean swapped;
        do {
            swapped = false;
            MyListNode<T> curr = head;

            while (curr.getNext() != null) {
                MyListNode<T> next = curr.getNext();

                if (curr.getElement().compareTo(next.getElement()) < 0) {
                    T tmp = curr.getElement();
                    curr.setElement(next.getElement());
                    next.setElement(tmp);
                    swapped = true;
                }
                curr = next;
            }
        } while (swapped);
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    @Override
    public T removeFirst() {
        if (head == null) {
            return null;
        }

        T elem = head.getElement();     // safe element before removing

        if (head == tail) {
            clear();                    // clear if only one element in list
            return elem;
        }

        head = head.getNext();
        size--;
        return elem;
    }

    @Override
    public T removeLast() {
        if (head == null) {
            return null;
        }

        T elem = tail.getElement();     // safe element before removing

        if (head == tail) {             // clear if only one element in list
            clear();
            return elem;
        }

        MyListNode<T> curr = head;
        while (curr.getNext() != tail) {       // iteration
            curr = curr.getNext();
        }

        curr.setNext(null);
        tail = curr;
        size--;
        return elem;
    }

    @Override
    public T getFirst() {
        if (head == null) {
            return null;
        }
        return head.getElement();
    }

    @Override
    public T getLast() {
        if (tail == null) {
            return null;
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T val) throws IllegalArgumentException {
        if (val == null) {
            throw new IllegalArgumentException("element noch valid for search");
        }

        MyListNode<T> curr = head;
        while (curr != null) {
            if (curr.getElement().compareTo(val) == 0) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IllegalArgumentException("index " + index + " out of bounds for size " + size);
        }

        if (index == 0) {
            return getFirst();
        }
        if (index == size - 1) {
            return getLast();
        }

        MyListNode<T> curr = head;

        for (int i = 0; i != index; i++) {
            curr = curr.getNext();
        }
        return curr.getElement();
    }

    @Override
    public T remove(int index) {
        if (index > size - 1 || index < 0) {
            throw new IllegalArgumentException("index " + index + " out of bounds for size " + size);
        }

        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }

        MyListNode<T> prev = head;
        for (int i = 0; i != index - 1; i++) {
            prev = prev.getNext();
        }

        MyListNode<T> delete = prev.getNext();
        prev.setNext(delete.getNext());
        size--;
        return delete.getElement();
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];

        MyListNode<T> curr = head;
        int i = 0;
        while (curr != null) {
            arr[i] = curr.getElement();
            curr = curr.getNext();
            i++;
        }
        return arr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        MyListNode<T> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null) {
                sb.append(", ");            // while last element not reached
            }
            curr = curr.getNext();
        }
        return sb.append("]").toString();
    }
}