package com.risk.util;

import java.util.ArrayList;

public class CircularList<E> {

  private final ArrayList<E> values = new ArrayList<>();
  private E current = null;

  /**
   * Appends the specified element to the end of this list. Sets current if wasn't set before.
   *
   * @param e - element to be appended to this list
   * @return true (as specified by Collection.add(E))
   * @author floribau
   */
  public boolean add(E e) {
    if (current == null) {
      current = e;
    }
    return this.values.add(e);
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index - index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >=
   *                                   size())
   * @author floribau
   */
  public E get(int index) throws IndexOutOfBoundsException {
    return this.values.get(index);
  }

  /**
   * Replaces the element at the specified position in this list with the specified element.
   * Replaces current if it was the value on the specified position.
   *
   * @param index   - index of the element to replace
   * @param element - element to be stored at the specified position
   * @return the element previously at the specified position
   * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >=
   *                                   size())
   * @author floribau
   */
  public E set(int index, E element) throws IndexOutOfBoundsException {
    if (this.get(index).equals(this.current)) {
      this.current = element;
    }
    return this.values.set(index, element);
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the lowest index i such that
   * Objects.equals(o, get(i)), or -1 if there is no such index.
   *
   * @param e - element to search for
   * @return the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element
   * @author floribau
   */
  public int indexOf(E e) {
    return this.values.indexOf(e);
  }

  /**
   * Removes the element at the specified position in this list. Shifts any subsequent elements to
   * the left (subtracts one from their indices). Sets current to the next element if current is
   * removed.
   *
   * @param index - the index of the element to be removed
   * @return the element that was removed from the list
   * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >=
   *                                   size())
   * @author floribau
   */
  public E remove(int index) throws IndexOutOfBoundsException {
    int i = this.indexOf(current);
    if (this.size() == 1) {
      this.current = null;
    } else if (i == index) {
      try {
        this.next();
      } catch (Exception e) {
        //ignore
      }
    }
    return this.values.remove(index);
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return the number of elements in this list
   * @author floribau
   */
  public int size() {
    return this.values.size();
  }

  /**
   * Returns the current element of the list.
   *
   * @return the current element of the list
   * @author floribau
   */
  public E getCurrent() {
    return current;
  }

  /**
   * sets current element to e.
   *
   * @param e - element to set current to
   * @author floribau
   */
  public void setCurrent(E e) {
    this.current = e;
  }


  /**
   * sets current to the list element coming after current.
   *
   * @return updated current element
   * @author floribau
   */
  public E next() {
    if (this.current == null) {
      return null;
    }
    int index = this.indexOf(this.current);
    index++;
    if (index == this.size()) {
      index = 0;
    }
    this.current = this.get(index);
    return this.current;
  }
}
