public class MyListNode<T extends Comparable<T>> {
    private T element;
    private MyListNode<T> next;

    public MyListNode() {
        this.element = null;
        this.next = null;
    }

    public MyListNode(T element) {
        this.element = element;
        this.next = null;
    }
    public T getElement() { return this.element; }
    public void setElement(T element) { this.element = element; }
    public MyListNode<T> getNext() { return this.next; }
    public void setNext(MyListNode<T> next) { this.next = next; }
}