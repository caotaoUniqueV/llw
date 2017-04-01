package com.linwang.uitls.web;

import java.util.ArrayList;
import java.util.List;

public class ExpressionChain implements java.io.Serializable {
	private List<Expression> expressionList;

    public ExpressionChain() {
        expressionList = new ArrayList<Expression>();
    }

    public ExpressionChain and(Expression expression) {
        expressionList.add(expression);
        return this;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }
}
