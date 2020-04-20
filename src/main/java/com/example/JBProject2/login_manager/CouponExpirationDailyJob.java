package com.example.JBProject2.login_manager;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JBProject2.beans.Coupon;
import com.example.JBProject2.db.CouponRepository;


@Service
public class CouponExpirationDailyJob extends Thread{
	
	@Autowired
	CouponRepository coupRepo;
    
	private boolean keepGoing = true;


    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }


    // Iterates over all coupons and deletes all expired coupons and their purchases.
    @Override
    public void run(){

        do{

            // Gets a correct date to pass to Date, because sql Date is the IE of Dates.
			Calendar cal = Calendar.getInstance(); 

			for(Coupon coupon:coupRepo.findByEndDateBefore(new Date(cal.getTimeInMillis()))){
			        coupRepo.deleteById(coupon.getCouponId());
			}

            try {
               Thread.sleep(86400000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }while(keepGoing == true);
    }


    // Method to stop the thread when closing the program.
    public void quit(){
        setKeepGoing(false);
        this.interrupt();
    }
}
