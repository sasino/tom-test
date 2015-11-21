package testSimulator;

import java.util.ArrayList;

import junit.framework.TestCase;
import junitx.framework.ListAssert;

import org.junit.Test;

import simulator.CustomerGroup;
import simulator.StateEating;
import simulator.StateInQueue;
import simulator.StateWaitingFood;
import simulator.Table;

public class TestTable extends TestCase {
  @Override
  public void setUp() {}

  @Override
  public void tearDown() {}
  
  @Test
  public void testConstructor1() {
    Table table1 = new Table(2);
    assertTrue(table1 != null);
  }
  
  @Test
  public void testConstructor2() {
    Table table = new Table(4);
    assertTrue(table != null);
  }
  
  @Test
  public void testConstructor3() {
    Table table = new Table(8);
    assertTrue(table != null);
  }
  
  @Test 
  public void testConstructor4() {
    boolean thrown = false;
    try {
      new Table(0);
    } catch (IllegalArgumentException e) {
      thrown = true;
    } finally {
      assertTrue(thrown);
    }
  }
  
  @Test
  public void testAdd1() {
    Table table = new Table(4);
    int result = table.add(new CustomerGroup(1, 3, new StateInQueue()));
    assertEquals(0, result);
  }
  
  @Test
  public void testAdd2() {
    Table table = new Table(4);
    int result = table.add(new CustomerGroup(1, 5, new StateInQueue()));
    assertEquals(1, result);
  }
  
  @Test
  public void testAdd3() {
    Table table = new Table(4);
    table.add(new CustomerGroup(1, 3, new StateInQueue()));
    int result = table.add(new CustomerGroup(2, 3, new StateInQueue()));
    assertEquals(1, result);
  }
  
  @Test
  public void testRemove1() {
    Table table = new Table(4);
    int result = table.remove(new CustomerGroup(1, 3, new StateWaitingFood()));
    assertEquals(1, result);
  }
  
  @Test
  public void testRemove2() {
    Table table = new Table(4);
    CustomerGroup customer = new CustomerGroup(1, 3, new StateWaitingFood());
    table.add(customer);
    int result = table.remove(customer);
    assertEquals(0, result);
  }
  
  @Test
  public void testGetAvail() {
    Table table = new Table(8);
    table.add(new CustomerGroup(1, 3, new StateWaitingFood()));
    table.add(new CustomerGroup(2, 2, new StateEating()));
    table.add(new CustomerGroup(3, 2, new StateEating()));
    int result = table.getAvailable();
    assertEquals(3, result);
  }
  
  @Test
  public void testGetWatingCustomer() {
    Table table = new Table(8);
    CustomerGroup group1 = new CustomerGroup(1, 3, new StateWaitingFood());
    CustomerGroup group2 = new CustomerGroup(2, 3, new StateWaitingFood());
    CustomerGroup group3 = new CustomerGroup(3, 2, new StateEating());
    table.add(group1);
    table.add(group2);
    table.add(group3);
    ArrayList<CustomerGroup> expected = new ArrayList<CustomerGroup>();
    ArrayList<CustomerGroup> actual = new ArrayList<CustomerGroup>();
    expected.add(group1);
    expected.add(group2);
    actual = table.getWaitingCustomers();
    ListAssert.assertEquals(expected, actual);
  }
  
  @Test
  public void testClearTable() {
    Table table = new Table(4);
    CustomerGroup group1 = new CustomerGroup(1, 2, new StateWaitingFood());
    CustomerGroup group2 = new CustomerGroup(2, 2, new StateWaitingFood());
    table.add(group1);
    table.add(group2);
    ArrayList<CustomerGroup> expected = new ArrayList<CustomerGroup>();
    ArrayList<CustomerGroup> actual = new ArrayList<CustomerGroup>();
    table.clearTable();
    actual = table.getWaitingCustomers();
    ListAssert.assertEquals(expected, actual);
  }
}
