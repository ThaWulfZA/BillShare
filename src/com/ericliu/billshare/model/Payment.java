package com.ericliu.billshare.model;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.content.ContentValues;
import android.net.Uri;
import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class Payment extends Model {

	private final long payment_info_serial_number;
	private final long bill_id;
	private final long payee_id;
	private final int payee_days;
	private final String payee_start_date;
	private final String payee_end_date;
	private final double payee_amount;

	@Override
	public Uri save() {

		ContentValues values = new ContentValues();
		values.put(COL_PAYMENT_INFO_ID, payment_info_serial_number);
		values.put(COL_BILL_ID, bill_id);
		values.put(COL_PAYEE_ID, payee_id);
		values.put(COL_PAYEE_DAYS, payee_days);
		values.put(COL_PAYEE_START_DATE, payee_start_date);
		values.put(COL_PAYEE_END_DATE, payee_end_date);
		values.put(COL_PAYEE_AMOUNT, payee_amount);

		return MyApplication.getInstance().getContentResolver()
				.insert(BillProvider.PAYMENT_URI, values);
	}

	public static class Builder {
		private long payment_info_serial_number;
		private long bill_id;
		private long payee_id;
		private int payee_days;
		private String payee_start_date;
		private String payee_end_date;
		private double payee_amount;

		public Builder(long payment_info_serial_number, long bill_id,
				long payee_id) {
			this.payment_info_serial_number = payment_info_serial_number;
			this.bill_id = bill_id;
			this.payee_id = payee_id;
		}
		
		
		public Builder payeeDays(int days){
			this.payee_days = days;
			return this;
		}
		
		public Builder payeeStartDate(String date){
			this.payee_start_date = date;
			return this;
		}
		
		public Builder payeeEndDate(String date){
			this.payee_end_date = date;
			return this;
		}
		
		public Builder payee_amount(double amount){
			this.payee_amount = amount;
			return this;
		}

		public Payment build() {

			return new Payment(this);
		}


	}
	
	
	private Payment(Builder builder){
		this.payment_info_serial_number = builder.payment_info_serial_number;
		this.bill_id = builder.bill_id;
		this.payee_id = builder.payee_id;
		this.payee_days = builder.payee_days;
		this.payee_start_date = builder.payee_start_date;
		this.payee_end_date = builder.payee_end_date;
		this.payee_amount = builder.payee_amount;
	}


	public long getPayment_info_serial_number() {
		return payment_info_serial_number;
	}

	public long getBill_id() {
		return bill_id;
	}

	public long getPayee_id() {
		return payee_id;
	}

	public int getPayee_days() {
		return payee_days;
	}

	public String getPayee_start_date() {
		return payee_start_date;
	}

	public String getPayee_end_date() {
		return payee_end_date;
	}

	public double getPayee_amount() {
		return payee_amount;
	}

}
