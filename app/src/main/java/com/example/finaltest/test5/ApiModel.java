package com.example.finaltest.test5;

import java.util.List;

/**
 * Created by pinan on 2017/7/12.
 */

public class ApiModel {
  
    
    public int code;
    public String message;
    public ResultBean result;
    public String time;
    
    public static class ResultBean {
       
        
        public String nextPageToken;
        public String prevPageToken;
        public int requestCount;
        public int responseCount;
        public int totalResults;
        public List<ItemsBean> items;
        
        public static class ItemsBean {
            public String detail;
            public String href;
            public int id;
            public String img;
            public String name;
            public String pubDate;
            public int type;
    
    
            @Override
            public String toString() {
                return "ItemsBean{" +
                    "detail='" + detail + '\'' +
                    ", href='" + href + '\'' +
                    ", id=" + id +
                    ", img='" + img + '\'' +
                    ", name='" + name + '\'' +
                    ", pubDate='" + pubDate + '\'' +
                    ", type=" + type +
                    '}';
            }
        }
    
        @Override
        public String toString() {
            return "ResultBean{" +
                "nextPageToken='" + nextPageToken + '\'' +
                ", prevPageToken='" + prevPageToken + '\'' +
                ", requestCount=" + requestCount +
                ", responseCount=" + responseCount +
                ", totalResults=" + totalResults +
                ", items=" + items +
                '}';
        }
    }
    
    @Override
    public String toString() {
        return "ApiModel{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", result=" + result +
            ", time='" + time + '\'' +
            '}';
    }
}
