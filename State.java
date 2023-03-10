

import java.util.*;

public class State implements Comparable{

    private Square[][] board;
    private State parent;
    private int row;//number of queen
    private int heuristic;
    private int total;


    //constructors
    public State() {
        this.board=new Square[7][7];
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                this.board[i][j]=new Square();
            }
        }
    }

    public State(int i){
        this.board=new Square[i][i];
        for(int k=0;k<i;k++){
            for(int j=0;j<i;j++){
                this.board[k][j]=new Square();
            }
        }
    }

    public State(Square[][] board, State parent,int row) {
        this.board = board;
        this.parent = parent;
        this.row=row;
    }


    //getter and setter functions
    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }

    public Square[][] getBoard() {
        return board;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "State{" +
                "board=" + Arrays.toString(board) +
                ", parent=" + parent +
                '}';
    }

    public void print(){
        for(int i=0;i<this.board.length;i++){
            for(int j=0;j<this.board.length;j++){
                if (this.board[i][j].isHasQueen()==true){
                    System.out.print("Q");
                }else
                System.out.print("#");
            }
            System.out.print("\n");
        }
    }

    //to get the children of the state
    //By adding a new queen in each square that is not under attack
    public ArrayList<State> getchildren(){
        ArrayList<State> list=new ArrayList<State>();
        for(int i=0;i<this.board.length;i++){
            if(this.board[row][i].isAvailable()==true){//the square is not under attack from other queens
                //add new queen
                State state=setQueen(this,i);
                list.add(state);
            }
        }
        return list;
    }

    //add new queen to the board
    public State setQueen(State state,int col){
        //generate a new state and add the queen
        State newState=new State(state.board.length);
        //to copy the old board and add the new editing(attacks)
        newState.setBoard(state.getBoard(),state.row,col);
        newState.setParent(state);
        newState.setRow(state.getRow()+1);
        return newState;
    }



    //add the editing to the board
    public void  setBoard(Square[][] board,int row,int col){

        //initialization the new board
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){

                this.board[i][j]=new Square();
            }
        }
        //copy the old board
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board.length;j++){
                this.board[i][j].setAvailable(board[i][j].isAvailable());
                this.board[i][j].setHasQueen(board[i][j].isHasQueen());
            }
        }
        //add a new queen
        this.board[row][col].setHasQueen(true);

        //row under attack from the new queen
        for(int i=0;i<this.board.length;i++){
            this.board[row][i].setAvailable(false);
        }

        //column under attack from the new queen
        for(int i=row;i<this.board.length;i++){
            this.board[i][col].setAvailable(false);
        }

        //the main diameter under attack from the new queen
        int j=col;
        for(int i=row;i<this.board.length;i++,j++){
            this.board[i][j].setAvailable(false);
            if(j==this.board.length-1){
                break;
            }
        }

        //the secondary diameter under attack from the new queen
        j=col;
        for(int i=row;i<this.board.length;i++,j--){
            this.board[i][j].setAvailable(false);
            if(j==0){
                break;
            }
        }

    }

    public State BFS(){
        int nodeGenertated=1,maxsize=1;
        ArrayList<State> explored =new ArrayList<State>();
        Queue<State> frontier =new LinkedList<State>();
        ArrayList<State> child=new ArrayList<State>();
        frontier.add(this);
        explored.add(this);
        State s;
        while (frontier.size()!=0){
            s=frontier.poll();
            maxsize++;
            if(s.row==s.board.length){
                System.out.println("The number of step is:"+board.length+
                        "\nThe number of nodes generated is: "+nodeGenertated+
                        "\nThe maximum size of the frienge is: "+maxsize);
                return s;
            }
            child=s.getchildren();
            for(int i=0;i<child.size();i++){
                if(!child.get(i).inExplored(explored)&&!child.get(i).inFrontier(frontier)){
                    nodeGenertated++;
                    explored.add(child.get(i));
                    frontier.add(child.get(i));
                }
            }
        }
        return null;
    }

    public State IDS(){
        State s=new State();
        int i;
        for( i=1;i<50;i++){
            s= this.DLS(i);
            if(s!=null){
                System.out.println("the depth of solution is: "+i);
                break;
            }
        }return s;

    }

    public State DLS(int l){
        int nodeGenertated=1,maxsize=1;
        ArrayList<State>  explored =new ArrayList<State>();
         Stack<State> frontier =new Stack<State>();
         ArrayList<State> child=new ArrayList<State>();
         frontier.push(this);
         explored.add(this);
         State s;
         while (!frontier.empty()){
             s=frontier.pop();
             maxsize++;
             if(s.row==board.length){
                 System.out.println("The number of step is:"+board.length+
                         "\nThe number of nodes generated is: "+nodeGenertated+
                         "\nThe maximum size of the frienge is: "+maxsize);
                 return s;
             }
             if(s.getRout().size()<=l){
                 child=s.getchildren();
             for(int i=0;i<child.size();i++){
                 if(!child.get(i).inExplored(explored)){
                     nodeGenertated++;
                     explored.add(child.get(i));
                     frontier.push(child.get(i));
                 }
             }
             }
         }
         return null;

 }

    public State AStar (){
        int nodeGenertated=1,maxsize=1;
        ArrayList<State>  explored=new ArrayList<State>();
        PriorityQueue<State> frontier=new PriorityQueue<State>();
        ArrayList<State> child=new ArrayList<State>();
        frontier.add(this);
        explored.add(this);
        State s;
        while (frontier.size()!=0){
            s=frontier.poll();
            maxsize++;
            if(s.row==s.board.length){
                System.out.println("The number of step is:"+board.length+
                        "\nThe number of nodes generated is: "+nodeGenertated+
                        "\nThe maximum size of the frienge is: "+maxsize);
                return s;
            }
            child=s.getchildren();
            for(int i=0;i<child.size();i++){
                if(!child.get(i).inExplored(explored)) {
                   if(!child.get(i).inFrontier(frontier)){
                        child.get(i).setHeuristic(child.get(i).heuristic());
                        child.get(i).setTotal( child.get(i).getHeuristic() + child.get(i).getRow() );
                        nodeGenertated++;
                        frontier.add(child.get(i));
                        explored.add(child.get(i));
                    }
                }

            }
        }
        return null;
    }


    //the heuristic is possible number of attacks
    public int heuristic(){

        //know the queen's coordinates
        int col=0,x=this.row-1;
        for(int i=0;i<this.board.length;i++){
            if(this.board[x][i].isHasQueen()){
                col=i;
                break;
            }
        }
 
        int h=0;       
        //the main diameter possible attacks
        int j=col;
        for(int i=row;i<this.board.length;i++,j++){
            h++;
            if(j==this.board.length-1){
                break;
            }
        }
        //the secondary diameter possible attacks
        j=col;
        for(int i=row;i<this.board.length;i++,j--){
            h++;
            if(j==0){
                break;
            }
        }
        return h;
    }


    //check if the State in the explored
    public boolean inExplored(ArrayList<State> list){
        for (State temp :list){
            if(this.isEqual(temp)){
                return true;
            }
        }return false;
    }

    //check if the State in the frontier
    public boolean inFrontier(Queue<State> open){
        for(State temp :open){
            if(temp.isEqual(this)){
                return true;
            }
        }return false;
    }


    public boolean isEqual(State state){
        if(this.getRow()!=state.getRow()) {
            return false;
        }
        for(int i=0;i<state.board.length;i++){
            for (int j=0;j<state.board.length;j++){
                if(state.board[i][j].isHasQueen()!=this.board[i][j].isHasQueen()
                        ||state.board[i][j].isAvailable()!=this.board[i][j].isAvailable())
                    return false;
            }
        }
        return true;
    }

    //get the path of states from the current state to the initial state
    //by getting the parent for each state
    public ArrayList<State> getRout(){
        State temp=this;
        ArrayList<State> list=new ArrayList<State>();
        while (temp.getParent()!=null){
            list.add(temp);
            temp=temp.getParent();
        }list.add(temp);
        return list;
    }


    @Override
    public int compareTo(Object oo) {
        State o=(State)oo;
        return Integer.compare(this.getHeuristic(),o.getHeuristic());
    }


}
