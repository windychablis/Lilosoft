package com.chablis.lilosoft.model;

/**
 * Created by chablis on 2017/3/28.
 */

public class Material {
    /**
     * summary_id : 7c42132317db4f1694c7f2d602f2f172
     * version : 1
     * project_id : 7dae276f61444784ab512a7ea7eda86d
     * deal_main :
     * accept_condition :
     * process : "登陆北京市工商行政管理局官网;;www.baic.gov.cn，进入;在线办事;模块，点击;登记注册;模块中的;在线办理;进入网上登记服务系统界面。注册用户后按照网站指导逐步申请：填写主体注销申请数据，并提交审核;;审核通过后，下载与打印系统生成的申请表格（包括申请文书等），按要求签字、盖章并完备所有申请材料;;网上预约到登记大厅提交纸质材料的时间--预约成功后，按系统界面提示时间或告知短信提示时间到登记大厅提交全部申请材料--材料齐全、受理及准予后4个工作日领取注销通知书；材料不齐全的，受理人员将告知申请人补齐相关材料，再次提交登记申请。"
     * mater_audit_standard : "登陆北京市工商行政管理局官网;;www.baic.gov.cn，进入;在线办事;模块，点击;登记注册;模块中的;在线办理;进入网上登记服务系统界面。注册用户后按照网站指导逐步申请：填写主体注销申请数据，并提交审核;;审核通过后，下载与打印系统生成的申请表格（包括申请文书等），按要求签字、盖章并完备所有申请材料;;网上预约到登记大厅提交纸质材料的时间--预约成功后，按系统界面提示时间或告知短信提示时间到登记大厅提交全部申请材料--材料齐全、受理及准予后4个工作日领取注销通知书；材料不齐全的，受理人员将告知申请人补齐相关材料，再次提交登记申请。"
     * charge_foundation : 无
     * charge_standard :
     * statutory_days :
     * statutory_desc : 登记申请材料被受理及准予后4个工作日领取注销通知书
     * promise_days :
     * promise_desc : 登记申请材料被受理及准予后4个工作日领取注销通知书
     * license_name :
     * accept_name : 北京市工商行政管理局朝阳分局
     * accept_address : 北京市朝阳区霄云路霄云里1号一层登记注册大厅
     * summary_img :
     * states :
     * remark : 无
     * special_procedure :
     * special_procedure_day : 登记申请材料被受理及准予后4个工作日领取注销通知书
     * exam_app_dept :
     * monitor_mode :
     * consulting_mode :
     * cert_name : 注销通知书
     * view_monitor_mode : 010-64685077
     * view_consulting_mode : 010-51069126
     * apply_material : 1、《企业注销登记申请书》（由上级公司法定代表人亲笔签署，其他填写要求请参见申请书中的说明提示）
     2、《指定（委托）书》（应加盖公司或分公司公章）
     3、 税务部门出具的注销税务登记证明或由税务部门出具未办理有关税务登记的证明
     4、分公司被依法责令关闭的，提交责令关闭的文件
     5、《营业执照》正、副本
     注意事项：
     1、上述第1、2项材料应提交北京市工商登记机关制式格式的申请文件；
     2、所有提交的材料均需使用A4纸打印；
     3、未明确提示可提交复印件的，应当提交文件原件。提交复印件时，应在复印件上注明与原件一致，并由申请人签字或盖章确认；
     4、如有必要，工商登记机关会要求上级公司法定代表人等到场签字确认登记申请内容；
     5、因未参加年检被吊销营业执照的分公司办理注销登记时，应一并办理解除负责人警示限制手续，除按照一般注销登记提交文件、证件外，还应提交上级公司出具的对分公司债权、债务清理情况及税款完结情况的说明，并在《企业注销登记申请书》中填写注销原因（具体请参见申请书注释内容）。

     * foundation :  依照《行政许可法》、《中华人民共和国公司法》、《中华人民共和国公司登记管理条例》等制定。
     * premit_cond :
     * responsibility_matters :
     * responsibility_foundation :
     * responsibility_boundary :
     * content :
     * fileno :
     * approval_object :
     * year_desc :
     * raccept_name :
     * accept_num :
     */

