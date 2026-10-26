import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    private MyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    // helper to fill list (keeps insertion order)
    private void fill(int... values) {
        for (int v : values) {
            list.addLast(v);
        }
    }

    // ---------- empty list ----------

    @Test
    void emptyListSizeIsZero() {
        assertEquals(0, list.size());
    }

    @Test
    void emptyListToStringShowsEmptyList() {
        assertEquals("[empty list]", list.toString());
    }

    @Test
    void emptyListToArrayIsEmpty() {
        assertEquals(0, list.toArray().length);
    }

    @Test
    void emptyListGetFirstAndGetLastReturnNull() {
        assertNull(list.getFirst());
        assertNull(list.getLast());
    }

    @Test
    void emptyListRemoveFirstAndRemoveLastReturnNull() {
        assertNull(list.removeFirst());
        assertNull(list.removeLast());
        assertEquals(0, list.size());
    }

    @Test
    void emptyListGetAndRemoveByIndexReturnNull() {
        assertNull(list.get(0));
        assertNull(list.remove(0));
        assertEquals(0, list.size());
    }

    @Test
    void emptyListContainsReturnsFalse() {
        assertFalse(list.contains(1));
    }

    @Test
    void emptyListClearDoesNothing() {
        list.clear();
        assertEquals(0, list.size());
        assertEquals("[empty list]", list.toString());
    }

    // ---------- addFirst ----------

    @Test
    void addFirstOnEmptyListSetsHeadAndTail() {
        list.addFirst(5);
        assertEquals(1, list.size());
        assertEquals(5, list.getFirst());
        assertEquals(5, list.getLast());
    }

    @Test
    void addFirstAddsInFront() {
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        assertEquals("[1, 2, 3]", list.toString());
        assertEquals(1, list.getFirst());
        assertEquals(3, list.getLast());
        assertEquals(3, list.size());
    }

    @Test
    void addFirstNullThrowsAndListUnchanged() {
        fill(1, 2);
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> list.addFirst(null));
        assertEquals("adding element failed, no data available", ex.getMessage());
        assertEquals(2, list.size());
        assertEquals("[1, 2]", list.toString());
    }

    // ---------- addLast ----------

    @Test
    void addLastOnEmptyListSetsHeadAndTail() {
        list.addLast(5);
        assertEquals(1, list.size());
        assertEquals(5, list.getFirst());
        assertEquals(5, list.getLast());
    }

    @Test
    void addLastAddsAtEnd() {
        fill(1, 2, 3);
        assertEquals("[1, 2, 3]", list.toString());
        assertEquals(1, list.getFirst());
        assertEquals(3, list.getLast());
        assertEquals(3, list.size());
    }

    @Test
    void addLastNullThrowsAndListUnchanged() {
        fill(1, 2);
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> list.addLast(null));
        assertEquals("adding element failed, no data available", ex.getMessage());
        assertEquals(2, list.size());
        assertEquals("[1, 2]", list.toString());
    }

    @Test
    void addLastMixedWithAddFirst() {
        list.addLast(2);
        list.addFirst(1);
        list.addLast(3);
        assertEquals("[1, 2, 3]", list.toString());
        assertEquals(3, list.size());
    }

    // ---------- addSorted ----------

    @Test
    void addSortedOnEmptyList() {
        list.addSorted(5);
        assertEquals("[5]", list.toString());
        assertEquals(5, list.getFirst());
        assertEquals(5, list.getLast());
        assertEquals(1, list.size());
    }

    @Test
    void addSortedInsertsFrontMiddleEnd() {
        list.addSorted(43);
        list.addSorted(25);  // front
        list.addSorted(60);  // end
        list.addSorted(48);  // middle
        assertEquals("[25, 43, 48, 60]", list.toString());
        assertEquals(25, list.getFirst());
        assertEquals(60, list.getLast());
        assertEquals(4, list.size());
    }

    @Test
    void addSortedInsertsAtEveryMiddlePosition() {
        fill(10, 20, 30, 40);
        list.addSorted(15);
        list.addSorted(25);
        list.addSorted(35);
        assertEquals("[10, 15, 20, 25, 30, 35, 40]", list.toString());
        assertEquals(7, list.size());
    }

    @Test
    void addSortedHandlesDuplicates() {
        list.addSorted(2);
        list.addSorted(1);
        list.addSorted(2);
        list.addSorted(1);
        assertEquals("[1, 1, 2, 2]", list.toString());
        assertEquals(4, list.size());
    }

    @Test
    void addSortedEqualToHeadAndTail() {
        fill(1, 5);
        list.addSorted(1);
        list.addSorted(5);
        assertEquals("[1, 1, 5, 5]", list.toString());
    }

    @Test
    void addSortedTailStaysCorrectAfterInsertAtEnd() {
        list.addSorted(1);
        list.addSorted(2);
        list.addLast(3);
        assertEquals(3, list.getLast());
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void addSortedWorksAfterSortASC() {
        fill(30, 10, 20);
        list.sortASC();
        list.addSorted(15);
        assertEquals("[10, 15, 20, 30]", list.toString());
    }

    @Test
    void addSortedNullThrowsAndListUnchanged() {
        fill(1, 2);
        assertThrows(IllegalArgumentException.class, () -> list.addSorted(null));
        assertEquals(2, list.size());
    }

    // ---------- sortASC ----------

    @Test
    void sortASCSortsList() {
        fill(48, 25, 60, 43);
        list.sortASC();
        assertEquals("[25, 43, 48, 60]", list.toString());
        assertEquals(25, list.getFirst());
        assertEquals(60, list.getLast());
        assertEquals(4, list.size());
    }

    @Test
    void sortASCWithDuplicates() {
        fill(3, 1, 3, 2, 1);
        list.sortASC();
        assertEquals("[1, 1, 2, 3, 3]", list.toString());
    }

    @Test
    void sortASCAlreadySorted() {
        fill(1, 2, 3);
        list.sortASC();
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void sortASCReverseSorted() {
        fill(5, 4, 3, 2, 1);
        list.sortASC();
        assertEquals("[1, 2, 3, 4, 5]", list.toString());
    }

    @Test
    void sortASCSingleElement() {
        fill(7);
        list.sortASC();
        assertEquals("[7]", list.toString());
    }

    // ---------- sortDES ----------

    @Test
    void sortDESSortsList() {
        fill(48, 25, 60, 43);
        list.sortDES();
        assertEquals("[60, 48, 43, 25]", list.toString());
        assertEquals(60, list.getFirst());
        assertEquals(25, list.getLast());
        assertEquals(4, list.size());
    }

    @Test
    void sortDESWithDuplicates() {
        fill(3, 1, 3, 2, 1);
        list.sortDES();
        assertEquals("[3, 3, 2, 1, 1]", list.toString());
    }

    @Test
    void sortDESAlreadySortedAscending() {
        fill(1, 2, 3, 4, 5);
        list.sortDES();
        assertEquals("[5, 4, 3, 2, 1]", list.toString());
    }

    @Test
    void sortDESSingleElement() {
        fill(7);
        list.sortDES();
        assertEquals("[7]", list.toString());
    }

    // ---------- clear ----------

    @Test
    void clearEmptiesList() {
        fill(1, 2, 3);
        list.clear();
        assertEquals(0, list.size());
        assertEquals("[empty list]", list.toString());
        assertNull(list.getFirst());
        assertNull(list.getLast());
    }

    @Test
    void clearListUsableAfterwards() {
        fill(1, 2, 3);
        list.clear();
        list.addLast(4);
        list.addFirst(3);
        assertEquals("[3, 4]", list.toString());
        assertEquals(3, list.getFirst());
        assertEquals(4, list.getLast());
        assertEquals(2, list.size());
    }

    // ---------- removeFirst ----------

    @Test
    void removeFirstReturnsAndRemoves() {
        fill(1, 2, 3);
        assertEquals(1, list.removeFirst());
        assertEquals("[2, 3]", list.toString());
        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());
        assertEquals(3, list.getLast());
    }

    @Test
    void removeFirstLastRemainingElement() {
        fill(1);
        assertEquals(1, list.removeFirst());
        assertEquals(0, list.size());
        assertNull(list.getFirst());
        assertNull(list.getLast());
        list.addLast(2);
        assertEquals("[2]", list.toString());
    }

    @Test
    void removeFirstUntilEmpty() {
        fill(1, 2, 3);
        assertEquals(1, list.removeFirst());
        assertEquals(2, list.removeFirst());
        assertEquals(3, list.removeFirst());
        assertNull(list.removeFirst());
        assertEquals(0, list.size());
    }

    // ---------- removeLast ----------

    @Test
    void removeLastReturnsAndRemoves() {
        fill(1, 2, 3);
        assertEquals(3, list.removeLast());
        assertEquals("[1, 2]", list.toString());
        assertEquals(2, list.size());
        assertEquals(2, list.getLast());
    }

    @Test
    void removeLastUpdatesTail() {
        fill(1, 2, 3);
        list.removeLast();
        list.addLast(4);
        assertEquals("[1, 2, 4]", list.toString());
        assertEquals(4, list.getLast());
    }

    @Test
    void removeLastLastRemainingElement() {
        fill(1);
        assertEquals(1, list.removeLast());
        assertEquals(0, list.size());
        list.addFirst(2);
        assertEquals("[2]", list.toString());
        assertEquals(2, list.getLast());
    }

    @Test
    void removeLastUntilEmpty() {
        fill(1, 2, 3);
        assertEquals(3, list.removeLast());
        assertEquals(2, list.removeLast());
        assertEquals(1, list.removeLast());
        assertNull(list.removeLast());
        assertEquals(0, list.size());
    }

    // ---------- getFirst / getLast ----------

    @Test
    void getFirstAndGetLastDoNotRemove() {
        fill(1, 2, 3);
        assertEquals(1, list.getFirst());
        assertEquals(3, list.getLast());
        assertEquals(3, list.size());
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void getFirstAndGetLastSingleElement() {
        fill(9);
        assertEquals(9, list.getFirst());
        assertEquals(9, list.getLast());
    }

    // ---------- contains ----------

    @Test
    void containsFindsAllElements() {
        fill(1, 2, 3);
        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
    }

    @Test
    void containsReturnsFalseIfMissing() {
        fill(1, 2, 3);
        assertFalse(list.contains(4));
    }

    @Test
    void containsReturnsFalseAfterRemoval() {
        fill(1, 2, 3);
        list.remove(1);
        assertFalse(list.contains(2));
    }

    @Test
    void containsNullThrows() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> list.contains(null));
        assertEquals("element not valid for search", ex.getMessage());
    }

    // ---------- get(index) ----------

    @Test
    void getReturnsCorrectElements() {
        fill(10, 20, 30, 40);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
        assertEquals(40, list.get(3));
        assertEquals(4, list.size());
    }

    @Test
    void getInvalidIndexReturnsNull() {
        fill(1, 2, 3);
        assertNull(list.get(-1));
        assertNull(list.get(3));
        assertNull(list.get(100));
        assertEquals(3, list.size());
    }

    @Test
    void getSingleElement() {
        fill(7);
        assertEquals(7, list.get(0));
        assertNull(list.get(1));
    }

    // ---------- remove(index) ----------

    @Test
    void removeMiddle() {
        fill(1, 2, 3, 4);
        assertEquals(3, list.remove(2));
        assertEquals("[1, 2, 4]", list.toString());
        assertEquals(3, list.size());
    }

    @Test
    void removeSecondElement() {
        fill(1, 2, 3, 4);
        assertEquals(2, list.remove(1));
        assertEquals("[1, 3, 4]", list.toString());
    }

    @Test
    void removeFirstIndex() {
        fill(1, 2, 3);
        assertEquals(1, list.remove(0));
        assertEquals("[2, 3]", list.toString());
        assertEquals(2, list.getFirst());
    }

    @Test
    void removeLastIndexUpdatesTail() {
        fill(1, 2, 3);
        assertEquals(3, list.remove(2));
        assertEquals(2, list.getLast());
        list.addLast(5);
        assertEquals("[1, 2, 5]", list.toString());
    }

    @Test
    void removeOnlyElement() {
        fill(1);
        assertEquals(1, list.remove(0));
        assertEquals(0, list.size());
        assertEquals("[empty list]", list.toString());
    }

    @Test
    void removeInvalidIndexReturnsNullAndListUnchanged() {
        fill(1, 2, 3);
        assertNull(list.remove(-1));
        assertNull(list.remove(3));
        assertEquals(3, list.size());
        assertEquals("[1, 2, 3]", list.toString());
    }

    // ---------- toArray ----------

    @Test
    void toArrayContainsAllElementsInOrder() {
        fill(1, 2, 3);
        assertArrayEquals(new Object[]{1, 2, 3}, list.toArray());
    }

    @Test
    void toArrayLengthMatchesSize() {
        fill(5, 6, 7, 8);
        assertEquals(list.size(), list.toArray().length);
    }

    @Test
    void toArrayReflectsRemovals() {
        fill(1, 2, 3);
        list.removeFirst();
        assertArrayEquals(new Object[]{2, 3}, list.toArray());
    }

    // ---------- toString ----------

    @Test
    void toStringFormat() {
        fill(25, 43, 48, 60);
        assertEquals("[25, 43, 48, 60]", list.toString());
    }

    @Test
    void toStringSingleElementHasNoComma() {
        fill(1);
        assertEquals("[1]", list.toString());
    }

    @Test
    void toStringEmptyAfterRemovingAll() {
        fill(1, 2);
        list.removeFirst();
        list.removeFirst();
        assertEquals("[empty list]", list.toString());
    }

    // ---------- other types ----------

    @Test
    void worksWithStrings() {
        MyLinkedList<String> strings = new MyLinkedList<>();
        strings.addSorted("c");
        strings.addSorted("a");
        strings.addSorted("b");
        assertEquals("[a, b, c]", strings.toString());

        strings.sortDES();
        assertEquals("[c, b, a]", strings.toString());
        assertTrue(strings.contains("b"));
    }

    // ---------- addSorted on unsorted list ----------

    @Test
    void addSortedOnUnsortedListSortsFirst() {
        fill(30, 10, 20);
        list.addSorted(15);
        assertEquals("[10, 15, 20, 30]", list.toString());
        assertEquals(10, list.getFirst());
        assertEquals(30, list.getLast());
        assertEquals(4, list.size());
    }

    @Test
    void addSortedAfterSortDES() {
        fill(1, 2, 3);
        list.sortDES();
        list.addSorted(4);
        assertEquals("[1, 2, 3, 4]", list.toString());
        assertEquals(4, list.getLast());
    }

// ---------- sort edge cases ----------

    @Test
    void sortOnEmptyListDoesNothing() {
        list.sortASC();
        list.sortDES();
        assertEquals(0, list.size());
        assertEquals("[empty list]", list.toString());
    }

    @Test
    void sortASCTailUsableAfterwards() {
        fill(3, 1, 2);
        list.sortASC();
        list.addLast(4);
        assertEquals("[1, 2, 3, 4]", list.toString());
    }

    @Test
    void sortDESTailUsableAfterwards() {
        fill(1, 3, 2);
        list.sortDES();
        list.addLast(0);
        assertEquals("[3, 2, 1, 0]", list.toString());
    }

    @Test
    void sortASCLargeListOddLength() {
        java.util.Random rnd = new java.util.Random(42);
        int[] expected = new int[1001];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = rnd.nextInt(500);     // with duplicates
            list.addLast(expected[i]);
        }
        java.util.Arrays.sort(expected);
        list.sortASC();

        Object[] actual = list.toArray();
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
        assertEquals(expected[expected.length - 1], list.getLast());
    }
}