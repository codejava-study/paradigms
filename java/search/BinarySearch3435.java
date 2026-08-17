package search;

/** BinarySearch3435 */
public class BinarySearch3435 {
  public static void main(final String[] args) {

    if (args.length == 0) {
      System.out.println(0);
      return;
    }

    final int[] a = new int[args.length];
    for (int i = 0; i < a.length; i++) {
      a[i] = Integer.parseInt(args[i]);
    }

    final int iterative = BinarySearchUtil.leftIterativeIncreasing(a[a.length - 1], a);
    final int recursive = BinarySearchUtil.leftRecursiveIncreasing(a[a.length - 1], a, 0, a.length);

    if (iterative == recursive) {
      System.out.println(iterative);
    } else {
      throw new AssertionError("Results from iterative and recursive searches differ.");
    }
  }
}
