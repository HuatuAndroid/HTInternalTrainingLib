package com.liuxiaoji.module_contacts.selectparticipant.bean;

import com.google.gson.annotations.SerializedName;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuzhe
 * @date 2018/11/28
 * <p>
 * 联系人bean
 */
public class ContactsBean extends BaseResponse implements Serializable{

    /**
     * data : {"staffs":[{"roles":null,"id":31172,"name":"李钢江","staffNo":"021694","idType":1,"idNo":"430124197506130012","birthday":171849600000,"mobile":"13916835986","avatar":"","workEmailPrefix":"ligj","workEmail":"ligj@huatu.com","positionId":91,"position":null,"nodeId":122,"node":{"id":122,"pid":61,"name":"技术中心","ids":[1,61,122],"posterityIds":[2272,385,386,1858,2274,387,2275,2276,389,1989,2277,1990,2279,1992,2056,1993,1814,2268,2271],"fullName":"技术中心,总裁,北京华图宏阳教育文化发展股份有限公司","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1522671266000,"gradeDto":null,"staffStatus":5,"businessUnitId":null,"meetingList":[]}],"nodes":[{"id":385,"pid":122,"name":"信息技术研发部","ids":[1,61,122,385],"posterityIds":[2276,2277,1992,1993],"fullName":"信息技术研发部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":386,"pid":122,"name":"技术质量保障部","ids":[1,61,122,386],"posterityIds":[2056],"fullName":"技术质量保障部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":387,"pid":122,"name":"网络运维部","ids":[1,61,122,387],"posterityIds":[2272,2274,2275,2271],"fullName":"网络运维部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":389,"pid":122,"name":"产品设计部","ids":[1,61,122,389],"posterityIds":[],"fullName":"产品设计部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":1814,"pid":122,"name":"新产品研发部","ids":[1,61,122,1814],"posterityIds":[],"fullName":"新产品研发部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":1858,"pid":122,"name":"金融技术部","ids":[1,61,122,1858],"posterityIds":[],"fullName":"金融技术部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":1989,"pid":122,"name":"项目实施和培训部","ids":[1,61,122,1989],"posterityIds":[],"fullName":"项目实施和培训部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":1990,"pid":122,"name":"大数据部","ids":[1,61,122,1990],"posterityIds":[],"fullName":"大数据部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":2268,"pid":122,"name":"网站技术部","ids":[1,61,122,2268],"posterityIds":[],"fullName":"网站技术部,技术中心,总裁","children":null,"nodeType":null,"nodes":null},{"id":2279,"pid":122,"name":"南京研发部","ids":[1,61,122,2279],"posterityIds":[],"fullName":"南京研发部,技术中心,总裁","children":null,"nodeType":null,"nodes":null}]}
     * pageDto : null
     */

    @SerializedName("data")
    public DataBean data;
    @SerializedName("pageDto")
    public Object pageDto;

    public static class DataBean {
        @SerializedName("staffs")
        public List<StaffsBean> staffs;
        @SerializedName("nodes")
        public List<NodesBean> nodes;

        public static class StaffsBean extends BaseResponse implements Serializable{
            /**
             * roles : null
             * id : 31172
             * name : 李钢江
             * staffNo : 021694
             * idType : 1
             * idNo : 430124197506130012
             * birthday : 171849600000
             * mobile : 13916835986
             * avatar :
             * workEmailPrefix : ligj
             * workEmail : ligj@huatu.com
             * positionId : 91
             * position : null
             * nodeId : 122
             * node : {"id":122,"pid":61,"name":"技术中心","ids":[1,61,122],"posterityIds":[2272,385,386,1858,2274,387,2275,2276,389,1989,2277,1990,2279,1992,2056,1993,1814,2268,2271],"fullName":"技术中心,总裁,北京华图宏阳教育文化发展股份有限公司","children":null,"nodeType":null,"nodes":null}
             * sex : 1
             * entryDate : 1522671266000
             * gradeDto : null
             * staffStatus : 5
             * businessUnitId : null
             * meetingList : []
             */

            @SerializedName("roles")
            public Object roles;
            @SerializedName("id")
            public int id;
            @SerializedName("name")
            public String name;
            @SerializedName("staffNo")
            public String staffNo;
            @SerializedName("idType")
            public int idType;
            @SerializedName("idNo")
            public String idNo;
            @SerializedName("birthday")
            public long birthday;
            @SerializedName("mobile")
            public String mobile;
            @SerializedName("avatar")
            public String avatar;
            @SerializedName("workEmailPrefix")
            public String workEmailPrefix;
            @SerializedName("workEmail")
            public String workEmail;
            @SerializedName("positionId")
            public int positionId;
            @SerializedName("position")
            public Object position;
            @SerializedName("nodeId")
            public int nodeId;
            @SerializedName("node")
            public NodeBean node;
            @SerializedName("sex")
            public int sex;
            @SerializedName("entryDate")
            public long entryDate;
            @SerializedName("gradeDto")
            public Object gradeDto;
            @SerializedName("staffStatus")
            public int staffStatus;
            @SerializedName("is_schedule")
            public int is_schedule;
            @SerializedName("businessUnitId")
            public Object businessUnitId;
            @SerializedName("meetingList")
            public List<?> meetingList;

            public static class NodeBean implements Serializable{
                /**
                 * id : 122
                 * pid : 61
                 * name : 技术中心
                 * ids : [1,61,122]
                 * posterityIds : [2272,385,386,1858,2274,387,2275,2276,389,1989,2277,1990,2279,1992,2056,1993,1814,2268,2271]
                 * fullName : 技术中心,总裁,北京华图宏阳教育文化发展股份有限公司
                 * children : null
                 * nodeType : null
                 * nodes : null
                 */

                @SerializedName("id")
                public int id;
                @SerializedName("pid")
                public int pid;
                @SerializedName("name")
                public String name;
                @SerializedName("fullName")
                public String fullName;
                @SerializedName("children")
                public Object children;
                @SerializedName("nodeType")
                public Object nodeType;
                @SerializedName("nodes")
                public Object nodes;
                @SerializedName("ids")
                public List<Integer> ids;
                @SerializedName("posterityIds")
                public List<Integer> posterityIds;
            }
        }

        public static class NodesBean implements Serializable{
            /**
             * id : 385
             * pid : 122
             * name : 信息技术研发部
             * ids : [1,61,122,385]
             * posterityIds : [2276,2277,1992,1993]
             * fullName : 信息技术研发部,技术中心,总裁
             * children : null
             * nodeType : null
             * nodes : null
             */

            @SerializedName("id")
            public int id;
            @SerializedName("pid")
            public int pid;
            @SerializedName("name")
            public String name;
            @SerializedName("fullName")
            public String fullName;
            @SerializedName("children")
            public Object children;
            @SerializedName("nodeType")
            public Object nodeType;
            @SerializedName("nodes")
            public Object nodes;
            @SerializedName("ids")
            public List<Integer> ids;
            @SerializedName("posterityIds")
            public List<Integer> posterityIds;
        }
    }
}
