package com.nisq.mybatis.controller;

import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.nisq.mybatis.RespStat;
import com.nisq.mybatis.entity.Account;
import com.nisq.mybatis.entity.AppConfig;
import com.nisq.mybatis.service.IAccountService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/account")
public class AccountController {

//    @Autowired
//    FastFileStorageClient fsc;
    @DubboReference(version = "1.0")
    IAccountService accountService;

    @Autowired
    AppConfig config;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("config", config);
        return "account/login";

    }

    @RequestMapping("/toLogin")
    @ResponseBody
    public String toLogin(@RequestParam String loginName, @RequestParam String password, HttpServletRequest request) {
        Account account = accountService.getAccountByNameAndPass(loginName, password);
        if (account == null) {
            return "fail";
        } else {
            request.getSession().setAttribute("account", account);
            return "success";
        }
    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request, Model model) {
        request.getSession().removeAttribute("account");
        model.addAttribute("config", config);
        return "account/login";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "account/profile";
    }

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, HttpServletRequest request) {

        PageInfo<Account> pageList = accountService.getPagList(pageNum, pageSize);

        request.setAttribute("pageList", pageList);
        return "account/list";
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public RespStat deleteById(@RequestParam(required = false) int id) {
        RespStat stat = accountService.deleteById(id);
        return stat;
    }

    @RequestMapping("/updateById")
    @ResponseBody
    public RespStat updateById(Account account) {
        RespStat stat = accountService.updateById(account);
        return stat;
    }

    @RequestMapping("/getOneById")
    @ResponseBody
    public Account getOneById(@RequestParam(required = false) int id) {
        Account account = accountService.getOneById(id);
        return account;
    }

    @RequestMapping("/fileUploadController")
    public String fileUpload(MultipartFile filename, String password, HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
//        LocalDateTime ldt = LocalDateTime.now();
//        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String dateStr = dtf2.format(ldt);
//        String fileUrl = "D:/uploads/" + dateStr;
//        String name = filename.getOriginalFilename();
//        File file = new File(fileUrl);
//        if (!file.exists()) {
//            file.mkdir();
//        }
        try {
//            filename.transferTo(new File(fileUrl + "/" + name));

//            // 元数据
            Set<MetaData> metaDataSet = new HashSet<MetaData>();
            metaDataSet.add(new MetaData("Author", "yimingge"));
            metaDataSet.add(new MetaData("CreateDate", "2016-01-05"));
//            StorePath storePath = fsc.uploadFile(filename.getInputStream(), filename.getSize(),
//                    FilenameUtils.getExtension(filename.getOriginalFilename()), metaDataSet);
//            StorePath storePath = fsc.uploadImageAndCrtThumbImage(filename.getInputStream(), filename.getSize(),
//                    FilenameUtils.getExtension(filename.getOriginalFilename()), metaDataSet);
//            System.out.println("storePath:" + storePath.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        account.setPassword(password);
//        account.setLocation(dateStr+"/"+name);
        accountService.updateById(account);
        return "account/profile";
    }
    /**
     * @description 从fastDFS下载文件
     * @author nsq
     * @date  14:20
     * @return org.springframework.http.ResponseEntity<byte[]>
     */
    /*@RequestMapping("/down")
    @ResponseBody
    public ResponseEntity<byte[]> down() {

        DownloadByteArray cb = new DownloadByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "aaa.jpg");
        byte[] bs = fsc.downloadFile("group1", "M00/00/00/wKgAVGBiwouARp8fAADVVp3CazA886.jpg", cb);

        return new ResponseEntity<>(bs,headers, HttpStatus.OK);
    }*/
    /**
     * @description 从fastDFS删除文件
     * @author nsq
     * @date  14:20
     * @return org.springframework.http.ResponseEntity<byte[]>
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete() {

//        fsc.deleteFile("group1", "M00/00/00/wKgAVGBiwouARp8fAADVVp3CazA886.jpg");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
