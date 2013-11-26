package relationextract;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.*;
import ICTCLAS.I3S.AC.ICTCLAS50;

public class R_E {
	String [] words=null;
	public ArrayList<String[]> concepts=new ArrayList<String[]>();//用来存储概念对
	public ArrayList<String> sentences=new ArrayList<String>();		//用来存储句子
	public HashMap<String,Double> hmwords=new HashMap<String,Double>();//用来存储单词出现的句子数	数量，次数
	public HashMap<String[],Double> hmconcepts=new HashMap<String[],Double>();//用来存储概念对出现的句子数	数量，次数
	public HashMap<String[],Double> hmsupport=new HashMap<String[],Double>();//用来存储概念对的支持度
	public HashMap<String[],Double> hmconf=new HashMap<String[],Double>();//用来存储概念对的置信度
	public HashMap<String[],HashMap<String,Double>> hmrelation=new HashMap<String[],HashMap<String,Double>>();//用来存储概念对所有的关系及关系频率，~~~形如<概念对，<动词，频率>【……】>
	public ArrayList<String> relations=new ArrayList<String>();//用来存储概念对的关系 		~~~关系应该就是动词吧
	public HashMap<String[],ArrayList<String>> hmrulesentences=new HashMap<String[],ArrayList<String>>();//用来存储概念对的关系规则句集		~~~记录的是<概念对，概念对出现的句子集合【】>
	//public ArrayList<Pattern> rules=new ArrayList<Pattern>();
	//Pattern[] rules={};
	public String[] rules={"c0(.)*(((主要)?(分为))|((主要)?(包括))|(主要有)|(分别是))(.)*c1","某些c0如(.)*c1","c1是c0的一种","c1是一种c0","c0，特别是(.)*c1","将(.)*c0(.)*划分为(.)*c1"};
	public String[] rulename={"规则1","规则2","规则3","规则4","规则5","规则6"};//匹配模式名称
	public ArrayList<String> results=new ArrayList<String>();//匹配成功的结果
	
