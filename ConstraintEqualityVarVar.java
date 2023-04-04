package main.java;
import java.util.ArrayList;

public class ConstraintEqualityVarVar extends Constraint {
    
    Variable v1, v2;

    public ConstraintEqualityVarVar(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        System.out.println(v1.name+" = "+v2.name);
        return "";
    }

    protected boolean isSatisfied() {
        for(int i=0;i<v1.d.vals.length;i++){
            for(int j=0;j<v2.d.vals.length;j++){
                if(v1.d.vals[i]==v2.d.vals[j]){
                    return true;
                }
            }
        }
        //TODO
        return false;
    }

    protected void reduce() {
        ArrayList<Integer>list=new ArrayList<>();
        for(int i=0;i<v1.d.vals.length;i++){
            for(int j=0;j<v2.d.vals.length;j++){
                if(v1.d.vals[i]==v2.d.vals[j]&&!list.contains(v1.d.vals[i])){
                    list.add(v1.d.vals[i]);
                }

            }
        }
        int[]array=new int[list.size()];
        for(int i=0;i<array.length;i++){
            array[i]=list.get(i);
        }
       v1.d.vals=array;
       v2.d.vals=array;

}
}