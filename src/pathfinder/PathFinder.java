/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author bpyne
 */
public class PathFinder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PathFinder finder = new PathFinder();
        finder.run();
    }


    
    /**
     * 
     */
    public PathFinder() {
        open   = new ArrayList<>();
        closed = new ArrayList<>();
        goal   = new Node(4,5);
        start  = new Node(4,1);
        found  = false;       
    }
    
    
    /**
     * 
     */
    public void run() {
        Node current = start;
        open.add(current);
        
//        while (! found) {
            // Temporarily impose a 100 iteration limit.
            for (int i=0; i < 100; i++) {
                
            Collections.sort(open);
            current = open.get(0);
            
            
            current.print();
            
            open.remove(current);
            closed.add(current);
            
            if (current.equals(goal)) {
                found = true;
                System.out.println("INFO: Found goal " + current.getX() + "," + current.getY());
            }
            else {
                this.buildNeighborhood(current);
                addNeighborsToOpen(current);                
            }
                
            }
 //       }
        
    }
    
    
    /**
     * 
     * @param list
     * @param node
     * @return 
     */
    private boolean isInList(List<Node> list, Node node) {
        boolean inList = false;
        
        for (Node n : list) {
            if (n == node) {
                inList = true;
                break;
            }
        }
        
        return inList;        
    }

    
    /**
     * 
     * @param neighbors 
     */
    private void addNeighborsToOpen(Node current) {
        List<Node> neighbors = current.getNeighborhood();
        
        for (Node n : neighbors) {
            if (n.isPartOfWall() || isInList(closed, n)) {
            }
            else if (! isInList(open, n)) {
                n.setParent(current);
                open.add(n);
            }
            else {
                int cost = 10;
                
                if (n.getX() != current.getX()) {
                    cost = 15;
                }
                
                if (cost < n.getMovementCost()) {
                    n.setParent(current);
                    n.setMovementCost(cost);
                }
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
     * @param current 
     */
    private void buildNeighborhood(Node current) {
        List<Node> hood = current.getNeighborhood();
        int        x    = current.getX();
        int        y    = current.getY();
        
        hood.add(new Node(x, y-1));
        hood.add(new Node(x+1, y-1));
        hood.add(new Node(x+1, y));
        hood.add(new Node(x+1, y+1));
        hood.add(new Node(x, y+1));
        hood.add(new Node(x-1, y+1));
        hood.add(new Node(x-1, y));
        hood.add(new Node(x-1, y-1));
            
        // Make sure the neighbors have their goal.
        for (Node n : hood) {
            n.setParent(current);
            
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
        

    
    
    private final List<Node> open;
    private final List<Node> closed;
    private final Node       start;
    private final Node       goal;
    private       boolean    found;    
}
