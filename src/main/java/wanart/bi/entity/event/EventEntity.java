package wanart.bi.entity.event;

import java.util.List;

public class EventEntity {
    private String eventName;
    private List<EventColumnEntity> columnList;

    public String getEventName(){
       return eventName;
    }
    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public List<EventColumnEntity> getColumnList(){
        return columnList;
    }
    public void setColumnList(List<EventColumnEntity> columnList){
        this.columnList = columnList;
    }
}
