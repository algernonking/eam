package com.dt.platform.ops.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dt.platform.constants.enums.ops.*;
import com.dt.platform.domain.ops.MonitorNode;
import com.dt.platform.domain.ops.MonitorTpl;
import com.dt.platform.domain.ops.MonitorTplIndicator;
import com.dt.platform.domain.ops.MonitorVoucher;
import com.dt.platform.domain.ops.meta.MonitorNodeMeta;
import com.dt.platform.ops.service.*;
import com.github.foxnic.api.error.ErrorDesc;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.commons.busi.id.IDGenerator;
import com.github.foxnic.dao.spec.DAO;
import com.github.foxnic.sql.expr.ConditionExpr;
import com.github.foxnic.sql.expr.Insert;
import org.github.foxnic.web.framework.dao.DBConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

@Service("MonitorDataProcessScriptService")
public class MonitorDataProcessScriptServiceImpl implements IMonitorDataProcessScriptService {


    public static String NODE_VALUE_COLUMN_ROWS_SPLIT="@#@";
    public static String NODE_VALUE_COLUMN_COLS_SPLIT=",";

    /**
     * 注入DAO对象
     * */
    @Resource(name= DBConfigs.PRIMARY_DAO)
    private DAO dao=null;

    /**
     * 获得 DAO 对象
     * */
    public DAO dao() { return dao; }


    @Autowired
    private IMonitorNodeService monitorNodeService;

    @Autowired
    private IMonitorTplService monitorTplService;

    @Autowired
    private IMonitorNodeValueService monitorNodeValueService;

    @Autowired
    private IMonitorTplIndicatorService monitorTplIndicatorService;


    private Insert createBaseInsert(MonitorTplIndicator indicator,MonitorNode node){
        Insert nodeV=new Insert("ops_monitor_node_value");
        nodeV.set("id", IDGenerator.getSnowflakeIdString());
        nodeV.set("monitor_tpl_code",indicator.getMonitorTplCode());
        nodeV.set("indicator_code",indicator.getCode());
        nodeV.set("result_status", MonitorNodeValueResultStatusEnum.SUCESS.code());
        nodeV.set("result_message","执行成功");
        nodeV.set("record_time",new Date());
        nodeV.set("node_id",node.getId());
        return nodeV;
    }

    @Override
    public List<MonitorNode> queryNodeList() {
        String sql="select distinct a.id from ops_monitor_node a,ops_monitor_node_tpl_item b,ops_monitor_tpl_indicator c\n" +
                "where a.deleted='0'\n" +
                "and b.deleted='0'\n" +
                "and c.deleted='0'\n" +
                "and a.id=b.node_id\n" +
                "and c.status='1'\n" +
                "and b.tpl_code=c.monitor_tpl_code \n" +
                "and c.monitor_method='"+MonitorMethodEnum.SCRIPT.code()+"'";
        ConditionExpr expr=new ConditionExpr();
        expr.and(" id in ("+sql+")" );
        expr.andIf("node_enabled",MonitorEnableEnum.ENABLE.code());
        List<MonitorNode> list=monitorNodeService.queryList(expr);
        return list;
    }
//
//    @Override
//    public  queryNodeTplKeyValue() {
//        String sql="select distinct a.id from ops_monitor_node a,ops_monitor_node_tpl_item b,ops_monitor_tpl_indicator c\n" +
//                "where a.deleted='0'\n" +
//                "and b.deleted='0'\n" +
//                "and c.deleted='0'\n" +
//                "and a.id=b.node_id\n" +
//                "and b.tpl_code=c.monitor_tpl_code \n" +
//                "and c.monitor_method='"+MonitorMethodEnum.SCRIPT.code()+"'";
//        ConditionExpr expr=new ConditionExpr();
//        expr.and("id in ("+sql+")" );
//        expr.andIf("node_enabled",MonitorEnableEnum.ENABLE.code());
//        List<MonitorNode> list=monitorNodeService.queryList(expr);
//        return list;
//    }


    @Override
    public List<MonitorTpl> queryTplList() {
        String sql="select distinct b.tpl_code from ops_monitor_node a,ops_monitor_node_tpl_item b,ops_monitor_tpl_indicator c\n" +
                "where a.deleted='0'\n" +
                "and b.deleted='0'\n" +
                "and c.deleted='0'\n" +
                "and c.status='1'\n" +
                "and a.id=b.node_id\n" +
                "and b.tpl_code=c.monitor_tpl_code \n" +
                "and c.monitor_method='"+MonitorMethodEnum.SCRIPT.code()+"'";
        ConditionExpr expr=new ConditionExpr();
        expr.and("code in ("+sql+")" );
        List<MonitorTpl> list=monitorTplService.queryList(expr);
        return list;
    }



