package com.jhcode.spring.ch3.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.jhcode.spring.ch3.user.dao.LineCallback;

public class Calculator {
	
	public Integer calcSum(String filepath) throws IOException {
		
		//콜백 오브젝트, 익명 내부 클래스이다.
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			
			//콜백 메소드는 오직 파일의 읽은 한 줄의 값을 가져와서 더하는 작업만 실시한다.
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filepath, sumCallback, 0);
	}
	
	
	public Integer calcMultiply(String filePath) throws IOException {
		
		//콜백 오브젝트
		LineCallback<Integer> multiplyCallback = new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filePath, multiplyCallback, 1);
	}
	
	//== 제네릭을 사용하여 String으로 처리 ==//
	public String concatenate(String filepath) throws IOException {
		
		LineCallback<String> concatenateCallback = new LineCallback<String>() {
			
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}
		};
		return lineReadTemplate(filepath, concatenateCallback, "");
	}
	
	
	
	//템플릿, -> 변경되지 않는 부분
	public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException{
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(filepath));
			
			//콜백 메소드를 통해 받환된 결과를 담을 변수
			T res = initVal;
			String line = null;
			
			//각 라인의 내용을 계산하는 작업만 콜백에게 전담한다
			while((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
			
		} finally {
			if (br != null) {
				try {br.close();} 
				catch (IOException e) {System.out.println(e.getMessage());}
			}
		}
	}
}
