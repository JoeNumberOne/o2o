package com.joe.service;

import com.joe.dto.ShopExecution;
import com.joe.entity.Shop;

import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName);
}
