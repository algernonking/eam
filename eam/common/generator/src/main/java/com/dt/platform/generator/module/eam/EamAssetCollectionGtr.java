package com.dt.platform.generator.module.eam;

import com.dt.platform.constants.db.EAMTables;
import com.dt.platform.eam.page.AssetBorrowPageController;
import com.dt.platform.eam.page.AssetCollectionPageController;
import com.dt.platform.proxy.eam.AssetBorrowServiceProxy;
import com.dt.platform.proxy.eam.AssetCollectionServiceProxy;
import com.github.foxnic.generator.config.WriteMode;

public class EamAssetCollectionGtr extends BaseCodeGenerator {

    public EamAssetCollectionGtr() {
        super(EAMTables.EAM_ASSET_COLLECTION.$TABLE,BASIC_DATA_MENU_ID);
    }

    public void generateCode() throws Exception {


        //文件生成覆盖模式
        cfg.overrides()
                .setServiceIntfAnfImpl(WriteMode.COVER_EXISTS_FILE) //服务与接口
                .setControllerAndAgent(WriteMode.COVER_EXISTS_FILE) //Rest
                .setPageController(WriteMode.COVER_EXISTS_FILE) //页面控制器
                .setFormPage(WriteMode.COVER_EXISTS_FILE) //表单HTML页
                .setListPage(WriteMode.COVER_EXISTS_FILE); //列表HTML页
        cfg.buildAll();
    }
    public static void main(String[] args) throws Exception {
        EamAssetCollectionGtr g=new EamAssetCollectionGtr();

        //生成代码
      //  g.generateCode();
        //生成菜单
        g.generateMenu(AssetCollectionServiceProxy.class, AssetCollectionPageController.class);

    }

}
