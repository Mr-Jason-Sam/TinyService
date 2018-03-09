package smart.sftinyservice.gson;

/**
 * Created by jasonsam on 2018/3/7.
 */

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SpatialRelation {

    private String person1Id;
    private String person1Name;
    private String person2Id;
    private String person2Name;
    private String relationType;

    public String getPerson1Id() {
        return person1Id;
    }

    public void setPerson1Id(String person1Id) {
        this.person1Id = person1Id;
    }

    public String getPerson1Name() {
        return person1Name;
    }

    public void setPerson1Name(String person1Name) {
        this.person1Name = person1Name;
    }

    public String getPerson2Id() {
        return person2Id;
    }

    public void setPerson2Id(String person2Id) {
        this.person2Id = person2Id;
    }

    public String getPerson2Name() {
        return person2Name;
    }

    public void setPerson2Name(String person2Name) {
        this.person2Name = person2Name;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}