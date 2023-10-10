package fr.jasmin.utils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
@ManagedBean(name="navigatorMB")
@RequestScoped
public class Navigator {

@ManagedProperty("#{param.backurl}")
private String backurl;

@ManagedProperty("#{param.backId}")
private String backId;

@PostConstruct
void postConstruct() {
}

public String goBack() {
return backurl;

}

public String getBackId() {
return backId;
}

public void setBackId(String backId) {
this.backId = backId;
}

public String getBackurl() {
return backurl;
}

public void setBackurl(String backurl) {
this.backurl = backurl;
}

}