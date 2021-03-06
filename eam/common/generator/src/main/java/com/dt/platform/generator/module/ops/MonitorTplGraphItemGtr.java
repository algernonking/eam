package com.dt.platform.generator.module.ops;

import com.dt.platform.constants.db.EAMTables;
import com.dt.platform.constants.enums.ops.MonitorEnableEnum;
import com.dt.platform.constants.enums.ops.MonitorStatusEnum;
import com.dt.platform.domain.ops.MonitorTpl;
import com.dt.platform.domain.ops.meta.MonitorTplGraphMeta;
import com.dt.platform.domain.ops.meta.MonitorTplMeta;
import com.dt.platform.generator.config.Config;
import com.dt.platform.proxy.ops.MonitorTplServiceProxy;
import com.github.foxnic.generator.config.WriteMode;
import com.dt.platform.constants.db.OpsTables;
public class MonitorTplGraphItemGtr extends BaseCodeGenerator{


    public MonitorTplGraphItemGtr() {
        super(OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.$TABLE,MONITOR_MENU_ID);
    }

    public void generateCode() throws Exception {
        System.out.println(this.getClass().getName());

        //node type
        cfg.view().search().inputLayout(
                new Object[]{
                        OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.NAME,
                        OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.INDICATOR_CODE,
                }
        );

        cfg.view().search().labelWidth(1,Config.searchLabelWidth);
        cfg.view().search().labelWidth(2,Config.searchLabelWidth);
        cfg.view().search().inputWidth(Config.searchInputWidth);

        cfg.view().field(OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.NAME).search().fuzzySearch();
        cfg.view().field(OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.INDICATOR_CODE).search().fuzzySearch();
        cfg.view().field(OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.ID).basic().hidden(true);
        cfg.view().field(OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.ID).table().disable(true);
        cfg.view().field(OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.CREATE_TIME).table().disable(true);



        cfg.view().formWindow().bottomSpace(120);
        cfg.view().formWindow().width("98%");
        cfg.view().form().addGroup(null,
                new Object[] {
                        OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.NAME,
                        OpsTables.OPS_MONITOR_TPL_GRAPH_ITEM.INDICATOR_CODE
                }
        );

        //????????????????????????
        cfg.overrides()
                .setServiceIntfAnfImpl(WriteMode.COVER_EXISTS_FILE) //???????????????
                .setControllerAndAgent(WriteMode.COVER_EXISTS_FILE) //Rest
                .setPageController(WriteMode.COVER_EXISTS_FILE) //???????????????
                .setFormPage(WriteMode.COVER_EXISTS_FILE) //??????HTML???
                .setListPage(WriteMode.COVER_EXISTS_FILE)//??????HTML???
                .setExtendJsFile(WriteMode.COVER_EXISTS_FILE); //??????HTML???
        //????????????
        cfg.buildAll();
    }

    public static void main(String[] args) throws Exception {
        MonitorTplGraphItemGtr g=new MonitorTplGraphItemGtr();
        //????????????
        g.generateCode();

        //?????????????????????????????????????????????
       // g.generateMenu(MonitorTplGraphServiceProxy.class, MonitorTplGraphPageController.class);


    }
}
