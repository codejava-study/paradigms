package search;

/**
 * BinarySearch
 *
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */
public final class BinarySearch {
  public static void main(final String[] args) {

    if (args.length < 1) {
      System.err.println("Usage: java search.BinarySearch <x> [a1] [a2] [a3] ...");
      return;
    }

    if (args.length == 1) {
      System.out.println(0);
      return;
    }

    final int x = Integer.parseInt(args[0]);
    final int[] a = new int[args.length - 1];
    for (int i = 1; i < args.length; i++) {
      a[i - 1] = Integer.parseInt(args[i]);
    }

    final int iterative = BinarySearchUtil.leftIterative(x, a);
    final int recursive = BinarySearchUtil.leftRecursive(x, a, 0, a.length);

    if (iterative == recursive) {
      System.out.println(iterative);
    } else {
      throw new AssertionError("Results from iterative and recursive searches differ.");
    }
  }
}
