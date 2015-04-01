/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    
    /**
     * 
     * @param x
     * @param y 
     */
    public Node(int x, int y, Node goal) {
        this.x       = x;
        this.y       = y;
        this.hood    = new ArrayList<>();
        this.goal    = goal;
    }
        
    
    /**
     * This constructor gets used by the goal node only.
     * @param x
     * @param y 
     */
    
    public Node(int x, int y) {
        this.x       = x;
        this.y       = y;
        this.hood    = new ArrayList<>();
    }

    /**
     * 
     */
    public void print() {
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("cost of movement: " + costOfMovement);
        System.out.println("cost to goal: " + costToGoal);            
    }
        
        
    /**
     * 
     * @param current 
     */
    public void buildNeighborhood() {
        
        hood.add(new Node(x, y-1, goal));
        hood.add(new Node(x+1, y-1, goal));
        hood.add(new Node(x+1, y, goal));
        hood.add(new Node(x+1, y+1, goal));
        hood.add(new Node(x, y+1, goal));
        hood.add(new Node(x-1, y+1, goal));
        hood.add(new Node(x-1, y, goal));
        hood.add(new Node(x-1, y-1, goal));
            
        // Make sure the neighbors have their goal.
        for (Node n : hood) {
            n.setParent(this);
            
            int cost = this.calculateCostToGoal(n);
            n.setCostToGoal(cost);
            
            if (n.getX() == x) {
                n.setMovementCost(10);
            }
            else {
                n.setMovementCost(15);
            }
        }
    }
        
        
    /**
     * 
     * @param other
     * @return 
     */
    private int calculateCostToGoal(Node node) {
        int diffX = Math.abs(goal.getX() - node.getX());
        int diffY = Math.abs(goal.getY() - node.getY());
        
        return (diffX + diffY) * 10;
    }

            
        
    /**
     * 
     * @return 
     */
    public int getX() {
        return x;
    }
        
    /**
     * 
     * @return 
     */
    public int getY() {
        return y;
    }
        
    /**
     * 
     * @return 
     */
    public List<Node> getNeighborhood() {
        return hood;
    }
        

    /**
     * 
     * @return 
     */
    public int getSizeOfNeighborhood() {
        return hood.size();            
    }

        
    /**
     * 
     * @param cost 
     */
    public void setMovementCost(int cost) {
        this.costOfMovement = cost;
    }
        
        
    /**
     * 
     * @return 
     */
    public int getMovementCost() {
        return this.costOfMovement;
    }
        
        
        
    /**
     * 
     * @param parent 
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }
        

    /**
     * 
     * @return 
     */
    public int getTotalCost() {
        return this.costOfMovement + this.costToGoal;
    }
        

    /**
     * 
     * @param cost 
     */
    public void setCostToGoal(int cost) {
        this.costToGoal = cost;
    }
        
        
    /**
     * 
     * @return 
     */
    public int getCostToGoal() {
        return this.costToGoal;
    }
        
        
    /**
     * 
     * @return 
     */
    public boolean isPartOfWall() {
        return (((x >= 2) && (x <= 5) && (y == 3))
                || (x < 0)
                || (x > 7)
                || (y < 0)
                || (y > 7)
               );        
    }
        
        
    /**
     * 
     * @param other
     * @return 
     */
    @Override
    public int compareTo(Node other) {
        int otherCost = other.getTotalCost();
        // ascending order
        return this.getTotalCost() - otherCost;            
    }

        
    /**
     * 
     * @param other
     * @return 
     */
    @Override
    public boolean equals(Object other) {
        boolean isEqual = false;
            
        if (other instanceof Node) {
            Node castOther = (Node) other;
            if ((this.x != castOther.getX()) 
                    || (this.y != castOther.getY())) {
            } else {
                isEqual = true;
            }
                
        }           
            
        return isEqual;
    }

        
    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        return hash;
    }
        
    private final int           x;
    private final int           y;
    private final List<Node>    hood;
    private int                 costOfMovement;
    private int                 costToGoal;
    private Node                parent;
    private Node                goal;
}