package com.example.JBProject2.login_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JBProject2.facades.AdminFacade;
import com.example.JBProject2.facades.ClientFacade;
import com.example.JBProject2.facades.CompanyFacade;
import com.example.JBProject2.facades.CustomerFacade;
import com.example.JBProject2.login_manager.exception.WrongLoginException;

@Service
public class LoginManager {
	
	@Autowired
	AdminFacade adminFace;
	@Autowired
	CompanyFacade compFace;
	@Autowired
	CustomerFacade custFace;
	
    public ClientFacade login(String email, String password, ClientType clientType) throws WrongLoginException {

        switch(clientType){
            case Administrator:
                if(adminFace.login(email, password)) return adminFace;
                else throw new WrongLoginException();
            case Company:
                if(compFace.login(email, password)) return compFace;
                else throw new WrongLoginException();
            case Customer:
                if(custFace.login(email, password)) return custFace;
                else throw new WrongLoginException();
            default:
                    return null;

            }
    }

}
