package com.example.JBProject2.facades;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.JBProject2.controllers.exceptions.AccessDeniedException;
import com.example.JBProject2.controllers.exceptions.LoginExpiredException;
import com.example.JBProject2.facades.exceptions.CompanyAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CompanyNotFoundException;
import com.example.JBProject2.facades.exceptions.CouponAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CouponExpiredOrNoStockException;
import com.example.JBProject2.facades.exceptions.CouponNotFoundException;
import com.example.JBProject2.facades.exceptions.CustomerAlreadyExistsException;
import com.example.JBProject2.facades.exceptions.CustomerNotFoundException;
import com.example.JBProject2.facades.exceptions.DataMismatchException;
import com.example.JBProject2.login_manager.exception.WrongLoginException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(CompanyAlreadyExistsException.class)
	public ResponseEntity<String> handleCompanyExistsException(Exception e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<String> handleCompanyNotFoundException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(CouponAlreadyExistsException.class)
	public ResponseEntity<String> handleCouponExistsException(Exception e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(CouponExpiredOrNoStockException.class)
	public ResponseEntity<String> handleCouponExpiredOrNoStockException(Exception e){
		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
	}
	
	@ExceptionHandler(CouponNotFoundException.class)
	public ResponseEntity<String> couponNotFoundException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<String> customerAlreadyExistsException(Exception e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> CustomerNotFoundException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(DataMismatchException.class)
	public ResponseEntity<String> dataMismatchException(Exception e){
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
	}
	
	@ExceptionHandler(WrongLoginException.class)
	public ResponseEntity<String> wrongLoginException(Exception e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> accessDeniedException(Exception e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(LoginExpiredException.class)
	public ResponseEntity<String> loginExpiredException(Exception e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	

}
