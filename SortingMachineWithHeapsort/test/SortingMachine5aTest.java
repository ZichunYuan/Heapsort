import java.util.Comparator;

import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Customized JUnit test fixture for {@code SortingMachine5a}.
 *
 * @author Jimmy Yuan
 * @author Jackson Jiang
 */
public final class SortingMachine5aTest extends SortingMachineTest {

    @Override
    protected SortingMachine<String> constructorTest(Comparator<String> order) {
        return new SortingMachine5a<String>(order);
    }

    @Override
    protected SortingMachine<String> constructorRef(Comparator<String> order) {
        return new SortingMachine1L<String>(order);
    }

}
