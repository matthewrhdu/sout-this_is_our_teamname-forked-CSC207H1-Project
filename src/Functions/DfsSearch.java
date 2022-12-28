package Functions;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * A Depth first search program
 *
 * === Attributes ===
 * path: the path for all TimeTablePuzzle items
 */
public class DfsSearch {
    /**
     * A Depth first search program
     *
     */
    ArrayList<TimeTablePuzzle> path;

    /**
     * Constructor. Initializes a DfsSearch object
     */
    public DfsSearch(){
        super();
    }

    /**
     * Solves the puzzle
     * @param puzzle The puzzle that needs to be solved
     * @param seen The set of all puzzle configurations that has been seen
     * @return An array of all the moves that led up to the solution.
     */
    public ArrayList<TimeTablePuzzle> solve(TimeTablePuzzle puzzle, Set<String> seen) {
        if (puzzle.isSolved()) {
            ArrayList<TimeTablePuzzle> path = new ArrayList<>();
            path.add(puzzle);
            return path;
        } else {
            for (TimeTablePuzzle next_move : puzzle.extensions()) {
                if (seen.contains(next_move.toString())) {
                    continue;
                }

                ArrayList<TimeTablePuzzle> solution = solve(next_move, seen);

                if (solution.size() != 0) {
                    solution.add(0, puzzle);
                    path.add(puzzle);
                    return path;
                }

                seen.add(next_move.toString());
            }
            return new ArrayList<>();
        }
    }
}
