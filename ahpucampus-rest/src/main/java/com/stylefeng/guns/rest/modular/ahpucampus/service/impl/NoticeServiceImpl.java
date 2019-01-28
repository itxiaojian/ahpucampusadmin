package com.stylefeng.guns.rest.modular.ahpucampus.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.NoticeMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.Notice;
import com.stylefeng.guns.rest.modular.ahpucampus.service.INoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通知表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Map<String, Object> getAboutInfo() {

        Map<String,Object> returnMap = new HashMap<>();

        returnMap.put("introduce","");
        returnMap.put("updateLog","");


        //获取最新的简介内容
        Wrapper<Notice> noticeWrapper = new EntityWrapper<>();

        noticeWrapper.eq("type",3);

        noticeWrapper.orderBy("id",false).last(" LIMIT 1");

        List<Notice> introduceList = noticeMapper.selectList(noticeWrapper);

        if(CollectionUtils.isNotEmpty(introduceList)){
            Notice introduceNotice = introduceList.get(0);
            String introduceContent = introduceNotice.getContent();
            Map<String,Object> introduce = resolveIntroduceContent(introduceContent);
            returnMap.put("introduce",introduce);
        }

        //获取版本迭代信息

        Wrapper<Notice> updateLogWrapper = new EntityWrapper<>();

        updateLogWrapper.eq("type",4);

        updateLogWrapper.orderBy("id",false);

        List<Notice> updateLogList = noticeMapper.selectList(updateLogWrapper);

        if(CollectionUtils.isNotEmpty(updateLogList)){
            List<Map<String,Object>> updateLog = resolveUpdateLogContent(updateLogList);
            String version = MapUtils.getString(updateLog.get(0),"version","1.0.0");
            returnMap.put("version",version);
            returnMap.put("updateLog",updateLog);
        }


        return returnMap;
    }

    private Map<String,Object> resolveIntroduceContent(String introduceContent){
        Map<String,Object> introduceMap = new HashMap<>();
        introduceContent = introduceContent.replace(" ","").replace("<br>","").replace("<p>","")
                .replace("</p>","").replace("<spanstyle=\"color:inherit;\">","").replace("</span>","");
        if(StringUtils.isNotEmpty(introduceContent)){
            String data = introduceContent.substring(introduceContent.indexOf("#data#")+"#data#".length(),introduceContent.lastIndexOf("#data#"));
            if(StringUtils.isNotEmpty(data)){
                String[] nodeArray = data.split("#node#");
                if(nodeArray.length > 0){
                    List<Map<String,Object>> contents = new ArrayList<>();
                    List<Map<String,Object>> options = new ArrayList<>();
                    for (String node:nodeArray) {
                        if(node.contains("#")){
                            String key = node.split("#")[1];
                            String value = node.split("#")[2];
                            if("content".equals(key)){
                                Map<String,Object> content = new HashMap<>();
                                content.put(key,value);
                                contents.add(content);
                            }else{
                                Map<String,Object> option = new HashMap<>();
                                option.put("option",key+"："+value);
                                options.add(option);
                            }

                        }
                    }
                    introduceMap.put("contents",contents);
                    introduceMap.put("options",options);
                }

            }

        }
        return  introduceMap;

    }


    private List<Map<String,Object>> resolveUpdateLogContent(List<Notice> updateLogList){
        List<Map<String,Object>> updateLog = new ArrayList<>();
        for (Notice updateLogNotice:updateLogList) {
            Map<String,Object> updateLogMap = new HashMap<>();
            String updateLogContent = updateLogNotice.getContent().replace(" ","").replace("<br>","").replace("<p>","")
                    .replace("</p>","").replace("<spanstyle=\"color:inherit;\">","").replace("</span>","");
            if(StringUtils.isNotEmpty(updateLogContent)){
                String data = updateLogContent.substring(updateLogContent.indexOf("#data#")+"#data#".length(),updateLogContent.lastIndexOf("#data#"));
                if(StringUtils.isNotEmpty(data)){
                    String[] nodeArray = data.split("#node#");
                    if(nodeArray.length > 0){
                        List<Map<String,Object>> contents = new ArrayList<>();
                        for (String node:nodeArray) {
                            if(node.contains("#")){
                                String key = node.split("#")[1];
                                String value = node.split("#")[2];
                                if("content".equals(key)){
                                    Map<String,Object> content = new HashMap<>();
                                    content.put(key,value);


                                    //拼接item
                                    String items = node.substring(node.indexOf("#items#")+"#items#".length(),node.lastIndexOf("#items#"));
                                    if(StringUtils.isNotEmpty(items)){
                                        String[] sonNodeArray = items.split("#sonnode#");
                                        if(sonNodeArray.length>0){
                                            List<Map<String,Object>> itemsList = new ArrayList<>();
                                            for (String sonnode:sonNodeArray) {
                                                if(sonnode.contains("#")){
                                                    Map<String,Object> itemMap = new HashMap<>();
                                                    itemMap.put(sonnode.split("#")[1],sonnode.split("#")[2]);
                                                    itemsList.add(itemMap);
                                                }

                                            }
                                            content.put("items",itemsList);

                                        }

                                    }
                                    contents.add(content);

                                }else{
                                    updateLogMap.put(key,value);
                                }
                            }
                        }
                        updateLogMap.put("contents",contents);
                    }

                }

            }
            updateLog.add(updateLogMap);

        }

        return updateLog;
    }


}
