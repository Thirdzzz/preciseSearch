package org.ictclas4j.segment;

import java.util.ArrayList;

import org.ictclas4j.bean.SegNode;
import org.ictclas4j.utility.NumberTools;
import org.ictclas4j.utility.PosTag;
import org.ictclas4j.utility.Utility;


/**
 * �ִ��Ż�����
 */
public class AdjustSeg {
	/**
	 * �Գ��ηִʽ�����е�������Ҫ�Ƕ�ʱ�䡢���ڡ����ֵȽ��кϲ�����
	 */
	public static ArrayList<SegNode> firstAdjust(ArrayList<SegNode> sgs) {

		ArrayList<SegNode> wordResult = null;
		int index = 0;
		int j = 0;
		int pos = 0;

		if (sgs != null) {
			wordResult = new ArrayList<SegNode>();

			for (int i = 0; i < sgs.size(); i++, index++) {
				SegNode sn = sgs.get(i);
				String srcWord = null;
				String curWord = sn.getSrcWord();
				SegNode newsn = new SegNode();
				pos = sn.getPos();

				boolean isNum = false;
				if ((Utility.isAllNum(curWord) || Utility.isAllChineseNum(curWord))) {
					isNum = true;
					for (j = i + 1; j < sgs.size() - 1; j++) {
						String temp = sgs.get(j).getSrcWord();

						if (Utility.isAllNum(temp) || Utility.isAllChineseNum(temp)) {
							isNum = true;
							index = j;
							curWord += temp;
						} else
							break;
					}
				}

				if (!isNum) {
					SegNode prevsn = null;
					if (wordResult.size() > 0)
						prevsn = wordResult.get(wordResult.size() - 1);
					if (Utility.isDelimiter(curWord)) {

						if (prevsn != null && Utility.isDelimiter(prevsn.getWord())) {
							prevsn.setCol(sn.getCol());
							prevsn.appendWord(curWord);
							continue;
						} else
							// 'w'*256;Set the POS with 'w'
							pos = PosTag.PUNC;
					} else if (curWord.length() == 1 && "����ʱ����".indexOf(curWord) != -1 || "�·�".equals(curWord)) {
						if (prevsn != null && prevsn.getPos() == -PosTag.NUM) {
							prevsn.setCol(sn.getCol());
							prevsn.setWord(Utility.UNKNOWN_TIME);
							prevsn.setSrcWord(prevsn.getSrcWord() + curWord);
							prevsn.setPos(-PosTag.TIME);
							continue;
						}
					} else if ("��".equals(curWord)) {
						if (prevsn != null && Utility.isYearTime(prevsn.getSrcWord())) {
							prevsn.setCol(sn.getCol());
							prevsn.setWord(Utility.UNKNOWN_TIME);
							prevsn.setSrcWord(prevsn.getSrcWord() + curWord);
							prevsn.setPos(-PosTag.TIME);
							continue;
						}
					}
				} else {
					if (NumberTools.isNumStrNotNum(curWord)) {
						for (int k = i; k <= index; k++)
							wordResult.add(sgs.get(k));
						continue;
					}
					else {
						boolean flag = false;
						int size = wordResult.size();
						if (wordResult.size() > 1) {
							SegNode prevPrevsn = wordResult.get(size - 2);
							SegNode prevsn = wordResult.get(size - 1);
							if (NumberTools.isNumDelimiter(prevPrevsn.getPos(), prevsn.getWord())) {
								pos = PosTag.NUM;
								flag = true;
							}
						}
						if (!flag) {
							if (curWord.indexOf("��") == curWord.length() - 1) {
								pos = -PosTag.TIME;
								srcWord = curWord;
								curWord = Utility.UNKNOWN_TIME;
							} else if (curWord.length() > 1) {
								String last = curWord.substring(curWord.length() - 1);
								if ("�á�����./".indexOf(last) == -1) {
									pos = -PosTag.NUM;
									srcWord = curWord;
									curWord = Utility.UNKNOWN_NUM;

								} else {
									if (".".equals(last) || "/".equals(last)) {
										pos = -PosTag.NUM;
										srcWord = curWord.substring(0, curWord.length() - 1);
										curWord = Utility.UNKNOWN_NUM;
										//index--;
									} else if (curWord.length() > 2) {
										pos = -PosTag.NUM;
										srcWord = curWord.substring(0, curWord.length() - 2);
										curWord = Utility.UNKNOWN_NUM;
										//index -= 2;
									}
								}
							}
						}
					}

				}

				int col = index > i ? sgs.get(index).getCol() : sn.getCol();
				newsn.setCol(col);
				newsn.setRow(sn.getRow());
				newsn.setWord(curWord);
				newsn.setPos(pos);
				newsn.setValue(sn.getValue());
				if (srcWord != null)
					newsn.setSrcWord(srcWord);
				wordResult.add(newsn);
				i = index;
			}
		}

		return wordResult;
	}

