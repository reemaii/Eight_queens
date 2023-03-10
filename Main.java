

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);
        System.out.print("Please enter the size: ");
        int x=scanner.nextInt();
        State s=new State(x);

        System.out.print("\n\n\tBFS\n");
        State s1=s.BFS();
        if(s1!=null){
            s1.print();
        }else {
            System.out.print("\nThere is no solution\n\n");
        }

        System.out.print("\n\n\tIDS\n");
        State s2=s.IDS();
        if(s2!=null){
            s2.print();
        }else {
            System.out.print("\nThere is no solution\n\n");
        } 


        System.out.print("\n\n\tA*\n");
        State s3=s.AStar();
        if(s3!=null){
            s3.print();
        }else {
            System.out.print("\nThere is no solution\n\n");
        }
    }
}
