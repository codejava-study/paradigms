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
      final int mid = lo + (hi - lo) / 2;

      if (x < a[mid]) {
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }

    return lo;
  }

  public static int rightIterative(final int x, final int[] a) {
    int lo = 0;
    int hi = a.length;

    while (lo < hi) {
      final int mid = lo + (hi - lo) / 2;

      if (x <= a[mid]) {
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }

    return lo;
  }

  public static int leftRecursive(final int x, final int[] a, final int lo, final int hi) {
    if (lo >= hi) return lo;

    final int mid = lo + (hi - lo) / 2;
    if (x < a[mid]) {
      return leftRecursive(x, a, mid + 1, hi);
    } else {
      return leftRecursive(x, a, lo, mid);
    }
  }

  public static int rightRecursive(final int x, final int[] a, final int lo, final int hi) {
    if (lo >= hi) return lo;

    final int mid = lo + (hi - lo) / 2;
    if (x <= a[mid]) {
      return rightRecursive(x, a, mid + 1, hi);
    } else {
      return rightRecursive(x, a, lo, mid);
    }
  }

  public static int leftRecursiveIncreasing(
      final int x, final int[] a, final int lo, final int hi) {
    if (lo >= hi) return lo;

    final int mid = lo + (hi - lo) / 2;
    if (x > a[mid]) {
      return leftRecursiveIncreasing(x, a, mid + 1, hi);
    } else {
      return leftRecursiveIncreasing(x, a, lo, mid);
    }
  }

  public static int leftIterativeIncreasing(final int x, final int[] a) {
    int lo = 0;
    int hi = a.length;

    while (lo < hi) {
      final int mid = lo + (hi - lo) / 2;

      if (x > a[mid]) {
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }

    return lo;
  }
}