    @Override
    public List<MonitorTplIndicator> queryIndicatorList(String nodeId) {
        String sql="select distinct c.id from ops_monitor_node a,ops_monitor_node_tpl_item b,ops_monitor_tpl_indicator c\n" +
                "where a.deleted='0'\n" +
                "and b.deleted='0'\n" +
                "and c.deleted='0'\n" +
                "and c.status='1'\n" +
                "and a.id=b.node_id\n" +
                "and b.tpl_code=c.monitor_tpl_code \n" +
                "and c.monitor_method='"+MonitorMethodEnum.SCRIPT.code()+"'";
        if(!StringUtil.isBlank(nodeId)){
            sql=sql+"and a.id='"+nodeId+"'";
        }
        ConditionExpr expr=new ConditionExpr();
        expr.and("id in ("+sql+")" );
        List<MonitorTplIndicator> list=monitorTplIndicatorService.queryList(expr);
        return list;
    }


    private List<MonitorTplIndicator> queryExecuteIndicatorList(String nodeId) {
        String sql="select distinct c.id from ops_monitor_node a,ops_monitor_node_tpl_item b,ops_monitor_tpl_indicator c\n" +
                "where a.deleted='0'\n" +
                "and b.deleted='0'\n" +
                "and c.deleted='0'\n" +
                "and c.status='1'\n" +
                "and a.id=b.node_id\n" +
                "and b.tpl_code=c.monitor_tpl_code \n" +
                "and c.monitor_method='"+MonitorMethodEnum.SCRIPT.code()+"'";
        if(!StringUtil.isBlank(nodeId)){
            sql=sql+"and a.id='"+nodeId+"'";
        }

        ConditionExpr expr=new ConditionExpr();
        expr.and("id in ("+sql+")" );
        List<MonitorTplIndicator> list=monitorTplIndicatorService.queryList(expr);
        return list;
    }

    @Override
    public Result collectData() {
        List<MonitorNode> nodeList=queryNodeList();
        System.out.println("collectData,find nodes number:"+nodeList.size());
        for(MonitorNode node:nodeList){
            Result result=collectNodeData(node);
            if(!result.isSuccess()){
                System.out.println("node ip:"+node.getNodeIp()+",message"+result.getMessage());
            }
        }
        return ErrorDesc.success();
    }

    public Result collectNodeData(MonitorNode node) {

        String ip=node.getNodeIp();
        int sshPort=node.getSshPort();
        monitorNodeService.dao().fill(node)
                .with(MonitorNodeMeta.SSH_VOUCHER)
                .execute();
        MonitorVoucher monitorVoucher=node.getSshVoucher();
        if(monitorVoucher==null){
            return ErrorDesc.failure().message("主机("+ip+")凭证不存在");
        }
        String account=monitorVoucher.getAccount();
        String voucher=monitorVoucher.getVoucher();

        if(StringUtil.isBlank(account)){
            return ErrorDesc.failure().message("主机("+ip+")凭证用户不存在");
        }
        if(StringUtil.isBlank(voucher)){
            return ErrorDesc.failure().message("主机("+ip+")凭证数据不存在");
        }
        if(StringUtil.isBlank(ip+"")){
            return ErrorDesc.failure().message("主机("+ip+")端口不存在");
        }

        //获取指标
        List<MonitorTplIndicator> monitorTplIndicatorList=queryExecuteIndicatorList(node.getId());
        System.out.println("collectData,process node:"+node.getId()+",ip:"+node.getNodeIp()+",find indicator number:"+monitorTplIndicatorList.size());
        List<Insert> list=executeScriptSingle(monitorTplIndicatorList,node,account,voucher,sshPort);
        //开始执行insert
        executeCollectDataInsert(list);
        return ErrorDesc.success();

    }

    private List<Insert> executeScriptSingle(List<MonitorTplIndicator> monitorTplIndicatorList,MonitorNode node,String account,String voucher,int sshPort){
        List<Insert> insList=new ArrayList<>();
        RemoteShellExecutor rmt=new RemoteShellExecutor(node.getNodeIp(),account,voucher,sshPort);
        for(MonitorTplIndicator tplIndicator:monitorTplIndicatorList){
            //获取一个指标的执行结果
            RemoteShellResult executeResult = rmt.exec(tplIndicator.getCommand());
            System.out.println("executeResult"+executeResult.result+executeResult.code);
          if(executeResult.code==0){
                //执行结果
                String content=executeResult.result;
                //转换成insert语句
                Result<Object> insertResult=convertToInsertData(tplIndicator,content,node);
                if(insertResult.isSuccess()){
                    List<Insert> nodeInsList=(List<Insert>)insertResult.getData();
                    if(nodeInsList.size()>0){
                        insList.addAll(nodeInsList);
                    }
                }
            }
        }
       return insList;
    }



