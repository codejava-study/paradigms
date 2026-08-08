package search;

/**
 * BinarySearchUtil
 *
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */
public class BinarySearchUtil {

  public static int leftIterative(final int x, final int[] a) {
    int lo = 0;
    int hi = a.length;

    while (lo < hi) {
      int mid = lo + (hi - lo) / 2;

      if (x < a[mid]) {
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }

    return lo;
  }

  public static int leftRecursive(final int x, final int[] a, int lo, int hi) {
    if (hi >= lo) return lo;

    int mid = lo + (hi - lo) / 2;
    if (x >= a[mid]) {
      return leftRecursive(x, a, mid + 1, hi);
    } else {
      return leftRecursive(x, a, lo, mid);
    }
  }
}
