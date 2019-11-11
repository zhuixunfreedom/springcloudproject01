package com.ylt.Impl;

import com.ylt.Interface.ProductFeignService;
import com.ylt.bean.Product;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFeignServiceFallbackFactory implements FallbackFactory<ProductFeignService> {

    @Override
    public ProductFeignService create(Throwable throwable) {
        return new ProductFeignService() {

//            页面返回信息熔断
            @Override
            public String list() {
                return "(熔断)";
            }
//            数据返回空值
            @Override
            public String list(Integer nowPage, Integer pageSize) {
                return null;
            }

            @Override
            public String delMore(Long[] ids) {
                return null;
            }

            @Override
            public String delOne(Long id) {
                return null;
            }

            @Override
            public String add(Product product) {
                return null;
            }

            @Override
            public String update(Long[] ids, Long num) {
                return null;
            }

            @Override
            public List<Object> search(String keyword) {
                return null;
            }
        };
    }
}
