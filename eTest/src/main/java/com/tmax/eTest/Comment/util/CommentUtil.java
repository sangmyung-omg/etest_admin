package com.tmax.eTest.Comment.util;

public class CommentUtil {

	public enum CommentType{
		ALL("all"),
		KNOWLEDGE("knowledge"),
		RISK("risk"),
		INVEST("invest"),
		GI("gi");
		
		private String value;
		
		private CommentType(String value) {
			this.value = value;
		}
		
		public final String toString() {
			return this.value;
		}
	}
	public static CommentType getCommentTypeFromSeqNum(int seqNum)
	{
		if(seqNum < 3)
			return CommentType.GI;
		else if(seqNum < 70)
			return CommentType.RISK;
		else if(seqNum < 112)
			return CommentType.INVEST;
		else
			return CommentType.KNOWLEDGE;
	}
}
