package io.hyperbola.algo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import io.hyperbola.base.Assignment;
import io.hyperbola.base.Board;
import io.hyperbola.base.Variable;

/**
 * A node must: (1) Know its successor so that a tree can be built. (2) Hold the unassigned variable-domain map. (3)
 * Know the assignment from its successor to itself so that an assignment-tree is maintained. (4) Know how to assign
 * itself with a given assignment. (5) Prevent itself from being generated if some rules are violated e.g. forward
 * checking.
 *
 * A node need not: (1) Know how to assign itself with a given expanding algorithm, this is the task of the {@link
 * Expander}. (2) Determine the order of its expanding offspring, this is still the task of {@link Expander}; (3)
 * Determine the order in which its offspring are inserted into the stack. This is the task of {@link PuzzleIterator}
 */
public interface Node {

    /**
     * Given an assignment, the node must return back a new node.
     */
    Node expand(Assignment assignment);

    /**
     * Queries all assignments in the order from the root to this node.
     */
    default List<Assignment> getAllAssignments() {
        LinkedList<Assignment> records = new LinkedList<>();
        Node n = this;
        while (n != null) {
            records.add(0, n.getAssignment());
            n = n.getSuccessor();
        }
        return new ArrayList<>(records);
    }

    /**
     * Queries the word that is assigned to a given variable
     * @return the word assigned to the variable; or null if such variable is unassigned.
     */
    default String getAssigned(Variable variable) {
        Node n = this;
        Assignment a;
        while (n != null) {
            a = n.getAssignment();
            if (a.variable == variable) {
                // Found
                return a.word;
            }
            n = n.getSuccessor();
        }
        return null;
    }

    /**
     * Queries the assignment between this node and its successor.
     */
    Assignment getAssignment();

    /**
     * Queries the current board status.
     */
    Board getBoard();

    /**
     * Queries the successor of this node
     * @return the successor; or null if this node is a root node
     */
    Node getSuccessor();

    /**
     * Queries if this node represents a solution, that is, if all variables are assigned.
     * @return true it this node represents a solution
     */
    boolean isSolution();
}
