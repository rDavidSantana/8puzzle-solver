import java.io.*;
import java.util.*;

class Board implements Ilayout, Cloneable {

        private static final int dim=3;
        private int board[][];

        public Board(){ board = new int[dim][dim];}

        public Board(String str) throws IllegalStateException {
            if (str.length() != dim*dim) throw new
                    IllegalStateException("Invalid arg in Board constructor");
            board = new int[dim][dim];
            int si=0;
            for(int i=0; i<dim; i++)
                for(int j=0; j<dim; j++)
                    board[i][j] = Character.getNumericValue(str.charAt(si++));
        }

        public Board(int[][] b) {
            board = new int[dim][dim];
            for(int i=0; i<dim; i++)
                for(int j=0; j<dim; j++)
                    board[i][j] = b[i][j];
        }

        @Override
        public String toString() {
            // Cada linha termina com '\n'. O 0 deve ser impresso como espaço ' '.
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    int v = board[i][j];
                    pw.print(v == 0 ? ' ' : (char) ('0' + v));
                }
                pw.println();
            }
            pw.flush();
            return writer.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || this.getClass() != o.getClass()) return false;
            Board other = (Board) o;
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    if (this.board[i][j] != other.board[i][j]) return false;
            return true;
        }

        @Override
        public int hashCode() {
            // Hash simples e consistente com equals
            int h = 17;
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    h = 31 * h + board[i][j];
            return h;
        }

        @Override
        public List<Ilayout> children() {
            // Gera os 4 movimentos possíveis do espaço (0): cima/baixo/esq/dir (se existirem)
            int zi = -1, zj = -1;
            outer:
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    if (board[i][j] == 0) {
                        zi = i; zj = j; break outer;
                    }

            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}}; // up, down, left, right
            List<Ilayout> res = new ArrayList<>(4);
            for (int[] d : dirs) {
                int ni = zi + d[0], nj = zj + d[1];
                if (ni >= 0 && ni < dim && nj >= 0 && nj < dim) {
                    int[][] copy = copyBoard();
                    // swap 0 with neighbor
                    copy[zi][zj] = copy[ni][nj];
                    copy[ni][nj] = 0;
                    res.add(new Board(copy));
                }
            }
            return res;
        }

        @Override
        public boolean isGoal(Ilayout l) {
            if (!(l instanceof Board)) return false;
            return this.equals(l);
        }

        @Override
        public int getG() {
            // Custo unitário por movimento
            return 1;
        }

        private int[][] copyBoard() {
            int[][] c = new int[dim][dim];
            for (int i = 0; i < dim; i++) System.arraycopy(board[i], 0, c[i], 0, dim);
            return c;
        }
}
