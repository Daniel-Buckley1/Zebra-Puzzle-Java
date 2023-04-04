package main.java;

import java.util.ArrayList;

public class ConstraintDifferenceVarVar extends  Constraint {
        Variable v1;
        Variable v2;
    
        public ConstraintDifferenceVarVar(Variable v1,Variable v2) {
            this.v1=v1;
            this.v2=v2;
        }
        public String toString() {
            System.out.println(v1.name+"      doesNotEqual     "+v2.name);
        
            return "";
        }
    
        protected boolean isSatisfied(){
            if(v1.d.vals.length==1&&v2.d.vals.length==1){
                return false;
            }
            return true;
        }
        protected void reduce(){
            if(v1.d.vals.length==1){
                ArrayList<Integer>newV2Domain=new ArrayList<Integer>();
                int num=v1.d.vals[0];
                for(int i=0;i<v2.d.vals.length;i++){
                    if(v2.d.vals[i]!=num){
                        newV2Domain.add(v2.d.vals[i]);
                    }
                }
                int[] newDom=new int[newV2Domain.size()];
                for(int i=0;i<newDom.length;i++){
                    newDom[i]=newV2Domain.get(i);
                }
                v2.d.vals=newDom;




                return;

            }
            if(v2.d.vals.length==1){
                ArrayList<Integer>newV1Domain=new ArrayList<Integer>();
                int num=v2.d.vals[0];
                for(int i=0;i<v1.d.vals.length;i++){
                    if(v1.d.vals[i]!=num){
                        newV1Domain.add(v1.d.vals[i]);
                    }



            }
            int[] newDom=new int[newV1Domain.size()];
            for(int i=0;i<newDom.length;i++){
                newDom[i]=newV1Domain.get(i);
            }
            v1.d.vals=newDom;

           

        }
    
    }
  
}