    private String summary_id;
    private int version;
    private String project_id;
    private String deal_main;
    private String accept_condition;
    private String process;
    private String mater_audit_standard;
    private String charge_foundation;
    private String charge_standard;
    private String statutory_days;
    private String statutory_desc;
    private String promise_days;
    private String promise_desc;
    private String license_name;
    private String accept_name;
    private String accept_address;
    private String summary_img;
    private String states;
    private String remark;
    private String special_procedure;
    private String special_procedure_day;
    private String exam_app_dept;
    private String monitor_mode;
    private String consulting_mode;
    private String cert_name;
    private String view_monitor_mode;
    private String view_consulting_mode;
    private String apply_material;
    private String foundation;
    private String premit_cond;
    private String responsibility_matters;
    private String responsibility_foundation;
    private String responsibility_boundary;
    private String content;
    private String fileno;
    private String approval_object;
    private String year_desc;
    private String raccept_name;
    private String accept_num;

    @Override
    public String toString() {
        return "Material{" +
                "summary_id='" + summary_id + '\'' +
                ", version=" + version +
                ", project_id='" + project_id + '\'' +
                ", deal_main='" + deal_main + '\'' +
                ", accept_condition='" + accept_condition + '\'' +
                ", process='" + process + '\'' +
                ", mater_audit_standard='" + mater_audit_standard + '\'' +
                ", charge_foundation='" + charge_foundation + '\'' +
                ", charge_standard='" + charge_standard + '\'' +
                ", statutory_days='" + statutory_days + '\'' +
                ", statutory_desc='" + statutory_desc + '\'' +
                ", promise_days='" + promise_days + '\'' +
                ", promise_desc='" + promise_desc + '\'' +
                ", license_name='" + license_name + '\'' +
                ", accept_name='" + accept_name + '\'' +
                ", accept_address='" + accept_address + '\'' +
                ", summary_img='" + summary_img + '\'' +
                ", states='" + states + '\'' +
                ", remark='" + remark + '\'' +
                ", special_procedure='" + special_procedure + '\'' +
                ", special_procedure_day='" + special_procedure_day + '\'' +
                ", exam_app_dept='" + exam_app_dept + '\'' +
                ", monitor_mode='" + monitor_mode + '\'' +
                ", consulting_mode='" + consulting_mode + '\'' +
                ", cert_name='" + cert_name + '\'' +
                ", view_monitor_mode='" + view_monitor_mode + '\'' +
                ", view_consulting_mode='" + view_consulting_mode + '\'' +
                ", apply_material='" + apply_material + '\'' +
                ", foundation='" + foundation + '\'' +
                ", premit_cond='" + premit_cond + '\'' +
                ", responsibility_matters='" + responsibility_matters + '\'' +
                ", responsibility_foundation='" + responsibility_foundation + '\'' +
                ", responsibility_boundary='" + responsibility_boundary + '\'' +
                ", content='" + content + '\'' +
                ", fileno='" + fileno + '\'' +
                ", approval_object='" + approval_object + '\'' +
                ", year_desc='" + year_desc + '\'' +
                ", raccept_name='" + raccept_name + '\'' +
                ", accept_num='" + accept_num + '\'' +
                '}';
    }

    public String getSummary_id() {
        return summary_id;
    }

