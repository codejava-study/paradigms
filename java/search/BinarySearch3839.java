package search;

/** BinarySearch3839 */
public class BinarySearch3839 {

  public static void main(String[] args) {

    if (args.length < 1) {
      System.err.println("Usage: java search.BinarySearch <x> [a1] [a2] [a3] ...");
      return;
    }

    final int x = Integer.parseInt(args[0]);
    final int[] a = new int[args.length - 1];
    for (int i = 1; i < args.length; i++) {
      a[i - 1] = Integer.parseInt(args[i]);
    }

    final int leftIterative = BinarySearchUtil.leftIterative(x, a);
    final int leftRecursive = BinarySearchUtil.leftRecursive(x, a, 0, a.length);

    final int rangeIterative =
        BinarySearchUtil.rightIterative(x, a) - BinarySearchUtil.leftIterative(x, a);
    final int rangeRecursive =
        BinarySearchUtil.rightRecursive(x, a, 0, a.length)
            - BinarySearchUtil.leftRecursive(x, a, 0, a.length);

    if ((leftIterative == leftRecursive) && (rangeIterative == rangeRecursive)) {

      System.out.println(leftIterative + " " + rangeRecursive);

    } else {
      throw new AssertionError("Results from iterative and recursive searches differ.");
    }
  }
}
