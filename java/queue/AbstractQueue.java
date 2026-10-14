package queue;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * AbstractQueue
 */
public abstract class AbstractQueue implements Queue {
  protected int size;

  @Override
  public void clear() {
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public Object element() {
    if (isEmpty()) {
      throw new NoSuchElementException("queue is empty");
    }

    final Object e = dequeue();
    push(e);
    return e;
  }

  @Override
  public Object peek() {
    if (isEmpty()) {
      throw new NoSuchElementException("queue is empty");
    }

    final Object removed = remove();
    enqueue(removed);
    return removed;
  }

  @Override
  public int count(final Object e) {
    return countImpl(v -> v.equals(e));
  }

  @Override
  public int countIf(final Predicate<Object> p) {
    return countImpl(p);
  }

  @Override
  public int indexIf(final Predicate<Object> p) {
    return index(p, false);
  }

  @Override
  public int indexOf(final Object e) {
    return index(v -> v.equals(e), false);
  }

  @Override
  public int lastIndexIf(final Predicate<Object> p) {
    return index(p, true);
  }

  @Override
  public int lastIndexOf(final Object e) {
    return index(v -> v.equals(e), true);
  }

  @Override
  public void removeAll(final Object e) {
    filter(v -> !v.equals(e));
  }

  @Override
  public void removeIf(final Predicate<Object> p) {
    filter(v -> !p.test(v));
  }

  @Override
  public void retainAll(final Object e) {
    filter(v -> v.equals(e));
  }

  @Override
  public void retainIf(final Predicate<Object> p) {
    filter(p);
  }

  @Override
  public boolean contains(final Object e) {
    return findFirst(e, false);
  }

  @Override
  public boolean removeFirst(final Object e) {
    return findFirst(e, true);
  }

  protected void addImpl(final Object e) {
    Objects.requireNonNull(e);
    size++;
  }

  protected void removeImpl() {
    if (isEmpty()) {
      throw new NoSuchElementException("queue is empty");
    }

    size--;
  }

  private int countImpl(final Predicate<Object> p) {
    final Queue copied = copy();
    int c = 0;
    while (!copied.isEmpty()) {
      final Object dequeued = copied.dequeue();
      if (p.test(dequeued))
        c++;
    }
    return c;
  }

  private int index(final Predicate<Object> p, final boolean last) {
    final Queue copied = copy();
    int result = -1;

    for (int i = 0; i < this.size(); i++) {
      final Object dequeued = copied.dequeue();
      if (p.test(dequeued)) {
        result = i;
        if (!last)
          return i;
      }
    }

    return result;
  }

  private void filter(final Predicate<Object> p) {
    final Queue q = init();
    while (!isEmpty()) {
      final Object dequeued = dequeue();
      if (p.test(dequeued)) {
        q.enqueue(dequeued);
      }
    }
    clear();
    while (!q.isEmpty()) {
      enqueue(q.dequeue());
    }
  }

  private boolean findFirst(final Object e, final boolean remove) {
    final int initialSize = size();
    boolean flag = false;
    boolean removed = false;
    final Queue q = copy();
    clear();
    for (int i = 0; i < initialSize; i++) {
      final Object dequeued = q.dequeue();
      if (dequeued.equals(e)) {
        flag = true;
        if (remove && !removed) {
          removed = true;
          continue;
        }
      }
      enqueue(dequeued);
    }
    return flag;
  }

}
