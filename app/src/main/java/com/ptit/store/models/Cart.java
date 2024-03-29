package com.ptit.store.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by KingIT on 4/29/2018.
 */

public class Cart implements Serializable{
    private ArrayList<Item> items= new ArrayList<>();
    private int total;

    public Cart(ArrayList<Item> items, int total) {
        this.items = items;
        this.total = total;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void plusToCart(Item item){
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(item)){
                items.get(i).setCount(items.get(i).getCount()+1);
                return;
            }
        }
        items.add(item);
    }
    public void subToCart(Item item){
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(item)){
                if(items.get(i).getCount()>1){
                    items.get(i).setCount(items.get(i).getCount()-1);
                    return;
                } else{
                    items.remove(i);
                }
            }
        }
    }
    public void removeToCart(Item item){
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(item)){
                items.remove(i);
                break;
            }
        }
    }
    public int getTotalMoney(){
        int tong=0;
        for (int i = 0; i < items.size(); i++) {
            tong+= items.get(i).getCount()* items.get(i).getClothes().getPrice();
        }
        return tong;
    }
}
