import java.util.*;

class Solution {

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = -1, startC = -1;
        int litterCount = 0;

        // Assign a bit to every litter cell
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        // All litter collected
        int targetMask = (1 << litterCount) - 1;

        if (targetMask == 0) {
            return 0;
        }

        /*
         * visited[r][c][mask][energy]
         *
         * Energy ranges from 0 to energy.
         */
        boolean[][][][] visited =
                new boolean[m][n][1 << litterCount][energy + 1];

        Queue<State> queue = new ArrayDeque<>();

        visited[startR][startC][0][energy] = true;
        queue.offer(new State(startR, startC, 0, energy, 0));

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            State cur = queue.poll();

            int r = cur.r;
            int c = cur.c;
            int mask = cur.mask;
            int e = cur.energy;
            int moves = cur.moves;

            if (mask == targetMask) {
                return moves;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Cannot make a move without energy
                if (e == 0) {
                    continue;
                }

                int newEnergy = e - 1;
                int newMask = mask;

                // Collect litter
                if (classroom[nr].charAt(nc) == 'L') {
                    int id = litterId[nr][nc];
                    newMask |= (1 << id);
                }

                // Reset energy on R
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                if (!visited[nr][nc][newMask][newEnergy]) {
                    visited[nr][nc][newMask][newEnergy] = true;

                    queue.offer(
                        new State(
                            nr,
                            nc,
                            newMask,
                            newEnergy,
                            moves + 1
                        )
                    );
                }
            }
        }

        return -1;
    }

    static class State {
        int r;
        int c;
        int mask;
        int energy;
        int moves;

        State(int r, int c, int mask, int energy, int moves) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.moves = moves;
        }
    }
}