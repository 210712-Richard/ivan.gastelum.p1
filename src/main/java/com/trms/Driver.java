package com.trms;

import com.trms.util.CassandraUtil;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dbtest();
	}
	
	public static void dbtest() {
		CassandraUtil.getInstance().getSession();
	}

}
