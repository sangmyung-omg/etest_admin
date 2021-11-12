package com.tmax.eTest.Comment.util;

public class CommentUtil {

	public enum CommentUnit{
		ALL("all"),
		KNOWLEDGE("knowledge"),
		RISK("risk"),
		INVEST("invest"),
		GI("gi");
		
		private String value;
		
		private CommentUnit(String value) {
			this.value = value;
		}
		
		public final String toString() {
			return this.value;
		}
	}
	
}
