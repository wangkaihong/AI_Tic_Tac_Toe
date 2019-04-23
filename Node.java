import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Created by wangkaihong on 2019/4/21.
 */
public class Node {
    public List<positionTicTacToe> board;
    public ArrayList<Node> child;
    public int player;
    private static List<List<positionTicTacToe>> winningLines = new ArrayList<>();
    public positionTicTacToe move;

    public Node(List<positionTicTacToe> board, int player) {
        winningLines = initializeWinningLines();
        this.board = board;
        child = new ArrayList<>();
        this.player = player;
    }

    public void generate_child() {
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                for(int k=0;k<4;k++)
                {
                    int index = i*16+j*4+k;
                    if(board.get(index).state == 0) {
                        List<positionTicTacToe> new_board = deepCopyATicTacToeBoard(board);
                        new_board.get(index).state = player;
                        child.add(new Node(new_board,3-player));
                        child.get(child.size() - 1).move = new positionTicTacToe(i,j,k);
                    }
                }
        Collections.shuffle(child);
    }
    public static int isEnded(List<positionTicTacToe> board)
    {
        //test whether the current game is ended

        //brute-force
        for(int i=0;i<winningLines.size();i++)
        {

            positionTicTacToe p0 = winningLines.get(i).get(0);
            positionTicTacToe p1 = winningLines.get(i).get(1);
            positionTicTacToe p2 = winningLines.get(i).get(2);
            positionTicTacToe p3 = winningLines.get(i).get(3);

            int state0 = getStateOfPositionFromBoard(p0,board);
            int state1 = getStateOfPositionFromBoard(p1,board);
            int state2 = getStateOfPositionFromBoard(p2,board);
            int state3 = getStateOfPositionFromBoard(p3,board);

            //if they have the same state (marked by same player) and they are not all marked.
            if(state0 == state1 && state1 == state2 && state2 == state3 && state0!=0)
            {
                //someone wins
                p0.state = state0;
                p1.state = state1;
                p2.state = state2;
                p3.state = state3;
                return state0;
            }
        }
        for(int i=0;i<board.size();i++)
        {
            if(board.get(i).state==0)
            {
                //game is not ended, continue
                return 0;
            }
        }
        return -1; //call it a draw
    }
    private List<List<positionTicTacToe>> initializeWinningLines() {
        //create a list of winning line so that the game will "brute-force" check if a player satisfied any 	winning condition(s).
        List<List<positionTicTacToe>> winningLines = new ArrayList<List<positionTicTacToe>>();

        //48 straight winning lines
        //z axis winning lines
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
                oneWinCondtion.add(new positionTicTacToe(i, j, 0, -1));
                oneWinCondtion.add(new positionTicTacToe(i, j, 1, -1));
                oneWinCondtion.add(new positionTicTacToe(i, j, 2, -1));
                oneWinCondtion.add(new positionTicTacToe(i, j, 3, -1));
                winningLines.add(oneWinCondtion);
            }
        //y axis winning lines
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
                oneWinCondtion.add(new positionTicTacToe(i, 0, j, -1));
                oneWinCondtion.add(new positionTicTacToe(i, 1, j, -1));
                oneWinCondtion.add(new positionTicTacToe(i, 2, j, -1));
                oneWinCondtion.add(new positionTicTacToe(i, 3, j, -1));
                winningLines.add(oneWinCondtion);
            }
        //x axis winning lines
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
                oneWinCondtion.add(new positionTicTacToe(0, i, j, -1));
                oneWinCondtion.add(new positionTicTacToe(1, i, j, -1));
                oneWinCondtion.add(new positionTicTacToe(2, i, j, -1));
                oneWinCondtion.add(new positionTicTacToe(3, i, j, -1));
                winningLines.add(oneWinCondtion);
            }

        //12 main diagonal winning lines
        //xz plane-4
        for (int i = 0; i < 4; i++) {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0, i, 0, -1));
            oneWinCondtion.add(new positionTicTacToe(1, i, 1, -1));
            oneWinCondtion.add(new positionTicTacToe(2, i, 2, -1));
            oneWinCondtion.add(new positionTicTacToe(3, i, 3, -1));
            winningLines.add(oneWinCondtion);
        }
        //yz plane-4
        for (int i = 0; i < 4; i++) {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(i, 0, 0, -1));
            oneWinCondtion.add(new positionTicTacToe(i, 1, 1, -1));
            oneWinCondtion.add(new positionTicTacToe(i, 2, 2, -1));
            oneWinCondtion.add(new positionTicTacToe(i, 3, 3, -1));
            winningLines.add(oneWinCondtion);
        }
        //xy plane-4
        for (int i = 0; i < 4; i++) {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0, 0, i, -1));
            oneWinCondtion.add(new positionTicTacToe(1, 1, i, -1));
            oneWinCondtion.add(new positionTicTacToe(2, 2, i, -1));
            oneWinCondtion.add(new positionTicTacToe(3, 3, i, -1));
            winningLines.add(oneWinCondtion);
        }

        //12 anti diagonal winning lines
        //xz plane-4
        for (int i = 0; i < 4; i++) {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0, i, 3, -1));
            oneWinCondtion.add(new positionTicTacToe(1, i, 2, -1));
            oneWinCondtion.add(new positionTicTacToe(2, i, 1, -1));
            oneWinCondtion.add(new positionTicTacToe(3, i, 0, -1));
            winningLines.add(oneWinCondtion);
        }
        //yz plane-4
        for (int i = 0; i < 4; i++) {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(i, 0, 3, -1));
            oneWinCondtion.add(new positionTicTacToe(i, 1, 2, -1));
            oneWinCondtion.add(new positionTicTacToe(i, 2, 1, -1));
            oneWinCondtion.add(new positionTicTacToe(i, 3, 0, -1));
            winningLines.add(oneWinCondtion);
        }
        //xy plane-4
        for (int i = 0; i < 4; i++) {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0, 3, i, -1));
            oneWinCondtion.add(new positionTicTacToe(1, 2, i, -1));
            oneWinCondtion.add(new positionTicTacToe(2, 1, i, -1));
            oneWinCondtion.add(new positionTicTacToe(3, 0, i, -1));
            winningLines.add(oneWinCondtion);
        }

        //4 additional diagonal winning lines
        List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(0, 0, 0, -1));
        oneWinCondtion.add(new positionTicTacToe(1, 1, 1, -1));
        oneWinCondtion.add(new positionTicTacToe(2, 2, 2, -1));
        oneWinCondtion.add(new positionTicTacToe(3, 3, 3, -1));
        winningLines.add(oneWinCondtion);

        oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(0, 0, 3, -1));
        oneWinCondtion.add(new positionTicTacToe(1, 1, 2, -1));
        oneWinCondtion.add(new positionTicTacToe(2, 2, 1, -1));
        oneWinCondtion.add(new positionTicTacToe(3, 3, 0, -1));
        winningLines.add(oneWinCondtion);

        oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(3, 0, 0, -1));
        oneWinCondtion.add(new positionTicTacToe(2, 1, 1, -1));
        oneWinCondtion.add(new positionTicTacToe(1, 2, 2, -1));
        oneWinCondtion.add(new positionTicTacToe(0, 3, 3, -1));
        winningLines.add(oneWinCondtion);

        oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(0, 3, 0, -1));
        oneWinCondtion.add(new positionTicTacToe(1, 2, 1, -1));
        oneWinCondtion.add(new positionTicTacToe(2, 1, 2, -1));
        oneWinCondtion.add(new positionTicTacToe(3, 0, 3, -1));
        winningLines.add(oneWinCondtion);

        return winningLines;

    }

    private List<positionTicTacToe> deepCopyATicTacToeBoard(List<positionTicTacToe> board) {
        //deep copy of game boards
        List<positionTicTacToe> copiedBoard = new ArrayList<positionTicTacToe>();
        for (int i = 0; i < board.size(); i++) {
            copiedBoard.add(new positionTicTacToe(board.get(i).x, board.get(i).y, board.get(i).z, board.get(i).state));
        }
        return copiedBoard;
    }

    public static int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> targetBoard) {
        //a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
        int index = position.x * 16 + position.y * 4 + position.z;
        return targetBoard.get(index).state;
    }

    public double heuristic(int player) {
        int counter_self = 0;
        int counter_other = 0;
        for(int i=0;i<winningLines.size();i++)
        {
            positionTicTacToe p0 = winningLines.get(i).get(0);
            positionTicTacToe p1 = winningLines.get(i).get(1);
            positionTicTacToe p2 = winningLines.get(i).get(2);
            positionTicTacToe p3 = winningLines.get(i).get(3);

            int state0 = getStateOfPositionFromBoard(p0,board);
            int state1 = getStateOfPositionFromBoard(p1,board);
            int state2 = getStateOfPositionFromBoard(p2,board);
            int state3 = getStateOfPositionFromBoard(p3,board);

            if(state0 != (3 - player) && state1 != (3 - player) && state2 != (3 - player) && state3 != (3 - player))
            {
                counter_self += 1;
            }
            if(state0 != player && state1 != player && state2 != player && state3 != player)
            {
                counter_other += 1;
            }
        }
        return counter_self - counter_other;

    }
}