    public void setSummary_id(String summary_id) {
        this.summary_id = summary_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getDeal_main() {
        return deal_main;
    }

    public void setDeal_main(String deal_main) {
        this.deal_main = deal_main;
    }

    public String getAccept_condition() {
        return accept_condition;
    }

    public void setAccept_condition(String accept_condition) {
        this.accept_condition = accept_condition;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getMater_audit_standard() {
        return mater_audit_standard;
    }

    public void setMater_audit_standard(String mater_audit_standard) {
        this.mater_audit_standard = mater_audit_standard;
    }

    public String getCharge_foundation() {
        return charge_foundation;
    }

    public void setCharge_foundation(String charge_foundation) {
        this.charge_foundation = charge_foundation;
    }

    public String getCharge_standard() {
        return charge_standard;
    }

    public void setCharge_standard(String charge_standard) {
        this.charge_standard = charge_standard;
    }

    public String getStatutory_days() {
        return statutory_days;
    }

    public void setStatutory_days(String statutory_days) {
        this.statutory_days = statutory_days;
    }

    public String getStatutory_desc() {
        return statutory_desc;
    }

    public void setStatutory_desc(String statutory_desc) {
        this.statutory_desc = statutory_desc;
    }

    public String getPromise_days() {
        return promise_days;
    }

    public void setPromise_days(String promise_days) {
        this.promise_days = promise_days;
    }

    public String getPromise_desc() {
        return promise_desc;
    }

    public void setPromise_desc(String promise_desc) {
        this.promise_desc = promise_desc;
    }

    public String getLicense_name() {
        return license_name;
    }

    public void setLicense_name(String license_name) {
        this.license_name = license_name;
    }

    public String getAccept_name() {
        return accept_name;
    }

    public void setAccept_name(String accept_name) {
        this.accept_name = accept_name;
    }

    public String getAccept_address() {
        return accept_address;
    }

    public void setAccept_address(String accept_address) {
        this.accept_address = accept_address;
    }

    public String getSummary_img() {
        return summary_img;
    }

    public void setSummary_img(String summary_img) {
        this.summary_img = summary_img;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpecial_procedure() {
        return special_procedure;
    }

    public void setSpecial_procedure(String special_procedure) {
        this.special_procedure = special_procedure;
    }

    public String getSpecial_procedure_day() {
        return special_procedure_day;
    }

    public void setSpecial_procedure_day(String special_procedure_day) {
        this.special_procedure_day = special_procedure_day;
    }

    public String getExam_app_dept() {
        return exam_app_dept;
    }

    public void setExam_app_dept(String exam_app_dept) {
        this.exam_app_dept = exam_app_dept;
    }

    public String getMonitor_mode() {
        return monitor_mode;
    }

    public void setMonitor_mode(String monitor_mode) {
        this.monitor_mode = monitor_mode;
    }

    public String getConsulting_mode() {
        return consulting_mode;
    }

    public void setConsulting_mode(String consulting_mode) {
        this.consulting_mode = consulting_mode;
    }

    public String getCert_name() {
        return cert_name;
    }

    public void setCert_name(String cert_name) {
        this.cert_name = cert_name;
    }

    public String getView_monitor_mode() {
        return view_monitor_mode;
    }

    public void setView_monitor_mode(String view_monitor_mode) {
        this.view_monitor_mode = view_monitor_mode;
    }

    public String getView_consulting_mode() {
        return view_consulting_mode;
    }

    public void setView_consulting_mode(String view_consulting_mode) {
        this.view_consulting_mode = view_consulting_mode;
    }

    public String getApply_material() {
        return apply_material;
    }

    public void setApply_material(String apply_material) {
        this.apply_material = apply_material;
    }

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getPremit_cond() {
        return premit_cond;
    }

    public void setPremit_cond(String premit_cond) {
        this.premit_cond = premit_cond;
    }

    public String getResponsibility_matters() {
        return responsibility_matters;
    }

    public void setResponsibility_matters(String responsibility_matters) {
        this.responsibility_matters = responsibility_matters;
    }

    public String getResponsibility_foundation() {
        return responsibility_foundation;
    }

    public void setResponsibility_foundation(String responsibility_foundation) {
        this.responsibility_foundation = responsibility_foundation;
    }

    public String getResponsibility_boundary() {
        return responsibility_boundary;
    }

    public void setResponsibility_boundary(String responsibility_boundary) {
        this.responsibility_boundary = responsibility_boundary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileno() {
        return fileno;
    }

    public void setFileno(String fileno) {
        this.fileno = fileno;
    }

    public String getApproval_object() {
        return approval_object;
    }

    public void setApproval_object(String approval_object) {
        this.approval_object = approval_object;
    }

    public String getYear_desc() {
        return year_desc;
    }

    public void setYear_desc(String year_desc) {
        this.year_desc = year_desc;
    }

    public String getRaccept_name() {
        return raccept_name;
    }

    public void setRaccept_name(String raccept_name) {
        this.raccept_name = raccept_name;
    }

    public String getAccept_num() {
        return accept_num;
    }

    public void setAccept_num(String accept_num) {
        this.accept_num = accept_num;
    }
}
