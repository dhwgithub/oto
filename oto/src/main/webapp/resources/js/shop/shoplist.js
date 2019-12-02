
$(function () {

    getlist();

    function getlist(e) {
        $.ajax({
            url : '/oto/shopadmin/getshoplist',
            type : 'GET',
            dataType : 'json',
            success : function (data) {
                if (data.success){
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }

    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    function handleList(data) {
        var html = '';
        data.map(function (item, index) {
            html += '<div class="row row-shop"><div class="col-40">'
                    + item.shopName + '</div><div class="col-40">'
                    + shopStatus(item.enableStatus)
                    + '</div><div class="col-20">'
                    + goShop(item.enableStatus, item.shopId)
                    + '</div></div>';
        });
        $('.shop-wrap').html(html);
    }

    /**
     * 显示状态
     * @param status
     * @returns {string}
     */
    function shopStatus(status) {
        if(status == 0){
            return '审核中';
        }else if (status == -1) {
            return '店铺非法';
        }else if (status == 1) {
            return '审核通过';
        }else {
            return '未定义';
        }
    }

    /**
     * 进入商铺
     * @param status
     * @param id
     * @returns {string}
     */
    function goShop(status, id) {
        if (status == 1){
            return '<a href="/oto/shopadmin/shopmanage?shopId=' + id + '">进入</a>';
        }else {
            return '';
        }
    }
})
