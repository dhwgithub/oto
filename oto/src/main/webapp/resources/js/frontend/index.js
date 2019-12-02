$(function() {
    // 获取头条列表以及一级类别列表的URL
    var url = '/oto/frontend/listmainpageinfo';
    // 访问后台
    $.getJSON(url, function (data) {
        if (data.success) {
            var headLineList = data.headLineList;
            var swiperHtml = '';
            headLineList.map(function (item, index) {
                swiperHtml += ''
                    + '<div class="swiper-slide img-wrap">'
                    + '<a href="' + item.lineLink
                    + '"external><img class="banner-img" src="'+ item.lineImg
                    +'" alt="' + item.lineName +'"></a>>'
                    + '</div>';
            });
            $('.swiper-wrapper').html(swiperHtml);
            $(".swiper-container").swiper({
                // 设定轮播图转化时间为3秒
                autoplay: 3000,
                // 用户对轮播图进行操作时，是否自动停止autoplay
                autoplayDisableOnInteraction: false
            });

            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            shopCategoryList.map(function (item, index) {
                categoryHtml += ''
                    +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                    +      '<div class="word">'
                    +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                    +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                    +      '</div>'
                    +      '<div class="shop-classify-img-warp">'
                    +          '<img class="shop-img" src="'+ item.shopCategoryImg +'">'
                    +      '</div>'
                    +  '</div>';
            });
            $('.row').html(categoryHtml);
        }
    });

    // 点击显示侧栏
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    // 点击类别按钮
    $('.row').on('click', '.shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = '/oto/frontend/shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });

});