	/**
	 * 获取概念对
	 */
	public void setConcepts(){		
		try {
			String path="D:\\Documents and Settings\\Administrator\\Workspaces\\MyEclipse 8.6\\RE\\概念集.txt";
			FileReader freader;																		//~~~完了，这个RE文件夹还没有创建呢？？？
			freader = new FileReader(path);
			BufferedReader breader=new BufferedReader(freader);
			String line=null;
				while((line=breader.readLine())!=null){
					words=line.split(",");				//~~~概念集中提取
					for(int i=0;i<words.length;i++){
						hmwords.put(words[i].replace("/", "").trim(), 0.0);		//~~~replace("/", "")将单词中的/去除
						System.out.println(hmwords);							//~~~hmwords放入的是所有	去除/和左右端空格的单词			
						for(int j=i+1;j<words.length;j++){
							String[] temp=new String[2];
							temp[0]=words[i].replace("/", "").replace(" ", "").trim();		//~~~trim()是去除单词两端的空格
							temp[1]=words[j].replace("/", "").replace(" ", "").trim();		//~~~这里是去除单词中所有/和空格符
							if(temp[0].length()>0&&temp[1].length()>0){
								concepts.add(temp);								//当满足条件时，加入到概念对中。 对  即是不止一个，是两个单词组成的对
								
							}
						}
					}					
				}
				for(int i=0;i<concepts.size();i++){
					//System.out.println(concepts.get(i)[0]+concepts.get(i)[1]);
					hmconcepts.put(concepts.get(i), 0.0);		//~~~这些都是hashmap<string,double>，put是给对应key值赋予value值
					hmsupport.put(concepts.get(i), 0.0);		//~~~给这些概念对赋初值
					hmconf.put(concepts.get(i), 0.0);
					HashMap<String,Double> hm=new HashMap<String,Double>();
					hmrelation.put(concepts.get(i), hm);		//~~~这个关系更复杂，记录的是概念对和两者之间的<动词和动词出现的频率>
				}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 循环获取语料集中的文章，并调用下面的获取句子集得方法
	 */
	public void setArticles(String pathCorpus)throws FileNotFoundException, IOException {
															//~~~setArticles是对一个路径下的文件或者文件夹中的文本进行处理，递归处理，将其中的|句子|划分出来
		try {
            File file = new File(pathCorpus);
            if (!file.isDirectory()) {		//是一个文件                    
            	setSenteces(file);

            } else if (file.isDirectory()) {		//是一个文件夹
                    String[] filelist = file.list();
                    for (int i = 0; i < filelist.length; i++) {
                            File readfile = new File(pathCorpus + "\\" + filelist[i]);
                            if (!readfile.isDirectory()) {
                            	setSenteces(readfile);
                            } else if (readfile.isDirectory()) {
                            	setArticles(pathCorpus + "\\" + filelist[i]);			//~~~哎哟，递归哟
                            }
                    }

            }

    } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
    }

	}
	
	/**
	 * 获取一篇文章的句子集
	 * @throws IOException 
	 */
	public void setSenteces(File file) throws IOException{			//~~~所以说setSenteces是处理单个文本根据标点符号划分|句子|
		FileReader freader=new FileReader(file);
		BufferedReader breader=new BufferedReader(freader);
		String text=null;
		while((text=breader.readLine())!=null){
			int start=0,end=0,count=0;
			for(int i=0;i<text.length();i++)
            {
				count++;
         	   if(text.charAt(i)=='。'||text.charAt(i)=='！'||text.charAt(i)=='？'||text.charAt(i)=='，'||text.charAt(i)==';'||text.charAt(i)==':'||text.charAt(i)=='\n'||(i==text.length()-1))
         	   {
         		   end=count;
         		   sentences.add(text.substring(start, end));		//~~~分句，将标点符号作为分隔符，分开|句子|加入记录到sentences中
         		   start=count;
         	   }
            }		
			
		}
	}
	
	/**
	 * 用来分解文章记录句子，用来求概念对之间出现的动词，并计算支持度和置信度
	 */
	public void setSC(){
		for(int i=0;i<sentences.size();i++){			//~~~sentences中记录的是所有文件里被符号分割的句子
			Iterator iterword=hmwords.keySet().iterator();
			while(iterword.hasNext()){//更新该词出现的句子数
				String word=(String)iterword.next();
				if(word.length()>0&&sentences.get(i).contains(word)){
					Double num=hmwords.get(word)+1;		//~~~单词word在一个句子中出现一次，则hmwords的value值就加一，记录的单词word的出现频率
					hmwords.put(word, num);
				}
			}
			Iterator iterconcepts=hmconcepts.keySet().iterator();
			while(iterconcepts.hasNext()){				//更新概念对出现的句子数
				String[] concept=(String[])iterconcepts.next();
				if(sentences.get(i).contains(concept[0])&&sentences.get(i).contains(concept[1])){		//~~~当某一个句子包含有该概念对时
					if(hmrulesentences.containsKey(concept)){//获取概念对的用于关系规则抽取的句子集
						hmrulesentences.get(concept).add(sentences.get(i));
					}else{
						
						ArrayList<String> sentence=new ArrayList<String>();
						sentence.add(sentences.get(i));
						hmrulesentences.put(concept, sentence);		//~~~hmrulesentences记录的是概念对 和 概念对出现的句子集合
					}
					Double num=hmconcepts.get(concept)+1;		//~~~hmconcepts也记录概念对出现的频率
					hmconcepts.put(concept, num);
					//下面更新概念对间出现的动词，及符合|把字句| 和 |被子句| 	的概念对后面出现的动词
					//System.out.println(concept[0]);			
					//System.out.println(concept[1]);
					String temprelation=null;				//~~~一下要开始更新动词关系了
					String ba="(("+concept[0]+")|("+concept[1]+"))(.)*把(.)*(("+concept[0]+")|("+concept[1]+"))(.)*";	//~~~把字句
					String bei="(("+concept[0]+")|("+concept[1]+"))(.)*被(.)*(("+concept[0]+")|("+concept[1]+"))(.)*";	//~~~被字句
					//lixue
					Pattern p1=Pattern.compile(ba);
					Matcher m1=p1.matcher(sentences.get(i));			//~~~JAVA正则表达式！！！
					Pattern p2=Pattern.compile(bei);
					Matcher m2=p2.matcher(sentences.get(i));
					if(m1.find()||m2.find()){
						int c1=sentences.get(i).lastIndexOf(concept[0]);	//~~~返回概念词最后在句子中出现的位置
						int c2=sentences.get(i).lastIndexOf(concept[1]);
						
						temprelation=sentences.get(i).substring(Math.max(c1, c2));	//~~~temprelation记录的是不包含概念词的句子的最后部分
					}else{
						int begin=sentences.get(i).indexOf(concept[0])+concept[0].length();
						int end=sentences.get(i).indexOf(concept[1]);
						if(begin<end){
							temprelation=sentences.get(i).substring(begin, end);	//~~~这截得会不会有点突兀？？？？
						}
					}
					//对temprelation进行分次，运用ICTCLAS提取出词性为动词的作为关系存入
					ArrayList<String> arrayrelation=fenci(temprelation);		//~~~运用ICTCLAS分词，获得动词作为关系
					if(arrayrelation!=null){
						for(int j=0;j<arrayrelation.size();j++){
							String relation=arrayrelation.get(j);
							if(hmrelation.get(concept).containsKey(relation)){	//~~~将概念对的关系出现和次数都记录下来
								Double numr=hmrelation.get(concept).get(relation)+1;
								hmrelation.get(concept).put(relation, numr);
							}else{
								hmrelation.get(concept).put(relation, 1.0);		//~~~初始化
							}
						}
					}
					
				}
			}
		}
		Iterator itersupport=hmsupport.keySet().iterator();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4);					//~~~猜测是某种规范化表示小数？？
		df.setMinimumFractionDigits(4);
		while(itersupport.hasNext()){					//计算概念对的支持度
			String[] concept=(String[])itersupport.next();
			Double value=hmconcepts.get(concept)/sentences.size();	//支持度
			hmsupport.put(concept, value);
			System.out.println("支持度：（"+concept[0]+"，"+concept[1]+"，"+df.format(value)+"）");
		}
		Iterator iterconf=hmconf.keySet().iterator();
		while(iterconf.hasNext()){					//计算概念对的置信度
			String[] concept=(String[])iterconf.next();
			Double value=hmconcepts.get(concept)/hmwords.get(concept[0]);
			hmconf.put(concept, value);
			System.out.println("置信度：（"+concept[0]+"，"+concept[1]+"，"+df.format(value)+"）");	//~~~计算概念对的支持度和置信度
		}
	}
	
	
	
