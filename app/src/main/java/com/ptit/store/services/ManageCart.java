package com.ptit.store.services;

import com.ptit.store.models.Cart;
import com.ptit.store.models.Item;

import java.util.ArrayList;

/**
 * Created by KingIT on 4/29/2018.
 */

public class ManageCart {
    private static Cart cart;
    public static Cart getCart(){
        if(cart==null){
            cart= new Cart(new ArrayList<Item>(), 0);
        }
        return cart;
    }

}
