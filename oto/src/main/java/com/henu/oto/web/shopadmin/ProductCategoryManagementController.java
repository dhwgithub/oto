package com.henu.oto.web.shopadmin;

import com.henu.oto.dto.ProductCategoryExecution;
import com.henu.oto.dto.Result;
import com.henu.oto.entity.ProductCategory;
import com.henu.oto.entity.Shop;
import com.henu.oto.enums.ProductCategoryStateEnum;
import com.henu.oto.exceptions.ProductCategoryOperationExeption;
import com.henu.oto.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productCategoryId != null && productCategoryId > 0){
            try {
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                ProductCategoryExecution productCategoryExecution =
                        productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productCategoryExecution.getStateInfo());
                }
            }catch (ProductCategoryOperationExeption e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个商品类别");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
        // to be removed
        Shop shop = new Shop();
        shop.setShopId(69L);
        request.getSession().setAttribute("currentShop", shop);

        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0){
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        }else {
            ProductCategoryStateEnum productCategoryStateEnum = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,
                    productCategoryStateEnum.getState(), productCategoryStateEnum.getStateInfo());
        }
    }

    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategorys(HttpServletRequest request,
                                                @RequestBody List<ProductCategory> productCategoryList){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        for (ProductCategory productCategory : productCategoryList){
            productCategory.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size() > 0){
            try {
                ProductCategoryExecution productCategoryExecution =
                        productCategoryService.batchAddProductCategory(productCategoryList);
                if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productCategoryExecution.getStateInfo());
                }
            }catch (ProductCategoryOperationExeption e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }
}
