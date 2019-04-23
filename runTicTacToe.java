import java.util.*;

//TicTacToe-4x4x4
public class runTicTacToe {

    private List<List<positionTicTacToe>> winningLines = new ArrayList<>();
    private List<positionTicTacToe> board = new ArrayList<>();
    private aiTicTacToe ai1;
    private aiTicTacToe ai2;

    public int result;

    public runTicTacToe() {
        //initialize winning lines
        winningLines = initializeWinningLines();
        //initialzie board
        board = createTicTacToeBoard();

        //initialize AI players
        ai1 = new aiTicTacToe(1);
        ai2 = new aiTicTacToe(2);
    }

    private List<positionTicTacToe> createTicTacToeBoard() {
        //create a 3-d 4x4x4 TicTacToe board and store it in a list
        List<positionTicTacToe> boardTicTacToe = new ArrayList<positionTicTacToe>();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++) {
                    boardTicTacToe.add(new positionTicTacToe(i, j, k, 0)); //0 is state "not marked"
                }
        return boardTicTacToe;
    }

    private List<positionTicTacToe> deepCopyATicTacToeBoard(List<positionTicTacToe> board) {
        //deep copy of game boards
        List<positionTicTacToe> copiedBoard = new ArrayList<positionTicTacToe>();
        for (int i = 0; i < board.size(); i++) {
            copiedBoard.add(new positionTicTacToe(board.get(i).x, board.get(i).y, board.get(i).z, board.get(i).state));
        }
        return copiedBoard;
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


    private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> targetBoard) {
        //a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
        int index = position.x * 16 + position.y * 4 + position.z;
        return targetBoard.get(index).state;
    }

    private int isEnded() {
        //test whether the current game is ended

        //brute-force
        for (int i = 0; i < winningLines.size(); i++) {

            positionTicTacToe p0 = winningLines.get(i).get(0);
            positionTicTacToe p1 = winningLines.get(i).get(1);
            positionTicTacToe p2 = winningLines.get(i).get(2);
            positionTicTacToe p3 = winningLines.get(i).get(3);

            int state0 = getStateOfPositionFromBoard(p0, board);
            int state1 = getStateOfPositionFromBoard(p1, board);
            int state2 = getStateOfPositionFromBoard(p2, board);
            int state3 = getStateOfPositionFromBoard(p3, board);

            //if they have the same state (marked by same player) and they are not all marked.
            if (state0 == state1 && state1 == state2 && state2 == state3 && state0 != 0) {
                //someone wins
                p0.state = state0;
                p1.state = state1;
                p2.state = state2;
                p3.state = state3;

                //print the satisified winning line (one of them if there are several)
                p0.printPosition();
                p1.printPosition();
                p2.printPosition();
                p3.printPosition();
                return state0;
            }
        }
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).state == 0) {
                //game is not ended, continue
                return 0;
            }
        }
        return -1; //call it a draw
    }

    public void printAllWinningLines() {
        //print all winning lines to help understand in what cases will any player win
        System.out.println(winningLines.size());
        for (int i = 0; i < winningLines.size(); i++) {
            System.out.println("Winning Line " + i + ":");
            for (int j = 0; j < winningLines.get(i).size(); j++) {
                winningLines.get(i).get(j).printPosition();
            }
        }
    }

    public void printBoardTicTacToe(List<positionTicTacToe> targetBoard) {
        //print each position on the board, uncomment this for debugging if necessary
		/*
		System.out.println("board:");
		System.out.println("board slots: "+board.size());
		for (int i=0;i<board.size();i++)
		{
			board.get(i).printPosition();
		}
		*/

        //print in "graphical" display
        for (int i = 0; i < 4; i++) {
            System.out.println("level(z) " + i);
            for (int j = 0; j < 4; j++) {
                System.out.print("["); // boundary
                for (int k = 0; k < 4; k++) {
                    if (getStateOfPositionFromBoard(new positionTicTacToe(j, k, i), targetBoard) == 1) {
                        System.out.print("X"); //print cross "X" for position marked by player 1
                    } else if (getStateOfPositionFromBoard(new positionTicTacToe(j, k, i), targetBoard) == 2) {
                        System.out.print("O"); //print cross "O" for position marked by player 2
                    } else if (getStateOfPositionFromBoard(new positionTicTacToe(j, k, i), targetBoard) == 0) {
                        System.out.print("_"); //print "_" if the position is not marked
                    }
                    if (k == 3) {
                        System.out.print("]"); // boundary
                        System.out.println();
                    }


                }

            }
            System.out.println();
        }
    }

    public boolean makeMove(positionTicTacToe position, int player, List<positionTicTacToe> tagergetBoard) {
        //make move on Tic-Tac-Toe board, given position and player
        //player 1 = 1, player 2 = 2

        //brute force (obviously not a wise way though)
        for (int i = 0; i < tagergetBoard.size(); i++) {
            if (tagergetBoard.get(i).x == position.x && tagergetBoard.get(i).y == position.y && tagergetBoard.get(i).z == position.z) //if this is the position
            {
                if (tagergetBoard.get(i).state == 0) {
                    tagergetBoard.get(i).state = player;
                    return true;
                } else {
                    System.out.println("Error: this is not a valid move.");
                }
            }

        }
        return false;
    }

    public void run() {

        Random rand = new Random();
        int turn = rand.nextInt(2) + 1; //1 = player1's turn, 2 = player2's turn, who go first is randomized

        while ((result = isEnded()) == 0) //game loop
        {
//            System.out.println("result"+result);
            if (turn == 1) {
                positionTicTacToe player1NextMove = ai1.myAIAlgorithm(board, 1); //1 stands for player 1
                if (makeMove(player1NextMove, 1, board))
                    printBoardTicTacToe(board);
                turn = 2;
            } else if (turn == 2) {
                positionTicTacToe player2NextMove = ai2.myAIAlgorithm(board, 2); //2 stands for player 2
                if (makeMove(player2NextMove, 2, board))
                    printBoardTicTacToe(board);
                turn = 1;
            } else {
                //exception occurs, stop
                System.out.println("Error!");
            }
            System.out.println("++++++++++++++++++++++++++++");
        }

        //game is ended
        if (result == 1) {
            //game ends, player 1 wins
            System.out.println("Player1 Wins");
            printBoardTicTacToe(board);
        } else if (result == 2) {
            //game ends, player 1 wins
            System.out.println("Player2 Wins");
            printBoardTicTacToe(board);
        } else if (result == -1) {
            //game ends, it's a draw
            System.out.println("This is a draw.");
            printBoardTicTacToe(board);
        } else {
            //exception occurs, stop
            System.out.println("Error!");
        }

    }

    private int isEnded(List<positionTicTacToe> board) {
        //test whether the current game is ended

        //brute-force
        for (int i = 0; i < winningLines.size(); i++) {

            positionTicTacToe p0 = winningLines.get(i).get(0);
            positionTicTacToe p1 = winningLines.get(i).get(1);
            positionTicTacToe p2 = winningLines.get(i).get(2);
            positionTicTacToe p3 = winningLines.get(i).get(3);

            int state0 = getStateOfPositionFromBoard(p0, board);
            int state1 = getStateOfPositionFromBoard(p1, board);
            int state2 = getStateOfPositionFromBoard(p2, board);
            int state3 = getStateOfPositionFromBoard(p3, board);

            //if they have the same state (marked by same player) and they are not all marked.
            if (state0 == state1 && state1 == state2 && state2 == state3 && state0 != 0) {
                //someone wins
                p0.state = state0;
                p1.state = state1;
                p2.state = state2;
                p3.state = state3;

                //print the satisified winning line (one of them if there are several)
                p0.printPosition();
                p1.printPosition();
                p2.printPosition();
                p3.printPosition();
                return state0;
            }
        }
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).state == 0) {
                //game is not ended, continue
                return 0;
            }
        }
        return -1; //call it a draw
    }

    public void fakerun() {

        Random rand = new Random();
        int turn = rand.nextInt(2) + 1; //1 = player1's turn, 2 = player2's turn, who go first is randomized
        positionTicTacToe player2NextMove1 = new positionTicTacToe(0,0,0);
        positionTicTacToe player2NextMove2 = new positionTicTacToe(0,2,0);
        positionTicTacToe player2NextMove3 = new positionTicTacToe(0,3,0);
        positionTicTacToe player2NextMove4 = new positionTicTacToe(2,0,0);
        positionTicTacToe player2NextMove5 = new positionTicTacToe(3,2,0);
        positionTicTacToe player2NextMove6 = new positionTicTacToe(1,2,1);
        positionTicTacToe player2NextMove7 = new positionTicTacToe(2,1,1);
        positionTicTacToe player2NextMove8 = new positionTicTacToe(2,3,1);
        positionTicTacToe player2NextMove9 = new positionTicTacToe(3,1,1);
        positionTicTacToe player2NextMove10 = new positionTicTacToe(3,2,1);
        positionTicTacToe player2NextMove11 = new positionTicTacToe(1,1,2);
        positionTicTacToe player2NextMove12 = new positionTicTacToe(2,1,2);
        positionTicTacToe player2NextMove13 = new positionTicTacToe(0,0,3);
        positionTicTacToe player2NextMove14 = new positionTicTacToe(1,0,3);
        positionTicTacToe player2NextMove15 = new positionTicTacToe(1,2,3);
        positionTicTacToe player2NextMove16 = new positionTicTacToe(2,2,3);
        makeMove(player2NextMove1, 2, board);
        makeMove(player2NextMove2, 2, board);
        makeMove(player2NextMove3, 2, board);
        makeMove(player2NextMove4, 2, board);
        makeMove(player2NextMove5, 2, board);
        makeMove(player2NextMove6, 2, board);
        makeMove(player2NextMove7, 2, board);
        makeMove(player2NextMove8, 2, board);
        makeMove(player2NextMove9, 2, board);
        makeMove(player2NextMove10, 2, board);
        makeMove(player2NextMove11, 2, board);
        makeMove(player2NextMove12, 2, board);
        makeMove(player2NextMove13, 2, board);
        makeMove(player2NextMove14, 2, board);
        makeMove(player2NextMove15, 2, board);
        makeMove(player2NextMove16, 2, board);

        positionTicTacToe player1NextMove1 = new positionTicTacToe(1,3,0);
        positionTicTacToe player1NextMove2 = new positionTicTacToe(2,1,0);
        positionTicTacToe player1NextMove3 = new positionTicTacToe(3,3,0);
        positionTicTacToe player1NextMove4 = new positionTicTacToe(0,2,1);
        positionTicTacToe player1NextMove5 = new positionTicTacToe(1,0,1);
        positionTicTacToe player1NextMove6 = new positionTicTacToe(1,3,1);
        positionTicTacToe player1NextMove7 = new positionTicTacToe(2,2,1);
        positionTicTacToe player1NextMove8 = new positionTicTacToe(3,0,1);
        positionTicTacToe player1NextMove9 = new positionTicTacToe(3,3,1);
        positionTicTacToe player1NextMove10 = new positionTicTacToe(0,1,2);
        positionTicTacToe player1NextMove11 = new positionTicTacToe(1,2,2);
        positionTicTacToe player1NextMove12 = new positionTicTacToe(2,0,2);
        positionTicTacToe player1NextMove13 = new positionTicTacToe(2,2,2);
        positionTicTacToe player1NextMove14 = new positionTicTacToe(2,3,2);
        positionTicTacToe player1NextMove15 = new positionTicTacToe(0,3,3);
        positionTicTacToe player1NextMove16 = new positionTicTacToe(3,0,3);
        positionTicTacToe player1NextMove17 = new positionTicTacToe(3,1,3);
        makeMove(player1NextMove1, 1, board);
        makeMove(player1NextMove2, 1, board);
        makeMove(player1NextMove3, 1, board);
        makeMove(player1NextMove4, 1, board);
        makeMove(player1NextMove5, 1, board);
        makeMove(player1NextMove6, 1, board);
        makeMove(player1NextMove7, 1, board);
        makeMove(player1NextMove8, 1, board);
        makeMove(player1NextMove9, 1, board);
        makeMove(player1NextMove10, 1, board);
        makeMove(player1NextMove11, 1, board);
        makeMove(player1NextMove12, 1, board);
        makeMove(player1NextMove13, 1, board);
        makeMove(player1NextMove14, 1, board);
        makeMove(player1NextMove15, 1, board);
        makeMove(player1NextMove16, 1, board);
        makeMove(player1NextMove17, 1, board);

        while ((result = isEnded()) == 0) //game loop
        {
//            System.out.println("result"+result);
            if (turn == 1) {
                positionTicTacToe player1NextMove = ai1.myAIAlgorithm(board, 1); //1 stands for player 1
                if (makeMove(player1NextMove, 1, board))
                    printBoardTicTacToe(board);
                turn = 2;
            } else if (turn == 2) {
                positionTicTacToe player2NextMove = ai2.myAIAlgorithm(board, 2); //2 stands for player 2
                if (makeMove(player2NextMove, 2, board))
                    printBoardTicTacToe(board);
                turn = 1;
            } else {
                //exception occurs, stop
                System.out.println("Error!");
            }
            System.out.println("++++++++++++++++++++++++++++");
        }

        //game is ended
        if (result == 1) {
            //game ends, player 1 wins
            System.out.println("Player1 Wins");
            printBoardTicTacToe(board);
        } else if (result == 2) {
            //game ends, player 1 wins
            System.out.println("Player2 Wins");
            printBoardTicTacToe(board);
        } else if (result == -1) {
            //game ends, it's a draw
            System.out.println("This is a draw.");
            printBoardTicTacToe(board);
        } else {
            //exception occurs, stop
            System.out.println("Error!");
        }

    }

    public static void main(String[] args) {

        //run game loop
        runTicTacToe rttt = new runTicTacToe();
        rttt.run();
    }
}
