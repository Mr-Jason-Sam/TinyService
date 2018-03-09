package smart.sftinyservice.gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jasonsam on 2018/3/7.
 */

public class DataSImulation {
    private String relationClose = "close";
    private String relationEstranged = "estranged";

    private List<SpatialRelation> data = new ArrayList<>();

    public List<SpatialRelation> getData() {
        return data;
    }

    public void setData(List<SpatialRelation> data) {
        this.data = data;
    }

    public void toRelation() throws Exception {
        FaceBox b1 = new FaceBox(0.33f, 0.30f, 0.43f, 0.50f);
        FaceBox b2 = new FaceBox(0.53f, 0.30f, 0.63f, 0.50f);
        FaceBox b3 = new FaceBox(0.83f, 0.50f, 0.93f, 0.70f);
        Map<String, FaceBox> childMap = new HashMap<>();
        childMap.put("213", b1);
        childMap.put("214", b2);
        childMap.put("215", b3);
        List<SpatialRelation> relations = new ArrayList<>();
        Set<String> keySet = childMap.keySet();
        List<String> keyList = new ArrayList<>();
        for (String key:keySet) {
            keyList.add(key);
        }
        for (String key:keyList) {
            FaceBox faceBox = childMap.get(key);
            childMap.remove(key, faceBox);
            Set<String> keySetRemain = childMap.keySet();
            for (String keyRemain : keySetRemain) {
                FaceBox faceBoxRemain = childMap.get(keyRemain);
                SpatialRelation relation = new SpatialRelation();
                relation.setPerson1Id(key);
                relation.setPerson2Id(keyRemain);
                float x = Math.abs(faceBox.centerX - faceBoxRemain.centerX);
                float y = Math.abs(faceBox.centerY - faceBoxRemain.centerY);
                //0.1 is default width value of child face
                double distance = Math.sqrt(x * x + y * y) * 0.1 / faceBox.width;
                relation.setRelationType(distance < 0.5 ? relationClose : relationEstranged);
                System.out.print("toRelation, s:" + relation);
                relations.add(relation);
            }
        }
        setData(relations);
    }
}
