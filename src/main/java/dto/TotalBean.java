package dto;

import java.util.List;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 08:42
 * @Description:
 */
public class TotalBean {

    private  Integer jls ;

    private List<DataBean> datalist;

    public Integer getJls() {
        return jls;
    }

    public void setJls(Integer jls) {
        this.jls = jls;
    }

    public List<DataBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DataBean> datalist) {
        this.datalist = datalist;
    }
}
