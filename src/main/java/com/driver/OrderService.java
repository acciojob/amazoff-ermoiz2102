package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderService {
@Autowired
    public OrderRepository orderRepositoryobj;
    public void addOrder(Order order){
        orderRepositoryobj.ordersList.put(order.getId(),order);
    }
    public void addPartner(String id){
        DeliveryPartner partner= new DeliveryPartner(id);
        orderRepositoryobj.partnerList.put(partner.getId(),partner);
    }
    public void addPair(String orderid,String partnerid){
        orderRepositoryobj.orderPair.put(orderid,partnerid);
        orderRepositoryobj.addDelivery(orderid,partnerid);

    }
    public Order getOrder(String id){
        return orderRepositoryobj.getOrderByorder(id);
    }
    public DeliveryPartner getPartner(String id){
        return orderRepositoryobj.getpartnerByPartner(id);
    }
    public int getOrders(String id){

        return orderRepositoryobj.getordersize(id);
    }
    public List<Order> getOrdersbypartner(String id){

        return orderRepositoryobj.getorderList(id);
    }
    public List<Order>getAllOrders(){
        return orderRepositoryobj.getAllOrders();
    }
    public List<DeliveryPartner>getAllPartners(){
        return orderRepositoryobj.getAllPartners();
    }
    public int getUnassigned(){
        List<Order>list=orderRepositoryobj.getAllOrders();
        int ct=0;
        for(Order order:list){
          if(!orderRepositoryobj.orderPair.containsKey(order.getId()))
              ct++;
        }
        return ct;
    }
    public int getorderspart(String time,String id){
        int ct=0;
        List<Order>list=orderRepositoryobj.getorderList(id);
        int nn=Integer.parseInt(time.substring(0,2))*60+Integer.parseInt(time.substring(3));
        for(Order order:list){
            if(order.getDeliveryTime()>nn)
                ct++;
        }
        return ct;
    }
    public String getTime(String id){
        try{
            List<Order>list=orderRepositoryobj.getorderList(id);
            int max=0;
            for(Order order:list)
                max=Math.max(max,order.getDeliveryTime());
            int hr=max/60;
            int mm=max%60;
            String hh="";
            String mn="";
            if(hr<=9)
                hh="0"+String.valueOf(hr);
            else
                hh=String.valueOf(hr);
            if(mm<=9)
                mn="0"+String.valueOf(mn);
            else
                mn=String.valueOf(mm);

            return hh+":"+mn;

        }catch(Exception e){
            return e.toString();
        }



       }
       public void deletePartner(String id){
        List<Order>list=orderRepositoryobj.getorderList(id);
        for(Order order:list){
            orderRepositoryobj.orderPair.remove(order.getId());
        }
        orderRepositoryobj.partnerList.remove(id);
        orderRepositoryobj.delivery.remove(id);

    }
    public void deleteOrder(String id){
          String ptid=orderRepositoryobj.orderPair.get(id);
          Order odd=orderRepositoryobj.ordersList.get(id);
          orderRepositoryobj.ordersList.remove(id);
          orderRepositoryobj.orderPair.remove(id);
          orderRepositoryobj.delivery.get(ptid).remove(odd);
    }

}
