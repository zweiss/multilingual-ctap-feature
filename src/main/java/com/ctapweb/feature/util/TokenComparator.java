package com.ctapweb.feature.util;

import java.util.Comparator;

import com.ctapweb.feature.type.Token;

public class TokenComparator implements Comparator<Token> {

	@Override
	public int compare(Token o1, Token o2) {
		return o1.getBegin() - o2.getBegin();
	}

}
