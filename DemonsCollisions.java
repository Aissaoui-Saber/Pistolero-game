package sample;

import java.util.ArrayList;

public class DemonsCollisions {
    private ArrayList<DemonsCollisionIndex> collisions;
    public DemonsCollisions(){
        collisions = new ArrayList();
    }
    public void add(int index1,int index2){
        collisions.add(new DemonsCollisionIndex(index1,index2));
    }
    public boolean exists(int index1,int index2){
        int i=0;
        while (i<this.collisions.size()){
            if (collisions.get(i).getValueOne() == index1 || collisions.get(i).getValueOne() == index2){
                if (collisions.get(i).getValueTwo() == index1 || collisions.get(i).getValueTwo() == index2){
                    return true;
                }
            }
            i++;
        }
        return false;
    }
    public void destroyOneDemon(ArrayList<Demon> demons){
        demons.get(collisions.get(collisions.size()-1).valueOne).blesser(1000);
    }
    public void changeDirection(ArrayList<Demon> demons,int M,int N){
        //demons.get(M).changeDirection();
        //demons.get(N).changeDirection();
        collisions.remove(collisions.size()-1);
    }
    public void remove(int index1,int index2){
        int i=0;
        while (i<this.collisions.size()){
            if (collisions.get(i).getValueOne() == index1 || collisions.get(i).getValueOne() == index2){
                if (collisions.get(i).getValueTwo() == index1 || collisions.get(i).getValueTwo() == index2){
                    collisions.remove(i);
                    break;
                }
            }
            i++;
        }
    }

    private class DemonsCollisionIndex{
        private int valueOne;
        private int valueTwo;

        public DemonsCollisionIndex(int v1,int v2){
            this.valueOne = v1;
            this.valueTwo = v2;
        }

        public int getValueOne() {
            return valueOne;
        }
        public int getValueTwo(){
            return valueTwo;
        }

        public void setValueOne(int valueOne) {
            this.valueOne = valueOne;
        }

        public void setValueTwo(int valueTwo) {
            this.valueTwo = valueTwo;
        }
    }
}
