package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository

public class OrderRepository {
    HashMap<String,Order> ordersList= new HashMap<>();
    HashMap<String,DeliveryPartner>partnerList=new HashMap<>();
    HashMap<String,String>orderPair= new HashMap<>();
    HashMap<String, Set<Order>>delivery= new HashMap<>();



  public List<Order> getAllOrders(){
      return ordersList.values().stream().toList();
  }
    public List<DeliveryPartner> getAllPartners(){
        return partnerList.values().stream().toList();
    }
  public Order getOrderByorder(String id){
      return ordersList.get(id);
  }
  public DeliveryPartner getpartnerByPartner(String id){
      return partnerList.get(id);
  }
  public int getordersize(String id){
      return partnerList.get(id).getNumberOfOrders();
  }
  public List<Order> getorderList(String id){
      return (List<Order>) delivery.get(id).stream().toList();
  }

 public void addDelivery(String id1,String id2){
      if(delivery.containsKey(id2)){
          delivery.get(id2).add(getOrderByorder(id1));
      }
      else{
          HashSet<Order>hs=new HashSet<>();
          hs.add(getOrderByorder(id1));
          delivery.put(id2,hs);
      }
      DeliveryPartner part=getpartnerByPartner(id2);
      part.setNumberOfOrders(delivery.get(id2).size());
 }

}
