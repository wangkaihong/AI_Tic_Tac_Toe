import java.util.*;
public class aiTicTacToe {

	public int player; //1 for player 1 and 2 for player 2
	private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> board)
	{
		//a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
		int index = position.x*16+position.y*4+position.z;
		return board.get(index).state;
	}
	public positionTicTacToe myAIAlgorithm(List<positionTicTacToe> board, int player, int depth)
	{
		positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);
		if(player == 1) {
            Node root = new Node(board,1);
            double[] res = minimax(root, depth, -99999, 99999, true,player);
            System.out.println("num:"+root.child.size());
            myNextMove.x = root.child.get((int)res[1]).move.x;
            myNextMove.y = root.child.get((int)res[1]).move.y;
            myNextMove.z = root.child.get((int)res[1]).move.z;

            System.out.println("player"+player+":"+myNextMove.x +"," + myNextMove.y + "," +myNextMove.z);
            return myNextMove;
        }
        else {
            Node root = new Node(board,2);
            double[] res = minimax(root, depth, -99999, 99999, true,player);
            ArrayList<Integer> candidates = new ArrayList<>();
            System.out.println("num:"+root.child.size());
            myNextMove.x = root.child.get((int)res[1]).move.x;
            myNextMove.y = root.child.get((int)res[1]).move.y;
            myNextMove.z = root.child.get((int)res[1]).move.z;
            System.out.println("player"+player+":"+myNextMove.x +"," + myNextMove.y + "," +myNextMove.z);
            return myNextMove;
        }
	}
    public positionTicTacToe myRandomAlgorithm(List<positionTicTacToe> board, int player)
    {
        positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);
		do
			{
				Random rand = new Random();
				int x = rand.nextInt(4);
				int y = rand.nextInt(4);
				int z = rand.nextInt(4);
				myNextMove = new positionTicTacToe(x,y,z);
			}while(getStateOfPositionFromBoard(myNextMove,board)!=0);
        return myNextMove;
    }

    public double[] minimax(Node node, int depth, double alpha, double beta, boolean maximizing, int player) {
	    if(Node.isEnded(node.board) != 0) {
            if((player == 1 && Node.isEnded(node.board) == 1) || (player == 2 && Node.isEnded(node.board) == 2)) {
                double[] ret = {999,-1};
                return ret;
            }
            else {
                if((player == 1 && Node.isEnded(node.board) == 2) || (player == 2 && Node.isEnded(node.board) == 1)) {
                    double[] ret = {-999,-1};
                    return ret;
                }
                else {
                    if (Node.isEnded(node.board) == -1) {
                        double[] ret = {0,-1};
                        return ret;
                    } else {
                        System.out.println("Error!");
                        double[] ret = {-1,-1};
                        return ret;
                    }
                }
            }
        }
        else{
	        if(depth == 0) {
                double[] ret = {node.heuristic(player),-1};
                return ret;
            }
            else {
                if(maximizing) {
                    double v = -99999;
                    node.generate_child();
                    ArrayList<Integer> ind = new ArrayList<>();
                    for(int i = 0; i < node.child.size(); i++) {
                        double new_v = minimax(node.child.get(i),depth-1,alpha,beta,false, player)[0];
                        if(new_v > v) {
                            v = new_v;
                            ind.clear();
                        }
                        if(new_v == v) {
                            ind.add(i);
                        }
                        alpha = Math.max(new_v,alpha);
                        if(alpha >= beta) {
                            break;
                        }
                    }
                    double[] ret = new double[2];
                    ret[0] = v;
                    Random rand = new Random();
                    int i = rand.nextInt(ind.size());
                    ret[1] = ind.get(0);
                    return ret;
                }
                else {
                    double v = 99999;
                    node.generate_child();
                    ArrayList<Integer> ind = new ArrayList<>();
                    for(int i = 0; i < node.child.size(); i++) {
                        double new_v = minimax(node.child.get(i),depth-1,alpha,beta,false, player)[0];
                        if(new_v < v) {
                            v = new_v;
                            ind.clear();
                        }
                        if(new_v == v) {
                            ind.add(i);
                        }
                        beta = Math.min(new_v,beta);
                        if(alpha >= beta) {
                            break;
                        }
                    }

                    double[] ret = new double[2];
                    ret[0] = v;
                    Random rand = new Random();
                    int i = rand.nextInt(ind.size());
                    ret[1] = ind.get(0);
                    return ret;
                }
            }
        }
    }
	private List<List<positionTicTacToe>> initializeWinningLines()
	{
		//create a list of winning line so that the game will "brute-force" check if a player satisfied any 	winning condition(s).
		List<List<positionTicTacToe>> winningLines = new ArrayList<List<positionTicTacToe>>();
		
		//48 straight winning lines
		//z axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,j,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//y axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,j,-1));
				winningLines.add(oneWinCondtion);
			}
		//x axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,j,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 main diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,0,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,0,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,3,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 anti diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,3,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,3,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,3,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,0,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//4 additional diagonal winning lines
		List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,3,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,0,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(3,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(0,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,3,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,0,3,-1));
		winningLines.add(oneWinCondtion);	
		
		return winningLines;
		
	}
	public aiTicTacToe(int setPlayer)
	{
		player = setPlayer;
	}

}
