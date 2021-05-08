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
   boolean bitis = false;
   final DecimalFormat df = new DecimalFormat("#.##");
   Oyun oyun;
   OyunUI oyunui;
   final double alpha = 0.9;
   final double gamma = 0.9;
   int statesCount;
   int [][] R;
   double [][] Q;
   int [] Y;
   int stateFinish;
   ArrayList<Point> pointler = new ArrayList();
   ArrayList<ArrayList<Integer>> kazanclar = new ArrayList();
   ArrayList<Integer> kazanc = new ArrayList();
   ArrayList<ArrayList<Double>> maliyetler = new ArrayList(); 
   ArrayList<Double> maliyet = new ArrayList(); 
    public Q_Learning(Oyun oyun){
      this.oyun = oyun;
      this.statesCount=createSira();
      R = new int [statesCount][statesCount];  // 8*8 = {0}
      Q = new double [statesCount][statesCount];  // 8*8 {0}
      Y = new int [statesCount+1];
      komsuBul();
      init();
    }
    public Q_Learning(){
        
    }
    
   
    public void init(){

       this.stateFinish = oyun.getGrid()[oyun.getFinish().x][oyun.getFinish().y].getSira();
       
       for(Node n : oyun.getGrid()[oyun.getFinish().x][oyun.getFinish().y].getKomsular()){
           if(!oyun.getGrid()[n.p.x][n.p.y].isDuvar())
            R[n.getSira()][stateFinish] = 100;
       }
       for(int i = 0; i<statesCount;i++){
           for(int j =0;j<statesCount;j++){
             
               System.out.print(R[i][j]+" ");
           }
           System.out.println("");
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
        int startTemp;
        int finishTemp;
        for(int i=0;i<this.oyun.getLines();i++){
            for(int j=0;j<this.oyun.getCols();j++){
                if(i-1>=0){ 
                    if(oyun.getGrid()[i-1][j].isDuvar() == false){
                          startTemp = oyun.getGrid()[i][j].getSira();
                          finishTemp = oyun.getGrid()[i-1][j].getSira();
                         R[startTemp][finishTemp] = 3;
                    oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j]);

                    }else{
                          startTemp = oyun.getGrid()[i][j].getSira();
                          finishTemp = oyun.getGrid()[i-1][j].getSira();
                         R[startTemp][finishTemp] = -5;
                    }   
                         
                }
                if(i+1<oyun.getLines()){
                    if(oyun.getGrid()[i+1][j].isDuvar() == false){
                        startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i+1][j].getSira();
                        R[startTemp][finishTemp] = 3;
                      oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j]);  
                    }else{
                          startTemp = oyun.getGrid()[i][j].getSira();
                          finishTemp = oyun.getGrid()[i+1][j].getSira();
                         R[startTemp][finishTemp] = -5;
                    }
                     
                }
                if(j-1>=0 ){
                    if(oyun.getGrid()[i][j-1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j-1].getSira();
                        R[startTemp][finishTemp] = 3;
                      oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i][j-1]); 
                    }else{
                          startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j-1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }
                     
                    
                }
                if(j+1<oyun.getCols()){
                    if(oyun.getGrid()[i][j+1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j+1].getSira();
                        R[startTemp][finishTemp] = 3;
                       oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i][j+1]);  
                    }else{
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j+1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }
                    
                   
                
                }
              
                if( i-1>=0 && j+1<oyun.getCols()){
                    if(oyun.getGrid()[i-1][j+1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j+1].getSira();
                        R[startTemp][finishTemp] = 3;
                       oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j+1]);
                    }else{
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j+1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }
                     
                }
                if( i-1>=0 && j-1 >=0){
                    if(oyun.getGrid()[i-1][j-1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j-1].getSira();
                        R[startTemp][finishTemp] = 3;
                      oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j-1]);
                    }else{
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j-1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }  
                      
                }
                if(i+1<oyun.getLines() && j+1 < oyun.getCols()){
                    if(oyun.getGrid()[i+1][j+1].isDuvar() == false){
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j+1].getSira();
                       R[startTemp][finishTemp] = 3;
                      oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j+1]);
                    }else{
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j+1].getSira();
                       R[startTemp][finishTemp] = -5; 
                    }
                     
                  
                }
                if(i+1<oyun.getLines() && j-1>=0 ){
                    if(oyun.getGrid()[i+1][j-1].isDuvar() == false){
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j-1].getSira();
                       R[startTemp][finishTemp] = 3;
                         oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j-1]);
                    }else{
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j-1].getSira();
                       R[startTemp][finishTemp] = -5; 
                    }
                   
                   
                   
                }
            
            }
        }
    }
    
    public void run(){
        Random rand = new Random();
        for(int i = 0;i<100;i++){
            if(kazanc.isEmpty()!=true && maliyet.isEmpty()!=true){               
                kazanclar.add(kazanc);
                maliyetler.add(maliyet);
            }
            kazanc.clear();
            maliyet.clear();
            int state = oyun.getGrid()[oyun.getStart().x][oyun.getStart().y].getSira();        
            while(state!=stateFinish){
                Point p = findGrid(state);
                int index = rand.nextInt(oyun.getGrid()[p.x][p.y].getKomsular().size());     
                int action = oyun.getGrid()[p.x][p.y].getKomsular().get(index).getSira();
                Point a = findGrid(action);   
                pointler.add(a);                          
                int nextState = action;
                double q = Q(state,action);
                double maxQ = maxQ(nextState);
                int r = R(state,action);    
                kazanc.add(r);
                double value = r+(gamma*maxQ);
                maliyet.add(value);
                //System.out.println(value);
                //double value = q + alpha * ( r + (gamma * (maxQ))-q);
                //double value = q + alpha * (r + gamma* maxQ - q);
                setQ(state,action,value);         
                state = nextState;
                
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
    public void setR(int s,int a,int value){
        R[s][a]=value;
    }
    void setY(int s,int a){
        Y[s] = a;
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
                //pointAr.add(oyun.getGrid()[i][j].p);
                //pointAr.add(p);
                // index [0] = 1 
                setY(oyun.getGrid()[i][j].getSira(),oyun.getGrid()[p.x][p.y].getSira());
                System.out.println("From :"+oyun.getGrid()[i][j].getSira()+" --> GO TO :"+oyun.getGrid()[p.x][p.y].getSira());
                }
       
            }
        }
        bitis = true;
        return pointAr;
    }
     
}
