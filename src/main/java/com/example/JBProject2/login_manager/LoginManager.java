package com.example.JBProject2.login_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.JBProject2.facades.AdminFacade;
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.CompanyFacade;
import com.example.JBProject2.facades.CustomerFacade;
import com.example.JBProject2.login_manager.exception.WrongLoginException;

@Service
@Scope("prototype")
public class LoginManager {
	
	@Autowired
	ConfigurableApplicationContext ctx;
	
    public ClientFacade login(String email, String password, ClientType clientType) throws WrongLoginException {

        switch(clientType){
            case Administrator:
            	AdminFacade adminFace = ctx.getBean(AdminFacade.class);
            	if(adminFace.login(email, password)) return adminFace;
                else throw new WrongLoginException();
            case Company:
            	CompanyFacade compFace = ctx.getBean(CompanyFacade.class);
                if(compFace.login(email, password)) return compFace;
                else throw new WrongLoginException();
            case Customer:
            	CustomerFacade custFace = ctx.getBean(CustomerFacade.class);
                if(custFace.login(email, password)) return custFace;
                else throw new WrongLoginException();
            default:
                    return null;

            }
    }

}
