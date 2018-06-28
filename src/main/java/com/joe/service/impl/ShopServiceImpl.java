package com.joe.service.impl;

import com.joe.dao.ShopDao;
import com.joe.dto.ShopExecution;
import com.joe.entity.Shop;
import com.joe.enums.ShopStateEnum;
import com.joe.exceptions.ShopOperationException;
import com.joe.service.ShopService;
import com.joe.util.ImageUtil;
import com.joe.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        //空值判断
        if(shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum <=0) {
                throw new ShopOperationException("店铺创建失败");
            }else {
                if(shopImgInputStream !=null) {
                    //存储图片
                    try {
                        addShopImg(shop, shopImgInputStream, fileName);
                    }catch (Exception e) {
                        // TODO: handle exception
                        throw new ShopOperationException("addShopImg error"+e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <=0) {
                        throw new  ShopOperationException("更新图片地址失败");
                    }

                }
            }
        }catch (Exception e) {
            // TODO: handle exception
            throw new ShopOperationException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
        // 获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName, dest);
        shop.setShopImg(shopImgAddr);
    }
}
