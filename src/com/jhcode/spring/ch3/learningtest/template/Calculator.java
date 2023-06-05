package com.jhcode.spring.ch3.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	
	public Integer calcSum(String filepath) throws IOException {
		
		//콜백 오브젝트, 익명 내부 클래스이다.
		BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
			
			//변경되는 부분, doSomethingWithReader을 오버라이드하여 재정의하여 콜백 동작을 수행한다.
			public Integer doSomethingWithReader(BufferedReader br) throws IOException{
				
				Integer sum = 0;
				String line = null;
				while((line = br.readLine()) != null) {
					sum += Integer.valueOf(line);
				}
				
				return sum;
			}
		};
		return fileReadTemplate(filepath, sumCallback);
	}
	
	
	public Integer calcMultiply(String filePath) throws IOException {
		
		//콜백 오브젝트
		BufferedReaderCallback multiplyCallback = new BufferedReaderCallback() {
			
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				Integer multiply = 1;
				String line = null;
				while((line = br.readLine()) != null) {
					multiply *= Integer.valueOf(line);
				}
				return multiply;
			}
		};
		return fileReadTemplate(filePath, multiplyCallback);
	}
	
	
	
	//템플릿, -> 변경되지 않는 부분
	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException{
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(filepath));
			int ret = callback.doSomethingWithReader(br);
			return ret;
			
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
