package com.intheeast.learningtest.template;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
