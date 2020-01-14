package wanart.bi.util;

import javafx.util.Pair;
import wanart.bi.entity.data.CommonDataEntity;

import java.util.*;

public class CommonDataCalcUtil {

    private static String MakeCommonDataEntityKey(CommonDataEntity data){
       if(data == null){
           return  "";
       }
       return  String.format("%s_%s",data.getGroupId(), data.getTime());
    }
    /***
     *
     * @param numerator 分子
     * @param denominator 分母
     * @return 返回按group 分子的(int)(value * 1000 / 分母的value)
     */
    public static List<CommonDataEntity> CalcDivision(List<CommonDataEntity> numerator, List<CommonDataEntity> denominator){
        ArrayList<CommonDataEntity> result = new ArrayList<>();
        if(numerator == null || denominator == null) {
            return result;
        }
        HashMap<String, Pair<CommonDataEntity, CommonDataEntity>> map = new HashMap<>();
        for(CommonDataEntity entity :numerator){
          map.put(MakeCommonDataEntityKey(entity), new Pair<>(entity, null));
        }
        for(CommonDataEntity entity: denominator){
            String key = MakeCommonDataEntityKey(entity);
            Pair<CommonDataEntity, CommonDataEntity> pair = map.get(key);
            if(pair != null){
                pair = new Pair<>(pair.getKey(), entity);
            }else{
               pair = new Pair<> (null, entity);
            }
            map.put(key, pair);
        }
        for(Map.Entry<String, Pair<CommonDataEntity, CommonDataEntity>>kv : map.entrySet()) {
            Pair<CommonDataEntity, CommonDataEntity> pair = kv.getValue();
            CommonDataEntity pairKey = pair.getKey();
            CommonDataEntity pairValue = pair.getValue();
            boolean numeratorNull = (pairKey == null);
            boolean denominatorNull = (pairValue == null);
            if(numeratorNull && denominatorNull){
                continue;
            }
            CommonDataEntity entity = new CommonDataEntity();
            // 分子不存在
            if(numeratorNull){
                entity.setGroupId(pairValue.getGroupId());
                entity.setTime(pairValue.getTime());
                entity.setValue(0);
            }
            else {
                entity.setGroupId(pairKey.getGroupId());
                entity.setTime(pairKey.getTime());

                // 分母不存在 或者分母为0
                if(pairValue == null || pairValue.getValue() == 0){
                    entity.setValue(0);
                }else{
                    entity.setValue(pairKey.getValue() * 10000 / pairValue.getValue());
                }
            }
            result.add(entity);
        }
        return  result;
    }
}
