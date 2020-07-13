package com.biz.score.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import com.biz.score.config.DBcontract;
import com.biz.score.config.stLines;
import com.biz.score.domain.ScoreVO;

public class ScoreServiceImplV1 implements ScoreService {

	private List<ScoreVO> scoreList;
	private Scanner scn;
	private String fileName;
	private int[] totalSum;

	public ScoreServiceImplV1() {
		scoreList = new ArrayList<ScoreVO>();
		scn = new Scanner(System.in);

		fileName = "src/com/biz/score/data/score.txt";
		totalSum = new int[4];
	}

	@Override
	public boolean inputSt() {
		System.out.println("학번(END>>종료)입력해주세요>>");
		String stNum = scn.nextLine();

		if (stNum.equalsIgnoreCase("END")) {
			return false;
		}

		int intNum = 0;
		try {
			intNum = Integer.valueOf(stNum);
		} catch (Exception e) {
			System.out.println("학번은숫자만입력가능");
			System.out.println("입력한문자열:" + stNum);
		}
		if (intNum < 1 || intNum > 99999) {
			System.out.println("학번은1 ~ 99999까지만가능");
			System.out.println("다시입력해주세요");
			return true;
		}

		stNum = String.format("%05d", intNum);
		for (ScoreVO scVO : scoreList) {
			if (scVO.getStNum().equals(stNum)) {
				System.out.println("이미 등록 되어있는 학생입니다.");
				return true;
			}
		}

		System.out.println("국어 점수입력>>");
		String stKor = scn.nextLine();

		int intStKor = 0;
		try {
			intStKor = Integer.valueOf(stKor);
		} catch (NumberFormatException e) {
			System.out.println("점수입력은숫자만가능!!");
		}
		if (intStKor < 0 || intStKor > 100) {
			System.out.println("점수는0점부터100점까지입력가능!!! ");
			System.out.println("다시입력해주세요");
			return true;
		}

		System.out.println("영어점수입력>>");
		String stEng = scn.nextLine();

		int intStEng = 0;
		try {
			intStEng = Integer.valueOf(stEng);
		} catch (NumberFormatException e) {
			System.out.println("점수입력은숫자만가능!!");
		}
		if (intStEng < 0 || intStEng > 100) {
			System.out.println("점수는0점부터100점까지입력가능!!! ");
			System.out.println("다시입력해주세요");
			return true;
		}

		System.out.println("수학점수입력>>");
		String stMath = scn.nextLine();

		int intStMath = 0;
		try {
			intStMath = Integer.valueOf(stMath);
		} catch (NumberFormatException e) {
			System.out.println("점수입력은숫자만가능!!");
		}
		if (intStMath < 0 || intStMath > 100) {
			System.out.println("점수는0점부터100점까지입력가능!!! ");
			System.out.println("다시입력해주세요");
			return true;
		}

		System.out.println("음악점수입력>>");
		String stMusic = scn.nextLine();

		int intStMusic = 0;
		try {
			intStMusic = Integer.valueOf(stMusic);
		} catch (NumberFormatException e) {
			System.out.println("점수입력은숫자만가능!!");
		}
		if (intStMusic < 0 || intStMusic > 100) {
			System.out.println("점수는0점부터100점까지입력가능!!! ");
			System.out.println("다시입력해주세요");
			return true;
		}
		
		ScoreVO sVO = new ScoreVO();
		sVO.setStNum(stNum);
		sVO.setKor(intStKor);
		sVO.setEng(intStEng);
		sVO.setMath(intStMath);
		sVO.setMusic(intStMusic);
		scoreList.add(sVO);
		return true;
	}

