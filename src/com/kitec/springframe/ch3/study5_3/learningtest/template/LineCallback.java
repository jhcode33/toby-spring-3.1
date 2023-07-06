package com.kitec.springframe.ch3.study5_3.learningtest.template;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
