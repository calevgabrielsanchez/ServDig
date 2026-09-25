package mx.gob.imss.cit.cda.web.bandeja.vo;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class RequestSolicitudBandejaPage extends BaseModel {
    
        /**
         * 
         */
        private static final long serialVersionUID = 5805755486562033534L;
        
        private int pageSize;
        private int page;
        private UserProfile userProfile;
        private FilterBandeja filter;
        private Integer pantallaConsulta;
        private String folioConsulta;

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

        public FilterBandeja getFilter() {
            return filter;
        }

        public void setFilter(FilterBandeja filter) {
            this.filter = filter;
        }

		
		public String getFolioConsulta() {
			return folioConsulta;
		}

		public void setFolioConsulta(String folioConsulta) {
			this.folioConsulta = folioConsulta;
		}

		public Integer getPantallaConsulta() {
			return pantallaConsulta;
		}

		public void setPantallaConsulta(Integer pantallaConsulta) {
			this.pantallaConsulta = pantallaConsulta;
		}
}