package queue;

import java.util.function.Predicate;

/**
 * ArrayQueueADT
 * 
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */
public class ArrayQueueADT extends ArrayQueue {

  public static void enqueue(final ArrayQueueADT q, final Object e) {
    q.enqueue(e);
  }

  public static Object element(final ArrayQueueADT q) {
    return q.element();
  }

  public static Object dequeue(final ArrayQueueADT q) {
    return q.dequeue();
  }

  public static int size(final ArrayQueueADT q) {
    return q.size();
  }

  public static boolean isEmpty(final ArrayQueueADT q) {
    return q.isEmpty();
  }

  public static void clear(final ArrayQueueADT q) {
    q.clear();
  }

  public static int indexOf(final ArrayQueueADT q, final Object e) {
    return q.indexOf(e);
  }

  public static int lastIndexOf(final ArrayQueueADT q, final Object e) {
    return q.lastIndexOf(e);
  }

  public static int lastIndexIf(final ArrayQueueADT q, final Predicate<Object> p) {
    return q.lastIndexIf(p);
  }

  public static int indexIf(final ArrayQueueADT q, final Predicate<Object> p) {
    return q.indexIf(p);
  }

  public static void push(final ArrayQueueADT q, final Object e) {
    q.push(e);
  }

  public static Object peek(final ArrayQueueADT q) {
    return q.peek();
  }

  public static Object remove(final ArrayQueueADT q) {
    return q.remove();
  }

  public static int count(final ArrayQueueADT q, final Object e) {
    return q.count(e);
  }

  public static int countIf(final ArrayQueueADT q, final Predicate<Object> p) {
    return q.countIf(p);
  }

}
