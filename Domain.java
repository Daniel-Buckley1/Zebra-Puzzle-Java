package main.java;

import java.util.ArrayList;

public class Domain {

    int[] vals;


    public Domain(int[] vals) {
        this.vals = vals;
    }

    public void deleteAtIdx(int idx){
        ArrayList<Integer>newValsList=new ArrayList<>();
        for(int num:vals){
            if(num!=vals[idx]){
                newValsList.add(num);
            }
        }
        int[]newValsArray=new int[newValsList.size()];
        for(int i=0;i<newValsArray.length;i++){
            newValsArray[i]=newValsList.get(i);
        }
        vals=newValsArray;
    }


    public Domain(Domain d2) {
        //TODO make a copy of the domain from what d2 contains
        vals = new int[d2.vals.length];
        for(int i = 0; i < vals.length; i++)
            this.vals[i] = d2.vals[i];
    }

    /**
     * @return
     */
    public String toString() {
        String result  = "{";
        for (int i = 0; i < vals.length; i++)
            result += vals[i];
        result += "}";
        return result;
    }

    /**
     * @return
     */
     
    protected Domain[] split() {
        if(vals.length==1){
            int[]array1={};
            int[]array2={vals[0]};
            Domain d1=new Domain(array1);
            Domain d2=new Domain(array2);
            Domain[]arr={d1,d2};
            return arr;
        }
        int num=vals.length/2;
        int []firstHalfVals=new int[num];
        int[] secondHalfVals=new int[vals.length-num];
        int Idx=0;
        while(Idx<vals.length){
            for(int i=0;i<firstHalfVals.length;i++){
                firstHalfVals[i]=vals[Idx];
                Idx++;

            }
            for(int i=0;i<secondHalfVals.length;i++){
                secondHalfVals[i]=vals[Idx];
                Idx++;
            }
            
        }
        Domain d1=new Domain(firstHalfVals);
        Domain d2=new Domain(secondHalfVals);
        Domain[] domains={d1,d2};
        return domains;
    }
    

    /**
     * @return
     */
    public boolean isEmpty() {
        if(vals.length==0){
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    private boolean equals(Domain d2) {
           if(vals.length!=d2.vals.length){
            return false;
           }
            for(int i=0;i<vals.length;i++){
                if(vals[i]!=d2.vals[i]){
                   return false;
                }
            }
            return true;
           
    }

    /**
     * @return
     */
    private boolean  isReducedToOnlyOneValue() {
        if(vals.length==1){
            return true;
        }
        return false;
    }



}
