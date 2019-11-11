package com.ylt.Interface;

import com.ylt.Impl.ProductFeignServiceFallbackFactory;
import com.ylt.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-product",fallbackFactory = ProductFeignServiceFallbackFactory.class)
public interface ProductFeignService {

    @RequestMapping("/list")
    String list();

    @RequestMapping(value = "data/list",method = RequestMethod.POST)
    String list(@RequestParam(name = "nowPage") Integer nowPage,
                   @RequestParam(name = "pageSize") Integer pageSize);

    @RequestMapping(value = "data/delMore",method = RequestMethod.POST)
    String delMore(@RequestParam(name = "ids") Long[] ids);

    @RequestMapping(value = "data/delOne", method = RequestMethod.POST)
    String delOne(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "data/add", method = RequestMethod.POST)
    String add(@RequestBody Product product);

    @RequestMapping(value = "data/update", method = RequestMethod.POST)
    String update(@RequestParam(name = "ids") Long[] ids,@RequestParam(name = "num") Long num);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    List<Object> search(@RequestParam(name = "keyword") String keyword);

}