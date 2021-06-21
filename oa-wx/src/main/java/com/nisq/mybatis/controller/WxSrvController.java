package com.nisq.mybatis.controller;

import com.nisq.mybatis.entity.WxConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-04-1213:15
 */
@RestController
@RequestMapping("/wx")
public class WxSrvController {

    private static ExpireKey expireKey = new DefaultExpireKey();

    private static final Logger logger = LoggerFactory.getLogger(WxSrvController.class);


    @Autowired
    WxConfig wxConfig;


    @RequestMapping("addMenu")
    public BaseResult addMenu() {
        String muneStr = "{\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"今日歌曲\",\n" +
                "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"搜索\",\n" +
                "               \"url\":\"http://rbiqh9.natappfree.cc/profile/my\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"赞一下我们\",\n" +
                "               \"key\":\"V1001_GOOD\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";
        String menu2 = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"扫码\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"scancode_waitmsg\", \n" +
                "                    \"name\": \"扫码带提示\", \n" +
                "                    \"key\": \"rselfmenu_0_0\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"scancode_push\", \n" +
                "                    \"name\": \"扫码推事件\", \n" +
                "                    \"key\": \"rselfmenu_0_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"发图\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"pic_sysphoto\", \n" +
                "                    \"name\": \"系统拍照发图\", \n" +
                "                    \"key\": \"rselfmenu_1_0\", \n" +
                "                   \"sub_button\": [ ]\n" +
                "                 }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_photo_or_album\", \n" +
                "                    \"name\": \"拍照或者相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_weixin\", \n" +
                "                    \"name\": \"微信相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_2\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"发送位置\", \n" +
                "            \"type\": \"location_select\", \n" +
                "            \"key\": \"rselfmenu_2_0\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        BaseResult baseResult = MenuAPI.menuCreate(TokenManager.getDefaultToken(), muneStr);
        return baseResult;
    }

    @RequestMapping("sig")
    public void sig(@RequestParam Map<String, String> parm, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("-----" + parm);

        String signature = parm.get("signature");
        String timestamp = parm.get("timestamp");
        String nonce = parm.get("nonce");
        String echostr = parm.get("echostr");

        InputStream inputStream = request.getInputStream();
        OutputStream outputStream = response.getOutputStream();

        String tokenString = wxConfig.getToken();

        if (echostr != null) {
            outputStreamWriter(outputStream, echostr);
            return;
        }

        if (!signature.equals(SignatureUtil.generateEventMessageSignature(tokenString, timestamp, nonce))) {
            logger.info("fail-----");
        }
        if (null != inputStream) {
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String fromName = eventMessage.getFromUserName();
            String toUserName = eventMessage.getToUserName();
            String msgId = eventMessage.getMsgId();
            System.err.println("recv:" + ToStringBuilder.reflectionToString(eventMessage));

            String key = fromName + "_" + toUserName + "_" + msgId;

            if (expireKey.exists(key)) {
                System.out.println("重复通知不做处理");
                return;
            } else {
                expireKey.add(key);
            }
            //文本消息
            XMLTextMessage xmlTextMessage = new XMLTextMessage(fromName, toUserName, "请<a href='http://rbiqh9.natappfree.cc/profile/my'>完善一下信息</a>");
            xmlTextMessage.outputStreamWrite(outputStream);
            //图片消息
//            XMLImageMessage imageMessage = new XMLImageMessage(fromName, toUserName, "PV3R4SPGcjHeqMGJKnVrOZ9ePf4ihfgismgbSg2Z_EFqRbEi6RIqqOyqF3nS_Zjo");
//            imageMessage.outputStreamWrite(outputStream);
        }
    }

    public void outputStreamWriter(OutputStream out, String str) {
        try {
            out.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
