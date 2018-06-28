/*
*
* */
$(function () {
    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';

    /*
	 * 获取店铺初始化信息：店铺分类和区域信息列表
	 */
    function getshopinitinfo() {
        $.getJSON(initUrl, function(data) {
            // 数据存在
            if (data.success) {
                var tempHtml = "";
                var tempAreaHtml = "";
                // 迭代店铺分类列表
                data.shopCategoryList.map(function(item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId
                        + '">' + item.shopCategoryName + '</option>';
                });
                // 迭代区域信息
                data.areaList.map(function(item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        })
    }

    /*
	 * 点击提交事件
	 */
    $("#submit").click(function() {
        var shop = {};
        shop.shopname = $("#shop-name").val();
        shop.shopAddr = $("#shop-addr").val();
        shop.phone = $("#shop-phone").val();
        shop.shopDesc = $("#shop-desc").val();
        shop.shopCategoty = {
            // 双重否定等于肯定
            shopCategoryId : $("#shop-category").find("option").not(function() {
                return !this.selected;
            }).data("id")
        };
        shop.area = {
            areaId : $("#area").find("option").not(function() {
                return !this.selected;
            }).data("id")
        };
        // 获取图片文件流
        var shopImg = $('#shop-img')[0].files[0];
        var formData = new formData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));
        var verifyCodeActual = $('#j_kaptcha').val();
        if (!verifyCodeActual) {
            $.toast("请输入验证码！");
            return;
        } else {
            formData.append('verifyCodeActual', verifyCodeActual);
        }
        $.ajax({
            url : registerShopUrl,
            type : "POST",
            data : formData,
            contentType : false,
            processType : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast("提交成功！");
                } else {
                    $.toast("提交失败！" + data.errorMsg);
                }
                // 更换验证码
                $('#kaptcha_img').click();
            }
        });
    });

});