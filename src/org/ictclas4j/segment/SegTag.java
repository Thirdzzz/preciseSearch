package org.ictclas4j.segment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.ictclas4j.bean.Atom;
import org.ictclas4j.bean.Dictionary;
import org.ictclas4j.bean.SegNode;
import org.ictclas4j.bean.Sentence;
import org.ictclas4j.utility.PosTag;
import org.ictclas4j.utility.Utility;

public class SegTag {
	private Dictionary coreDict;
	private Dictionary bigramDict;
	private PosTagger personTagger;
	private PosTagger transPersonTagger;
	private PosTagger placeTagger;
	private PosTagger lexTagger;

	private int segPathCount = 1;

	public SegTag(int segPathCount) {
		this.segPathCount = segPathCount;
		String s=Thread.currentThread().getContextClassLoader().getResource("").toString();
		int Idx = s.indexOf("file:/")+"file:/".length();
		s = s.substring(Idx);
		/*s=s.replace("/", "\\\\");
		int index=s.indexOf("C:");
		s=s.substring(index);
		index=s.indexOf(".metadata");
		s=s.substring(0, index);
		System.out.println(s);
		System.out.println("���֣�");
		*/
		//coreDict = new Dictionary(s+"data\\coreDict.dct");
		//bigramDict = new Dictionary(s+"data\\bigramDict.dct");
		//String s = "E:/MyEclipseProject/foodsafety/src";
		coreDict = new Dictionary(s+"/data\\coreDict.dct");
		bigramDict = new Dictionary(s+"/data\\bigramDict.dct");
		personTagger = new PosTagger(Utility.TAG_TYPE.TT_PERSON, s+"/data\\nr", coreDict);
		transPersonTagger = new PosTagger(Utility.TAG_TYPE.TT_TRANS_PERSON, s+"/data\\tr", coreDict);
		placeTagger = new PosTagger(Utility.TAG_TYPE.TT_TRANS_PERSON, s+"/data\\ns", coreDict);
		lexTagger = new PosTagger(Utility.TAG_TYPE.TT_NORMAL, s+"/data\\lexical", coreDict);
	}

	public String split(String src) {
		
		String finalResult = null;

		if (src != null) {
			finalResult = "";
			String midResult = null;

			SentenceSeg ss = new SentenceSeg(src);
			ArrayList<Sentence> sens = ss.getSens();
			
			for (Sentence sen : sens) {
				long start=System.currentTimeMillis();

				if (sen.isSeg()) {
					
					// ԭ�ӷִ�
					AtomSeg as = new AtomSeg(sen.getContent());
					ArrayList<Atom> atoms = as.getAtoms();

					System.out.println("[AtomSeg time]:"+(System.currentTimeMillis()-start)+"ms");
					start=System.currentTimeMillis();
					
					SegGraph segGraph = GraphGenerate.generate(atoms, coreDict);
					
					// ���ɶ���ִ�ͼ��
					SegGraph biSegGraph = GraphGenerate.biGenerate(segGraph, coreDict, bigramDict);

					System.out.println("[GraphGenerate time]:"+(System.currentTimeMillis()-start)+"ms");
					start=System.currentTimeMillis();
					
					// ��N���·��
					NShortPath nsp = new NShortPath(biSegGraph, segPathCount);
					ArrayList<ArrayList<Integer>> bipath = nsp.getPaths();

					System.out.println("[NShortPath time]:"+(System.currentTimeMillis()-start)+"ms");
					start=System.currentTimeMillis();
					
					for (ArrayList<Integer> onePath : bipath) {

						ArrayList<SegNode> segPath = getSegPath(segGraph, onePath);
						ArrayList<SegNode> firstPath = AdjustSeg.firstAdjust(segPath);

						System.out.println("[FirstSeg time]:"+(System.currentTimeMillis()-start)+"ms");
						start=System.currentTimeMillis();

						// ����δ��½�ʣ����Գ��ηִʽ�������Ż�
						SegGraph optSegGraph = new SegGraph(firstPath);
						ArrayList<SegNode> sns = clone(firstPath);
						personTagger.recognition(optSegGraph, sns);
						transPersonTagger.recognition(optSegGraph, sns);
						placeTagger.recognition(optSegGraph, sns);

						System.out.println("[Unknown Recognition time]:"+(System.currentTimeMillis()-start)+"ms");
						start=System.currentTimeMillis();

						// �����Ż���Ľ�������½������ɶ���ִ�ͼ��
						SegGraph optBiSegGraph = GraphGenerate.biGenerate(optSegGraph, coreDict, bigramDict);


						// ������ȡN�����·��
						NShortPath optNsp = new NShortPath(optBiSegGraph, segPathCount);
						ArrayList<ArrayList<Integer>> optBipath = optNsp.getPaths();


						// �����Ż���ķִʽ�������Խ�����д��Ա�Ǻ������Ż���������
						ArrayList<SegNode> adjResult = null;
						for (ArrayList<Integer> optOnePath : optBipath) {
							ArrayList<SegNode> optSegPath = getSegPath(optSegGraph, optOnePath);
							lexTagger.recognition(optSegPath);

							adjResult = AdjustSeg.finaAdjust(optSegPath, personTagger, placeTagger);
							String adjrs = outputResult(adjResult);
							System.out.println("[FinaAdjust time]:"+(System.currentTimeMillis()-start)+"ms");
							start=System.currentTimeMillis();
							if (midResult == null)
								midResult = adjrs;
							break;
						}
					}
				} else
					midResult = sen.getContent();
				finalResult += midResult;
				midResult = null;
			}

		}

		return finalResult;
	}

	private ArrayList<SegNode> clone(ArrayList<SegNode> sns) {
		ArrayList<SegNode> result = null;
		if (sns != null && sns.size() > 0) {
			result = new ArrayList<SegNode>();
			for (SegNode sn : sns)
				result.add(sn.clone());
		}
		return result;
	}

	// ���ݶ���ִ�·�����ɷִ�·��
	private ArrayList<SegNode> getSegPath(SegGraph sg, ArrayList<Integer> bipath) {
		ArrayList<SegNode> path = null;

		if (sg != null && bipath != null) {
			ArrayList<SegNode> sns = sg.getSnList();
			path = new ArrayList<SegNode>();

			for (int index : bipath)
				path.add(sns.get(index));
		}
		return path;
	}

	// ���ݷִ�·�����ɷִʽ��
	private String outputResult(ArrayList<SegNode> wrList) {
		String result = null;
		String temp=null;
		char[] pos = new char[2];
		if (wrList != null && wrList.size() > 0) {
			result = "";
			for (int i = 0; i < wrList.size(); i++) {
				SegNode sn = wrList.get(i);
				if (sn.getPos() != PosTag.SEN_BEGIN && sn.getPos() != PosTag.SEN_END) {
					int tag = Math.abs(sn.getPos());
					pos[0] = (char) (tag / 256);
					pos[1] = (char) (tag % 256);
					temp=""+pos[0];
					if(pos[1]>0)
						temp+=""+pos[1];
					result += sn.getSrcWord() + "/" + temp + " ";
				}
			}
		}

		return result;
	}

	public void setSegPathCount(int segPathCount) {
		this.segPathCount = segPathCount;
	}
	
	public static void main(String[] args) {
		SegTag segTag = new SegTag(1);	
		
		BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
		String line=null;
		try {
			while ((line=reader.readLine())!=null) {
				try { 
					String seg_res=segTag.split(line);
					System.out.println(seg_res);
				} catch (Throwable t) {
					t.printStackTrace();					
				}
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}						
	}
	

}
