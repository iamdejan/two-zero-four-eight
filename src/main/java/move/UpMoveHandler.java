package move;

import board.Board;

import java.util.LinkedList;

public class UpMoveHandler implements MoveHandler {

    @Override
    public void move(final int[][] grid) {
        for (int c = 0; c < grid.length; c++) {
            final LinkedList<Integer> queue = new LinkedList<>();
            for (int r = 0; r < grid.length; r++) {
                if (grid[r][c] == Board.EMPTY_CELL_VALUE) {
                    continue;
                }

                if (queue.isEmpty() || queue.getLast() != grid[r][c]) {
                    queue.add(grid[r][c]);
                } else {
                    queue.add(queue.removeLast() + grid[r][c]);
                }
            }
            while (queue.size() < grid.length) {
                queue.addLast(Board.EMPTY_CELL_VALUE);
            }

            for (int r = 0; r < grid.length; r++) {
                grid[r][c] = queue.removeFirst();
            }
        }
    }
}
