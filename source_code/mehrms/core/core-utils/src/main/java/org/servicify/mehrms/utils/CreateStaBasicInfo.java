package org.servicify.mehrms.utils;

import org.servicify.mehrms.model.Department;
import org.servicify.mehrms.model.JobLevel;
import org.servicify.mehrms.model.Position;
import org.servicify.mehrms.model.StaBasicInfo;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
//该类负责将所有的部门、职位、职称集合，通过创建添加至一个类型为List<StaBasicInfo>的信息中
public class CreateStaBasicInfo {
        public static List<StaBasicInfo> createDepStaBasicInfosList(List<Department> list){
            List<StaBasicInfo> listStaBasicInfos=new ArrayList<StaBasicInfo>();
            for(int i=0;i<list.size();i++){
                StaBasicInfo staBasicInfo=new StaBasicInfo();
                staBasicInfo.setName(list.get(i).getName());
                staBasicInfo.setId(list.get(i).getId());
                staBasicInfo.setValue(0);
                listStaBasicInfos.add(staBasicInfo);
            }
            return listStaBasicInfos;
        }
        public static List<StaBasicInfo> createJobStaBasicInfosList(List<JobLevel> list){
            List<StaBasicInfo> listStaBasicInfos=new ArrayList<StaBasicInfo>();
            for(int i=0;i<list.size();i++){
                StaBasicInfo staBasicInfo=new StaBasicInfo();
                staBasicInfo.setName(list.get(i).getName());
                staBasicInfo.setId(list.get(i).getId());
                staBasicInfo.setValue(0);
                listStaBasicInfos.add(staBasicInfo);
            }
            return listStaBasicInfos;
        }
        public static List<StaBasicInfo> createPosStaBasicInfosList(List<Position> list){
           List<StaBasicInfo> listStaBasicInfos=new ArrayList<StaBasicInfo>();
            for(int i=0;i<list.size();i++){
                StaBasicInfo staBasicInfo=new StaBasicInfo();
                staBasicInfo.setName(list.get(i).getName());
                staBasicInfo.setId(list.get(i).getId());
                staBasicInfo.setValue(0);
                listStaBasicInfos.add(staBasicInfo);
            }
            return listStaBasicInfos;
        }
}
