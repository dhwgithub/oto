$(function() {
    var productId = getQueryString('productId');
    var infoUrl = '/oto/shopadmin/getproductbyid?productId=' + productId;
    var categoryUrl = '/oto/shopadmin/getproductcategorylist';
    var productPostUrl = '/oto/shopadmin/modifyproduct';

    var isEdit = false;
    if (productId) {
        getInfo(productId);
        isEdit = true;
    } else {
        getCategory();
        productPostUrl = '/oto/shopadmin/addproduct';
    }

    function getInfo(id) {
        $.getJSON(
                infoUrl,
                function(data) {
                    if (data.success) {
                        var product = data.product;
                        $('#product-name').val(product.productName);
                        $('#product-desc').val(product.productDesc);
                        $('#priority').val(product.priority);
                        $('#normal-price').val(product.normalPrice);
                        $('#promotion-price').val(product.promotionPrice);

                        var optionHtml = '';
                        var optionArr = data.productCategoryList;
                        var optionSelected = product.productCategory.productCategoryId;

                        optionArr.map(function(item, index) {
                                var isSelect = optionSelected == item.productCategoryId ?
                                    'selected' : '';
                                optionHtml += '<option data-value="'
                                    + item.productCategoryId
                                    + '"'
                                    + isSelect
                                    + '>'
                                    + item.productCategoryName
                                    + '</option>';
                        });
                        $('#category').html(optionHtml);
                    }
                });
    }

    function getCategory() {
        $.getJSON(categoryUrl, function(data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = '';
                productCategoryList.map(function(item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    // 针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片）
    // 且控件总数未达到6个，则生成新的一个文件上传控件
    $('.detail-img-div').on('change', '.detail-img:last-child', function() {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('</Br><input type="file" class="detail-img">');
        }
    });

    $('#submit').click(
        function() {
            var product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#priority').val();
            product.normalPrice = $('#normal-price').val();
            product.promotionPrice = $('#promotion-price').val();
            product.productCategory = {
                productCategoryId : $('#category').find('option').not(
                    function() {
                        return !this.selected;
                    }).data('value')
            };
            product.productId = productId;

            // 获取缩略图文件流（单个）
            var thumbnail = $('#small-img')[0].files[0];
            // 生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            formData.append('thumbnail', thumbnail);
            // 多张图片获取方式
            $('.detail-img').map(
                function(index, item) {
                    if ($('.detail-img')[index].files.length > 0) {
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                }
            );

            // 传给后台其他信息，如上是获取信息（不包括文件流）
            formData.append('productStr', JSON.stringify(product));

            // 获取表单输入的验证码
            var verifyCodeActual = $('#j_captcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);

            $.ajax({
                url : productPostUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        $.toast('提交成功！');
                    } else {
                        $.toast(data.errMsg);
                    }
                    $('#captcha_img').click();
                }
            });
        }
    );

});