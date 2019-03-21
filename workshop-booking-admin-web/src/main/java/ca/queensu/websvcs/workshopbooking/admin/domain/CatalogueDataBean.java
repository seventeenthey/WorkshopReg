/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.admin.domain;

/**
 *
 * @author Vincent
 */
public class CatalogueDataBean {
    private static final long serialVersionUID = 1L;
    
    private Integer workshopId;
    private String title;
    
    public CatalogueDataBean() {
    }
    
    public Integer getWorkshopId() {
        return workshopId;
    }
    
    public void setWorkshopId(Integer id) {
        this.workshopId = id;
    }
    
    public String getWorkshopTitle() {
        return title;
    }
    
    public void setWorkshopId(String title) {
        this.title = title;
    }
    
}
