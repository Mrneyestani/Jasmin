package fr.jasmin.model.dao.interfaces;

import fr.jasmin.entity.Param;

public interface IParamDao {

	void addSecurityKey(Param param) throws Exception;
	Param getSecurityKeyById(Integer id) throws Exception;

}
