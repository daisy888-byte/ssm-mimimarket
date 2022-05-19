package com.bjpn.controller;

import com.bjpn.pojo.ProductInfo;
import com.bjpn.service.ProductService;
import com.bjpn.utils.FileNameUtil;
import com.bjpn.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/prod")
public class ProductController {
    String newFilename="";
    public static final int PAGE_SIZE=5;
    @Resource
    private ProductService productService;
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo<ProductInfo> info = productService.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }
    @RequestMapping("/ajaxSplitPage")
    public @ResponseBody void ajaxSplitPage(ProductInfoVo vo, HttpServletRequest request) {
       PageInfo<ProductInfo> info= productService.splitPageVo(vo,PAGE_SIZE);
       request.getSession().setAttribute("info",info);

    }
    @RequestMapping("/ajaxImgUpload")
    public @ResponseBody Object ajaxImgUpload(MultipartFile pimage,HttpServletRequest request){
        newFilename = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
       /* System.out.println("==========="+File.pathSeparator);//  ===========:
        System.out.println("==========="+File.separator);//===========/    */

        try {
            pimage.transferTo(new File(request.getServletContext().getRealPath("/image_big")+File.separator+newFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject();
        object.put("filename",newFilename);
        return object.toString();
    }

    @RequestMapping("/saveProduct")
    public  String saveProduct(ProductInfo productInfo,HttpServletRequest request){
        productInfo.setpImage(newFilename);
        productInfo.setpDate(new Date());
        int num = 0;
        try {
            num = productService.saveProduct(productInfo);
            if(num>0){
                request.setAttribute("msg","保存成功！");
                newFilename="";
            }else{
                request.setAttribute("msg","保存失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:/prod/split.action";

    }
    @RequestMapping("/updateProduct")
    public  String updateProduct(ProductInfoVo vo,Integer pid,HttpServletRequest request){

        request.getSession().setAttribute("productVo",vo);
        ProductInfo productInfo = productService.selectById(pid);
        request.setAttribute("prod",productInfo);
        return "update";
    }
    @RequestMapping("/updateSaveProduct")
    public  String updateSaveProduct(ProductInfo productInfo,HttpServletRequest request){
        if(newFilename!=""){
            productInfo.setpImage(newFilename);
        }

        int num = 0;
        try {
//            num = productService.saveProduct(productInfo);
            num = productService.updateById(productInfo);
            if(num>0){
                request.setAttribute("msg","更新成功！");
                newFilename="";
            }else{
                request.setAttribute("msg","更新失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:/prod/splitPage.action";
    }
    @RequestMapping("/splitPage")
    public  String splitPage(HttpServletRequest request){
           PageInfo<ProductInfo> info;
           ProductInfoVo vo = (ProductInfoVo) request.getSession().getAttribute("productVo");
           if(vo!=null){
               info =  productService.splitPageVo(vo,PAGE_SIZE);
               request.setAttribute("productVo",vo);

//               System.out.println("============"+vo.getPName());
              request.getSession().removeAttribute("productVo");
           }else {
               info = productService.splitPage(1,PAGE_SIZE);
           }

//            ProductInfo productInfo = new ProductInfo();
//           productInfo.setpContent("这是一个内容测试。");
//
//           request.setAttribute("product",productInfo);
           request.setAttribute("info",info);
           return "product";
    }

    @RequestMapping("/deleteBatch")
    public  String deleteBatch(String pids,ProductInfoVo vo,HttpServletRequest request){
        //data:{"pids":pids,"pName":pName,"typeId":typeId,"lprice":lprice,"hprice":hprice,"page":page},
        String []ps = pids.split(",") ;
        request.getSession().setAttribute("productVo",vo);
        int num = productService.deleteBatch(ps);
        if(num>0){
            request.setAttribute("msg","批量删除成功！");

        }else {
            request.setAttribute("msg","批量删除失败！");
        }
        return "forward:/prod/ajaxSplitPage2.action";
    }
    @RequestMapping(value = "/ajaxSplitPage2",produces = "text/html;charset=utf-8")
    public @ResponseBody Object ajaxSplitPage2(HttpServletRequest request){
        PageInfo<ProductInfo> info;
        ProductInfoVo vo = (ProductInfoVo) request.getSession().getAttribute("productVo");
        if(vo!=null){
            info =  productService.splitPageVo(vo,PAGE_SIZE);
            if(info.getList().size()==0 && vo.getPage()>1){ //判断如果当前页数据全部删除，就显示前面一页数据；
                vo.setPage(vo.getPage()-1);
                info =  productService.splitPageVo(vo,PAGE_SIZE);
            }


            request.getSession().removeAttribute("productVo");
        }else {
            info = productService.splitPage(1,PAGE_SIZE);
        }

        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }


    @RequestMapping("/deleteById")
    public  String deleteById(int pid,ProductInfoVo vo,HttpServletRequest request){
        //            "pid":pid,"pName":pName,"typeId":typeId,"lprice":lprice,"hprice":hprice,"page":page
        int num = productService.deleteByKey(pid);
        request.getSession().setAttribute("productVo",vo);
        if(num>0){
            request.setAttribute("msg","删除成功！");
        }else{
            request.setAttribute("msg","删除失败！");
        }
        return "forward:/prod/ajaxSplitPage2.action";
    }


}
