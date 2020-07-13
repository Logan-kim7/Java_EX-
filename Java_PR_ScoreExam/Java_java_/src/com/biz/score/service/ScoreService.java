package com.biz.score.service;

import com.biz.score.domain.ScoreVO;

public interface ScoreService {
	public boolean inputSt();

	public void loadSt();

	public void calcSum();

	public void calcAvg();

	public void scoerList();

	public void saveSt();

	public void scoreSave();

}
