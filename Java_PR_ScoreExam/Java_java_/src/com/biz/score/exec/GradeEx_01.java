package com.biz.score.exec;

import java.util.Scanner;

import com.biz.score.config.DBcontract;
import com.biz.score.config.stLines;
import com.biz.score.service.ScoreService;
import com.biz.score.service.ScoreServiceImplV1;

public class GradeEx_01 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		ScoreService ssService = new ScoreServiceImplV1();
		ssService.loadSt();
		String stMenu = "";
		while (true) {
			System.out.println(stLines.dLine);
			System.out.println("빛고을대학학사관리시스템V1");
			System.out.println(stLines.dLine);
			System.out.println("1. 성적정보등록");
			System.out.println("2. 성적일람표출력");
			System.out.println("3. 성적파일 생성(.txt)");
			System.out.println(stLines.sLine);
			System.out.println("QUIT. 업무종료");
			System.out.println(stLines.dLine);
			System.out.println("업무선택>> ");

			stMenu = scn.nextLine();
			if (stMenu.equalsIgnoreCase("QUIT")) {
				break;
			}
			int intMenu = 0;
			try {
				intMenu = Integer.valueOf(stMenu);

			} catch (Exception e) {
				System.out.println("메뉴는숫자로만선택해주세요");
				continue;
			}
			if (intMenu == DBcontract.MENU.STUDENTSDB) {
				while (ssService.inputSt());
					
				ssService.calcSum();
				ssService.calcAvg();
			} else if (intMenu == DBcontract.MENU.SCORELIST) {
				ssService.calcSum();
				ssService.calcAvg();
				ssService.scoerList();
				ssService.saveSt();
			} else if (intMenu == DBcontract.MENU.SCORELIST_TXT) {
				ssService.scoreSave();
			}
		}
		System.out.println("업무 종료 :)");
	}

}
