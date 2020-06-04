/* ******************************************************************************
 * 
 * This file is part of Optsicom
 * 
 * License:
 *   EPL: http://www.eclipse.org/legal/epl-v10.html
 *   LGPL 3.0: http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 *   See the LICENSE file in the project's top-level directory for details.
 *
 * **************************************************************************** */
package es.optsicom.lib.util;

import java.util.Comparator;
import java.util.List;

/**
 * Written by Chee Wai Lee 4/25/2002 Sorter is a static class that provides
 * methods to sort numeric data (using the simplest Quicksort variant) but
 * produce a sort map instead of the actual sorted array. ***CURRENT IMP*** For
 * now, it is hard coded to do reverse sorting (with the assumption that bigger
 * is more significant). It should be written to register an object that
 * implements the java.util.Comparator interface.
 */

public class ObjectListSorter {

	public static <T extends Comparable<? super T>> int[] sort(List<T> inArray, Comparator<T> comparator, int begin,
			int end) {
		int sortMap[] = new int[end - begin];
		// double workArray[] = new double[inArray.length];

		// initialization
		for (int i = 0; i < (end - begin); i++) {
			sortMap[i] = i;
			// workArray[i] = inArray[i];
		}

		qSort(sortMap, inArray, comparator, begin, end - 1);

		return sortMap;
	}

	public static <T extends Comparable<? super T>> int[] sort(List<T> inArray, Comparator<T> comparator) {
		return sort(inArray, comparator, 0, inArray.size());
	}

	// public static int[] sort(double[] inArray) {
	// int sortMap[] = new int[inArray.length];
	// double workArray[] = new double[inArray.length];
	//
	// // initialization
	// for (int i = 0; i < inArray.length; i++) {
	// sortMap[i] = i;
	// workArray[i] = inArray[i];
	// }
	//
	// qSort(sortMap, workArray, 0, inArray.length - 1);
	//
	// return sortMap;
	// }

	/**
	 * Invariant: Every element to the left of the pivot is smaller than the
	 * pivot element.
	 */
	private static <T extends Comparable<? super T>> void qSort(int[] map, List<T> array, Comparator<T> comparator,
			int low, int high) {
		// test termination condition
		if (low >= high) {
			return;
		}

		// pick first element as pivot
		int pivot = low;

		// partition the data
		for (int i = low + 1; i <= high; i++) {
			if (i < pivot) {
				// seeking to swap with smaller or equal elements
				// if I am not wrong, this never happens!
				if (compare(comparator, array.get(i), array.get(pivot)) > 0) {
					swap(map, array, i, pivot);
					pivot = i;
				}
			} else {
				// seeking to swap with larger elements
				if (compare(comparator, array.get(i), array.get(pivot)) <= 0) {
					if (i == pivot + 1) {
						// if smaller element is next to pivot, do a swap
						swap(map, array, i, pivot);
						pivot = i;
					} else {
						// if i > pivot + 1,
						// do a double swap with next larger element
						swap(map, array, pivot, pivot + 1);
						swap(map, array, i, pivot);
						pivot = pivot + 1;
					}
				}
			}
		}

		// recurse
		qSort(map, array, comparator, low, pivot - 1);
		qSort(map, array, comparator, pivot + 1, high);
	}

	private static <T extends Comparable<? super T>> int compare(Comparator<T> comparator, T objA, T objB) {
		if (comparator != null) {
			return comparator.compare(objA, objB);
		} else {
			return objA.compareTo(objB);
		}
	}

	private static <T extends Comparable<? super T>> void swap(int[] map, List<T> array, int idx1, int idx2) {
		int mapTemp;
		T arrayTemp;

		mapTemp = map[idx1];
		map[idx1] = map[idx2];
		map[idx2] = mapTemp;

		arrayTemp = array.get(idx1);
		array.set(idx1, array.get(idx2));
		array.set(idx2, arrayTemp);
	}
}