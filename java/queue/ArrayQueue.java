package queue;

import java.util.Arrays;
import java.util.Objects;

/**
 * ArrayQueue
 *
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */
public class ArrayQueue extends AbstractQueue {

  private static final int DEFAULT_CAPACITY = 16;

  private Object[] elements;
  private int head;
  private int tail;

  public ArrayQueue(final int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException("capacity must be positive: " + capacity);
    }
    this.elements = new Object[capacity];
  }

  public ArrayQueue() {
    this(DEFAULT_CAPACITY);
  }

  @Override
  public void enqueue(final Object e) {
    Objects.requireNonNull(e);
    ensureCapacity();
    addImpl(e);
    elements[tail] = e;
    tail = (tail + 1) % elements.length;
  }

  @Override
  public Object dequeue() {
    removeImpl();
    final Object result = elements[head];
    elements[head] = null;
    head = (head + 1) % elements.length;
    return result;
  }

  @Override
  public void clear() {
    super.clear();
    Arrays.fill(elements, null);
    head = 0;
    tail = 0;
  }

  @Override
  public void push(final Object e) {
    Objects.requireNonNull(e);
    ensureCapacity();
    addImpl(e);
    head = (head - 1 + elements.length) % elements.length;
    elements[head] = e;
  }

  @Override
  public Object remove() {
    removeImpl();
    tail = (tail - 1 + elements.length) % elements.length;
    final Object result = elements[tail];
    elements[tail] = null;
    return result;
  }

  @Override
  public Queue init() {
    return new ArrayQueue();
  }

  private void ensureCapacity() {
    if (size < elements.length) {
      return;
    }
    final Object[] newElements = new Object[2 * elements.length];
    final int firstPart = elements.length - head;
    System.arraycopy(elements, head, newElements, 0, firstPart);
    System.arraycopy(elements, 0, newElements, firstPart, head);
    elements = newElements;
    head = 0;
    tail = size;
  }

}
