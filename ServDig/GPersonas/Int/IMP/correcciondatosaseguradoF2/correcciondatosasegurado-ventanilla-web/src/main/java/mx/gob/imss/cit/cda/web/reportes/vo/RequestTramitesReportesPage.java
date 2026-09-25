package mx.gob.imss.cit.cda.web.reportes.vo;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class RequestTramitesReportesPage extends BaseModel {

    private static final long serialVersionUID = -202951098680358624L;
    private int pageSize;
    private int page;
    private FilterReporte filter;
    private UserProfile userProfile;

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the userProfile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * @param userProfile
     *            the userProfile to set
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public FilterReporte getFilter() {
        return filter;
    }

    public void setFilter(FilterReporte filter) {
        this.filter = filter;
    }
}
