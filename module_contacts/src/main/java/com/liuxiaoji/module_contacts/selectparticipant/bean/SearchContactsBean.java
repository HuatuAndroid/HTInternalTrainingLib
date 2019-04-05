package com.liuxiaoji.module_contacts.selectparticipant.bean;

import com.google.gson.annotations.SerializedName;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseResponse;

import java.util.List;

/**
 * @author liuzhe
 * @date 2019/4/4
 * 联系人bean
 */
public class SearchContactsBean extends BaseResponse {

    /**
     * code : 0
     * data : {"staffs":[{"roles":null,"id":43551,"name":"宋静林","staffNo":"043551","idType":1,"idNo":"370681199610082273","birthday":844704000000,"mobile":"15688548565","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/372dc254cd6642e0b10aa475a1427bdc.jpg","workEmailPrefix":"songjinglin","workEmail":"songjinglin@huatu.com","positionId":866,"position":null,"nodeId":2698,"node":{"id":2698,"pid":2622,"name":"烟台学习中心","ids":[1,61,64,149,2622,2698],"posterityIds":[2928,2929,2930,2931,2932,2933,2926,2927],"fullName":"烟台学习中心,山东分校（试点）,华北大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1541039254000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43595,"name":"林雪聪","staffNo":"043595","idType":1,"idNo":"511011199212105068","birthday":723916800000,"mobile":"15828719144","avatar":"","workEmailPrefix":"linxuecong","workEmail":"linxuecong@huatu.com","positionId":383,"position":null,"nodeId":2243,"node":{"id":2243,"pid":2209,"name":"内江学习中心","ids":[1,61,64,66,2209,2243],"posterityIds":[],"fullName":"内江学习中心,四川分校（试点）,华中大区","children":null,"nodeType":null,"nodes":null},"sex":2,"entryDate":1541379019000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43656,"name":"毕光林","staffNo":"043656","idType":1,"idNo":"530423199312040353","birthday":754934400000,"mobile":"15391487286","avatar":"","workEmailPrefix":"biguanglin","workEmail":"biguanglin@huatu.com","positionId":933,"position":null,"nodeId":1178,"node":{"id":1178,"pid":485,"name":"教学教研部","ids":[1,61,64,146,485,1178],"posterityIds":[1562],"fullName":"教学教研部,云南分校,华南大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1541347200000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43709,"name":"王大林","staffNo":"043709","idType":1,"idNo":"37078419941128861X","birthday":785952000000,"mobile":"15275206065","avatar":"","workEmailPrefix":"wangdalin","workEmail":"wangdalin@huatu.com","positionId":493,"position":null,"nodeId":2686,"node":{"id":2686,"pid":2622,"name":"青岛学习中心","ids":[1,61,64,149,2622,2686],"posterityIds":[2896,2897,2898,2899,2900,2901,2902,2903],"fullName":"青岛学习中心,山东分校（试点）,华北大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1541983682000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43734,"name":"林桃平","staffNo":"043734","idType":1,"idNo":"452122199104233045","birthday":672332400000,"mobile":"13907839562","avatar":"","workEmailPrefix":"lintaoping","workEmail":"lintaoping@huatu.com","positionId":933,"position":null,"nodeId":749,"node":{"id":749,"pid":489,"name":"产品教研部","ids":[1,61,64,146,489,749],"posterityIds":[],"fullName":"产品教研部,南宁分校,华南大区","children":null,"nodeType":null,"nodes":null},"sex":2,"entryDate":1541993472000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43793,"name":"张志林","staffNo":"043793","idType":1,"idNo":"410521199212153022","birthday":724348800000,"mobile":"17610077538","avatar":"","workEmailPrefix":"zhangzhilin","workEmail":"zhangzhilin@huatu.com","positionId":626,"position":null,"nodeId":1886,"node":{"id":1886,"pid":1850,"name":"教师项目部","ids":[1850,1886],"posterityIds":[],"fullName":"华图网校,教师项目部","children":null,"nodeType":null,"nodes":null},"sex":2,"entryDate":1542259800000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43804,"name":"胡海林","staffNo":"043804","idType":1,"idNo":"342425196412080059","birthday":-159868800000,"mobile":"13956101668","avatar":"","workEmailPrefix":"huhailin","workEmail":"huhailin@huatu.com","positionId":933,"position":null,"nodeId":657,"node":{"id":657,"pid":499,"name":"公职教研中心","ids":[1,61,64,66,499,657],"posterityIds":[],"fullName":"公职教研中心,安徽分校,华中大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1542268667000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":44021,"name":"郭林炜","staffNo":"044021","idType":1,"idNo":"440303199201181310","birthday":695664000000,"mobile":"13537633233","avatar":"","workEmailPrefix":"guolinwei","workEmail":"guolinwei@huatu.com","positionId":493,"position":null,"nodeId":2812,"node":{"id":2812,"pid":2811,"name":"罗湖学习中心","ids":[1,61,64,146,2756,2811,2812],"posterityIds":[],"fullName":"罗湖学习中心,深圳学习中心,广东分校（试点）","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1543193815000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":44109,"name":"林廷璋","staffNo":"044109","idType":1,"idNo":"511304199411120714","birthday":784569600000,"mobile":"13350669254","avatar":"","workEmailPrefix":"lintingzhang","workEmail":"lintingzhang@huatu.com","positionId":828,"position":null,"nodeId":2242,"node":{"id":2242,"pid":2209,"name":"南充学习中心","ids":[1,61,64,66,2209,2242],"posterityIds":[],"fullName":"南充学习中心,四川分校（试点）,华中大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1543369749000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":44281,"name":"李顺林","staffNo":"044281","idType":1,"idNo":"533223199211280018","birthday":722880000000,"mobile":"15025171268","avatar":"","workEmailPrefix":"lishunlin","workEmail":"lishunlin@huatu.com","positionId":520,"position":null,"nodeId":1242,"node":{"id":1242,"pid":485,"name":"丽江分校","ids":[1,61,64,146,485,1242],"posterityIds":[],"fullName":"丽江分校,云南分校,华南大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1543813159000,"gradeDto":null,"staffStatus":5,"businessUnitId":null}],"pages":15,"total":148}
     * extra :
     */