    //未实现
    private Map<String,MonitorTplIndicator> executeScriptBatch(List<MonitorTplIndicator> tplIndicator,String host,int port,String account,String voucher){
        Map<String,MonitorTplIndicator> map=new HashMap<>();
        return map;
    }

    private Result executeCollectDataInsert(List<Insert> insList) {

        for(Insert insert:insList){
            try{
                dao.execute(insert);
            }catch(UncategorizedSQLException e){
                System.out.println("Sql execute error,sql:"+insert.getSQL());
                Insert errInsert=new Insert("ops_monitor_node_value");
                errInsert.set("id",insert.getValue("id"));
                errInsert.set("result_status",insert.getValue("id"));
                errInsert.set("result_message",insert.getValue("id"));
                errInsert.set("indicator_code",insert.getValue("id"));
                errInsert.set("result_message",insert.getValue("id"));

                e.printStackTrace();
                System.out.println(e.getMessage());


            }

        }
        return ErrorDesc.success();
    }



    private Result<Object> convertToInsertData(MonitorTplIndicator tplIndicator,String content,MonitorNode node){

        List<Insert> insList=new ArrayList<>();
        List<String> contentList=new ArrayList<>();

        //判断验证
        String errorMsg="模版:"+tplIndicator.getMonitorTplCode()+",指标:"+tplIndicator.getMonitorTplCode()+",节点:"+node.getId();
        if(StringUtil.isBlank(content)){
            Insert ins=createBaseInsert(tplIndicator,node);
            ins.set("result_status", MonitorNodeValueResultStatusEnum.FAILED.code());
            ins.set("result_message",errorMsg+",采集数据为空");
            return ErrorDesc.success().data(insList);
        }

        //结果数据转换，开始转换行
        if(MonitorIndicatorValueColumnRowsEnum.SINGLE.code().equals(tplIndicator.getValueColumnRows())){
            contentList.add(content);
        }else if (MonitorIndicatorValueColumnRowsEnum.MULTIPLE.code().equals(tplIndicator.getValueColumnRows())){

            String colContent=null;
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            try {
                colContent = br.readLine();
                while (colContent != null)
                {
                    contentList.add(colContent);
                    System.out.println("ss"+colContent);
                    colContent=br.readLine();

                }
            }
             catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            Insert ins=createBaseInsert(tplIndicator,node);
            ins.set("result_status", MonitorNodeValueResultStatusEnum.FAILED.code());
            ins.set("result_message",errorMsg+",getValueColumnRows参数设置错误");
            return ErrorDesc.success().data(insList);
        }

        //结果数据转换，开始转换列
        String[] valueColumnArr=tplIndicator.getValueColumn().split(NODE_VALUE_COLUMN_COLS_SPLIT);

        //当单列时
        if(MonitorIndicatorValueColumnColsEnum.SINGLE.code().equals(tplIndicator.getValueColumnCols())){
            for(String itemValue:contentList){
                Insert ins=createBaseInsert(tplIndicator,node);
                ins.set(tplIndicator.getValueColumn(),itemValue);
                insList.add(ins);
            }
        }else if(MonitorIndicatorValueColumnColsEnum.MULTIPLE.code().equals(tplIndicator.getValueColumnCols())){
            //当多列时
            for(String itemValue:contentList){
                System.out.println("itemValue:"+itemValue);
                String[] valueColumnItemArr=itemValue.split(NODE_VALUE_COLUMN_COLS_SPLIT);
                Insert ins=createBaseInsert(tplIndicator,node);
                //System.out.println("valueColumnItemArr"+valueColumnItemArr);
                //System.out.println("valueColumnArr"+valueColumnArr);
                if(valueColumnItemArr.length==valueColumnArr.length){
                    for(int i=0;i<valueColumnArr.length;i++){
                        ins.set(valueColumnArr[i],valueColumnItemArr[i]);
                    }
                }else{
                    ins.set("result_status", MonitorNodeValueResultStatusEnum.FAILED.code());
                    ins.set("result_message","采集数据和模版设置列数不一致");
                }
                insList.add(ins);
            }
        }
        return ErrorDesc.success().data(insList);
    }


}