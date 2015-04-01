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
        start  = new Node(4,1, goal);
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
                current.buildNeighborhood();
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

    
    
    private final List<Node> open;
    private final List<Node> closed;
    private final Node       start;
    private final Node       goal;
    private       boolean    found;    
}
