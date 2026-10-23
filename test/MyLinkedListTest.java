import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    private MyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    // helper to fill list
    private void fill(int... values) {
        for (int v : values) {
            list.addLast(v);
        }
    }

    // ---------- empty list ----------

    @Test
    void newListIsEmpty() {
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
        assertEquals(0, list.toArray().length);
    }

    // ---------- addFirst ----------

    @Test
    void addFirstOnEmptyList() {
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
    void addFirstNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.addFirst(null));
        assertEquals(0, list.size());
    }

    // ---------- addLast ----------

    @Test
    void addLastOnEmptyList() {
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
    void addLastNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.addLast(null));
        assertEquals(0, list.size());
    }

    @Test
    void mixedAddFirstAndAddLast() {
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
        assertEquals(5, list.getLast());
    }

    @Test
    void addSortedFrontMiddleEnd() {
        list.addSorted(43);
        list.addSorted(25);  // vorne
        list.addSorted(60);  // hinten
        list.addSorted(48);  // Mitte
        assertEquals("[25, 43, 48, 60]", list.toString());
        assertEquals(25, list.getFirst());
        assertEquals(60, list.getLast());
        assertEquals(4, list.size());
    }

    @Test
    void addSortedWithDuplicates() {
        list.addSorted(2);
        list.addSorted(1);
        list.addSorted(2);
        list.addSorted(1);
        assertEquals("[1, 1, 2, 2]", list.toString());
        assertEquals(4, list.size());
    }

    @Test
    void addSortedNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.addSorted(null));
    }

    // ---------- sortASC / sortDES ----------

    @Test
    void sortASCSortsList() {
        fill(48, 25, 60, 43);
        list.sortASC();
        assertEquals("[25, 43, 48, 60]", list.toString());
        assertEquals(25, list.getFirst());
        assertEquals(60, list.getLast());
    }

    @Test
    void sortASCWithDuplicates() {
        fill(3, 1, 3, 2, 1);
        list.sortASC();
        assertEquals("[1, 1, 2, 3, 3]", list.toString());
    }

    @Test
    void sortASCSingleElement() {
        fill(7);
        list.sortASC();
        assertEquals("[7]", list.toString());
    }

    @Test
    void sortASCEmptyListThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.sortASC());
    }

    @Test
    void sortDESSortsList() {
        fill(48, 25, 60, 43);
        list.sortDES();
        assertEquals("[60, 48, 43, 25]", list.toString());
        assertEquals(60, list.getFirst());
        assertEquals(25, list.getLast());
    }

    @Test
    void sortDESWithDuplicates() {
        fill(3, 1, 3, 2, 1);
        list.sortDES();
        assertEquals("[3, 3, 2, 1, 1]", list.toString());
    }

    @Test
    void sortDESEmptyListThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.sortDES());
    }

    // ---------- clear ----------

    @Test
    void clearEmptiesList() {
        fill(1, 2, 3);
        list.clear();
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
        // nach clear muss die Liste wieder normal benutzbar sein
        list.addLast(4);
        assertEquals("[4]", list.toString());
    }

    // ---------- removeFirst ----------

    @Test
    void removeFirstReturnsAndRemoves() {
        fill(1, 2, 3);
        assertEquals(1, list.removeFirst());
        assertEquals("[2, 3]", list.toString());
        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());
    }

    @Test
    void removeFirstLastRemainingElement() {
        fill(1);
        assertEquals(1, list.removeFirst());
        assertEquals(0, list.size());
        // head und tail müssen zurückgesetzt sein
        list.addLast(2);
        assertEquals("[2]", list.toString());
    }

    @Test
    void removeFirstOnEmptyListReturnsNull() {
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
        list.addLast(4);   // muss an den neuen tail (2) angehängt werden
        assertEquals("[1, 2, 4]", list.toString());
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
    void removeLastOnEmptyListReturnsNull() {
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
    }

    @Test
    void getFirstOnEmptyListReturnsNull() {
        assertNull(list.getFirst());
        assertEquals(0, list.size());
    }

    @Test
    void getLastOnEmptyListReturnsNull() {
        assertNull(list.getLast());
        assertEquals(0, list.size());
    }

    // ---------- contains ----------

    @Test
    void containsFindsElements() {
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
    void containsOnEmptyList() {
        assertFalse(list.contains(1));
    }

    @Test
    void containsNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.contains(null));
    }

    // ---------- get ----------

    @Test
    void getReturnsCorrectElements() {
        fill(10, 20, 30, 40);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
        assertEquals(40, list.get(3));
        assertEquals(4, list.size()); // nichts entfernt
    }

    @Test
    void getInvalidIndexThrows() {
        fill(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> list.get(-1));
        assertThrows(IllegalArgumentException.class, () -> list.get(3));
    }

    @Test
    void getOnEmptyListThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.get(0));
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
    void removeFirstIndex() {
        fill(1, 2, 3);
        assertEquals(1, list.remove(0));
        assertEquals("[2, 3]", list.toString());
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
    void removeInvalidIndexThrows() {
        fill(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> list.remove(-1));
        assertThrows(IllegalArgumentException.class, () -> list.remove(3));
        assertEquals(3, list.size());
    }

    @Test
    void removeOnEmptyListThrows() {
        assertThrows(IllegalArgumentException.class, () -> list.remove(0));
    }

    // ---------- toArray ----------

    @Test
    void toArrayContainsAllElementsInOrder() {
        fill(1, 2, 3);
        assertArrayEquals(new Object[]{1, 2, 3}, list.toArray());
    }

    // ---------- toString ----------

    @Test
    void toStringFormat() {
        fill(25, 43, 48, 60);
        assertEquals("[25, 43, 48, 60]", list.toString());
    }

    @Test
    void toStringWithStrings() {
        // die Liste muss mit jedem Comparable-Typ funktionieren
        MyLinkedList<String> strings = new MyLinkedList<>();
        strings.addSorted("c");
        strings.addSorted("a");
        strings.addSorted("b");
        assertEquals("[a, b, c]", strings.toString());
    }
}