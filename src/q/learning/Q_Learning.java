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
   final double alpha = 0.8;
   final double gamma = 0.9;
   int statesCount;
   int [][] R;
   double [][] Q;
   int [] Y;
   int deger;
   int stateFinish;
   int engelSayisi;
   ArrayList<Point> point;
   ArrayList<ArrayList<Point>> pointler = new ArrayList();
   ArrayList<ArrayList<Integer>> kazanclar = new ArrayList();
   ArrayList<Integer> kazanc;
   ArrayList<ArrayList<Double>> maliyetler = new ArrayList(); 
   ArrayList<Double> maliyet;
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
            R[n.getSira()][stateFinish] =5;
       }
       for(int i = 0; i<statesCount;i++){
           for(int j =0;j<statesCount;j++){
              if(R[i][j] == 0){
                  if(i!=j){
                      R[i][j]= -1;
                  }
              }
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
                    }else{
                        engelSayisi++;
                          startTemp = oyun.getGrid()[i][j].getSira();
                          finishTemp = oyun.getGrid()[i-1][j].getSira();
                         R[startTemp][finishTemp] = -5;
                    }   
                     oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j]);
                         
                }
                if(i+1<oyun.getLines()){
                    if(oyun.getGrid()[i+1][j].isDuvar() == false){
                        startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i+1][j].getSira();
                        R[startTemp][finishTemp] = 3;
                     
                    }else{
                          engelSayisi++;
                          startTemp = oyun.getGrid()[i][j].getSira();
                          finishTemp = oyun.getGrid()[i+1][j].getSira();
                         R[startTemp][finishTemp] = -5;
                    }
                      oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j]); 
                     
                }
                if(j-1>=0 ){
                    if(oyun.getGrid()[i][j-1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j-1].getSira();
                        R[startTemp][finishTemp] = 3;
                     
                    }else{
                          engelSayisi++;
                          startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j-1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }
                      oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i][j-1]); 
                    
                }
                if(j+1<oyun.getCols()){
                    if(oyun.getGrid()[i][j+1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j+1].getSira();
                        R[startTemp][finishTemp] = 3;
                       
                    }else{
                          engelSayisi++;
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i][j+1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }
                     oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i][j+1]); 
                   
                
                }
              
                if( i-1>=0 && j+1<oyun.getCols()){
                    if(oyun.getGrid()[i-1][j+1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j+1].getSira();
                        R[startTemp][finishTemp] = 3;
                       
                    }else{
                          engelSayisi++;
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j+1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }
                     oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j+1]);
                }
                if( i-1>=0 && j-1 >=0){
                    if(oyun.getGrid()[i-1][j-1].isDuvar() == false){
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j-1].getSira();
                        R[startTemp][finishTemp] = 3;
                     
                    }else{
                          engelSayisi++;
                         startTemp = oyun.getGrid()[i][j].getSira();
                         finishTemp = oyun.getGrid()[i-1][j-1].getSira();
                        R[startTemp][finishTemp] = -5;
                    }  
                     oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i-1][j-1]);
                      
                }
                if(i+1<oyun.getLines() && j+1 < oyun.getCols()){
                    if(oyun.getGrid()[i+1][j+1].isDuvar() == false){
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j+1].getSira();
                       R[startTemp][finishTemp] = 3;
                    
                    }else{
                          engelSayisi++;
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j+1].getSira();
                       R[startTemp][finishTemp] = -5; 
                    }
                       oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j+1]);
                  
                }
                if(i+1<oyun.getLines() && j-1>=0 ){
                    if(oyun.getGrid()[i+1][j-1].isDuvar() == false){
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j-1].getSira();
                       R[startTemp][finishTemp] = 3;
                        
                    }else{
                          engelSayisi++;
                        startTemp = oyun.getGrid()[i][j].getSira();
                        finishTemp = oyun.getGrid()[i+1][j-1].getSira();
                       R[startTemp][finishTemp] = -5; 
                    }
                     oyun.getGrid()[i][j].addKomsular(oyun.getGrid()[i+1][j-1]);
                   
                   
                   
                }
            
            }
        }
    }
    
    public void run(){
        Random rand = new Random();
        int n;
        if(oyun.getCols() > oyun.getLines()){
            n = oyun.getCols()*oyun.getCols();
        }else if (oyun.getCols()< oyun.getLines()){
            n = oyun.getLines()*oyun.getLines();
        }else{
            n = oyun.getLines()*oyun.getCols();
        }
        System.out.println(n);
        for(int i = 0;i<n;i++){
            kazanc = new ArrayList();
            maliyet = new ArrayList();
            point = new ArrayList();
            // sona geldiğinde maliyet
            int state = oyun.getGrid()[oyun.getStart().x][oyun.getStart().y].getSira();        
            while(state!=stateFinish){
                Point p = findGrid(state);
                int index = rand.nextInt(oyun.getGrid()[p.x][p.y].getKomsular().size());     
                int action = oyun.getGrid()[p.x][p.y].getKomsular().get(index).getSira();
                Point a = findGrid(action);
               
                point.add(a);                          
                int nextState = action;
                double q = Q(state,action);
                double maxQ = maxQ(nextState);
                int r = R(state,action);   
                kazanc.add(r);
           
                //double value = r+(gamma*maxQ);
                if(nextState == stateFinish){
                    double value = r;
                   // System.out.println("STATE : "+state+"action : "+action + " : "+value +" : " + i);
                    maliyet.add(value);
                     setQ(state,action,value); 
                     state = nextState;
                }else{
                    double value =  (alpha * ( r + (gamma * (maxQ) - q )));
                    maliyet.add(value);
                    //double value = q + alpha * (r + gamma* maxQ - q);
                    setQ(state,action,value);
                    if(!oyun.getGrid()[a.x][a.y].isDuvar()){
                        state = nextState;
                    }else{
                        state = oyun.getGrid()[oyun.getStart().x][oyun.getStart().y].getSira();        
                    }    
                 }

                //System.out.println(value);   
            }

                kazanclar.add(kazanc);
                maliyetler.add(maliyet);
                pointler.add(point);
   
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