	/**
	 * �Էִʽ�������յĵ�������Ҫ�������Ĳ�ֻ��ص��ʵĺϲ�
	 */
	public static ArrayList<SegNode> finaAdjust(ArrayList<SegNode> optSegPath, PosTagger personTagger,
			PosTagger placeTagger) {
		ArrayList<SegNode> result = null;
		SegNode wr = null;

		if (optSegPath != null && optSegPath.size() > 0 && personTagger != null && placeTagger != null) {

			result = new ArrayList<SegNode>();
			for (int i = 0; i < optSegPath.size(); i++) {
				boolean isBeProcess = false;
				wr = optSegPath.get(i);


				if (wr.getPos() == PosTag.NUM && i + 2 < optSegPath.size() && optSegPath.get(i + 1).getLen() == 2
						&& optSegPath.get(i + 1).getSrcWord().equals(optSegPath.get(i + 2).getSrcWord())) {
					SegNode wr2 = new SegNode();
					wr2.setWord(wr.getSrcWord() + optSegPath.get(i + 1).getSrcWord()
							+ optSegPath.get(i + 2).getSrcWord());
					wr2.setPos(PosTag.NUM);
					result.add(wr2);
					i += 2;
					isBeProcess = true;
				}
				else if (wr.getLen() == 2 && i + 1 < optSegPath.size()
						&& wr.getSrcWord().equals(optSegPath.get(i + 1).getSrcWord())) {
					SegNode wr2 = new SegNode();
					wr2.setWord(wr.getSrcWord() + optSegPath.get(i + 1).getSrcWord());
					wr2.setPos(PosTag.ADJ);
					if (wr.getPos() == PosTag.VERB || optSegPath.get(i + 1).getPos() == PosTag.VERB)// 30208='v'8256
						wr2.setPos(PosTag.VERB);

					if (wr.getPos() == PosTag.NOUN || optSegPath.get(i + 1).getPos() == PosTag.NOUN)// 30208='v'8256
						wr2.setPos(PosTag.NOUN);

					i += 1;
					if (optSegPath.get(i + 1).getLen() == 2) {// AAB:ϴ/ϴ/����������
						if ((wr2.getPos() == PosTag.VERB && optSegPath.get(i + 1).getPos() == PosTag.NOUN)
								|| (wr2.getPos() == PosTag.ADJ && optSegPath.get(i + 1).getPos() == PosTag.ADJ)) {
							wr2.setWord(wr2.getWord() + optSegPath.get(i + 1).getSrcWord());
							i += 1;
						}
					}
					isBeProcess = true;
					result.add(wr2);
				}
				else if (wr.getLen() == 2 && i + 1 < optSegPath.size()
						&& (wr.getPos() == PosTag.VERB || wr.getPos() == PosTag.ADJ)
						&& optSegPath.get(i + 1).getLen() == 4
						&& optSegPath.get(i + 1).getSrcWord().indexOf(wr.getSrcWord()) == 0) {
					SegNode wr2 = new SegNode();
					wr2.setWord(wr.getWord() + optSegPath.get(i + 1).getSrcWord());
					wr2.setPos(PosTag.ADJ); // 24832=='a'*256

					if (wr.getPos() == PosTag.VERB || optSegPath.get(i + 1).getPos() == PosTag.VERB)// 30208='v'8256
						wr2.setPos(PosTag.VERB);

					i += 1;
					isBeProcess = true;
					result.add(wr2);
				} else if (wr.getPos() / 256 == 'u' && wr.getPos() % 256 != 0)// uj,ud,uv,uz,ul,ug->u
					wr.setPos('u' * 256);

				else if (wr.getLen() == 2 && i + 2 < optSegPath.size() && optSegPath.get(i + 1).getLen() == 4
						&& optSegPath.get(i + 1).getWord().indexOf(wr.getWord()) == 0
						&& optSegPath.get(i + 1).getWord().indexOf(optSegPath.get(i + 2).getWord()) == 0) {
					SegNode wr2 = new SegNode();
					wr2.setWord(wr.getWord() + optSegPath.get(i + 1).getWord() + optSegPath.get(i + 2).getWord());
					wr2.setPos(optSegPath.get(i + 1).getPos());
					i += 2;
					isBeProcess = true;
					result.add(wr2);
				}
				// 28275=='n'*256+'s' ����+X
				else if (wr.getPos() == PosTag.NOUN_SPACE && i + 1 < optSegPath.size())// PostFix
				{
					SegNode next = optSegPath.get(i + 1);
					if (placeTagger.getUnknownDict().isExist(next.getSrcWord(), 4)) {
						SegNode wr2 = new SegNode();
						wr2.setWord(wr.getSrcWord() + next.getSrcWord());
						wr2.setPos(PosTag.NOUN_SPACE);
						i += 1;
						isBeProcess = true;
						result.add(wr2);
					} else if ("��".equals(next.getSrcWord())) {
						SegNode wr2 = new SegNode();
						wr2.setWord(wr.getSrcWord() + next.getSrcWord());
						wr2.setPos(PosTag.NOUN_ORG);
						i += 1;
						isBeProcess = true;
						result.add(wr2);
					} else if (optSegPath.get(i + 1).getLen() == 2 && "�����ֱ�".indexOf(next.getSrcWord()) != -1) {
						SegNode wr2 = new SegNode();
						wr2.setWord(wr.getSrcWord() + next.getSrcWord());
						wr2.setPos(PosTag.NOUN_ZHUAN);
						i += 1;
						isBeProcess = true;
						result.add(wr2);
					} else if ("��".equals(next.getSrcWord())) {
						SegNode wr2 = new SegNode();
						wr2.setWord(wr.getSrcWord() + next.getSrcWord());
						wr2.setPos(PosTag.NOUN);
						i += 1;
						isBeProcess = true;
						result.add(wr2);
					}
				} else if (wr.getPos() == PosTag.VERB  || wr.getPos() == PosTag.VERB_NOUN  ||wr.getPos() == PosTag.NOUN)// v
				{
					if (i + 1 < optSegPath.size() && "Ա".equals(optSegPath.get(i + 1).getSrcWord())) {
						SegNode wr2 = new SegNode();
						wr2.setWord(wr.getSrcWord() + optSegPath.get(i + 1).getSrcWord());
						wr2.setPos(PosTag.NOUN);
						i += 1;
						isBeProcess = true;
						result.add(wr2);
					}
				}

				else if (wr.getPos() == PosTag.NOUN_LETTER && i + 1 < optSegPath.size()) {
					SegNode wr2 = new SegNode();
					wr2.setWord(wr.getSrcWord());
					wr2.setPos(PosTag.NOUN_LETTER);
					while (true) {
						SegNode nextSN = optSegPath.get(i + 1);
						if (nextSN.getPos() == PosTag.NOUN_LETTER || ".��-��".indexOf(nextSN.getSrcWord()) != -1
								|| (nextSN.getPos() == PosTag.NUM && Utility.isAllNum(nextSN.getSrcWord()))) {
							wr2.setWord(wr2.getSrcWord() + nextSN.getSrcWord());
							i++;
						} else
							break;
					}
					isBeProcess = true;
					result.add(wr2);
				}

				if (!isBeProcess) {
					SegNode wr2 = new SegNode();
					wr2.setWord(wr.getSrcWord());
					wr2.setPos(wr.getPos());
					result.add(wr2);

				}
			}
		}

		return result;
	}

}
