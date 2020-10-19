import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 *
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 *
 * kernel methods.
 *
 * @author Jimmy Yuan
 * @author Jackson Jiang
 *
 */

public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     *
     * implementation under test and returns the result.
     *
     * @param order
     *
     *            the {@code Comparator} defining the order for {@code String}
     *
     * @return the new {@code SortingMachine}
     *
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     *
     * @ensures constructorTest = (true, order, {})
     *
     */

    protected abstract SortingMachine<String> constructorTest(

            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     *
     * reference implementation and returns the result.
     *
     * @param order
     *
     *            the {@code Comparator} defining the order for {@code String}
     *
     * @return the new {@code SortingMachine}
     *
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     *
     * @ensures constructorRef = (true, order, {})
     *
     */

    protected abstract SortingMachine<String> constructorRef(

            Comparator<String> order);

    /**
     * Creates and returns a {@code SortingMachine<String>} of the
     *
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *
     *            the {@code Comparator} defining the order for {@code String}
     *
     * @param insertionMode
     *
     *            flag indicating the machine mode
     *
     * @param args
     *
     *            the entries for the {@code SortingMachine}
     *
     * @return the constructed {@code SortingMachine}
     *
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     *
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     *
     */

    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;

    }

    /**
     * Creates and returns a {@code SortingMachine<String>} of the reference
     *
     * implementation type with the given entries and mode.
     *
     * @param order
     *
     *            the {@code Comparator} defining the order for {@code String}
     *
     * @param insertionMode
     *
     *            flag indicating the machine mode
     *
     * @param args
     *
     *            the entries for the {@code SortingMachine}
     *
     * @return the constructed {@code SortingMachine}
     *
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     *
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     *
     */

    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Comparator<String> implementation to be used in all test cases. Compare
     *
     * {@code String}s in lexicographic order.
     *
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }

    /**
     *
     * Comparator instance to be used in all test cases.
     *
     */

    private static final StringLT ORDER = new StringLT();

    /*
     *
     * Test cases for constructor.
     *
     */
    @Test
    public final void testConstructor() {

        SortingMachine<String> m = this.constructorTest(ORDER);

        SortingMachine<String> mExpected = this.constructorRef(ORDER);

        assertEquals(mExpected, m);
    }

    /*
     *
     * Test cases for add.
     *
     */
    @Test
    public final void testAddEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");

        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddOne() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "b");

        m.add("b");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMoreThanOne() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "b");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "b", "c");

        m.add("c");
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for changeToExtractionMode
     */
    @Test
    public final void testChangeToExtractionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true);

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionMode() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "b");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "b");

        m.changeToExtractionMode();
        assertEquals(mExpected, m);

    }
    /*
     * Test cases for removeFirst
     */

    @Test
    public final void testRemoveFirstEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        String result = m.removeFirst();

        assertEquals(m, mExpected);
        assertEquals(result, "green");
    }

    @Test
    public final void testRemoveFirstOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                ("green"));

        String result = m.removeFirst();

        assertEquals(m, mExpected);
        assertEquals(result, "blue");
    }

    @Test
    public final void testRemoveFirstMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "hello", "blue", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "hello");

        String result = m.removeFirst();

        assertEquals(m, mExpected);
        assertEquals(result, "blue");
    }

    @Test
    public final void testRemoveFirstLeavingEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "b");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "b");

        String x = m.removeFirst();
        String xExpected = mExpected.removeFirst();

        assertEquals(mExpected, m);
        assertEquals(xExpected, x);
    }

    @Test
    public final void testRemoveFirstLeavingOne() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "b", "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "b", "a");

        String x = m.removeFirst();
        String xExpected = mExpected.removeFirst();

        assertEquals(mExpected, m);
        assertEquals(xExpected, x);
    }

    @Test
    public final void testRemoveFirstLeavingMoreThanOne() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "b", "a", "c");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "b", "a", "c");

        String x = m.removeFirst();
        String xExpected = mExpected.removeFirst();

        assertEquals(mExpected, m);
        assertEquals(xExpected, x);
    }

    /*
     * Test cases for Order
     */
    @Test
    public final void testOrderInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi",
                "hello", "blue", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "hi", "hello", "blue", "green");

        assertEquals(mExpected.order(), m.order());
        assertEquals(m, mExpected);
    }

    @Test
    public final void testOrderExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi",
                "hello", "blue", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi", "hello", "blue", "green");

        assertEquals(mExpected.order(), m.order());
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for size
     */

    @Test
    public final void testSizeEmptyInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        int size = m.size();
        int sizeExpected = mExpected.size();

        assertEquals(size, sizeExpected);
        assertEquals(m, mExpected);
    }

    @Test
    public final void testSizeOneInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "hi");

        int size = m.size();
        int sizeExpected = mExpected.size();

        assertEquals(size, sizeExpected);
        assertEquals(m, mExpected);
    }

    @Test
    public final void testSizeMultipleInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi",
                "hello", "blue", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "hi", "hello", "blue", "green");

        int size = m.size();
        int sizeExpected = mExpected.size();

        assertEquals(size, sizeExpected);
        assertEquals(m, mExpected);
    }

    @Test
    public final void testSizeEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        int size = m.size();
        int sizeExpected = mExpected.size();

        assertEquals(size, sizeExpected);
        assertEquals(m, mExpected);
    }

    @Test
    public final void testSizeOneExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi");

        int size = m.size();
        int sizeExpected = mExpected.size();

        assertEquals(size, sizeExpected);
        assertEquals(m, mExpected);
    }

    @Test
    public final void testSizeMultipleExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi",
                "hello", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi", "hello", "blue");

        int size = m.size();
        int sizeExpected = mExpected.size();

        assertEquals(size, sizeExpected);
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for isInInsertionMode
     */

    @Test
    public final void testIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A");

        assertEquals(m.isInInsertionMode(), mExpected.isInInsertionMode());
        assertEquals(m, mExpected);
    }

    @Test
    public final void testIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "A");

        assertEquals(m.isInInsertionMode(), mExpected.isInInsertionMode());
        assertEquals(m, mExpected);
    }

}