	@Override
	public void loadSt() {
		if (!scoreList.isEmpty()) {
			System.out.println("리스트에 값이 남아있습니다!");
			return;
		}

		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(fileName);
			buffer = new BufferedReader(fileReader);
			String reader = "";
			String[] scores;
			ScoreVO scVO;
			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}
				scores = reader.split(":");
				scVO = new ScoreVO();
				scVO.setStNum(scores[DBcontract.STUDENT.SC_STNUM]);
				scVO.setKor(Integer.valueOf(scores[DBcontract.STUDENT.SC_KOR]));
				scVO.setEng(Integer.valueOf(scores[DBcontract.STUDENT.SC_ENG]));
				scVO.setMath(Integer.valueOf(scores[DBcontract.STUDENT.SC_MATH]));
				scVO.setMusic(Integer.valueOf(scores[DBcontract.STUDENT.SC_MUSIC]));
				scoreList.add(scVO);
			}
			buffer.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("학생정보파일열기오류");
		} catch (IOException e) {
			System.out.println("학생정보파일읽기오류");
		}

	}

	@Override
	public void calcSum() {
		int sum = 0;
		for (ScoreVO sumVO : scoreList) {
			sum = sumVO.getKor() + sumVO.getEng() + sumVO.getMath() + sumVO.getMusic();
			sumVO.setIntSum(sum);
		}
	}

	@Override
	public void calcAvg() {
		for (ScoreVO avgVO : scoreList) {
			int avg = avgVO.getIntSum() / 4;
			avgVO.setIntAvg(avg);
		}
	}

	@Override
	public void scoerList() {

		Arrays.fill(totalSum, 0);
		System.out.println(stLines.dLine);
		System.out.println("성적일람표");
		System.out.println(stLines.dLine);
		System.out.println("학번\t|국어\t|영어\t|수학\t|음악\t|총점\t|평균\t|");
		for (ScoreVO stListVO : scoreList) {
			System.out.printf("%s\t|", stListVO.getStNum());
			System.out.printf("%s\t|", stListVO.getKor());
			System.out.printf("%s\t|", stListVO.getEng());
			System.out.printf("%s\t|", stListVO.getMath());
			System.out.printf("%s\t|", stListVO.getMusic());
			System.out.printf("%s\t|", stListVO.getIntSum());
			System.out.printf("%s\t|\n", stListVO.getIntAvg());

			totalSum[0] += stListVO.getKor();
			totalSum[1] += stListVO.getEng();
			totalSum[2] += stListVO.getMath();
			totalSum[3] += stListVO.getMusic();
		}
		System.out.println(stLines.sLine);
		System.out.print("과목총점:\t|");
		int sumAndSum = totalSum[0] + totalSum[1] + totalSum[2] + totalSum[3];

		System.out.println(String.format("%d\t|%d\t|%d\t|%d\t|%d\t|x\t|", 
				totalSum[0], totalSum[1], totalSum[2], totalSum[3], sumAndSum));

		System.out.println(stLines.sLine);
		System.out.print("과목평균:\t|");
		float avgAndAvg = 0f;
		int size = scoreList.size();
		for (int sum : totalSum) {
			float avg = (float) sum / size;
			System.out.printf("%5.2f\t|", avg);
			avgAndAvg += avg;
		}
		System.out.printf("\t|%5.2f\t|\n|", avgAndAvg / totalSum.length);

		System.out.println(stLines.dLine);
	}

	@Override
	public void saveSt() {
		PrintStream printStream = null;
        try {
			printStream = new PrintStream(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을수 없어서 새로 생성합니다!");
        }
        
		for (ScoreVO scoreVO : scoreList) {
			printStream.printf("%s:", scoreVO.getStNum());
			printStream.printf("%d:", scoreVO.getKor());
			printStream.printf("%d:", scoreVO.getEng());
			printStream.printf("%d:", scoreVO.getMath());
			printStream.printf("%d\n", scoreVO.getMusic());
		}
		printStream.close();
	}

	@Override
	public void scoreSave() {
		PrintStream printStream = null;
        try {
			printStream = new PrintStream("src/com/biz/score/data/scoreList.txt");
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을수 없어서 새로 생성합니다!");
        }
        
        printStream.println(stLines.dLine);
		printStream.println("성적일람표");
		printStream.println(stLines.dLine);
		printStream.println("학번\t|국어\t|영어\t|수학\t|음악\t|총점\t|평균\t|");
		for (ScoreVO stListVO : scoreList) {
			printStream.printf("%s\t|", stListVO.getStNum());
			printStream.printf("%s\t|", stListVO.getKor());
			printStream.printf("%s\t|", stListVO.getEng());
			printStream.printf("%s\t|", stListVO.getMath());
			printStream.printf("%s\t|", stListVO.getMusic());
			printStream.printf("%s\t|", stListVO.getIntSum());
			printStream.printf("%s\t|\n", stListVO.getIntAvg());

			totalSum[0] += stListVO.getKor();
			totalSum[1] += stListVO.getEng();
			totalSum[2] += stListVO.getMath();
			totalSum[3] += stListVO.getMusic();

		}
		printStream.println(stLines.sLine);
		printStream.print("과목총점:\t|");
		int sumAndSum = totalSum[0] + totalSum[1] + totalSum[2] + totalSum[3];

		printStream.println(String.format("%d\t|%d\t|%d\t|%d\t|%d\t|x\t|", 
				totalSum[0], totalSum[1], totalSum[2], totalSum[3], sumAndSum));

		printStream.println(stLines.sLine);
		printStream.print("과목평균:\t|");
		float avgAndAvg = 0f;
		int size = scoreList.size();
		for (int sum : totalSum) {
			float avg = (float) sum / size;
			printStream.printf("%5.2f\t|", avg);
			avgAndAvg += avg;
		}
		printStream.printf("\t|%5.2f\t|\n|", avgAndAvg / totalSum.length);

		printStream.println(stLines.dLine);
		printStream.close();
	}
}
