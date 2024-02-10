package com.risk.ai;

import com.risk.game.GameState;
import com.risk.objects.Country;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Node for the tree search algorithm. Each node contains a GameState, a parent Node, a score, a
 * depth, a TreeSet of child Nodes and the start and target country for the move that led to this
 * node.
 *
 * @author hneumann
 */
public class Node implements Comparable {

  private final GameState gameState;
  private final int depth;
  private final Country startCountry;
  private final Country targetCountry;
  private double score;
  private TreeSet<Node> children;
  private Node parent;

  /**
   * Constructor for the root node.
   *
   * @param gameState GameState of the root node
   * @param depth     depth of the root node
   * @author hneumann
   */

  public Node(GameState gameState, Node parent, int depth, Country startCountry,
      Country targetCountry) {

    this.gameState = gameState;
    this.parent = parent;
    this.score = com.risk.ai.util.Score.calculateScoring(gameState);
    this.depth = depth;
    this.children = new TreeSet<>(Comparator.comparingDouble(Node::getScore));
    this.targetCountry = targetCountry;
    this.startCountry = startCountry;
  }

  /**
   * Constructor for the root node.
   *
   * @param gameState GameState of the root node
   * @param depth     depth of the root node
   * @author hneumann
   */
  public Node(GameState gameState, int depth) {
    if (gameState == null) {
      throw new IllegalArgumentException("GameState can't be null");
    }
    this.gameState = gameState;
    this.parent = null;
    this.score = com.risk.ai.util.Score.calculateScoring(gameState);
    this.depth = depth;
    this.children = new TreeSet<>(Comparator.comparingDouble(Node::getScore));
    this.targetCountry = null;
    this.startCountry = null;

  }

  /**
   * Overridden toString method for debugging purposes. Prints the name of the current player, the
   * move that led to this node, the score and the depth of the node.
   *
   * @return String representation of the node for debugging purposes
   * @author hneumann
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Attacker ").append(this.gameState.getCurrentPlayerKey()).append(" ");
    if (this.startCountry != null) {
      sb.append("Move: ").append(this.startCountry.getName()).append(" -> ");
    } else {
      sb.append("Move: No move - start country is null (Node.toString())");
    }
    if (this.targetCountry != null) {
      sb.append(this.targetCountry.getName()).append("\n");
    } else {
      sb.append("No move - target country is null (Node.toString())");
    }

    sb.append(" Score: ").append(this.score);
    sb.append(" Depth: ").append(this.depth).append("\n");
    if (this.children != null) {
      sb.append("Children: ").append(this.children.size()).append("\n");
    }
    return sb.toString();
  }

  /**
   * Returns the target country of the move that led to this node.
   *
   * @author hneumann
   */
  public double getScore() {
    return score;
  }

  /**
   * Sets the score of the node.
   *
   * @param d score of the node
   * @author lkuech
   */
  public void setScore(double d) {
    this.score = d;
  }

  /**
   * Returns the target country of the move that led to this node.
   *
   * @return current GameState of the node
   * @author lkuech
   */
  public GameState getGameState() {
    return this.gameState;
  }

  /**
   * Returns the depth of the node.
   *
   * @return int depth of the node
   * @author hneumann
   */
  public int getDepth() {
    return depth;
  }

  /**
   * Adds a child to the node.
   *
   * @param node child node to be added to the node
   * @author hneumann
   */
  public void addChildren(Node node) {
    this.children.add(node);
    node.setParent(this);

  }

  /**
   * Returns the parent node of the node.
   *
   * @return Node parent node of the node
   * @author hneumann
   */
  public Node getParent() {
    return parent;
  }

  /**
   * Sets the parent node of the node.
   *
   * @param node parent node of the node
   * @author hneumann
   */
  private void setParent(Node node) {
    this.parent = node;
  }

  /**
   * Compares the score of the node to the score of another node. Overrides the compareTo method
   * to sort the nodes in descending order.
   *
   * @param o the object to be compared.
   * @return int 1 if the score of the node is smaller than the score of the other node, 0 if the
   * scores are equal, -1 if the score of the node is bigger than the score of the other node
   * @author hneumann
   */
  @Override
  public int compareTo(Object o) {
    Node node = (Node) o;
    if (this.score - node.getScore() < 0) {
      return 1;
    } else if (this.score - node.getScore() == 0) {
      return 0;
    }
    return -1;
  }

  /**
   * Returns a boolean indicating whether the node has children or not.
   *
   * @return boolean indicating whether the node has children or not
   * @author hneumann
   */
  public boolean hasChild() {
    try {
      if (this.children == null) {
        return false;
      }
    } catch (NullPointerException e) {
      return false;
    }
    return this.children.size() > 0;
  }

  /**
   * Returns the children of the node.
   *
   * @return TreeSet children of the node (sorted in descending order by score) (Node.getChildren())
   * @throws NullPointerException if the node has no children (Node.getChildren())
   * @author hneumann
   */
  public TreeSet<Node> getChildren() {
    return this.children;
  }

  /**
   * Sets the children of the node.
   *
   * @param children children of the node (Node.setChildren())
   * @author hneumann
   */
  public void setChildren(TreeSet<Node> children) {
    this.children = children;
  }

  /**
   * Returns the child node with the highest score.
   *
   * @return Node child node with the highest score (Node.getBestChild())
   * @author hneumann
   */
  public Node getBestChild() {
    //returns the child Node with
    // the highest score
    if (this.children.isEmpty()) {
      return null;
    }
    return this.children.first();
  }

  /**
   * Returns the country from which the move is made.
   *
   * @return start country
   * @author hneumann
   */
  public Country getFrom() {
    return this.startCountry;
  }

  /**
   * Returns the target country of the move.
   *
   * @return target country
   * @author hneumann
   */
  public Country getTo() {
    return this.targetCountry;
  }
}