    @SerializedName("data")
    public DataBean data;
    @SerializedName("extra")
    public String extra;

    public static class DataBean {
        /**
         * staffs : [{"roles":null,"id":43551,"name":"宋静林","staffNo":"043551","idType":1,"idNo":"370681199610082273","birthday":844704000000,"mobile":"15688548565","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/372dc254cd6642e0b10aa475a1427bdc.jpg","workEmailPrefix":"songjinglin","workEmail":"songjinglin@huatu.com","positionId":866,"position":null,"nodeId":2698,"node":{"id":2698,"pid":2622,"name":"烟台学习中心","ids":[1,61,64,149,2622,2698],"posterityIds":[2928,2929,2930,2931,2932,2933,2926,2927],"fullName":"烟台学习中心,山东分校（试点）,华北大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1541039254000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43595,"name":"林雪聪","staffNo":"043595","idType":1,"idNo":"511011199212105068","birthday":723916800000,"mobile":"15828719144","avatar":"","workEmailPrefix":"linxuecong","workEmail":"linxuecong@huatu.com","positionId":383,"position":null,"nodeId":2243,"node":{"id":2243,"pid":2209,"name":"内江学习中心","ids":[1,61,64,66,2209,2243],"posterityIds":[],"fullName":"内江学习中心,四川分校（试点）,华中大区","children":null,"nodeType":null,"nodes":null},"sex":2,"entryDate":1541379019000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43656,"name":"毕光林","staffNo":"043656","idType":1,"idNo":"530423199312040353","birthday":754934400000,"mobile":"15391487286","avatar":"","workEmailPrefix":"biguanglin","workEmail":"biguanglin@huatu.com","positionId":933,"position":null,"nodeId":1178,"node":{"id":1178,"pid":485,"name":"教学教研部","ids":[1,61,64,146,485,1178],"posterityIds":[1562],"fullName":"教学教研部,云南分校,华南大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1541347200000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43709,"name":"王大林","staffNo":"043709","idType":1,"idNo":"37078419941128861X","birthday":785952000000,"mobile":"15275206065","avatar":"","workEmailPrefix":"wangdalin","workEmail":"wangdalin@huatu.com","positionId":493,"position":null,"nodeId":2686,"node":{"id":2686,"pid":2622,"name":"青岛学习中心","ids":[1,61,64,149,2622,2686],"posterityIds":[2896,2897,2898,2899,2900,2901,2902,2903],"fullName":"青岛学习中心,山东分校（试点）,华北大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1541983682000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43734,"name":"林桃平","staffNo":"043734","idType":1,"idNo":"452122199104233045","birthday":672332400000,"mobile":"13907839562","avatar":"","workEmailPrefix":"lintaoping","workEmail":"lintaoping@huatu.com","positionId":933,"position":null,"nodeId":749,"node":{"id":749,"pid":489,"name":"产品教研部","ids":[1,61,64,146,489,749],"posterityIds":[],"fullName":"产品教研部,南宁分校,华南大区","children":null,"nodeType":null,"nodes":null},"sex":2,"entryDate":1541993472000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43793,"name":"张志林","staffNo":"043793","idType":1,"idNo":"410521199212153022","birthday":724348800000,"mobile":"17610077538","avatar":"","workEmailPrefix":"zhangzhilin","workEmail":"zhangzhilin@huatu.com","positionId":626,"position":null,"nodeId":1886,"node":{"id":1886,"pid":1850,"name":"教师项目部","ids":[1850,1886],"posterityIds":[],"fullName":"华图网校,教师项目部","children":null,"nodeType":null,"nodes":null},"sex":2,"entryDate":1542259800000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":43804,"name":"胡海林","staffNo":"043804","idType":1,"idNo":"342425196412080059","birthday":-159868800000,"mobile":"13956101668","avatar":"","workEmailPrefix":"huhailin","workEmail":"huhailin@huatu.com","positionId":933,"position":null,"nodeId":657,"node":{"id":657,"pid":499,"name":"公职教研中心","ids":[1,61,64,66,499,657],"posterityIds":[],"fullName":"公职教研中心,安徽分校,华中大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1542268667000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":44021,"name":"郭林炜","staffNo":"044021","idType":1,"idNo":"440303199201181310","birthday":695664000000,"mobile":"13537633233","avatar":"","workEmailPrefix":"guolinwei","workEmail":"guolinwei@huatu.com","positionId":493,"position":null,"nodeId":2812,"node":{"id":2812,"pid":2811,"name":"罗湖学习中心","ids":[1,61,64,146,2756,2811,2812],"posterityIds":[],"fullName":"罗湖学习中心,深圳学习中心,广东分校（试点）","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1543193815000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":44109,"name":"林廷璋","staffNo":"044109","idType":1,"idNo":"511304199411120714","birthday":784569600000,"mobile":"13350669254","avatar":"","workEmailPrefix":"lintingzhang","workEmail":"lintingzhang@huatu.com","positionId":828,"position":null,"nodeId":2242,"node":{"id":2242,"pid":2209,"name":"南充学习中心","ids":[1,61,64,66,2209,2242],"posterityIds":[],"fullName":"南充学习中心,四川分校（试点）,华中大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1543369749000,"gradeDto":null,"staffStatus":5,"businessUnitId":null},{"roles":null,"id":44281,"name":"李顺林","staffNo":"044281","idType":1,"idNo":"533223199211280018","birthday":722880000000,"mobile":"15025171268","avatar":"","workEmailPrefix":"lishunlin","workEmail":"lishunlin@huatu.com","positionId":520,"position":null,"nodeId":1242,"node":{"id":1242,"pid":485,"name":"丽江分校","ids":[1,61,64,146,485,1242],"posterityIds":[],"fullName":"丽江分校,云南分校,华南大区","children":null,"nodeType":null,"nodes":null},"sex":1,"entryDate":1543813159000,"gradeDto":null,"staffStatus":5,"businessUnitId":null}]
         * pages : 15
         * total : 148
         */

        @SerializedName("pages")
        public int pages;
        @SerializedName("total")
        public int total;
        @SerializedName("staffs")
        public List<ContactsBean.DataBean.StaffsBean> staffs;

    }
}
