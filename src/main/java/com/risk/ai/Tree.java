package com.risk.ai;

import com.risk.game.GameState;
import com.risk.objects.Country;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Tree to evaluate the best move for the AI.
 * @author hneumann
 */
public class Tree {

  private static final int max_depth = 5;
  private final Node root;

  /**
   * Constructor of Tree class that creates a tree with a given root.
   *
   * @param root of the tree
   * @author hneumann
   */
  public Tree(Node root) {
    this.root = root;
    createTree(root);
  }

  /**
   * Method that checks if an attack move is legal.
   *
   * @param start  country that attacks
   * @param target country that gets attacked
   * @return true if move is legal, false if not
   * @author hneumann
   */
  public static boolean checkMove(Country start, Country target) {
    if (target.getOwner().equals(start.getOwner())
        || start.getTroops() == target.getTroops()) {
      return false;
    }
    return start.getTroops() - target.getTroops() > 1;
  }

  /**
   * Method that creates a tree with a given root.
   *
   * @param root of the tree
   * @author hneumann
   */
  public void createTree(Node root) {
    try {
      GameState gameState = root.getGameState();
      //TODO remove after testing
      if (root.getDepth() < max_depth) {

        GameState result = gameState.cloneGameState();
        List<Country> countries = result.getCountriesOwnedByPlayer(
            gameState.getCurrentPlayerKey());
        for (Country c : countries) {
          Country country = result.getCountryByName(c.getName());
          Country[] neighbours = country.getNeighbours();
          for (Country n : neighbours) {
            Country neighbour = result.getCountryByName(n.getName());
            if (checkMove(gameState.getCountryByName(country.getName()),
                gameState.getCountryByName(neighbour.getName()))) {
              //TODO remove after testing
              result.updateCountryOwner(result.getCountryByName(neighbour.getName()),
                  gameState.getCurrentPlayerKey(),
                  gameState.getCountryByName(country.getName()).getTroops()
                      - gameState.getCountryByName(neighbour.getName()).getTroops());

              Node child = new Node(result, root, root.getDepth() + 1, country, neighbour);
              root.addChildren(child);
            }
          }
        }
        for (Node node : root.getChildren()) {
          createTree(node);
        }
      } else {
        root.setChildren(null);
      }
    } catch (Exception e) {
      //ignore
    }
  }

  /**
   * Method that calculates the best move by finding the way to the leaf with the highest score.
   *
   * @return the path from the root to the leaf with the highest score as a list of nodes
   * @author hneumann
   */
  public List<Node> calcBestMove() {
    //method that finds the leaf of the tree with the highest score
    // and return the path from the root to this leaf as a list
    List<Node> bestPath = new ArrayList<>();
    Node bestNode = findBestLeaf(root);
    while (bestNode.getParent() != null) {
      bestPath.add(bestNode);
      bestNode = bestNode.getParent();
    }
    bestPath.add(bestNode);
    bestPath.sort(Comparator.comparingInt(Node::getDepth));
    return bestPath;
  }

  /**
   * Method that finds the leaf with the best score and returns it.
   *
   * @param node the node to start the search from
   * @return the best leaf
   * @author hneumann
   */
  private Node findBestLeaf(Node node) {
    //TODO implement different difficulty
    // Method that finds the leaf with the best score and returns it
    //
    // .out.println("FindBestLeaf: " + node.  toString());
    Node bestNode = node;
    if (node.hasChild()) {
      Iterator<Node> nodeIterator = node.getChildren().iterator();
      for (int i = 0; i < max_depth; i++) {
        if (!nodeIterator.hasNext()) {
          break;
        }
        Node tmp = findBestLeaf(nodeIterator.next());
        if (tmp.getScore() > bestNode.getScore()) {
          bestNode = tmp;
        }
      }
      return bestNode;
    }
    return node;
  }

  /**
   * Method that calculates the score of a node.
   *
   * @param node the node to calculate the score of
   * @return the node with the calculated score
   * @author hneumann
   */
  private Node calcScores(Node node) {
    Node bestNode = node;
    bestNode.setScore(0d);
    if (node.hasChild()) {
      Iterator<Node> nodeIterator = node.getChildren().iterator();
      for (int i = 0; i < max_depth; i++) {
        if (!nodeIterator.hasNext()) {
          break;
        }
        Node tmp = calcScores(nodeIterator.next());
        if (tmp.getScore() > bestNode.getScore()) {
          bestNode = tmp;
        }
      }
      return bestNode;
    }
    return node;
  }

  /**
   * Method that returns the root of the tree.
   *
   * @return the root of the tree
   * @author hneumann
   */
  public Node getRoot() {
    return root;
  }

}
