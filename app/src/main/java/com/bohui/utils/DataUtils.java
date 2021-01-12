package com.bohui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bohui.R;
import com.bohui.entity.FanEntity;
import com.bohui.entity.Fault;
import com.bohui.entity.Gasentitiy;
import com.bohui.entity.LightEntity;
import com.bohui.entity.TemperatureEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/21 0021.
 * 测试假数据
 */

public class DataUtils {
    public static List<Drawable> createAdd(Context context){
        List<Drawable> list=new ArrayList<>();
        list.add(context.getResources().getDrawable(R.drawable.add));
        return list;
    }
    public static List<String>createMenuData(){
         List<String> list=new ArrayList<>();
        list.add("第一段");
        list.add("第二段");
        list.add("第三段");
        list.add("第四段");
        list.add("第五段");

        return list;
    }
    public static Gasentitiy createWeather(){
        Gasentitiy gasentitiy=new Gasentitiy("检测","2018-04-16 16:00:00","正常","123","1234","12345");
        return gasentitiy;
    }
    public static List<FanEntity> caeateFan(){
        List<FanEntity>fans=new ArrayList<>();
        FanEntity entity1=new FanEntity("风机1","状态1",false);
        FanEntity entity2=new FanEntity("风机2","状态2",true);
        fans.add(entity1);
        fans.add(entity2);
        return fans;
    }
    public static List<LightEntity> caeateLights(){
        List<LightEntity>list=new ArrayList<>();
        LightEntity lightEntity1=new LightEntity("照明1","状态1",true);
        LightEntity lightEntity2=new LightEntity("照明2","状态2",false);
        list.add(lightEntity1);
        list.add(lightEntity2);
        return list;
    }
    public static List<TemperatureEntity> caeateTemperature(){
        List<TemperatureEntity>list=new ArrayList<>();
        TemperatureEntity temperatureEntity1=new TemperatureEntity("温度1","状态1","27.3");
        TemperatureEntity temperatureEntity2=new TemperatureEntity("温度2","状态2","27.3");
        list.add(temperatureEntity1);
        list.add(temperatureEntity2);
        return list;
    }

    public static List<String>createFaultReasons(){
        List<String>reasons=new ArrayList<>();
        reasons.add("系统故障1");
        reasons.add("系统故障2");
        reasons.add("系统故障3");
        reasons.add("系统故障4");
        reasons.add("系统故障5");
        reasons.add("系统故障6");
        reasons.add("系统故障7");
        return reasons;
    }
    public static List<Fault>createFault(){
        List<Fault>faults=new ArrayList<>();
        Fault fault1=new Fault("2017-01-20 13:00:00","高","高","深圳南山区","这是一个故障,第一个故障,第一个故障,第一个故障,第一个故障,第一个故障,第一个故障,第一个故障,");
        Fault fault2=new Fault("2018-01-20 15:00:00","高","高","深圳宝安区","这是一个故障,第二个故障，第二个故障第二个故障第二个故障第二个故障第二个故障第二个故障第二个故障");
        faults.add(fault1);
        faults.add(fault2);
        return faults;
    }
}
