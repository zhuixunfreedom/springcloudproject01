package com.ylt.serviceorder.controller;

import com.ylt.serviceorder.bean.Product;
import com.ylt.serviceorder.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/data")
public class DataController {

    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Object> list(@RequestParam(name = "nowPage") Integer nowPage,
                             @RequestParam(name = "pageSize") Integer pageSize) {

        List<Object> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(nowPage - 1, pageSize);
        Page<Product> all = productDao.findAll(pageable);

//        int getTotalPages() 总的页数
//        long getTotalElements() 返回总数
//        List getContent() 返回此次查询的结果集

        int totalPage = all.getTotalPages();
        List<Product> products = all.getContent();
        List<Integer> totalPages = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) totalPages.add(i);
        list.add(products);
        list.add(totalPages);
//        list.add(nowPage);
        return list;
    }


    @Transactional
    @RequestMapping(value = "/delMore", method = RequestMethod.POST)
    public String delMore(@RequestParam(name = "ids") Long[] ids) {
        for (Long id:ids){
            productDao.deleteById(id);
        }
//        System.out.println(Arrays.toString(ids));
        return "删除成功";
    }

    @RequestMapping(value = "/delOne", method = RequestMethod.POST)
    public String delOne(@RequestParam(name = "id") Long id) {
        productDao.deleteById(id);
        return "删除成功";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Product product) {
        if (product.getPdate()==null) product.setPdate(LocalDate.now());
        productDao.save(product);
        return "添加成功";
    }
    @Transactional
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(name = "ids") Long[] ids,@RequestParam(name = "num") Long num) {
        for (Long id:ids) {
            Product product = productDao.findById(id).get();
            product.setPflag(num);
            productDao.save(product);
        }
//        如果对应的id不存在，save方法则为insert,否则update

        return "更新成功";
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Object> search(@RequestParam(name = "keyword") String keyword) {

        List<Object> list = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> all = productDao.findByPnameLike("%" + keyword + "%", pageable);

        int totalPage = all.getTotalPages();
        List<Product> products = all.getContent();
        list.add(products);
        list.add(totalPage);
        return list;
    }
}