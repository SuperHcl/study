package com.supergo.common.pojo;

import java.io.Serializable;
import java.util.List;

public class MergeCart implements Serializable {

    private List<Cart> redisCarts;

    private List<Cart> carts;

    public List<Cart> getRedisCarts() {
        return redisCarts;
    }

    public void setRedisCarts(List<Cart> redisCarts) {
        this.redisCarts = redisCarts;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
