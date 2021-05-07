/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Q_Learning {
   final DecimalFormat df = new DecimalFormat("#.##");
   Oyun oyun;
   OyunUI oyunui;
   final double alpha = 0.8;
   final double gamma = 0.9;
   int statesCount;
   int [][] R;
   double [][] Q;
   int stateFinish;
   ArrayList<Point> pointler = new ArrayList();
   
    public Q_Learning(Oyun oyun){
      this.oyun = oyun;
      this.statesCount=createSira();
      komsuBul();
      init();
      printResult();
             

    }
    
    
    public void init(){
       R = new int [statesCount][statesCount];
       Q = new double [statesCount][statesCount];
       this.stateFinish = oyun.getGrid()[oyun.getFinish().x][oyun.getFinish().y].getSira();
       for(Node n : oyun.getGrid()[oyun.getFinish().x][oyun.getFinish().y].getKomsular()){
           R[n.getSira()][stateFinish] = 100;
           //R[KOMSULARIN SATATELERİ][FİNİSH STATE] = 100
       }
       //R[stateB][stateC] = 100; // from b to c
       //R[stateF][stateC] = 100; // from f to c     
    }
    public int createSira(){
        int counter = 0;
        for(int i=0;i<this.oyun.getLines();i++){
            for(int j=0;j<this.oyun.getCols();j++){
                counter++; 
            }
        }
        return counter;
    }
    
    public void komsuBul(){
        for(int i=0;i<this.oyun.getLines();i++){
            for(int j=0;j<this.oyun.getCols();j++){
                if(i-1>=0 && oyun.getGrid()[i-1][j].isDuvar() == false){
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j]);
                }
                if(i+1<oyun.getLines() && oyun.getGrid()[i+1][j].isDuvar() == false ){
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j]);  
                }
                if(j-1>=0 && oyun.getGrid()[i][j-1].isDuvar() == false){
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i][j-1]);
                }
                if(j+1<oyun.getCols() && oyun.getGrid()[i][j+1].isDuvar() == false){
                     oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i][j+1]);
                }
                /*
                if( i-1>=0 && j+1<oyun.getCols() && oyun.getGrid()[i-1][j+1].isDuvar() == false){
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j+1]);
                }
                if( i-1>=0 && j-1 >=0 && oyun.getGrid()[i-1][j-1].isDuvar() == false){
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j-1]);
                }
                if(i+1<oyun.getLines() && j+1 < oyun.getCols() && oyun.getGrid()[i+1][j+1].isDuvar() == false){
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j+1]);
                }
                if(i+1<oyun.getLines() && j-1>=0 && oyun.getGrid()[i+1][j-1].isDuvar() == false){
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j-1]);
                }
              */
            }
        }
    }
    
    public void run(){
        Random rand = new Random();
        for(int i = 0;i<1;i++){
            int state = oyun.getGrid()[oyun.getStart().x][oyun.getStart().y].getSira();        
            while(state!=stateFinish){
                Point p = findGrid(state);
                int index = rand.nextInt(oyun.getGrid()[p.x][p.y].getKomsular().size());     
                int action = oyun.getGrid()[p.x][p.y].getKomsular().get(index).getSira();
                Point a = findGrid(action);
                if(!oyun.getGrid()[a.x][a.y].isDuvar()){
                pointler.add(a);
                             
                int nextState = action;
                double q = Q(state,action);
                double maxQ = maxQ(nextState);
                int r = R(state,action);
                
                double value = q + alpha * (r + gamma* maxQ - q);
                setQ(state,action,value);         
                state = nextState;
                }
            }
        }
       
    }
    void setQ(int s,int a,double value){
        Q[s][a] = value;
    }
    public double Q(int s,int a){
        return Q[s][a];
    }
    public int R(int s,int a){
        return R[s][a];
    }
    public double maxQ(int s){
        double maxValue = Double.MIN_VALUE;
        Point p = findGrid(s);
        ArrayList<Node> temp = oyun.getGrid()[p.x][p.y].getKomsular();
        for(int i = 0;i<temp.size();i++){
            int nextState   = temp.get(i).getSira();
            double value = Q[s][nextState];
            if(value > maxValue)
                maxValue = value;
        }
        return maxValue;
    }
    
    public Point findGrid(int s){
        Point p = null;
        for(int i=0;i<oyun.getLines();i++){
            for(int j=0;j<oyun.getCols();j++){
                if(oyun.getGrid()[i][j].getSira() == s){
                    p = new Point(i,j);
                }
            }
        }
        return p ;
    }
    public int policy (int state){
    double maxValue = Double.MIN_VALUE;
    Point p = findGrid(state);
    int policyGotoState = state;
    ArrayList<Node> temp = oyun.getGrid()[p.x][p.y].getKomsular();
    for(int i =0 ; i< temp.size();i++){
          int nextState = temp.get(i).getSira();
          double value  = Q[state][nextState];
          if(value > maxValue){
                maxValue = value;
          policyGotoState = nextState;
          }
            
    }
    return policyGotoState;
    }
    
    
    void printResult() {
        System.out.println("Print result");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("out from " + i + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.print(df.format(Q[i][j]) + " ");
            }
            System.out.println();
        }
    }
    
    public ArrayList <Point> showPolicy(){
        System.out.println("\nshowPolicy");
        ArrayList<Point> pointAr = new ArrayList();
        for(int i = 0; i< oyun.getLines();i++){
            for(int j = 0; j< oyun.getCols();j++){
                if(!oyun.getGrid()[i][j].isDuvar()){
                             int from = oyun.getGrid()[i][j].getSira();
                int to = policy(from);
                Point p = findGrid(to);
                pointAr.add(oyun.getGrid()[i][j].p);
                pointAr.add(p);
                System.out.println("From :"+oyun.getGrid()[i][j].getSira()+" --> GO TO :"+oyun.getGrid()[p.x][p.y].getSira());
                }
       
            }
        }
        return pointAr;
    }
     
}
