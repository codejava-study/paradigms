package search;

/** BinarySearch3233 */
public class BinarySearch3233 {
  public static void main(String[] args) {

    if (args.length == 0) {
      System.out.println(0);
      return;
    }

    int[] a = new int[args.length];
    for (int i = 0; i < a.length; i++) {
      a[i] = Integer.parseInt(args[i]);
    }

    final int iterative = BinarySearchUtil.leftIterative(a[a.length - 1], a);
    final int recursive = BinarySearchUtil.leftRecursive(a[a.length - 1], a, 0, a.length);

    if (iterative == recursive) {
      System.out.println(iterative);
    } else {
      throw new AssertionError("Results from iterative and recursive searches differ.");
    }
  }
}
