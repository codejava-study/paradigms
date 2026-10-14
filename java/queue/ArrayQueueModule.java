package queue;

import java.util.function.Predicate;

/**
 * ArrayQueueModule
 * 
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */
public class ArrayQueueModule {
  private final static Queue q = new ArrayQueue();

  public static void enqueue(final Object e) {
    q.enqueue(e);
  }

  public static Object element() {
    return q.element();
  }

  public static Object dequeue() {
    return q.dequeue();
  }

  public static int size() {
    return q.size();
  }

  public static boolean isEmpty() {
    return q.isEmpty();
  }

  public static void clear() {
    q.clear();
  }

  public static int indexOf(final Object e) {
    return q.indexOf(e);
  }

  public static int lastIndexOf(final Object e) {
    return q.lastIndexOf(e);
  }

  public static int lastIndexIf(final Predicate<Object> p) {
    return q.lastIndexIf(p);
  }

  public static int indexIf(final Predicate<Object> p) {
    return q.indexIf(p);
  }

  public static void push(final Object e) {
    q.push(e);
  }

  public static Object peek() {
    return q.peek();

  }

  public static Object remove() {
    return q.remove();
  }

  public static int count(final Object e) {
    return q.count(e);
  }

  public static int countIf(final Predicate<Object> p) {
    return q.countIf(p);
  }

}
