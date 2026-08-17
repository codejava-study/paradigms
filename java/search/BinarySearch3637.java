package search;

/** BinarySearch3637 */
public class BinarySearch3637 {

  public static void main(String[] args) {

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

    final int iterative =
        BinarySearchUtil.rightIterative(x, a) - BinarySearchUtil.leftIterative(x, a);
    final int recursive =
        BinarySearchUtil.rightRecursive(x, a, 0, a.length)
            - BinarySearchUtil.leftRecursive(x, a, 0, a.length);

    if (iterative == recursive) {
      System.out.println(iterative);
    } else {
      throw new AssertionError("Results from iterative and recursive searches differ.");
    }
  }
}
