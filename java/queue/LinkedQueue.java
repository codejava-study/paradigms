package queue;

public class LinkedQueue extends AbstractQueue {

  private static class Node {
    final Object element;
    Node prev;
    Node next;

    Node(final Object element, final Node prev, final Node next) {
      this.element = element;
      this.prev = prev;
      this.next = next;
    }
  }

  private Node head;
  private Node tail;

  @Override
  public void enqueue(final Object e) {
    addImpl(e);
    final Node n = new Node(e, tail, null);
    if (tail == null) {
      head = n;
    } else {
      tail.next = n;
    }
    tail = n;
  }

  @Override
  public void push(final Object e) {
    addImpl(e);
    final Node n = new Node(e, null, head);
    if (head == null) {
      tail = n;
    } else {
      head.prev = n;
    }
    head = n;
  }

  @Override
  public Object dequeue() {
    removeImpl();
    final Object result = head.element;
    head = head.next;
    if (head == null) {
      tail = null;
    } else {
      head.prev = null;
    }
    return result;
  }

  @Override
  public Object remove() {
    removeImpl();
    final Object result = tail.element;
    tail = tail.prev;
    if (tail == null) {
      head = null;
    } else {
      tail.next = null;
    }
    return result;
  }

  @Override
  public void clear() {
    super.clear();
    head = null;
    tail = null;
  }

  @Override
  public Queue init() {
    return new LinkedQueue();
  }
}