	public ArrayList<String> fenci(String temprelation){		//~~~分词函数使用了ICTCLAS50这个玩意，有待学习了解？？？
		String nativeStr=null;
		try
		{
    		ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
			//分词所需库的路径
			String argu = ".";
			//初始化
		if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
			{
				//System.out.println("Init Fail!");
				return null;
			}

		String sInput=temprelation;
		byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 0, 1);
			//System.out.println(nativeBytes.length);
			nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
			//System.out.println("The result is ：" + nativeStr);
			ArrayList<String> array=new ArrayList();
			String [] arraystr=nativeStr.split(" ");
			for(int i=0;i<arraystr.length;i++){
				if(arraystr[i].contains("/v")){
					array.add(arraystr[i].substring(0, arraystr[i].indexOf("/v")));
				}
			}
			testICTCLAS50.ICTCLAS_Exit();
			return array;
		}
		catch (Exception ex)
		{
		}
		return null;
	}
	
	/**
	 * 抽取概念对关系
	 */
	public void setRelations(){
		for(int i=0;i<concepts.size();i++){
			String[] concept=concepts.get(i);
			if(hmsupport.get(concept)>0.003&&hmconf.get(concept)>0.01){			//~~~当概念对的支持度和置信度满足一定条件后
				Iterator iterrelation=hmrelation.get(concept).keySet().iterator();
				String temprelation=null;
				Double tempf=0.0;		//~~~记录的是概念对使用最多的|关系|
				while(iterrelation.hasNext()){
					String relation=(String)iterrelation.next();		//~~~概念对关系relation
					Double f=hmrelation.get(concept).get(relation);		//~~~概念对关系relation出现的次数
					if(f>tempf){
						tempf=f;
						temprelation=relation;
					}

				}
				if(tempf>=5){
					String relation="（（"+concept[0]+"，"+concept[1]+"），"+temprelation+"）";
					relations.add(relation);			//~~~当概念对使用动词不少于五次，我们就认为他们有关系， 记录在relations[]字符串组中
				}
			}
		}
	}
	
	/**
	 * 获取概念对匹配成功的规则
	 */
	public void setrules(){
		Iterator iterrules=hmrulesentences.keySet().iterator();
		while(iterrules.hasNext()){
			String[] content=(String[])iterrules.next();			//~~~content概念对
			ArrayList<String> sent=hmrulesentences.get(content); 	//~~~sent概念对出现的句子集
			for(int i=0;i<sent.size();i++){				
				String ora=sent.get(i);								//~~~ora单独句子
				for(int j=0;j<rules.length;j++){	//循环获取模式匹配				//~~~rules中记录的是概念对之间的规则，关系。eg。c0包括c1，将c0.。。。c1什么的。。。
					String regex=rules[j].replace("c0", content[0]).replace("c1", content[1]) ;		//~~~修改规则的正则表达式				
					Pattern p=Pattern.compile(regex);
					Matcher m=p.matcher(ora);
					while(m.find()){
						String result="（（"+content[0]+"，"+content[1]+"），"+rulename[j]+"）";
						if(!results.contains(result)){
							results.add(result);		//~~~将c0，c1之间存在的关系和从属情况记录在result中
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String pathCorpus="D:\\Documents and Settings\\Administrator\\Workspaces\\MyEclipse 8.6\\RE\\示例";
		// TODO Auto-generated method stub
		R_E re=new R_E();
		re.setArticles(pathCorpus);
		re.setConcepts();
		re.setSC();
		re.setRelations();
		re.setrules();
		System.out.println(re.relations);//输出如（（预防，应急），与），则概念对为"预防，应急"，抽取的关系为"与"
		System.out.println(re.results);
		
		//输出每一个概念对间出现的所有词的频率public HashMap<String[],HashMap<String,Double>> hmrelation
		Iterator iterrelations=re.hmrelation.keySet().iterator();
		while(iterrelations.hasNext()){
			String[] concept=(String[])iterrelations.next();
			
			HashMap<String,Double> hmrela=re.hmrelation.get(concept);
				if(hmrela.size()>0&&hmrela!=null){
				System.out.println("概念对："+concept[0]+" "+concept[1]);
				Iterator iterrela=hmrela.keySet().iterator();
				while(iterrela.hasNext()){
					String rela=(String)iterrela.next();
					Double frela=hmrela.get(rela);
					System.out.println("动词："+rela+" 频率："+frela);
				}
			}
	}

	}
}
