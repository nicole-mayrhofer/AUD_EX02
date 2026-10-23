import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class EmptyList {

        @Test
        void sizeIsZero() {
            assertEquals(0, list.size());
        }

        @Test
        void toStringShowsEmptyList() {
            assertEquals("[empty list]", list.toString());
        }

        @Test
        void toArrayIsEmpty() {
            assertEquals(0, list.toArray().length);
        }

        @Test
        void getFirstAndGetLastReturnNull() {
            assertNull(list.getFirst());
            assertNull(list.getLast());
        }

        @Test
        void removeFirstAndRemoveLastReturnNull() {
            assertNull(list.removeFirst());
            assertNull(list.removeLast());
            assertEquals(0, list.size());
        }

        @Test
        void getAndRemoveByIndexReturnNull() {
            assertNull(list.get(0));
            assertNull(list.remove(0));
            assertEquals(0, list.size());
        }

        @Test
        void containsReturnsFalse() {
            assertFalse(list.contains(1));
        }

        @Test
        void clearOnEmptyListDoesNothing() {
            list.clear();
            assertEquals(0, list.size());
            assertEquals("[empty list]", list.toString());
        }
    }

    // ---------- addFirst ----------

    @Nested
    class AddFirst {

        @Test
        void onEmptyListSetsHeadAndTail() {
            list.addFirst(5);
            assertEquals(1, list.size());
            assertEquals(5, list.getFirst());
            assertEquals(5, list.getLast());
        }

        @Test
        void addsInFront() {
            list.addFirst(3);
            list.addFirst(2);
            list.addFirst(1);
            assertEquals("[1, 2, 3]", list.toString());
            assertEquals(1, list.getFirst());
            assertEquals(3, list.getLast());
            assertEquals(3, list.size());
        }

        @Test
        void nullThrowsAndListUnchanged() {
            fill(1, 2);
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> list.addFirst(null));
            assertEquals("adding element failed, no data available", ex.getMessage());
            assertEquals(2, list.size());
            assertEquals("[1, 2]", list.toString());
        }
    }

    // ---------- addLast ----------

    @Nested
    class AddLast {

        @Test
        void onEmptyListSetsHeadAndTail() {
            list.addLast(5);
            assertEquals(1, list.size());
            assertEquals(5, list.getFirst());
            assertEquals(5, list.getLast());
        }

        @Test
        void addsAtEnd() {
            fill(1, 2, 3);
            assertEquals("[1, 2, 3]", list.toString());
            assertEquals(1, list.getFirst());
            assertEquals(3, list.getLast());
            assertEquals(3, list.size());
        }

        @Test
        void nullThrowsAndListUnchanged() {
            fill(1, 2);
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> list.addLast(null));
            assertEquals("adding element failed, no data available", ex.getMessage());
            assertEquals(2, list.size());
            assertEquals("[1, 2]", list.toString());
        }

        @Test
        void mixedWithAddFirst() {
            list.addLast(2);
            list.addFirst(1);
            list.addLast(3);
            assertEquals("[1, 2, 3]", list.toString());
            assertEquals(3, list.size());
        }
    }

    // ---------- addSorted ----------

    @Nested
    class AddSorted {

        @Test
        void onEmptyList() {
            list.addSorted(5);
            assertEquals("[5]", list.toString());
            assertEquals(5, list.getFirst());
            assertEquals(5, list.getLast());
            assertEquals(1, list.size());
        }

        @Test
        void insertsFrontMiddleEnd() {
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
        void insertsAtEveryMiddlePosition() {
            fill(10, 20, 30, 40);
            list.addSorted(15);
            list.addSorted(25);
            list.addSorted(35);
            assertEquals("[10, 15, 20, 25, 30, 35, 40]", list.toString());
            assertEquals(7, list.size());
        }

        @Test
        void handlesDuplicates() {
            list.addSorted(2);
            list.addSorted(1);
            list.addSorted(2);
            list.addSorted(1);
            assertEquals("[1, 1, 2, 2]", list.toString());
            assertEquals(4, list.size());
        }

        @Test
        void equalToHeadAndTail() {
            fill(1, 5);
            list.addSorted(1);
            list.addSorted(5);
            assertEquals("[1, 1, 5, 5]", list.toString());
        }

        @Test
        void tailStaysCorrectAfterInsertAtEnd() {
            list.addSorted(1);
            list.addSorted(2);
            list.addLast(3);
            assertEquals(3, list.getLast());
            assertEquals("[1, 2, 3]", list.toString());
        }

        @Test
        void worksAfterSortASC() {
            fill(30, 10, 20);
            list.sortASC();
            list.addSorted(15);
            assertEquals("[10, 15, 20, 30]", list.toString());
        }

        @Test
        void nullThrowsAndListUnchanged() {
            fill(1, 2);
            assertThrows(IllegalArgumentException.class, () -> list.addSorted(null));
            assertEquals(2, list.size());
        }
    }

    // ---------- sortASC ----------

    @Nested
    class SortASC {

        @Test
        void sortsList() {
            fill(48, 25, 60, 43);
            list.sortASC();
            assertEquals("[25, 43, 48, 60]", list.toString());
            assertEquals(25, list.getFirst());
            assertEquals(60, list.getLast());
            assertEquals(4, list.size());
        }

        @Test
        void withDuplicates() {
            fill(3, 1, 3, 2, 1);
            list.sortASC();
            assertEquals("[1, 1, 2, 3, 3]", list.toString());
        }

        @Test
        void alreadySorted() {
            fill(1, 2, 3);
            list.sortASC();
            assertEquals("[1, 2, 3]", list.toString());
        }

        @Test
        void reverseSorted() {
            fill(5, 4, 3, 2, 1);
            list.sortASC();
            assertEquals("[1, 2, 3, 4, 5]", list.toString());
        }

        @Test
        void singleElement() {
            fill(7);
            list.sortASC();
            assertEquals("[7]", list.toString());
        }

        @Test
        void emptyListThrows() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> list.sortASC());
            assertEquals("empty list", ex.getMessage());
        }
    }

    // ---------- sortDES ----------

    @Nested
    class SortDES {

        @Test
        void sortsList() {
            fill(48, 25, 60, 43);
            list.sortDES();
            assertEquals("[60, 48, 43, 25]", list.toString());
            assertEquals(60, list.getFirst());
            assertEquals(25, list.getLast());
            assertEquals(4, list.size());
        }

        @Test
        void withDuplicates() {
            fill(3, 1, 3, 2, 1);
            list.sortDES();
            assertEquals("[3, 3, 2, 1, 1]", list.toString());
        }

        @Test
        void alreadySortedAscending() {
            fill(1, 2, 3, 4, 5);
            list.sortDES();
            assertEquals("[5, 4, 3, 2, 1]", list.toString());
        }

        @Test
        void singleElement() {
            fill(7);
            list.sortDES();
            assertEquals("[7]", list.toString());
        }

        @Test
        void emptyListThrows() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> list.sortDES());
            assertEquals("empty list", ex.getMessage());
        }
    }

    // ---------- clear ----------

    @Nested
    class Clear {

        @Test
        void emptiesList() {
            fill(1, 2, 3);
            list.clear();
            assertEquals(0, list.size());
            assertEquals("[empty list]", list.toString());
            assertNull(list.getFirst());
            assertNull(list.getLast());
        }

        @Test
        void listUsableAfterClear() {
            fill(1, 2, 3);
            list.clear();
            list.addLast(4);
            list.addFirst(3);
            assertEquals("[3, 4]", list.toString());
            assertEquals(3, list.getFirst());
            assertEquals(4, list.getLast());
            assertEquals(2, list.size());
        }
    }

    // ---------- removeFirst ----------

    @Nested
    class RemoveFirst {

        @Test
        void returnsAndRemoves() {
            fill(1, 2, 3);
            assertEquals(1, list.removeFirst());
            assertEquals("[2, 3]", list.toString());
            assertEquals(2, list.size());
            assertEquals(2, list.getFirst());
            assertEquals(3, list.getLast());
        }

        @Test
        void lastRemainingElement() {
            fill(1);
            assertEquals(1, list.removeFirst());
            assertEquals(0, list.size());
            assertNull(list.getFirst());
            assertNull(list.getLast());
            list.addLast(2);
            assertEquals("[2]", list.toString());
        }

        @Test
        void untilEmpty() {
            fill(1, 2, 3);
            assertEquals(1, list.removeFirst());
            assertEquals(2, list.removeFirst());
            assertEquals(3, list.removeFirst());
            assertNull(list.removeFirst());
            assertEquals(0, list.size());
        }
    }

    // ---------- removeLast ----------

    @Nested
    class RemoveLast {

        @Test
        void returnsAndRemoves() {
            fill(1, 2, 3);
            assertEquals(3, list.removeLast());
            assertEquals("[1, 2]", list.toString());
            assertEquals(2, list.size());
            assertEquals(2, list.getLast());
        }

        @Test
        void updatesTail() {
            fill(1, 2, 3);
            list.removeLast();
            list.addLast(4);
            assertEquals("[1, 2, 4]", list.toString());
            assertEquals(4, list.getLast());
        }

        @Test
        void lastRemainingElement() {
            fill(1);
            assertEquals(1, list.removeLast());
            assertEquals(0, list.size());
            list.addFirst(2);
            assertEquals("[2]", list.toString());
            assertEquals(2, list.getLast());
        }

        @Test
        void untilEmpty() {
            fill(1, 2, 3);
            assertEquals(3, list.removeLast());
            assertEquals(2, list.removeLast());
            assertEquals(1, list.removeLast());
            assertNull(list.removeLast());
            assertEquals(0, list.size());
        }
    }

    // ---------- getFirst / getLast ----------

    @Nested
    class GetFirstGetLast {

        @Test
        void doNotRemove() {
            fill(1, 2, 3);
            assertEquals(1, list.getFirst());
            assertEquals(3, list.getLast());
            assertEquals(3, list.size());
            assertEquals("[1, 2, 3]", list.toString());
        }

        @Test
        void singleElementIsFirstAndLast() {
            fill(9);
            assertEquals(9, list.getFirst());
            assertEquals(9, list.getLast());
        }
    }

    // ---------- contains ----------

    @Nested
    class Contains {

        @Test
        void findsAllElements() {
            fill(1, 2, 3);
            assertTrue(list.contains(1));
            assertTrue(list.contains(2));
            assertTrue(list.contains(3));
        }

        @Test
        void returnsFalseIfMissing() {
            fill(1, 2, 3);
            assertFalse(list.contains(4));
        }

        @Test
        void returnsFalseAfterRemoval() {
            fill(1, 2, 3);
            list.remove(1);
            assertFalse(list.contains(2));
        }

        @Test
        void nullThrows() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> list.contains(null));
            assertEquals("element not valid for search", ex.getMessage());
        }
    }

    // ---------- get(index) ----------

    @Nested
    class GetByIndex {

        @Test
        void returnsCorrectElements() {
            fill(10, 20, 30, 40);
            assertEquals(10, list.get(0));
            assertEquals(20, list.get(1));
            assertEquals(30, list.get(2));
            assertEquals(40, list.get(3));
            assertEquals(4, list.size());
        }

        @Test
        void invalidIndexReturnsNull() {
            fill(1, 2, 3);
            assertNull(list.get(-1));
            assertNull(list.get(3));
            assertNull(list.get(100));
            assertEquals(3, list.size());
        }

        @Test
        void singleElement() {
            fill(7);
            assertEquals(7, list.get(0));
            assertNull(list.get(1));
        }
    }

    // ---------- remove(index) ----------

    @Nested
    class RemoveByIndex {

        @Test
        void removesMiddle() {
            fill(1, 2, 3, 4);
            assertEquals(3, list.remove(2));
            assertEquals("[1, 2, 4]", list.toString());
            assertEquals(3, list.size());
        }

        @Test
        void removesSecondElement() {
            fill(1, 2, 3, 4);
            assertEquals(2, list.remove(1));
            assertEquals("[1, 3, 4]", list.toString());
        }

        @Test
        void removesFirstIndex() {
            fill(1, 2, 3);
            assertEquals(1, list.remove(0));
            assertEquals("[2, 3]", list.toString());
            assertEquals(2, list.getFirst());
        }

        @Test
        void removesLastIndexAndUpdatesTail() {
            fill(1, 2, 3);
            assertEquals(3, list.remove(2));
            assertEquals(2, list.getLast());
            list.addLast(5);
            assertEquals("[1, 2, 5]", list.toString());
        }

        @Test
        void removesOnlyElement() {
            fill(1);
            assertEquals(1, list.remove(0));
            assertEquals(0, list.size());
            assertEquals("[empty list]", list.toString());
        }

        @Test
        void invalidIndexReturnsNullAndListUnchanged() {
            fill(1, 2, 3);
            assertNull(list.remove(-1));
            assertNull(list.remove(3));
            assertEquals(3, list.size());
            assertEquals("[1, 2, 3]", list.toString());
        }
    }

    // ---------- toArray ----------

    @Nested
    class ToArray {

        @Test
        void containsAllElementsInOrder() {
            fill(1, 2, 3);
            assertArrayEquals(new Object[]{1, 2, 3}, list.toArray());
        }

        @Test
        void lengthMatchesSize() {
            fill(5, 6, 7, 8);
            assertEquals(list.size(), list.toArray().length);
        }

        @Test
        void reflectsRemovals() {
            fill(1, 2, 3);
            list.removeFirst();
            assertArrayEquals(new Object[]{2, 3}, list.toArray());
        }
    }

    // ---------- toString ----------

    @Nested
    class ToString {

        @Test
        void format() {
            fill(25, 43, 48, 60);
            assertEquals("[25, 43, 48, 60]", list.toString());
        }

        @Test
        void singleElementHasNoComma() {
            fill(1);
            assertEquals("[1]", list.toString());
        }

        @Test
        void emptyAfterRemovingAll() {
            fill(1, 2);
            list.removeFirst();
            list.removeFirst();
            assertEquals("[empty list]", list.toString());
        }
    }

    // ---------- other types ----------

    @Nested
    class WithStrings {

        @Test
        void addSortedAndSort() {
            MyLinkedList<String> strings = new MyLinkedList<>();
            strings.addSorted("c");
            strings.addSorted("a");
            strings.addSorted("b");
            assertEquals("[a, b, c]", strings.toString());

            strings.sortDES();
            assertEquals("[c, b, a]", strings.toString());
            assertTrue(strings.contains("b"));
        }
    }
}