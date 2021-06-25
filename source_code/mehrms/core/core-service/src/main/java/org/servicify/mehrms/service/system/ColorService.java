package org.servicify.mehrms.service.system;

import org.servicify.mehrms.mapper.ColorMapper;
import org.servicify.mehrms.model.Color;
import org.servicify.mehrms.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class ColorService {
    @Autowired
    ColorMapper colorMapper;
    public Color getInfo(){
        Integer hrid=HrUtils.getCurrentHr().getId();
        List<Color> colorList=colorMapper.selectAllInfos();
        Integer integer=0;
        for(int i=0;i<colorList.size();i++){
            if(colorList.get(i).getHrid()==hrid){
                integer=1;
                break;
            }
        }
        if(integer!=1){
            //            插入一条该管理员对应的默认颜色信息
            Color color=new Color();
            color.setHrid(hrid);
            color.setBackgroundColor("rgba(19, 215, 230, 0.836)");
            color.setColor("blue");
            colorMapper.insertSelective(color);
//            返回管理员所对应的颜色信息。
            return colorMapper.selectByHrid(hrid);
        }else {
            return colorMapper.selectByHrid(hrid);
        }
    }
//    修改颜色配置信息
    public Integer updateColorInof(Color color){
        Color color1 = new Color();
        Integer hrid=HrUtils.getCurrentHr().getId();
        color1.setId(colorMapper.selectByHrid(hrid).getId());
        color1.setHrid(hrid);
        color1.setColor(color.getColor());
        color1.setBackgroundColor(color.getBackgroundColor());
        return colorMapper.updateByPrimaryKeySelective(color1);
    }
}
