public class MyLinkedList <T extends Comparable<T>> implements MyList<T> {
    private int size;
    private MyListNode<T> head;
    private MyListNode<T> tail;

    public MyLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    ///Returns number of elements in the List (in O(1))
    @Override
    public int size() {
        return this.size;
    }

    ///Adds the element at the beginning of the list
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

    ///Adds the element as end of list
    @Override
    public void addLast(T elem) throws IllegalArgumentException {
        if (elem == null) {
            throw new IllegalArgumentException("adding element failed, no data available");
        }
        if (head == null) {
            addFirst(elem);     // empty list: same as addFirst
            return;
        }
        MyListNode<T> add = new MyListNode<>(elem);
        tail.setNext(add);      // link to current tail
        tail = add;             // set tail to new node
        size++;
    }

    ///Adds the element to the list (sorted in asc order)
    @Override
    public void addSorted(T val) throws IllegalArgumentException {
        if (val == null) {
            throw new IllegalArgumentException("adding element failed, no data available");
        }

        if (!isSortedASC()) {         // list unsorted
            sortASC();
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

        MyListNode<T> add = new MyListNode<>(val);
        add.setNext(curr.getNext());
        curr.setNext(add);
        size++;
    }

    ///Sorts the list in ascending order
    @Override
    public void sortASC() {
        if (head == null || size == 1) {
            return;
        }
        head = mergeSort(head, size, true);   // ascending

        MyListNode<T> curr = head;            // set tail
        while (curr.getNext() != null) {
            curr = curr.getNext();
        }
        tail = curr;
    }

    ///Sorts the list in descending order
    @Override
    public void sortDES() {
        if (head == null || size == 1) {
            return;
        }
        head = mergeSort(head, size, false);  // descending

        MyListNode<T> curr = head;            // set tail
        while (curr.getNext() != null) {
            curr = curr.getNext();
        }
        tail = curr;
    }

    ///Clears the list by removing all elements (in O(1))
    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    ///Returns and removes the first element from the list
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

    ///Returns and removes the last element from the list
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

    ///Returns the first element from the list (no removal)
    @Override
    public T getFirst() {
        if (head == null) {
            return null;
        }
        return head.getElement();
    }

    ///Returns the last element from the list (no removal)
    @Override
    public T getLast() {
        if (tail == null) {
            return null;
        }
        return tail.getElement();
    }

    ///Returns true if T is in list; false otherwise
    @Override
    public boolean contains(T val) throws IllegalArgumentException {
        if (val == null) {
            throw new IllegalArgumentException("element not valid for search");
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

    ///Returns element at specific index position (no removal)
    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
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

    ///Returns and removes element at specific index position
    @Override
    public T remove(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }

        // first or last element to remove
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }

        // search for element
        MyListNode<T> prev = head;
        for (int i = 0; i != index - 1; i++) {
            prev = prev.getNext();
        }

        MyListNode<T> delete = prev.getNext();      // safe element for return statement
        prev.setNext(delete.getNext());
        size--;
        return delete.getElement();
    }

    ///Returns a string representation of the list
    @Override
    public String toString() {
        if (head == null) {
            return "[empty list]";
        }

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

    ///Returns the list as array
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

    // helper ASCENDING/DESCENDING

    ///Recursive merge sort: splits the list, sorts both halves, merges them
    private MyListNode<T> mergeSort(MyListNode<T> start, int length, boolean asc) {
        if (length <= 1) return start;      // list is sorted already

        int half = length / 2;

        // walk to last node of left half
        MyListNode<T> beforeMid = start;
        for (int i = 0; i < half - 1; i++) {
            beforeMid = beforeMid.getNext();
        }

        MyListNode<T> mid = beforeMid.getNext(); // start right half of list
        beforeMid.setNext(null);                 // split list in two halfes

        MyListNode<T> left = mergeSort(start, half, asc);
        MyListNode<T> right = mergeSort(mid, length - half, asc);

        return merge(left, right, asc);
    }

    ///Merges two sorted sublists into one sorted list
    private MyListNode<T> merge(MyListNode<T> left, MyListNode<T> right, boolean asc) {
        if (left == null) return right;
        if (right == null) return left;

        // set head of merged list
        int cmp = left.getElement().compareTo(right.getElement());
        MyListNode<T> newHead;
        if (asc ? cmp <= 0 : cmp >= 0) {
            newHead = left;
            left = left.getNext();
        } else {
            newHead = right;
            right = right.getNext();
        }

        // append remaining nodes (alternate)
        MyListNode<T> curr = newHead;
        while (left != null && right != null) {
            cmp = left.getElement().compareTo(right.getElement());
            if (asc ? cmp <= 0 : cmp >= 0) {
                curr.setNext(left);
                left = left.getNext();
            } else {
                curr.setNext(right);
                right = right.getNext();
            }
            curr = curr.getNext();
        }

        // add rest
        curr.setNext(left != null ? left : right);
        return newHead;
    }

    // helper - check if list is sorted ascending
    private boolean isSortedASC() {
        MyListNode<T> curr = head;
        while (curr != null && curr.getNext() != null) {
            if (curr.getElement().compareTo(curr.getNext().getElement()) > 0) {
                return false;               // element bigger than successor - list is not sorted
            }
            curr = curr.getNext();
        }
        return true;                        // empty list or one element - list is sorted
    }
}