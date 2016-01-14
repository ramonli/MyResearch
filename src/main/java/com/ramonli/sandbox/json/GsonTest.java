package com.ramonli.sandbox.json;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

public class GsonTest {
	@Test
	public void testListCurrencyRecord() {
		Test1 t = new Test1();
		CurrencyDetail cd = new CurrencyDetail();
		cd.setCnyAmount(220l);
		cd.setCurrencyId(1);
		List<CurrencyDetail> cds = new LinkedList<CurrencyDetail>();
		cds.add(cd);
		t.setAmountDetail(cds);
		System.out.println(new Gson().toJson(t));

		String json = "{\"amountDetail\":[{\"currencyId\":1,\"cnyAmount\":220}]}";
		Test1 bean = new Gson().fromJson(json, Test1.class);
		System.out.println(bean.getAmountDetail().size());
	}

	class Test1 {
		/**
		 * 总金额明细
		 */
		List<CurrencyDetail> amountDetail = new LinkedList<CurrencyDetail>();

		public List<CurrencyDetail> getAmountDetail() {
			return amountDetail;
		}

		public void setAmountDetail(List<CurrencyDetail> amountDetail) {
			this.amountDetail = amountDetail;
		}
	}

	class CurrencyDetail {
		private int currencyId;
		private long cnyAmount;

		public int getCurrencyId() {
			return currencyId;
		}

		public void setCurrencyId(int currencyId) {
			this.currencyId = currencyId;
		}

		public long getCnyAmount() {
			return cnyAmount;
		}

		public void setCnyAmount(long cnyAmount) {
			this.cnyAmount = cnyAmount;
		}

	}
}
