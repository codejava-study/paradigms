package queue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Queue
 *
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */
public interface Queue {
  /**
   * Add element to the queue.
   * 
   * @param e - element to add.
   */
  void enqueue(Object e);

  /**
   * First element of the queue.
   * 
   * @return queue's first element.
   */
  Object element();

  /**
   * Delete and return first element of the queue.
   *
   * @return removed element.
   */
  Object dequeue();

  /**
   * Current size of the queue.
   * 
   * @return the queue's size.
   */
  int size();

  /**
   * Determine if the queue has no elements.
   * 
   * @return true - if the queue is empty, false otherwise.
   */
  boolean isEmpty();

  /**
   * Delete every element from the queue.
   */
  void clear();

  /**
   * Returns the index of the first element in the queue which is equal to
   * <code>e</code>.
   * 
   * @param e - element to compare with.
   * @return the index of the element.
   */
  int indexOf(Object e);

  /**
   * Returns the index of the last element in the queue which is equal to
   * <code>e</code>.
   * 
   * @param e - element to compare with.
   * @return the index of the element.
   */
  int lastIndexOf(Object e);

  /**
   * Returns the index of the first element in the queue which satisfies
   * <code>p.test(e)</code> condition.
   * 
   * @param e - element to check.
   * @return the index of the element.
   */
  int indexIf(Predicate<Object> p);

  /**
   * Returns the index of the last element in the queue which satisfies
   * <code>p.test(e)</code> condition.
   * 
   * @param e - element to check.
   * @return the index of the element.
   */
  int lastIndexIf(Predicate<Object> p);

  /**
   * Add element <code>e</code> to the start of the queue.
   * 
   * @param e - element to add
   */
  void push(Object e);

  /**
   * Get the last element in the queue.
   * 
   * @return element in question
   */
  Object peek();

  /**
   * Get the last element in the queue and delete it.
   *
   * @return deleted last element.
   */
  Object remove();

  /**
   * Get the amount of elements that are <code>.equals(e)</code> to
   * <code>e</code>.
   * 
   * @param e - element to compare with
   * @return amount of elements
   */
  int count(Object e);

  /**
   * Get the amount of elements which satisfy <code>p.test()</code> condition.
   * 
   * @param p - predicate
   * @return amount of elements
   */
  int countIf(Predicate<Object> p);

  /**
   * Generate a new {@link Queue} of the same subtype.
   * 
   * @return generated queue.
   */
  Queue init();

  /**
   * Get the exact copy of the queue.
   *
   * @return copied queue.
   */
  default Queue copy() {
    final Queue c = init();
    final List<Object> elements = new ArrayList<>(size());
    while (!isEmpty()) {
      elements.add(dequeue());
    }
    elements.forEach((e) -> {
      enqueue(e);
      c.enqueue(e);
    });
    return c;
  }

  /**
   * Delete all elements that are equal to <code>e</code>.
   * 
   * @param e - element to compare with.
   */
  void removeAll(Object e);

  /**
   * Keep all elements that are equal to <code>e</code>.
   * 
   * @param e - element to compare with.
   */
  void retainAll(Object e);

  /**
   * Delete all elements that satisfy the <code>p.test()</code> condition.
   * 
   * @param p - tester predicate.
   */
  void removeIf(Predicate<Object> p);

  /**
   * Keep all elements that satisfy the <code>p.test()</code> condition.
   * 
   * @param p - tester predicate.
   */
  void retainIf(Predicate<Object> p);

  /**
   * Check if the <code>e</code> is in the queue.
   * 
   * @param e - element to compare with.
   * @return true if <code>e</code> is in the queue, false otherwise.
   */
  boolean contains(Object e);

  /**
   * Delete the first appearance of the element <code>e</code> in the queue and
   * returns true if
   * there was such appearance (false otherwise).
   * 
   * @param e - element to compare with.
   * @return true if <code>e</code> is in the queue, false otherwise.
   */
  boolean removeFirst(Object e);
}
